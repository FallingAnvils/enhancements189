package me.fallinganvils.enhancements189.mixin;

import me.fallinganvils.enhancements189.EnhancementsMod;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerInventory.class)
public class PlayerInventoryMixin {
    @Inject(at = @At("HEAD"), method = "setInvStack(ILnet/minecraft/item/ItemStack;)V")
    public void onItemPickup(int slot, ItemStack stack, CallbackInfo info) {
        if(stack != null) {
            EnhancementsMod.FINDGEN_HANDLER.onInventorySet(slot, stack);
        }
    }
}
