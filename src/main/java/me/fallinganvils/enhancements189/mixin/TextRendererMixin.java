package me.fallinganvils.enhancements189.mixin;

import me.fallinganvils.enhancements189.EnhancementsMod;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.client.font.TextRenderer;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(TextRenderer.class)
public class TextRendererMixin {

    // this is the boolean that controls whether text is obfuscated or not
    @Shadow
    private boolean field_2832;

    @Redirect(method = "method_2395",
            at = @At(value = "FIELD", target = "Lnet/minecraft/client/font/TextRenderer;field_2832:Z"),
            // vaguely where the obfuscation happens
            slice = @Slice(
                    from = @At(value = "FIELD", opcode = Opcodes.GETFIELD, target = "Lnet/minecraft/client/font/TextRenderer;field_2832:Z"),
                    to = @At(value = "FIELD", opcode = Opcodes.GETFIELD, target = "Lnet/minecraft/client/font/TextRenderer;field_2825:Z")
            )
    )
    public boolean shouldObfuscate(TextRenderer _this) {
        if(EnhancementsMod.CONFIG.textObfuscationDisabled) return false;
        // forward the original value
        else return field_2832;
    }
}
