package me.fallinganvils.enhancements189.handler;

import me.fallinganvils.enhancements189.util.KeyPresserManager;
import me.fallinganvils.enhancements189.EnhancementsMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.GameOptions;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Tickable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.regex.Pattern;

public class WalkToIronHandler implements Tickable {

    public static final double SEARCH_RANGE = 10.0D;
    private static final int DELAY_INITIAL = 10;
    private static final int DELAY_REPEAT = 10;

    private Pattern[] compiledPatterns;

    public void compilePatterns() {
        String[] patterns = EnhancementsMod.CONFIG.findGenPatterns;

        compiledPatterns = new Pattern[patterns.length];

        for(int i = 0; i < patterns.length; ++i) {
            compiledPatterns[i] = Pattern.compile(patterns[i]);
        }
    }
    
    public class PresserIndex { 
        public static final int FORWARDS = 0, BACKWARDS = 1, LEFT = 2, RIGHT = 3;
    }

    public final KeyPresserManager pressersManager = new KeyPresserManager(4);
    
    private int joinGameTick = -1;
    private int timeTryingTick = -1;
    private boolean tempDisabled = false;
    
    public void setTempDisabled(boolean disabled) {
        this.tempDisabled = disabled;
    }

    public int getJoinGameTick() {
        return joinGameTick;
    }
    
    public void resetPressers() {
        GameOptions options = MinecraftClient.getInstance().options;
        pressersManager.reset(options.keyForward, options.keyBack, options.keyLeft, options.keyRight);
    }

    @Override
    public void tick() {
        // it's efficient, it checks if it should do anything at all
        // before proceeding
        pressersManager.tickAll();

        if(joinGameTick >= 0) { // it's -1 if we aren't doing anything with it
            if(--joinGameTick == 0) {
                if(!EnhancementsMod.COMMAND_FINDGEN.execute("/findgen")) {
                    // the command failed, try again in the configured time
                    joinGameTick = EnhancementsMod.CONFIG.findGenAutoRepeatDelay;
                }
            }
        }
    }

    // called when the client receives a message from the server
    public void receivedChatMessage(Text msg) {
        if(compiledPatterns != null && EnhancementsMod.CONFIG.doAutoFindGen) {
            for(Pattern pattern : compiledPatterns) {
                if(pattern.asPredicate().test(msg.getString())) {
                    if (!tempDisabled) {
                        joinGameTick = EnhancementsMod.CONFIG.findGenAutoInitialDelay;
                    } else {
                        MinecraftClient.getInstance().player.addMessage(new TranslatableText("commands.findgen.auto_was_disabled"));
                        tempDisabled = false;
                    }
                }
            }
        }
    }

    // called when the client sends a message to the server.
    public void sentChatMessage(String msg, CallbackInfo info) {
        // not optimal at all, TODO: make this command system good
        if(msg.startsWith(EnhancementsMod.COMMAND_FINDGEN.getName())) {
            EnhancementsMod.COMMAND_FINDGEN.execute(msg);
            info.cancel();
        }
    }

    // called when the player's inventory is added to
    public void onInventorySet(int slot, ItemStack stack) {
        if(stack.getItem() == Items.IRON_INGOT) {
            if(pressersManager.isRunning()) {
                MinecraftClient.getInstance().player.addMessage(new TranslatableText("commands.findgen.stop.reason.iron_ingot_picked_up"));
                pressersManager.stop();
            }
        }
    }
}
