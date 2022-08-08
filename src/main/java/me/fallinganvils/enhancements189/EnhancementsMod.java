package me.fallinganvils.enhancements189;

import me.fallinganvils.enhancements189.command.ConfigCommand;
import me.fallinganvils.enhancements189.command.WalkToIronCommand;
import me.fallinganvils.enhancements189.config.ConfigObject;
import me.fallinganvils.enhancements189.handler.AutoGGHandler;
import me.fallinganvils.enhancements189.handler.AutoTipHandler;
import me.fallinganvils.enhancements189.handler.ChatFilterHandler;
import me.fallinganvils.enhancements189.handler.GameSpecificBehavior;
import me.fallinganvils.enhancements189.handler.GameSpecificHandler;
import me.fallinganvils.enhancements189.handler.NoCloudsInDropperHandler;
import me.fallinganvils.enhancements189.handler.NoMusicInHousingHandler;
import me.fallinganvils.enhancements189.handler.WalkToIronHandler;
import me.fallinganvils.enhancements189.handler.ZoomHandler;
import me.fallinganvils.enhancements189.util.JsonLocRawOutput;
import net.fabricmc.api.ModInitializer;
import net.legacyfabric.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.RunArgs;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.world.ClientWorld;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class EnhancementsMod implements ModInitializer {

	@Override
	public void onInitialize() {
		System.out.println("Initializing config (Working directory: " + System.getProperty("user.dir") + ")");
		CONFIG = ConfigObject.setupConfig();
		System.out.println("Done initializing config");
		System.out.println("Compiling patterns");
		AUTOGG_HANDLER.compilePatterns();
		FINDGEN_HANDLER.compilePatterns();
		CHATFILTER_HANDLER.compilePatterns();
		System.out.println("Done compiling patterns");

		GAME_SPECIFIC_HANDLERS.add(NO_MUSIC_IN_HOUSING_HANDLER);
		GAME_SPECIFIC_HANDLERS.add(NO_CLOUDS_IN_DROPPER_HANDLER);

		ZOOM_HANDLER.init();
	}

	public static ConfigObject CONFIG;
	
	public static final WalkToIronCommand COMMAND_FINDGEN = new WalkToIronCommand();

	public static final WalkToIronHandler FINDGEN_HANDLER = new WalkToIronHandler();
	public static final AutoTipHandler AUTOTIP_HANDLER = new AutoTipHandler();
	public static final AutoGGHandler AUTOGG_HANDLER = new AutoGGHandler();
	public static final ChatFilterHandler CHATFILTER_HANDLER = new ChatFilterHandler();
	public static final GameSpecificHandler GAME_SPECIFIC_HANDLER = new GameSpecificHandler();
	public static final ArrayList<GameSpecificBehavior> GAME_SPECIFIC_HANDLERS = new ArrayList<>();
	public static final NoMusicInHousingHandler NO_MUSIC_IN_HOUSING_HANDLER = new NoMusicInHousingHandler();
	public static final NoCloudsInDropperHandler NO_CLOUDS_IN_DROPPER_HANDLER = new NoCloudsInDropperHandler();
	public static final ZoomHandler ZOOM_HANDLER = new ZoomHandler();
	public static final ConfigCommand CONFIG_COMMAND = new ConfigCommand(ConfigObject.class);

	public static boolean isOnHypixel = false;
}
