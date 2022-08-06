package me.fallinganvils.enhancements189.handler;

import me.fallinganvils.enhancements189.EnhancementsMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.ClientPlayerEntity;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Tickable;

public class AutoTipHandler implements Tickable {
    
    private int tipTick = -1;
    
    @Override
    public void tick() {
        if(EnhancementsMod.CONFIG.autoTipEnabled && EnhancementsMod.isOnHypixel && --tipTick <= 0) {
            if(doTip()) tipTick = EnhancementsMod.CONFIG.tipInterval;
                else tipTick = EnhancementsMod.CONFIG.tipRetryInterval; // try again in 10 seconds
        }
    }
    
    public void onJoinHypixel() {
        tipTick = 20 * 5;
    }
    
    private boolean doTip() {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if(player != null) {
            player.addMessage(new TranslatableText("commands.autotip.tipping"));
            player.sendChatMessage("/tipall");
            return true;
        }
        else return false;
    }
}
