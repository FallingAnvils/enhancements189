package me.fallinganvils.enhancements189.mixin;

import me.fallinganvils.enhancements189.EnhancementsMod;
import net.minecraft.client.network.ClientLoginNetworkHandler;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.packet.s2c.login.LoginSuccessS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.net.InetSocketAddress;

@Mixin(ClientLoginNetworkHandler.class)
public abstract class ClientLoginNetworkHandlerMixin {
    @Accessor("connection")
    abstract ClientConnection getConnection();
    
    @Inject(at = @At("HEAD"), method = "onLoginSuccess(Lnet/minecraft/network/packet/s2c/login/LoginSuccessS2CPacket;)V")
    public void onLoginSuccess(LoginSuccessS2CPacket packet, CallbackInfo info) {
        if(this.getConnection().getAddress() instanceof InetSocketAddress) {
            String hostname = ((InetSocketAddress)this.getConnection().getAddress()).getHostName();
            if(hostname.endsWith("hypixel.net") || hostname.endsWith("hypixel.io")) {
                EnhancementsMod.isOnHypixel = true;
                EnhancementsMod.AUTOTIP_HANDLER.onJoinHypixel();
            }
        }
    }
}
