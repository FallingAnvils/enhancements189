package me.fallinganvils.enhancements189.handler;

import me.fallinganvils.enhancements189.EnhancementsMod;
import me.fallinganvils.enhancements189.util.JsonLocRawOutput;
import net.minecraft.client.sound.SoundInstance;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class NoMusicInHousingHandler implements GameSpecificBehavior {
    public void playSound(SoundInstance instance, CallbackInfo info) {
        if(EnhancementsMod.CONFIG.disableMusicInHousing) {
            if(this.isInHousing) {
                if (instance.getIdentifier().getPath().contains("note")) {
                    info.cancel();
                }
            }
        }
    }

    boolean isInHousing = false;

    @Override
    public void onGameChange(JsonLocRawOutput jlro) {
        isInHousing = jlro.gameType.equals("HOUSING");
    }
}
