package br.com.essentialrx.storage;

import br.com.essentialrx.EssentialRXPlugin;
import br.com.essentialrx.utils.LocationUtil;
import org.bukkit.Location;

import java.util.Collections;
import java.util.Set;

public class WarpStore {

    private final EssentialRXPlugin plugin;
    private final YamlStorage storage;

    public WarpStore(EssentialRXPlugin plugin) {
        this.plugin = plugin;
        this.storage = new YamlStorage(plugin, "warps.yml");
    }

    public Set<String> listWarps() {
        if (storage.cfg().getConfigurationSection("warps") == null) return Collections.emptySet();
        return storage.cfg().getConfigurationSection("warps").getKeys(false);
    }

    public void setWarp(String name, Location loc) {
        storage.cfg().set("warps." + name.toLowerCase(), LocationUtil.toString(loc));
        storage.save();
    }

    public void delWarp(String name) {
        storage.cfg().set("warps." + name.toLowerCase(), null);
        storage.save();
    }

    public Location getWarp(String name) {
        String s = storage.cfg().getString("warps." + name.toLowerCase(), null);
        return LocationUtil.fromString(s);
    }
}
