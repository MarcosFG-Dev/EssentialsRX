package br.com.essentialrx.cache;

import br.com.essentialrx.EssentialRXPlugin;
import br.com.essentialrx.utils.TimeUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TpaRequestManager {

    private final EssentialRXPlugin plugin;
    private final Map<UUID, Request> requests = new HashMap<>();

    public TpaRequestManager(EssentialRXPlugin plugin) {
        this.plugin = plugin;
    }

    public void sendRequest(Player from, Player to) {
        int expire = plugin.getConfig().getInt("teleport.tpa_expire_seconds", 60);
        requests.put(to.getUniqueId(), new Request(from.getUniqueId(), System.currentTimeMillis(), expire * 1000L));
    }

    public Request getRequest(Player target) {
        Request r = requests.get(target.getUniqueId());
        if (r == null) return null;
        if (r.isExpired()) {
            requests.remove(target.getUniqueId());
            return null;
        }
        return r;
    }

    public void remove(Player target) {
        requests.remove(target.getUniqueId());
    }

    public static class Request {
        private final UUID from;
        private final long createdAt;
        private final long duration;

        public Request(UUID from, long createdAt, long duration) {
            this.from = from;
            this.createdAt = createdAt;
            this.duration = duration;
        }

        public UUID getFrom() {
            return from;
        }

        public boolean isExpired() {
            return System.currentTimeMillis() - createdAt > duration;
        }

        public Player fromPlayer() {
            return Bukkit.getPlayer(from);
        }

        public String timeLeft() {
            long left = (createdAt + duration) - System.currentTimeMillis();
            if (left < 0) left = 0;
            return TimeUtil.formatDuration(left);
        }
    }
}
