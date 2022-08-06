package me.fallinganvils.enhancements189.mixin;

import me.fallinganvils.enhancements189.EnhancementsMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.GameOptions;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.IntObjectStorage;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.client.options.KeyBinding;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyBinding.class)
public class KeyBindingMixin {
    @Accessor("KEY_MAP")
    public static IntObjectStorage<KeyBinding> getKeyMap() { throw new AssertionError(); }
    
    @Inject(at = @At("HEAD"), method = "onKeyPressed(I)V")
    private static void interceptKeyPressed(int keyCode, CallbackInfo info) {
        if(EnhancementsMod.FINDGEN_HANDLER.pressersManager.isRunning()) {

            MinecraftClient client = MinecraftClient.getInstance();
            GameOptions options = client.options;

            KeyBinding pressedKey = getKeyMap().get(keyCode);

            if(
                    pressedKey == options.keyBack
                    || pressedKey == options.keyForward
                    || pressedKey == options.keyLeft
                    || pressedKey == options.keyRight
            ) {
                EnhancementsMod.FINDGEN_HANDLER.pressersManager.stop();
                client.player.addMessage(new TranslatableText("commands.findgen.stop.reason.player_interaction"));
            }

        }
    }
}
