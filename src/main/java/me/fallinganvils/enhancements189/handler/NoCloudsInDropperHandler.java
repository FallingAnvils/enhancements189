package me.fallinganvils.enhancements189.handler;

import me.fallinganvils.enhancements189.util.JsonLocRawOutput;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.GameOptions;

public class NoCloudsInDropperHandler implements GameSpecificBehavior{
    @Override
    public void onGameChange(JsonLocRawOutput jlro) {
        GameOptions options = MinecraftClient.getInstance().options;

        if(jlro.mode != null && jlro.mode.equals("DROPPER")) {
            if(oldMode.equals("DROPPER")) return;
            oldMode = "DROPPER";
            oldCloudMode = options.cloudMode;
            System.out.println("Cloud mode from " + oldCloudMode + " to 0");
            options.cloudMode = 0;
        } else if(oldCloudMode != options.cloudMode) {
            System.out.println("Cloud mode from " + options.cloudMode + " to " + oldCloudMode);
            options.cloudMode = oldCloudMode;
            oldMode = "";
        }
    }

    String oldMode = "";
    int oldCloudMode = 0;
}
