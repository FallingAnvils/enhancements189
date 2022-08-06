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
    private boolean field_1158;

    @Redirect(method = "method_959",
            at = @At(value = "FIELD", target = "Lnet/minecraft/client/font/TextRenderer;field_1158:Z"),
            // vaguely where the obfuscation happens
            slice = @Slice(
                    from = @At(value = "FIELD", opcode = Opcodes.GETFIELD, target = "Lnet/minecraft/client/font/TextRenderer;field_1158:Z"),
                    to = @At(value = "FIELD", opcode = Opcodes.GETFIELD, target = "Lnet/minecraft/client/font/TextRenderer;field_1151:Z")
            )
    )
    public boolean shouldObfuscate(TextRenderer _this) {
        if(EnhancementsMod.CONFIG.textObfuscationDisabled) return false;
        // forward the original value
        else return field_1158;
    }
}
