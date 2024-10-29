package com.br.generator;

import com.br.generator.map.MapGenerator;
import org.bukkit.plugin.java.JavaPlugin;

public class Map extends JavaPlugin {

    private static Map instance;

    @Override
    public void onLoad() {
        instance = this;
        getLogger().info("Map plugin is loading...");
    }

    @Override
    public void onEnable() {
        getLogger().info("Map plugin is enabled.");
        MapGenerator.createSchematicFolder();
    }

    @Override
    public void onDisable() {
        getLogger().info("Map plugin is disabled.");
    }

    public static Map getInstance() {
        return instance;
    }
}
