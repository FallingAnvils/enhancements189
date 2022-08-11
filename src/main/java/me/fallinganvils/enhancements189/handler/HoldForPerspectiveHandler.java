package me.fallinganvils.enhancements189.handler;

import me.fallinganvils.enhancements189.EnhancementsMod;
import net.legacyfabric.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.options.KeyBinding;
import org.lwjgl.input.Keyboard;

public class HoldForPerspectiveHandler {

    public KeyBinding keyBind;
    public void init() {
        keyBind = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "controls.hold_for_perspective",
                Keyboard.KEY_R,
                "controls.category"
        ));
    }

    int defaultPerspective = 0;
    boolean setDefaultPerspectiveYet = false;
    boolean isHolding = false;

    public void tick() {
        GameOptions options = MinecraftClient.getInstance().options;
        if(keyBind.isPressed()) {
            if(!setDefaultPerspectiveYet) {
                defaultPerspective = options.perspective;
                setDefaultPerspectiveYet = true;
            }
            options.perspective = 2;
            isHolding = true;
        } else if(isHolding) {
            options.perspective = defaultPerspective;
            isHolding = false;
            setDefaultPerspectiveYet = false;
        }
    }
}
