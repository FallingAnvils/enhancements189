package me.fallinganvils.enhancements189.mixin;

import me.fallinganvils.enhancements189.EnhancementsMod;
import net.minecraft.client.gui.hud.ChatHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.text.Text;

@Mixin(ChatHud.class)
public class ChatHudMixin {
    @Inject(at = @At("HEAD"), method = "addMessage(Lnet/minecraft/text/Text;IIZ)V", cancellable = true)
    public void onChatMessage(Text text, int messageId, int timestamp, boolean bl, CallbackInfo info) {
        EnhancementsMod.FINDGEN_HANDLER.receivedChatMessage(text);
        EnhancementsMod.AUTOGG_HANDLER.receivedChatMessage(text);
        // give certain things the callbackinfo so they can cancel if they want
        EnhancementsMod.CHATFILTER_HANDLER.receivedChatMessage(text, info);
        EnhancementsMod.GAME_SPECIFIC_HANDLER.receivedChatMessage(text, info);
    }

    @ModifyVariable(at = @At("HEAD"), method = "addMessage(Lnet/minecraft/text/Text;IIZ)V")
    public Text applyChangesToText(Text text) {
        return EnhancementsMod.CENSOR_NAMES_HANDLER.receivedChatMessage(text);
    }
}
