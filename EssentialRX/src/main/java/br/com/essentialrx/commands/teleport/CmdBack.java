package br.com.essentialrx.commands.teleport;

import br.com.essentialrx.EssentialRXPlugin;
import br.com.essentialrx.commands.base.BaseCommand;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdBack extends BaseCommand {
    public CmdBack(EssentialRXPlugin plugin) { super(plugin); }
    @Override public String name() { return "back"; }
    @Override public String permission() { return "essentialrx.back"; }
    @Override public boolean playerOnly() { return true; }
    @Override public String usage() { return "/back"; }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        Location last = plugin.cache().getLastLocation(p.getUniqueId());
        if (last == null) last = plugin.playerData().getLastLocation(p.getUniqueId());
        if (last == null) { msg().send(p,"back_none"); return; }
        plugin.cache().setLastLocation(p.getUniqueId(), p.getLocation());
        plugin.playerData().setLastLocation(p.getUniqueId(), p.getLocation());
        p.teleport(last);
        msg().send(p,"back_teleported");
    }
}
