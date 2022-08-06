package me.fallinganvils.enhancements189.mixin;

import net.minecraft.client.gui.screen.SettingsScreen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.VideoOptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// make the completely useless realms button disappear

// this extends Screen so we get this.buttons, it's abstract so we can do the accessor
@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin extends Screen {
    @Accessor("realmsButton") // realms button
    abstract ButtonWidget getRealmsButton();

    @Inject(method = "initWidgetsNormal(II)V", at = @At("TAIL"))
    private void addMainButtonsToTitleScreen(int i, int j, CallbackInfo info) {
        this.buttons.remove(getRealmsButton());
    }
}
