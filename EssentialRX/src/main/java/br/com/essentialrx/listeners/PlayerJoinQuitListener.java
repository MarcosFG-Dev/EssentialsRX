package br.com.essentialrx.listeners;

import br.com.essentialrx.EssentialRXPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerJoinQuitListener implements Listener {

    private final EssentialRXPlugin plugin;

    public PlayerJoinQuitListener(EssentialRXPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        plugin.playerData().setLastSeen(p.getUniqueId(), System.currentTimeMillis());
        boolean vanished = plugin.playerData().isVanish(p.getUniqueId());
        if (vanished) {
            for (Player other : Bukkit.getOnlinePlayers()) {
                if (!other.hasPermission("essentialrx.vanish.see")) {
                    other.hidePlayer(p);
                }
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        plugin.playerData().setLastSeen(p.getUniqueId(), System.currentTimeMillis());
    }
}
