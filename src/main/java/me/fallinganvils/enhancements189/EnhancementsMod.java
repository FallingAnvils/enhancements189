package me.fallinganvils.enhancements189;

import me.fallinganvils.enhancements189.command.WalkToIronCommand;
import me.fallinganvils.enhancements189.config.ConfigObject;
import me.fallinganvils.enhancements189.handler.AutoGGHandler;
import me.fallinganvils.enhancements189.handler.AutoTipHandler;
import me.fallinganvils.enhancements189.handler.WalkToIronHandler;
import net.fabricmc.api.ModInitializer;

public class EnhancementsMod implements ModInitializer {
	@Override
	public void onInitialize() {
		System.out.println("Initializing config (Working directory: " + System.getProperty("user.dir") + ")");
		CONFIG = ConfigObject.setupConfig();
		System.out.println("Done initializing config");
		System.out.println("Compiling patterns");
		AUTOGG_HANDLER.compilePatterns();
		FINDGEN_HANDLER.compilePatterns();
		System.out.println("Done compiling patterns");
	}

	public static ConfigObject CONFIG;
	
	public static final WalkToIronCommand COMMAND_FINDGEN = new WalkToIronCommand();

	public static final WalkToIronHandler FINDGEN_HANDLER = new WalkToIronHandler();
	public static final AutoTipHandler AUTOTIP_HANDLER = new AutoTipHandler();
	public static final AutoGGHandler AUTOGG_HANDLER = new AutoGGHandler();
	
	public static boolean isOnHypixel = false;
}
