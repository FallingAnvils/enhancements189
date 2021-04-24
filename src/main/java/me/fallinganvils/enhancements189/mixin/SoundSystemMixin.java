package me.fallinganvils.enhancements189.mixin;

import me.fallinganvils.enhancements189.EnhancementsMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundSystem;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardObjective;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(SoundSystem.class)
public class SoundSystemMixin {
    @Inject(at = @At("HEAD"), method = "play(Lnet/minecraft/client/sound/SoundInstance;)V", cancellable = true)
    public void play(SoundInstance instance, CallbackInfo info) {
        if(EnhancementsMod.CONFIG.disableMusicInHousing) {
            MinecraftClient client = MinecraftClient.getInstance();
            if(client.world != null) {
                Scoreboard scoreboard = client.world.getScoreboard();
                if(scoreboard != null) {
                    ScoreboardObjective obj = scoreboard.getObjectiveForSlot(1);
                    if(obj != null) {
                        String displayName = obj.getDisplayName();
                        if(displayName.contains("HOUSING")) {
                            if(instance.getIdentifier().getPath().contains("note")) {
                                info.cancel();
                            }
                        }
                    }
                }
            }
        }
    }
}
