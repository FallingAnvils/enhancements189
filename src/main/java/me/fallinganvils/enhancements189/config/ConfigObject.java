package me.fallinganvils.enhancements189.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigObject {
    public static final String CONFIG_LOCATION = "config/enhancements189.json";
    
    @SerializedName("disable_text_obfuscation")
    public boolean textObfuscationDisabled = true;
    
    @SerializedName("enable_autogg")
    public boolean autoGGEnabled = true;
    
    @SerializedName("autogg_patterns")
    public String[] autoGGPatterns = { // https://static.sk1er.club/autogg/regex_triggers_new.json
            "^ +1st Killer - ?\\[?\\w*\\+*\\]? \\w+ - \\d+(?: Kills?)?$",
            "^ *1st (?:Place ?)?(?:-|:)? ?\\[?\\w*\\+*\\]? \\w+(?: : \\d+| - \\d+(?: Points?)?| - \\d+(?: x .)?| \\(\\w+ .{1,6}\\) - \\d+ Kills?|: \\d+:\\d+| - \\d+ (?:Zombie )?(?:Kills?|Blocks? Destroyed)| - \\[LINK\\])?$",
            "^ +Winn(?:er #1 \\(\\d+ Kills\\): \\w+ \\(\\w+\\)|er(?::| - )(?:Hiders|Seekers|Defenders|Attackers|PLAYERS?|MURDERERS?|Red|Blue|RED|BLU|\\w+)(?: Team)?|ers?: ?\\[?\\w*\\+*\\]? \\w+(?:, ?\\[?\\w*\\+*\\]? \\w+)?|ing Team ?[\\:-] (?:Animals|Hunters|Red|Green|Blue|Yellow|RED|BLU|Survivors|Vampires))$",
            "^ +Alpha Infected: \\w+ \\(\\d+ infections?\\)$",
            "^ +Murderer: \\w+ \\(\\d+ Kills?\\)$",
            "^ +You survived \\d+ rounds!$",
            "^ +(?:UHC|SkyWars|The Bridge|Sumo|Classic|OP|MegaWalls|Bow|NoDebuff|Blitz|Combo|Bow Spleef) (?:Duel|Doubles|Teams|Deathmatch|2v2v2v2|3v3v3v3)? ?- \\d+:\\d+$",
            "^ +They captured all wools!$",
            "^ +Game over!$",
            "^ +[\\d\\.]+k?/[\\d\\.]+k? \\w+$",
            "^ +(?:Criminal|Cop)s won the game!$",
            "^ +\\[?\\w*\\+*\\]? \\w+ - \\d+ Final Kills$",
            "^ +Zombies - \\d*:?\\d+:\\d+ \\(Round \\d+\\)$",
            "^ +. YOUR STATISTICS .$"
    };
    
    @SerializedName("enable_autotip")
    public boolean autoTipEnabled = true;
    
    @SerializedName("tip_interval_ticks")
    public int tipInterval = 20 * 60 * 15;
    
    @SerializedName("tip_retry_interval_ticks")
    public int tipRetryInterval = 20 * 10;
    
    @SerializedName("auto_find_gen_repeat_delay")
    public int findGenAutoRepeatDelay = 10;
    
    @SerializedName("auto_find_gen_initial_delay")
    public int findGenAutoInitialDelay = 10;
    
    @SerializedName("auto_find_gen")
    public boolean doAutoFindGen = true;

    @SerializedName("find_gen_patterns")
    public String[] findGenPatterns = {
            "^ *Protect your bed and destroy the enemy beds\\. *$",
            "^ *All generators are maxed! Your bed has three *$"
    };

    @SerializedName("title_scale_factor")
    public float titleScaleFactor = 0.5F;
    
    @SerializedName("title_how_far_down")
    public float titleHowFarDown = 1.0F;

    public static ConfigObject setupConfig() {
        ConfigObject cfg = new ConfigObject();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        File f = new File(CONFIG_LOCATION);
        f.getParentFile().mkdirs();
        if(!f.exists()) {
            try {
                f.createNewFile();
                FileWriter fw = new FileWriter(f);

                // write default
                fw.write(gson.toJson(cfg));
                fw.close();
            } catch(IOException e) {
                System.err.println("Couldn't create config file: " + e.getMessage());
            }
        }
        try {
            FileReader fr = new FileReader(f);
            cfg = gson.fromJson(fr, ConfigObject.class);
            fr.close();
        } catch(IOException e) {
            System.err.println("Couldn't read config file: " + e.getMessage());
        }

        try {
            // if missing options, add them with their defaults
            FileWriter fw = new FileWriter(f);
            fw.write(gson.toJson(cfg));
            fw.close();
        } catch(IOException e) {
            System.err.println("Couldn't write new config file: " + e.getMessage());
        }

        return cfg;
    }

    public ConfigObject() {  }

}
