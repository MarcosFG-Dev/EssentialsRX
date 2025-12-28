package br.com.essentialrx.commands.spawn;

import br.com.essentialrx.EssentialRXPlugin;
import br.com.essentialrx.commands.base.BaseCommand;
import br.com.essentialrx.utils.LocationUtil;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdSpawn extends BaseCommand {

    public CmdSpawn(EssentialRXPlugin plugin) { super(plugin); }

    @Override public String name() { return "spawn"; }
    @Override public String permission() { return "essentialrx.spawn"; }
    @Override public boolean playerOnly() { return true; }
    @Override public String usage() { return "/spawn"; }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        String s = plugin.getConfig().getString("spawn.location", null);
        Location loc = LocationUtil.fromString(s);
        if (loc == null) {
            msg().send(p, "spawn_not_set");
            return;
        }
        plugin.cache().setLastLocation(p.getUniqueId(), p.getLocation());
        plugin.playerData().setLastLocation(p.getUniqueId(), p.getLocation());

        p.teleport(loc);
        msg().send(p, "spawn_teleported");
    }
}
