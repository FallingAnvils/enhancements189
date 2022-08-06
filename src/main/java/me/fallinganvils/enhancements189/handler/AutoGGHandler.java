package me.fallinganvils.enhancements189.handler;

import me.fallinganvils.enhancements189.EnhancementsMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.ClientPlayerEntity;
import net.minecraft.text.Text;
import java.util.regex.Pattern;

public class AutoGGHandler {
    
    private Pattern[] compiledPatterns;
    
    public void compilePatterns() {
        String[] patterns = EnhancementsMod.CONFIG.autoGGPatterns;

        compiledPatterns = new Pattern[patterns.length];
        
        for(int i = 0; i < patterns.length; ++i) {
            compiledPatterns[i] = Pattern.compile(patterns[i]);
        }
    }
    
    public void receivedChatMessage(Text text) {
        if(compiledPatterns != null && EnhancementsMod.CONFIG.autoGGEnabled) {
            String unformattedStr = text.getString();
            for(Pattern pattern : compiledPatterns) {
                if(pattern.asPredicate().test(unformattedStr)) {
                    ClientPlayerEntity player = MinecraftClient.getInstance().player;
                    player.sendChatMessage("gg");
                }
            }
        }
    }
}
