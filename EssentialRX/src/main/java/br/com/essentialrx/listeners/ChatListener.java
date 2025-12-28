package br.com.essentialrx.listeners;

import br.com.essentialrx.EssentialRXPlugin;
import br.com.essentialrx.storage.PunishmentStore;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    private final EssentialRXPlugin plugin;

    public ChatListener(EssentialRXPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        if (!plugin.getConfig().getBoolean("moderation.mute_chat_block", true)) return;

        Player p = e.getPlayer();
        PunishmentStore.Mute mute = plugin.punishments().getMute(p.getUniqueId());
        if (mute != null && !mute.isExpired()) {
            e.setCancelled(true);
            plugin.msg().send(p, "muted");
        }
    }
}
