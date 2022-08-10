package me.fallinganvils.enhancements189.mixin;

import io.netty.channel.ChannelHandlerContext;
import me.fallinganvils.enhancements189.EnhancementsMod;
import me.fallinganvils.enhancements189.handler.GameSpecificBehavior;
import me.fallinganvils.enhancements189.util.JsonLocRawOutput;
import net.minecraft.network.ClientConnection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientConnection.class)
public class ClientConnectionMixin {
    @Inject(at = @At("HEAD"), method = "channelInactive(Lio/netty/channel/ChannelHandlerContext;)V")
    public void onServerChannelClosed(ChannelHandlerContext context, CallbackInfo info) {
        EnhancementsMod.isOnHypixel = false;
    }
}
