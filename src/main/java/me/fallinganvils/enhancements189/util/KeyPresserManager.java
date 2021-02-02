package me.fallinganvils.enhancements189.util;

import net.minecraft.client.options.KeyBinding;
import org.lwjgl.input.Keyboard;

public class KeyPresserManager {
    
    private OccasionalKeyPresser[] pressers;
    
    private boolean isAnythingRunning = false;
    
    public KeyPresserManager(int amount) {
        this.pressers = new OccasionalKeyPresser[amount];
    }
    
    public void reset(KeyBinding... newBindings) {
        for(int i = 0; i < newBindings.length; ++i) {
            pressers[i] = new OccasionalKeyPresser(newBindings[i]);
        }
    }
    
    public boolean isRunning() {
        return isAnythingRunning;
    }

    /**
     * Start pressing if told to...
     * @return false if the user is already pressing one of the keys
     */
    public boolean start() {
        for(OccasionalKeyPresser presser : pressers) {
            if(Keyboard.isKeyDown(presser.getKeybind().getCode())) return false;
        }
        this.isAnythingRunning = true;
        return true;
    }
    
    public void stop() {
        for(OccasionalKeyPresser presser : pressers) {
            if(presser != null && presser.isRunning())
                presser.cleanup();
        }
        this.isAnythingRunning = false;
    }
    
    public void setShouldRun(int index, boolean shouldRun) {
        // we can set it to "running" because it will only actually run
        // when we start ticking it (which is when isAnythingRunning is true)
        pressers[index].setRunning(shouldRun);
    }
    
    public void setDelays(int index, int waitTicks, int runTicks) {
        pressers[index].setDelays(waitTicks, runTicks);
    }
    
    public void tickAll() {
        if(isAnythingRunning) {
            for(OccasionalKeyPresser presser : pressers) {
                // the presser will check if it needs to do things
                presser.tick();
            }
        }
    }
    
    
    
}
