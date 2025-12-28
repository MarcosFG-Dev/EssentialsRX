package br.com.essentialrx.commands.economy;

import br.com.essentialrx.EssentialRXPlugin;
import br.com.essentialrx.commands.base.BaseCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdBal extends BaseCommand {
    public CmdBal(EssentialRXPlugin plugin) {
        super(plugin);
    }

    @Override
    public String name() {
        return "bal";
    }

    @Override
    public String permission() {
        return "essentialrx.bal";
    }

    @Override
    public boolean playerOnly() {
        return true;
    }

    @Override
    public String usage() {
        return "/bal [player]";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        if (!plugin.getConfig().getBoolean("economy.enabled", true)) {
            msg().send(p, "money_disabled");
            return;
        }
        Player t = p;
        if (args.length == 1) {
            if (!p.hasPermission("essentialrx.bal.others")) {
                msg().send(p, "no_permission");
                return;
            }
            t = Bukkit.getPlayer(args[0]);
            if (t == null) {
                msg().send(p, "player_not_found");
                return;
            }
        }
        double bal = plugin.getVaultHook().getBalance(t);
        msg().send(p, "money_balance", msg().ph("money", String.format("%.2f", bal)));
    }
}