package br.com.essentialrx.commands.teleport;

import br.com.essentialrx.EssentialRXPlugin;
import br.com.essentialrx.cache.TpaRequestManager;
import br.com.essentialrx.commands.base.BaseCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdTpdeny extends BaseCommand {
    public CmdTpdeny(EssentialRXPlugin plugin) { super(plugin); }
    @Override public String name() { return "tpdeny"; }
    @Override public String permission() { return "essentialrx.tpdeny"; }
    @Override public boolean playerOnly() { return true; }
    @Override public String usage() { return "/tpdeny"; }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player target = (Player) sender;
        TpaRequestManager.Request r = plugin.tpa().getRequest(target);
        if (r == null) { msg().send(target, "tpa_none"); return; }
        plugin.tpa().remove(target);
        msg().send(target, "tpa_denied");
        if (r.fromPlayer() != null) {
            msg().send(r.fromPlayer(), "tpa_expired");
        }
    }
}
