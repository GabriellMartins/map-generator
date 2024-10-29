package com.br.generator.map;

import com.br.generator.util.schematic.Schematic;
import com.br.generator.Map;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;

import java.io.File;
import java.util.logging.Logger;

public class MapGenerator {

    private static final File SCHEMATIC_FOLDER = new File("plugins/YourPluginName/schematics");
    private static final Logger LOGGER = Bukkit.getLogger();

    public static void generateMap(String schematicName, World world, Location location, boolean noAir) {
        long startTime = System.currentTimeMillis();

        File schematicFile = new File(SCHEMATIC_FOLDER, schematicName + ".schematic");
        if (!schematicFile.exists()) {
            LOGGER.warning("Schematic file " + schematicName + " not found in " + SCHEMATIC_FOLDER.getPath());
            return;
        }

        try {
            loadChunksAroundLocation(location);
            Schematic schematic = new Schematic(schematicFile);
            schematic.paste(location, noAir);

            long endTime = System.currentTimeMillis();
            LOGGER.info("Map generated in " + (endTime - startTime) + " ms");

        } catch (Exception e) {
            LOGGER.severe("Failed to load schematic: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void loadChunksAroundLocation(Location location) {
        World world = location.getWorld();
        int chunkRadius = 2;
        int chunkX = location.getChunk().getX();
        int chunkZ = location.getChunk().getZ();

        for (int x = -chunkRadius; x <= chunkRadius; x++) {
            for (int z = -chunkRadius; z <= chunkRadius; z++) {
                Chunk chunk = world.getChunkAt(chunkX + x, chunkZ + z);
                if (!chunk.isLoaded()) {
                    chunk.load(true);
                }
            }
        }
    }

    public static void createSchematicFolder() {
        if (!SCHEMATIC_FOLDER.exists()) {
            SCHEMATIC_FOLDER.mkdirs();
            LOGGER.info("Schematic folder created at: " + SCHEMATIC_FOLDER.getPath());
        }
    }
}
