package br.com.essentialrx.commands.economy;

import br.com.essentialrx.EssentialRXPlugin;
import br.com.essentialrx.commands.base.BaseCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdPay extends BaseCommand {
    public CmdPay(EssentialRXPlugin plugin) {
        super(plugin);
    }

    @Override
    public String name() {
        return "pay";
    }

    @Override
    public String permission() {
        return "essentialrx.pay";
    }

    @Override
    public boolean playerOnly() {
        return true;
    }

    @Override
    public String usage() {
        return "/pay <player> <amount>";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        if (!plugin.getConfig().getBoolean("economy.enabled", true)) {
            msg().send(p, "money_disabled");
            return;
        }
        if (args.length != 2) {
            msg().send(p, "invalid_usage", msg().ph("usage", usage()));
            return;
        }
        Player t = Bukkit.getPlayer(args[0]);
        if (t == null) {
            msg().send(p, "player_not_found");
            return;
        }
        double amount;
        try {
            amount = Double.parseDouble(args[1]);
        } catch (Exception e) {
            msg().send(p, "money_invalid");
            return;
        }
        if (amount <= 0) {
            msg().send(p, "money_invalid");
            return;
        }
        if (plugin.getVaultHook().getBalance(p) < amount) {
            msg().send(p, "money_no_funds");
            return;
        }
        plugin.getVaultHook().withdraw(p, amount);
        plugin.getVaultHook().deposit(t, amount);
        msg().send(p, "money_sent", msg().ph2("money", String.format("%.2f", amount), "player", t.getName()));
        msg().send(t, "money_received", msg().ph2("money", String.format("%.2f", amount), "player", p.getName()));
    }
}