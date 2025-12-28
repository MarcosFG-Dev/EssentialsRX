package br.com.essentialrx.commands.economy;

import br.com.essentialrx.EssentialRXPlugin;
import br.com.essentialrx.commands.base.BaseCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdEco extends BaseCommand {
    public CmdEco(EssentialRXPlugin plugin) {
        super(plugin);
    }

    @Override
    public String name() {
        return "eco";
    }

    @Override
    public String permission() {
        return "essentialrx.eco";
    }

    @Override
    public boolean playerOnly() {
        return false;
    }

    @Override
    public String usage() {
        return "/eco <give|take|set> <player> <amount>";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length != 3) {
            msg().send(sender, "invalid_usage", msg().ph("usage", usage()));
            return;
        }

        String action = args[0].toLowerCase();
        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            msg().send(sender, "player_not_found");
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(args[2]);
        } catch (Exception e) {
            msg().send(sender, "money_invalid");
            return;
        }

        switch (action) {
            case "give":
                plugin.getVaultHook().deposit(target, amount);
                msg().send(sender, "money_admin_give",
                        msg().ph2("player", target.getName(), "money", String.format("%.2f", amount)));
                break;
            case "take":
                plugin.getVaultHook().withdraw(target, amount);
                msg().send(sender, "money_admin_take",
                        msg().ph2("player", target.getName(), "money", String.format("%.2f", amount)));
                break;
            case "set":
                plugin.getVaultHook().setBalance(target, amount);
                msg().send(sender, "money_admin_set",
                        msg().ph2("player", target.getName(), "money", String.format("%.2f", amount)));
                break;
            default:
                msg().send(sender, "invalid_usage", msg().ph("usage", usage()));
                break;
        }
    }
}
