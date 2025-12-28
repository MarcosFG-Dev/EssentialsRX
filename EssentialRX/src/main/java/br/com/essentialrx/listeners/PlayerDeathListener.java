package br.com.essentialrx.listeners;

import br.com.essentialrx.EssentialRXPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

    private final EssentialRXPlugin plugin;

    public PlayerDeathListener(EssentialRXPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        if (!plugin.getConfig().getBoolean("teleport.back_on_death", true)) return;
        plugin.playerData().setLastLocation(e.getEntity().getUniqueId(), e.getEntity().getLocation());
        plugin.cache().setLastLocation(e.getEntity().getUniqueId(), e.getEntity().getLocation());
    }
}
