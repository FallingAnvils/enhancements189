package me.fallinganvils.enhancements189.handler;

import me.fallinganvils.enhancements189.EnhancementsMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.regex.Pattern;

public class ChatFilterHandler {
    
    private Pattern[] compiledPatterns;

    public void compilePatterns() {
        String[] patterns = EnhancementsMod.CONFIG.chatFilterPatterns;

        compiledPatterns = new Pattern[patterns.length];

        for(int i = 0; i < patterns.length; ++i) {
            compiledPatterns[i] = Pattern.compile(patterns[i]);
        }
    }

    public void receivedChatMessage(Text text, CallbackInfo info) {
        if(compiledPatterns != null && EnhancementsMod.CONFIG.chatFilterEnabled) {
            String formattedStr = text.asFormattedString();
            for(Pattern pattern : compiledPatterns) {
                if(pattern.asPredicate().test(formattedStr)) {
                    info.cancel();
                    return;
                }
            }
        }
    }
}
