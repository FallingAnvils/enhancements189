package me.fallinganvils.enhancements189.handler;

import com.mojang.authlib.GameProfile;
import me.fallinganvils.enhancements189.EnhancementsMod;
import me.fallinganvils.enhancements189.util.CensorFormat;
import me.fallinganvils.enhancements189.util.CensorMap;
import me.fallinganvils.enhancements189.util.ColorCode;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.Collection;
import java.util.Iterator;

public class CensorNamesHandler {

    private CensorMap censorMap = new CensorMap("Player#");

    private long player_list_update_time = 0;
    private int player_list_update_interval = 500;

    public Text receivedChatMessage(Text text) {
        if(!EnhancementsMod.CONFIG.enableNameCensor
        || EnhancementsMod.CONFIG.chatFilterEnabled) return text;

        String goodFormat = text.asFormattedString();

        updatePlayerList(false);

        boolean changedAthing = false;

        for(String playerName : censorMap.keySet()) {
            if(playerName.isEmpty()) {
                System.out.println("The player name was empty?");
                continue;
            }

            if(goodFormat.contains(playerName)) {


                // if their name has discrete color (ex. joining game messsages)
                /*goodFormat = goodFormat.replaceAll(
                    "(?:(?!.))*(\u00A7[6,7,a,b]){1}" + playerName,
                    ColorCode.LIGHTGRAY + censorMap.getCensoredName(playerName)
                );*/

                // if their name carries over the color from before (ex. chat)
                goodFormat = goodFormat.replaceAll(
                        playerName,
                        censorMap.getCensoredName(playerName)
                );

                // Make their chat white if they have no rank
                goodFormat = goodFormat.replaceAll(
                        "(?:(?!.))*"+ColorCode.LIGHTGRAY+":",
                        ColorCode.RESET + ":"
                );

                goodFormat = goodFormat.replaceAll(
                        "\u00A7r\u00A7e" + censorMap.getCensoredName(playerName) + "\u00A7r\u00A77",
                        ColorCode.YELLOW + censorMap.getCensoredName(playerName) + ColorCode.RESET
                );

                goodFormat = CensorFormat.removeRanks(goodFormat, censorMap.getCensoredName(playerName));

                changedAthing = true;
            }
        }


        if(changedAthing) {
            //System.out.println("I changed something!");

            // Remove their rank
            //goodFormat = goodFormat.replaceAll("(?:(?!.))*\u00A7.\\[(MVP|VIP)(\u00A7.)*\\+{0,2}(\u00A7.)*\\] ", ColorCode.LIGHTGRAY);

            //System.out.println("THE ACTUALLY GOOD MESSAGE: " + goodFormat);
            return new TranslatableText(goodFormat);
        }
        return text;
    }

    public void renderPlayerListHud(int width, Scoreboard scoreboard, ScoreboardObjective playerListScoreboardObjective, CallbackInfo info) {
        if(!EnhancementsMod.CONFIG.enableNameCensor) return;

        if(System.currentTimeMillis() - player_list_update_time >= player_list_update_interval) {
            updatePlayerList(true);
            player_list_update_time = System.currentTimeMillis();

        }
    }

    public void replacePlayerNameString(Args args) {
        if(!EnhancementsMod.CONFIG.enableNameCensor) return;


        AbstractClientPlayerEntity player = args.get(0);

        String username = player.getTranslationKey();

        if(!censorMap.containsKey(username)) {
            censorMap.putName(username);
        }

        args.set(4, ColorCode.LIGHTGRAY + censorMap.getCensoredName(username));
    }

    private void updatePlayerList(boolean updateTabListAlso) {
        if(!EnhancementsMod.CONFIG.enableNameCensor) return;

        Collection<PlayerListEntry> tabList = MinecraftClient.getInstance().getNetworkHandler().getPlayerList();
        Iterator<PlayerListEntry> tabListIter = tabList.iterator();
        while(tabListIter.hasNext()) {

            PlayerListEntry player = tabListIter.next();
            GameProfile profile = player.getProfile();
            String name = profile.getName();

            if(!censorMap.containsKey(name)) {
                censorMap.putName(name);
            } else if(updateTabListAlso) {
                String newName = name;
                newName = newName.replaceAll(name, censorMap.getCensoredName(name));

                if(player.getScoreboardTeam() != null) {
                    newName = player.getScoreboardTeam().decorateName(newName);
                }

                newName = CensorFormat.removeRanks(newName, censorMap.getCensoredName(name));
                player.setDisplayName(new TranslatableText(ColorCode.LIGHTGRAY + newName));

            }
        }
    }

    public CensorMap getCensorMap() {
        return this.censorMap;
    }
}
