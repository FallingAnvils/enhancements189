package me.fallinganvils.enhancements189.mixin;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.realms.RealmsScreenProxy;
import net.minecraft.realms.RealmsBridge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(RealmsBridge.class)
public class RealmsBridgeMixin {
    // get rid of a NoSuchMethodError on game startup
    @Overwrite
    public RealmsScreenProxy getNotificationScreen(Screen screen) {
        return null;
    }
}
