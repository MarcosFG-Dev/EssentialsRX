package br.com.essentialrx.commands.teleport;

import br.com.essentialrx.EssentialRXPlugin;
import br.com.essentialrx.commands.base.BaseCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdTphere extends BaseCommand {
    public CmdTphere(EssentialRXPlugin plugin) { super(plugin); }
    @Override public String name() { return "tphere"; }
    @Override public String permission() { return "essentialrx.tphere"; }
    @Override public boolean playerOnly() { return true; }
    @Override public String usage() { return "/tphere <player>"; }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        if (args.length != 1) { msg().send(p,"invalid_usage", msg().ph("usage", usage())); return; }
        Player t = Bukkit.getPlayer(args[0]);
        if (t == null) { msg().send(p,"player_not_found"); return; }
        plugin.cache().setLastLocation(t.getUniqueId(), t.getLocation());
        plugin.playerData().setLastLocation(t.getUniqueId(), t.getLocation());
        t.teleport(p.getLocation());
        msg().send(p, "teleported_here", msg().ph("player", t.getName()));
    }
}
