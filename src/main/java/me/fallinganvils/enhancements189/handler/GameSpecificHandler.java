package me.fallinganvils.enhancements189.handler;

import com.google.gson.Gson;
import me.fallinganvils.enhancements189.EnhancementsMod;
import me.fallinganvils.enhancements189.util.JsonLocRawOutput;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class GameSpecificHandler {
    public void tick() {
        if (worldChangeDebounce > 0) --worldChangeDebounce;
        if (afterWorldChangeDelay > 0) {
            --afterWorldChangeDelay;
            if (afterWorldChangeDelay == 0) {
                runWorldChangeTasks();
            }
        }
    }


    int afterWorldChangeDelay = 0;
    int worldChangeDebounce = 0;

    public void onApparentWorldChange() {
        if (this.worldChangeDebounce > 0) return;
        this.worldChangeDebounce = 5;
        this.onWorldChange();
    }

    public void onWorldChange() {
        this.afterWorldChangeDelay = 20;
    }

    public void runWorldChangeTasks() {
        System.out.println("Running world change tasks");
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player != null) {
            this.autoLocRaw = true;
            client.player.sendChatMessage("/locraw");
        }
    }

    boolean autoLocRaw = false;
    public void receivedChatMessage(Text text, CallbackInfo info) {
        if(!autoLocRaw) return;
        String str = text.asString();
        if(str.startsWith("{") && str.endsWith("}")) {
            autoLocRaw = false;
            System.out.println("Matched json " + str);
            info.cancel();
            Gson gs = new Gson();
            JsonLocRawOutput jlro = gs.fromJson(str, JsonLocRawOutput.class);

            if(jlro.isLobby()) {
                MinecraftClient.getInstance().player.addMessage(new TranslatableText("You are in a " + jlro.gameType + " lobby."));
            } else {
                MinecraftClient.getInstance().player.addMessage(new TranslatableText("You are currently playing on map " + jlro.map));
            }

            for(GameSpecificBehavior gsb : EnhancementsMod.GAME_SPECIFIC_HANDLERS) {
                gsb.onGameChange(jlro);
            }

        }
    }
}
