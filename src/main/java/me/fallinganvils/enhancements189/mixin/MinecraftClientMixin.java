package me.fallinganvils.enhancements189.mixin;

import me.fallinganvils.enhancements189.EnhancementsMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.GameOptions;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Inject(at = @At("HEAD"), method = "tick()V")
    public void clientTick(CallbackInfo info) {
        EnhancementsMod.FINDGEN_HANDLER.tick();
        EnhancementsMod.AUTOTIP_HANDLER.tick();
        EnhancementsMod.GAME_SPECIFIC_HANDLER.tick();
        EnhancementsMod.ZOOM_HANDLER.tick();
        EnhancementsMod.HOLD_FOR_PERSPECTIVE_HANDLER.tick();
        EnhancementsMod.HOTBAR_SLOT_ALT_HANDLER.tick();
    }
}
