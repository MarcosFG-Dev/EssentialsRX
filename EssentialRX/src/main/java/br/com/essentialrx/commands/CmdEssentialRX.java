package br.com.essentialrx.commands;

import br.com.essentialrx.EssentialRXPlugin;
import br.com.essentialrx.commands.base.BaseCommand;
import org.bukkit.command.CommandSender;

public class CmdEssentialRX extends BaseCommand {

    public CmdEssentialRX(EssentialRXPlugin plugin) {
        super(plugin);
    }

    @Override
    public String name() {
        return "essentialrx";
    }

    @Override
    public String permission() {
        return "essentialrx.admin";
    }

    @Override
    public boolean playerOnly() {
        return false;
    }

    @Override
    public String usage() {
        return "/essentialrx [reload]";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            plugin.reloadAll();
            msg().send(sender, "reloaded");
            return;
        }
        if (args.length == 1 && args[0].equalsIgnoreCase("migratevault")) {
            if (!plugin.getVaultHook().hasEconomy()) {
                sender.sendMessage("§cVault economy not detected for migration.");
                return;
            }
            int count = plugin.getVaultHook().migrateToVault();
            sender.sendMessage("§aMigration complete! §f" + count + " §asoldier(s) balance migrated to Vault.");
            return;
        }
        sender.sendMessage("§bEssentialRX §7- §f1.0.0");
        sender.sendMessage("§7Vault: §f" + (plugin.getVaultHook() != null));
        sender.sendMessage("§7Economy: §f" + (plugin.getVaultHook() != null && plugin.getVaultHook().hasEconomy()));
        sender.sendMessage(
                "§7Permissions: §f" + (plugin.getVaultHook() != null && plugin.getVaultHook().hasPermissions()));
    }
}
