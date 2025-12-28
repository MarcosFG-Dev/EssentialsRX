package br.com.essentialrx.commands.economy;

import br.com.essentialrx.EssentialRXPlugin;
import br.com.essentialrx.commands.base.BaseCommand;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public class CmdBaltop extends BaseCommand {
    public CmdBaltop(EssentialRXPlugin plugin) {
        super(plugin);
    }

    @Override
    public String name() {
        return "baltop";
    }

    @Override
    public String permission() {
        return "essentialrx.baltop";
    }

    @Override
    public boolean playerOnly() {
        return true;
    }

    @Override
    public String usage() {
        return "/baltop";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!plugin.getConfig().getBoolean("economy.enabled", true)) {
            msg().send(sender, "money_disabled");
            return;
        }

        sender.sendMessage("§b§lBaltop §7(Top 10)");
        List<Map.Entry<String, Double>> top = plugin.getVaultHook().getTopBalances();

        if (top.isEmpty()) {
            sender.sendMessage("§cNo data available yet. Please wait a minute.");
            return;
        }

        for (int i = 0; i < top.size(); i++) {
            Map.Entry<String, Double> entry = top.get(i);
            sender.sendMessage(
                    "§e" + (i + 1) + ". §f" + entry.getKey() + " §7- §a" + String.format("%.2f", entry.getValue()));
        }
    }
}