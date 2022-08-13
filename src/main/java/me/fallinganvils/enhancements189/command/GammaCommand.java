package me.fallinganvils.enhancements189.command;

import me.fallinganvils.enhancements189.EnhancementsMod;
import me.fallinganvils.enhancements189.util.CensorMap;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class GammaCommand {
    public static final String CMD_STRING = "/gamma";

    public void runCommand(String msg, CallbackInfo info) {
        if(msg.startsWith(CMD_STRING)) {
            info.cancel();

            MinecraftClient client = MinecraftClient.getInstance();

            String[] args = msg.split("\\s+");

            if(args.length != 2) {
                client.player.addMessage(new TranslatableText("commands.gamma.wrong_arguments"));
                return;
            }

            try {
                float gamma = Float.parseFloat(args[1]);
                client.options.gamma = gamma;
                client.options.save();
                client.player.addMessage(new TranslatableText("commands.gamma.success", gamma));
            } catch(NumberFormatException e) {
                client.player.addMessage(new TranslatableText("commands.gamma.wrong_arguments"));
                return;
            }
        }
    }
}
