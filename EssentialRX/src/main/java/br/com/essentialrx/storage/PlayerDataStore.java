package br.com.essentialrx.storage;

import br.com.essentialrx.EssentialRXPlugin;
import br.com.essentialrx.utils.LocationUtil;
import org.bukkit.Location;

import java.util.UUID;

public class PlayerDataStore {

    private final EssentialRXPlugin plugin;
    private final YamlStorage storage;

    public PlayerDataStore(EssentialRXPlugin plugin) {
        this.plugin = plugin;
        this.storage = new YamlStorage(plugin, "playerdata.yml");
    }

    public void setLastSeen(UUID uuid, long millis) {
        storage.cfg().set(uuid.toString() + ".lastSeen", millis);
        storage.save();
    }

    public long getLastSeen(UUID uuid) {
        return storage.cfg().getLong(uuid.toString() + ".lastSeen", -1L);
    }

    public void setLastLocation(UUID uuid, Location loc) {
        storage.cfg().set(uuid.toString() + ".lastLocation", LocationUtil.toString(loc));
        storage.save();
    }

    public Location getLastLocation(UUID uuid) {
        String s = storage.cfg().getString(uuid.toString() + ".lastLocation", null);
        return LocationUtil.fromString(s);
    }

    public void setVanish(UUID uuid, boolean v) {
        storage.cfg().set(uuid.toString() + ".vanish", v);
        storage.save();
    }

    public boolean isVanish(UUID uuid) {
        return storage.cfg().getBoolean(uuid.toString() + ".vanish", false);
    }

    public double getBalance(UUID uuid) {
        return storage.cfg().getDouble(uuid.toString() + ".balance",
                plugin.getConfig().getDouble("economy.initial_balance", 0.0));
    }

    public void setBalance(UUID uuid, double amount) {
        storage.cfg().set(uuid.toString() + ".balance", amount);
        storage.save();
    }

    public java.util.Map<UUID, Double> getAllBalances() {
        java.util.Map<UUID, Double> balances = new java.util.HashMap<>();
        for (String key : storage.cfg().getKeys(false)) {
            try {
                UUID uuid = UUID.fromString(key);
                double bal = storage.cfg().getDouble(key + ".balance", -1.0);
                if (bal != -1.0)
                    balances.put(uuid, bal);
            } catch (Exception ignored) {
            }
        }
        return balances;
    }

    public void saveAll() {
        storage.save();
    }
}
