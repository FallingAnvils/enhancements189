package me.fallinganvils.enhancements189.mixin;

import me.fallinganvils.enhancements189.EnhancementsMod;
import net.minecraft.client.gui.hud.ChatHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.text.Text;

@Mixin(ChatHud.class)
public class ChatHudMixin {
    @Inject(at = @At("HEAD"), method="addMessage(Lnet/minecraft/text/Text;IIZ)V", cancellable = true)
    public void onChatMessage(Text text, int messageId, int timestamp, boolean bl, CallbackInfo info) {
        EnhancementsMod.FINDGEN_HANDLER.receivedChatMessage(text);
        EnhancementsMod.AUTOGG_HANDLER.receivedChatMessage(text);
        // give the chat filter the callbackinfo so it can cancel if it wants
        EnhancementsMod.CHATFILTER_HANDLER.receivedChatMessage(text, info);
    }
}
