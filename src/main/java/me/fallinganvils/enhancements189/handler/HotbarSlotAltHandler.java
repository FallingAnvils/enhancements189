package me.fallinganvils.enhancements189.handler;

import net.legacyfabric.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.options.KeyBinding;
import org.lwjgl.input.Keyboard;

public class HotbarSlotAltHandler {

    public KeyBinding keyBinds[];

    public void init() {
        keyBinds = new KeyBinding[9];
        for(int i = 0; i < keyBinds.length; i++) {
            keyBinds[i] = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                    "controls.hotbar.alt." + (i + 1),
                    Keyboard.KEY_NONE,
                    "controls.category.hotbar_alts"
            ));
        }
    }

    public void tick() {
        KeyBinding[] hotbarKeys = MinecraftClient.getInstance().options.keysHotbar;
        for(int i = 0; i < keyBinds.length; i++) {
            KeyBinding ourKb = keyBinds[i];
            if(ourKb.wasPressed()) {
                KeyBinding.onKeyPressed(hotbarKeys[i].getCode());
            }
        }
    }
}
