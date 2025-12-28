package br.com.essentialrx.commands.player;

import br.com.essentialrx.EssentialRXPlugin;
import br.com.essentialrx.commands.base.BaseCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class CmdTrash extends BaseCommand {
    public CmdTrash(EssentialRXPlugin plugin){super(plugin);}
    @Override public String name(){return "trash";}
    @Override public String permission(){return "essentialrx.trash";}
    @Override public boolean playerOnly(){return true;}
    @Override public String usage(){return "/trash";}

    @Override
    public void execute(CommandSender sender, String[] args){
        Player p=(Player)sender;
        Inventory inv = Bukkit.createInventory(null, 54, "Lixeira");
        p.openInventory(inv);
    }
}