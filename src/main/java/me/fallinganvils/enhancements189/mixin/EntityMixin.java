package me.fallinganvils.enhancements189.mixin;

import me.fallinganvils.enhancements189.EnhancementsMod;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(PlayerEntityRenderer.class)
public class EntityMixin {

    /*@Inject(method = "method_10209(Lnet/minecraft/client/network/AbstractClientPlayerEntity;DDDLjava/lang/String;FD)V", at = @At("HEAD"))
    protected void method_10209(AbstractClientPlayerEntity abstractClientPlayerEntity, double d, double e, double f, String string, float g, double h, CallbackInfo info) {
        System.out.println(abstractClientPlayerEntity.getTranslationKey());
    }

    @ModifyVariable(at = @At("HEAD"), method = "method_10209(Lnet/minecraft/client/network/AbstractClientPlayerEntity;DDDLjava/lang/String;FD)V", ordinal = 0)
    public String replacePlayerNameString(String original) {
        return EnhancementsMod.CENSOR_NAMES_HANDLER.replacePlayerNameString(original);
    }*/

    @ModifyArgs(method = "method_10209(Lnet/minecraft/client/network/AbstractClientPlayerEntity;DDDLjava/lang/String;FD)V",
    at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/LivingEntityRenderer;method_10209(Lnet/minecraft/entity/Entity;DDDLjava/lang/String;FD)V"))
    public void modifyArgsTest(Args args) {
        EnhancementsMod.CENSOR_NAMES_HANDLER.replacePlayerNameString(args);
    }



}
