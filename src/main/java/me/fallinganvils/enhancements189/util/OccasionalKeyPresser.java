package me.fallinganvils.enhancements189.util;

import net.minecraft.client.options.KeyBinding;
import org.lwjgl.input.Keyboard;

public class OccasionalKeyPresser {
    
    public OccasionalKeyPresser(int waitTicks, int runTicks, KeyBinding key) {
        this(waitTicks, runTicks, key, true);
    }
    
    public OccasionalKeyPresser(KeyBinding key) {
        this(0, 0, key, false);
    }
    
    public OccasionalKeyPresser(int waitTicks, int runTicks, KeyBinding key, boolean shouldRun) {
        this.waitTicks = waitTicks;
        this.runTicks = runTicks;
        this.key = key;
        this.isRunning = shouldRun;
    }

    public KeyBinding getKeybind() {
        return this.key;
    }
    
    private int runTicks;
    private int waitTicks;
    private KeyBinding key;
    private boolean isRunning;
    private boolean isWaiting;
    
    private int currentTick;
    
    public void tick() {
        if(isRunning) {
            if(isWaiting) {
                if(++currentTick >= waitTicks) {
                    currentTick = 0;
                    isWaiting = false;
                    key.setKeyPressed(key.getCode(), true);
                }
            } else { // is being pressed
                if(++currentTick >= runTicks) {
                    currentTick = 0;
                    isWaiting = true;
                    key.setKeyPressed(key.getCode(), false);
                }
            }
        }
    }
    
    public boolean isRunning() {
        return isRunning;
    }

    /**
     * Just stops pressing the key.
     */
    public void cleanup() {
        if(!Keyboard.isKeyDown(key.getCode()))
            key.setKeyPressed(key.getCode(), false);
    }
    
    public void setRunning(boolean running) {
        this.isRunning = running;
    }
    
    public void setDelays(int waitTicks, int runTicks) {
        this.waitTicks = waitTicks;
        this.runTicks = runTicks;
    }
    
}
