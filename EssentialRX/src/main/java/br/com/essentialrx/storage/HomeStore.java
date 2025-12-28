package br.com.essentialrx.storage;

import br.com.essentialrx.EssentialRXPlugin;
import br.com.essentialrx.utils.LocationUtil;
import org.bukkit.Location;

import java.util.*;

public class HomeStore {

    private final EssentialRXPlugin plugin;
    private final YamlStorage storage;

    public HomeStore(EssentialRXPlugin plugin) {
        this.plugin = plugin;
        this.storage = new YamlStorage(plugin, "homes.yml");
    }

    public int countHomes(UUID uuid) {
        if (storage.cfg().getConfigurationSection(uuid.toString()) == null) return 0;
        return storage.cfg().getConfigurationSection(uuid.toString()).getKeys(false).size();
    }

    public Set<String> listHomes(UUID uuid) {
        if (storage.cfg().getConfigurationSection(uuid.toString()) == null) return Collections.emptySet();
        return storage.cfg().getConfigurationSection(uuid.toString()).getKeys(false);
    }

    public void setHome(UUID uuid, String name, Location loc) {
        storage.cfg().set(uuid.toString() + "." + name.toLowerCase(), LocationUtil.toString(loc));
        storage.save();
    }

    public void delHome(UUID uuid, String name) {
        storage.cfg().set(uuid.toString() + "." + name.toLowerCase(), null);
        storage.save();
    }

    public Location getHome(UUID uuid, String name) {
        String s = storage.cfg().getString(uuid.toString() + "." + name.toLowerCase(), null);
        return LocationUtil.fromString(s);
    }
}
