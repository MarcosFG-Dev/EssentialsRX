package br.com.essentialrx.storage;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class YamlStorage {

    private final JavaPlugin plugin;
    private final File file;
    private YamlConfiguration config;

    public YamlStorage(JavaPlugin plugin, String fileName) {
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder(), fileName);
        load();
    }

    public void load() {
        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            config = YamlConfiguration.loadConfiguration(file);
        } catch (Exception e) {
            plugin.getLogger().severe("Failed to load file " + file.getName() + ": " + e.getMessage());
        }
    }

    public void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            plugin.getLogger().severe("Failed to save file " + file.getName() + ": " + e.getMessage());
        }
    }

    public YamlConfiguration cfg() {
        return config;
    }
}
