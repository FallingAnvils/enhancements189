package me.fallinganvils.enhancements189.command;

import com.google.gson.annotations.SerializedName;
import me.fallinganvils.enhancements189.EnhancementsMod;
import me.fallinganvils.enhancements189.config.ConfigObject;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Pair;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class ConfigCommand {

    public static final String CMD_STRING = "/ecfg";

    // the String is the config key name, the field is the field in the config object it refers to
    private HashMap<String, Field> configKeys;

    public void runCommand(String msg, CallbackInfo info) {
        if(msg.startsWith(CMD_STRING)) {
            info.cancel();
            String[] args = msg.split("\\s+");

            MinecraftClient client = MinecraftClient.getInstance();

            if(args.length == 2 && args[1].equals("save")) {
                try {
                    EnhancementsMod.CONFIG.saveConfig();
                    client.player.addMessage(new TranslatableText("commands.ecfg.save_config_success"));
                } catch(IOException e) {
                    client.player.addMessage(new TranslatableText("commands.ecfg.save_config_failed"));
                    e.printStackTrace();
                }
                return;
            }

            if(args.length != 3)  {
                client.player.addMessage(new TranslatableText("commands.ecfg.wrong_arguments"));
                return;
            }

            String configKey = args[1];
            String newValue = args[2];

            if(configKeys.keySet().contains(configKey)) {
                Field field = configKeys.get(configKey);
                Class type = field.getType();

                if(type.isArray()) {
                    client.player.addMessage(new TranslatableText("commands.ecfg.arrays_not_supported"));
                    return;
                }

                System.out.println(type.getName());
                // this feels like it'd be bad code but ehh
                switch(type.getName()) {
                    case "java.lang.String": // UNTESTED, I DON'T KNOW IF STRINGS ARE THIS (and no config options use string right now so who cares)
                        break;
                    case "boolean":
                            boolean parsedBool = Boolean.parseBoolean(newValue);
                            try {
                                field.setBoolean(EnhancementsMod.CONFIG, parsedBool);
                                client.player.addMessage(new TranslatableText("commands.ecfg.config_changed", configKey, parsedBool));
                            } catch(IllegalAccessException e) {
                                e.printStackTrace(); // I know my own code, this shouldn't happen
                            }
                        break;
                    case "int":
                        try {
                            int parsedInt = Integer.parseInt(newValue);
                            field.setInt(EnhancementsMod.CONFIG, parsedInt);
                            client.player.addMessage(new TranslatableText("commands.ecfg.config_changed", configKey, parsedInt));
                        } catch(NumberFormatException e) {
                            client.player.addMessage(new TranslatableText("commands.ecfg.invalid_input"));
                            e.printStackTrace();
                        } catch(IllegalAccessException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "float":
                        try {
                            float parsedFloat = Float.parseFloat(newValue);
                            field.setFloat(EnhancementsMod.CONFIG, parsedFloat);
                            client.player.addMessage(new TranslatableText("commands.ecfg.config_changed", configKey, parsedFloat));
                        } catch(NumberFormatException e) {
                            client.player.addMessage(new TranslatableText("commands.ecfg.invalid_input"));
                            e.printStackTrace();
                        } catch(IllegalAccessException e) {
                            e.printStackTrace();
                        }
                        break;
                }

            } else {
                client.player.addMessage(new TranslatableText("commands.ecfg.config_key_invalid"));
            }

        }
    }

    public ConfigCommand(Class configClass) {
        configKeys = new HashMap<String, Field>();
        Field[] fields = configClass.getFields();
        for(Field field : fields) {
            SerializedName annotation = field.getAnnotation(SerializedName.class);
            if(annotation != null) {
                configKeys.put(annotation.value(), field);
            }
        }
    }
}
