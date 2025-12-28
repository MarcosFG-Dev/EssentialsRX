package br.com.essentialrx.cache;

import org.bukkit.Location;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerCache {

    private final Map<UUID, Location> lastLocation = new HashMap<>();
    private final Map<UUID, UUID> lastMessage = new HashMap<>();

    public void setLastLocation(UUID uuid, Location loc) {
        if (uuid == null || loc == null) return;
        lastLocation.put(uuid, loc);
    }

    public Location getLastLocation(UUID uuid) {
        return lastLocation.get(uuid);
    }

    public void setLastMessage(UUID sender, UUID target) {
        if (sender == null || target == null) return;
        lastMessage.put(sender, target);
    }

    public UUID getLastMessageTarget(UUID sender) {
        return lastMessage.get(sender);
    }
}
