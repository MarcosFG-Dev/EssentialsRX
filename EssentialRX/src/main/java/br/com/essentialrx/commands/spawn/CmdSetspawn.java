package br.com.essentialrx.commands.spawn;

import br.com.essentialrx.EssentialRXPlugin;
import br.com.essentialrx.commands.base.BaseCommand;
import br.com.essentialrx.utils.LocationUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdSetspawn extends BaseCommand {

    public CmdSetspawn(EssentialRXPlugin plugin) { super(plugin); }

    @Override public String name() { return "setspawn"; }
    @Override public String permission() { return "essentialrx.setspawn"; }
    @Override public boolean playerOnly() { return true; }
    @Override public String usage() { return "/setspawn"; }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        plugin.getConfig().set("spawn.location", LocationUtil.toString(p.getLocation()));
        plugin.saveConfig();
        msg().send(p, "spawn_set");
    }
}
