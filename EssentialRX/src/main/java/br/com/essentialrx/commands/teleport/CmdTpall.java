package br.com.essentialrx.commands.teleport;

import br.com.essentialrx.EssentialRXPlugin;
import br.com.essentialrx.commands.base.BaseCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdTpall extends BaseCommand {
    public CmdTpall(EssentialRXPlugin plugin) { super(plugin); }
    @Override public String name() { return "tpall"; }
    @Override public String permission() { return "essentialrx.tpall"; }
    @Override public boolean playerOnly() { return true; }
    @Override public String usage() { return "/tpall"; }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        for (Player t : Bukkit.getOnlinePlayers()) {
            if (t.equals(p)) continue;
            plugin.cache().setLastLocation(t.getUniqueId(), t.getLocation());
            plugin.playerData().setLastLocation(t.getUniqueId(), t.getLocation());
            t.teleport(p.getLocation());
        }
        msg().send(p, "teleporting");
    }
}
