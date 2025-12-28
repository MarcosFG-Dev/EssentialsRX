package br.com.essentialrx.storage;

import br.com.essentialrx.EssentialRXPlugin;
import br.com.essentialrx.utils.TimeUtil;
import org.bukkit.configuration.ConfigurationSection;

import java.util.UUID;

public class PunishmentStore {

    private final EssentialRXPlugin plugin;
    private final YamlStorage storage;

    public PunishmentStore(EssentialRXPlugin plugin) {
        this.plugin = plugin;
        this.storage = new YamlStorage(plugin, "punishments.yml");
    }

    // BAN
    public void ban(UUID uuid, String reason, long untilMillis) {
        storage.cfg().set("bans." + uuid + ".reason", reason);
        storage.cfg().set("bans." + uuid + ".until", untilMillis);
        storage.save();
    }

    public void unban(UUID uuid) {
        storage.cfg().set("bans." + uuid, null);
        storage.save();
    }

    public Ban getBan(UUID uuid) {
        ConfigurationSection cs = storage.cfg().getConfigurationSection("bans." + uuid);
        if (cs == null) return null;
        String reason = cs.getString("reason", "Banido.");
        long until = cs.getLong("until", -1L);
        return new Ban(uuid, reason, until);
    }

    // MUTE
    public void mute(UUID uuid, long untilMillis) {
        storage.cfg().set("mutes." + uuid + ".until", untilMillis);
        storage.save();
    }

    public void unmute(UUID uuid) {
        storage.cfg().set("mutes." + uuid, null);
        storage.save();
    }

    public Mute getMute(UUID uuid) {
        ConfigurationSection cs = storage.cfg().getConfigurationSection("mutes." + uuid);
        if (cs == null) return null;
        long until = cs.getLong("until", -1L);
        return new Mute(uuid, until);
    }

    public static class Ban {
        private final UUID uuid;
        private final String reason;
        private final long untilMillis; // -1 = permanent

        public Ban(UUID uuid, String reason, long untilMillis) {
            this.uuid = uuid;
            this.reason = reason;
            this.untilMillis = untilMillis;
        }

        public boolean isExpired() {
            return untilMillis != -1 && System.currentTimeMillis() > untilMillis;
        }

        public String displayTime() {
            if (untilMillis == -1) return "permanente";
            return TimeUtil.formatSince(untilMillis - (System.currentTimeMillis() - untilMillis));
        }

        public String getReason() { return reason; }
    }

    public static class Mute {
        private final UUID uuid;
        private final long untilMillis; // -1 = permanent

        public Mute(UUID uuid, long untilMillis) {
            this.uuid = uuid;
            this.untilMillis = untilMillis;
        }

        public boolean isExpired() {
            return untilMillis != -1 && System.currentTimeMillis() > untilMillis;
        }

        public boolean isPermanent() {
            return untilMillis == -1;
        }
    }
}
