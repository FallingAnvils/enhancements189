package me.fallinganvils.enhancements189.mixin;

import com.mojang.blaze3d.platform.GlStateManager;
import me.fallinganvils.enhancements189.EnhancementsMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.Window;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(InGameHud.class)
public class InGameHudMixin {
    @Redirect(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/font/TextRenderer;draw(Ljava/lang/String;FFIZ)I"
            )
    )
    private int drawDifferently(TextRenderer instance, String text, float x, float y, int color, boolean shadow) {
        Window window = new Window(MinecraftClient.getInstance());

        float howFarDown = 1F;

        float yTranslateFactor = 0F;
        if(y == -10) { // subtitle
            yTranslateFactor = -(window.getScaledHeight() / (16.0F * EnhancementsMod.CONFIG.titleHowFarDown));
        } else if(y == 5) { // normal title
            yTranslateFactor = -(window.getScaledHeight() / (8.0F * EnhancementsMod.CONFIG.titleHowFarDown)) + 12.5F;
        }

        //float yTranslateFactor = -(window.getScaledHeight() / y == -10 ? 4.0F : 2.0F) - 25F;

        GlStateManager.translatef(0F, yTranslateFactor, 0F);
        float scaleFactor = EnhancementsMod.CONFIG.titleScaleFactor;
        GlStateManager.scalef(scaleFactor, scaleFactor, scaleFactor);

        return instance.draw(text, x, 0, color, shadow);
  

    }
}