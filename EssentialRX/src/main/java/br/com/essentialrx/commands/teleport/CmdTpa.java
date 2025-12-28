package br.com.essentialrx.commands.teleport;

import br.com.essentialrx.EssentialRXPlugin;
import br.com.essentialrx.commands.base.BaseCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdTpa extends BaseCommand {
    public CmdTpa(EssentialRXPlugin plugin) { super(plugin); }
    @Override public String name() { return "tpa"; }
    @Override public String permission() { return "essentialrx.tpa"; }
    @Override public boolean playerOnly() { return true; }
    @Override public String usage() { return "/tpa <player>"; }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        if (args.length != 1) { msg().send(p,"invalid_usage", msg().ph("usage", usage())); return; }
        Player t = Bukkit.getPlayer(args[0]);
        if (t == null) { msg().send(p,"player_not_found"); return; }
        if (t.equals(p)) { msg().send(p,"error"); return; }
        plugin.tpa().sendRequest(p, t);
        msg().send(p, "tpa_sent", msg().ph("player", t.getName()));
        msg().send(t, "tpa_received", msg().ph("player", p.getName()));
    }
}
