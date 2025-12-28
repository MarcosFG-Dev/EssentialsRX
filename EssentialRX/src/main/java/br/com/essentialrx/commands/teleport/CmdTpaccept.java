package br.com.essentialrx.commands.teleport;

import br.com.essentialrx.EssentialRXPlugin;
import br.com.essentialrx.cache.TpaRequestManager;
import br.com.essentialrx.commands.base.BaseCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdTpaccept extends BaseCommand {
    public CmdTpaccept(EssentialRXPlugin plugin) { super(plugin); }
    @Override public String name() { return "tpaccept"; }
    @Override public String permission() { return "essentialrx.tpaccept"; }
    @Override public boolean playerOnly() { return true; }
    @Override public String usage() { return "/tpaccept"; }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player target = (Player) sender;
        TpaRequestManager.Request r = plugin.tpa().getRequest(target);
        if (r == null) { msg().send(target, "tpa_none"); return; }
        Player from = r.fromPlayer();
        plugin.tpa().remove(target);

        if (from == null) { msg().send(target, "player_not_found"); return; }

        plugin.cache().setLastLocation(from.getUniqueId(), from.getLocation());
        plugin.playerData().setLastLocation(from.getUniqueId(), from.getLocation());

        from.teleport(target.getLocation());
        msg().send(target, "tpa_accepted");
        msg().send(from, "teleported_to", msg().ph("player", target.getName()));
    }
}
