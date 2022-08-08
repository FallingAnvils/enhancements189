package me.fallinganvils.enhancements189.handler;

import me.fallinganvils.enhancements189.EnhancementsMod;
import net.legacyfabric.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.options.KeyBinding;
import org.lwjgl.input.Keyboard;

public class ZoomHandler {

    public KeyBinding keyBind;
    public void init() {
        keyBind = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "controls.zoom",
                Keyboard.KEY_C,
                "controls.category"
        ));
    }

    int defaultFov = 100;
    boolean setDefaultFovYet = false;
    boolean isZooming = false;

    public void tick() {
        GameOptions options = MinecraftClient.getInstance().options;
        if(keyBind.isPressed()) {
            if(!setDefaultFovYet) {
                defaultFov = (int)options.fov;
                setDefaultFovYet = true;
            }
            options.fov = EnhancementsMod.CONFIG.zoomFov;
            options.smoothCameraEnabled = EnhancementsMod.CONFIG.zoomSmooth;
            isZooming = true;
        } else if(isZooming) {
            options.fov = defaultFov;
            options.smoothCameraEnabled = false;
            isZooming = false;
            setDefaultFovYet = false;
        }
    }
}
