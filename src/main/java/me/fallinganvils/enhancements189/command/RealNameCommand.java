package me.fallinganvils.enhancements189.command;

import me.fallinganvils.enhancements189.EnhancementsMod;
import me.fallinganvils.enhancements189.util.CensorMap;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class RealNameCommand {

    public static final String CMD_STRING = "/realname";
    public static final String CMD_STRING_ALT = "/rn";

    public void runCommand(String msg, CallbackInfo info) {
        if(msg.startsWith(CMD_STRING) || msg.startsWith(CMD_STRING_ALT)) {
            info.cancel();

            MinecraftClient client = MinecraftClient.getInstance();

            if(!EnhancementsMod.CONFIG.enableNameCensor) {
                client.player.addMessage(new TranslatableText("commands.realname.not_enabled"));
                return;
            }

            String[] args = msg.split("\\s+");

            if(args.length < 2) {
                client.player.addMessage(new TranslatableText("commands.realname.wrong_arguments"));
                return;
            }

            CensorMap cm = EnhancementsMod.CENSOR_NAMES_HANDLER.getCensorMap();
            for(int i = 1; i < args.length; i++) {
                boolean foundAnything = false;
                for(String key : cm.keySet()) {
                    String hashStr = cm.getShort(key);
                    if(hashStr.startsWith(args[i])) {
                        foundAnything = true;

                        // hacky? way of making the filter ignore the command response
                        EnhancementsMod.CONFIG.enableNameCensor = false;
                        client.player.addMessage(new TranslatableText("commands.realname.success",
                                cm.getCensoredName(key), key));
                        EnhancementsMod.CONFIG.enableNameCensor = true;
                    }
                }
                if(!foundAnything) {
                    client.player.addMessage(new TranslatableText("commands.realname.no_matches", args[i]));
                }
            }
        }
    }
}
