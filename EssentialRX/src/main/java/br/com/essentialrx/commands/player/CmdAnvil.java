package br.com.essentialrx.commands.player;

import br.com.essentialrx.EssentialRXPlugin;
import br.com.essentialrx.commands.base.BaseCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdAnvil extends BaseCommand {
    public CmdAnvil(EssentialRXPlugin plugin) {
        super(plugin);
    }

    @Override
    public String name() {
        return "anvil";
    }

    @Override
    public String permission() {
        return "essentialrx.anvil";
    }

    @Override
    public boolean playerOnly() {
        return true;
    }

    @Override
    public String usage() {
        return "/anvil";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        p.openInventory(
                org.bukkit.Bukkit.createInventory(p, org.bukkit.event.inventory.InventoryType.ANVIL, "Bigorna"));
    }
}