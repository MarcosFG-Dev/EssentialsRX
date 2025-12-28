package br.com.essentialrx.listeners;

import br.com.essentialrx.EssentialRXPlugin;
import br.com.essentialrx.storage.PunishmentStore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerLoginListener implements Listener {

    private final EssentialRXPlugin plugin;

    public PlayerLoginListener(EssentialRXPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent e) {
        PunishmentStore.Ban ban = plugin.punishments().getBan(e.getPlayer().getUniqueId());
        if (ban == null) return;
        if (ban.isExpired()) {
            plugin.punishments().unban(e.getPlayer().getUniqueId());
            return;
        }
        e.disallow(PlayerLoginEvent.Result.KICK_BANNED, "§cVocê está banido: §f" + ban.getReason());
    }
}
