package me.fallinganvils.enhancements189.mixin;

import me.fallinganvils.enhancements189.EnhancementsMod;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardObjective;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerListHud.class)
public class PlayerListHudMixin {
    @Inject(method = "render(ILnet/minecraft/scoreboard/Scoreboard;Lnet/minecraft/scoreboard/ScoreboardObjective;)V", at = @At("HEAD"))
    public void render(int width, Scoreboard scoreboard, ScoreboardObjective playerListScoreboardObjective, CallbackInfo info) {
        EnhancementsMod.CENSOR_NAMES_HANDLER.renderPlayerListHud(width, scoreboard, playerListScoreboardObjective, info);
    }
}
