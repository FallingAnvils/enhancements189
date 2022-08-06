package me.fallinganvils.enhancements189.mixin;

import me.fallinganvils.enhancements189.EnhancementsMod;
import net.minecraft.entity.player.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {
    @Inject(at = @At("HEAD"), method = "sendChatMessage(Ljava/lang/String;)V", cancellable = true)
    private void interceptSentChat(String msg, CallbackInfo info) {
        EnhancementsMod.FINDGEN_HANDLER.sentChatMessage(msg, info);
        EnhancementsMod.CONFIG_COMMAND.runCommand(msg, info);
    }
}
