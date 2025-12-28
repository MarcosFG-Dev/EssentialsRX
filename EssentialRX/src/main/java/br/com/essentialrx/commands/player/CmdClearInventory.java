package br.com.essentialrx.commands.player;

import br.com.essentialrx.EssentialRXPlugin;
import br.com.essentialrx.commands.base.BaseCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdClearInventory extends BaseCommand {
    public CmdClearInventory(EssentialRXPlugin plugin){super(plugin);}
    @Override public String name(){return "clearinventory";}
    @Override public String permission(){return "essentialrx.clearinventory";}
    @Override public boolean playerOnly(){return true;}
    @Override public String usage(){return "/clearinventory [player]";}

    @Override
    public void execute(CommandSender sender, String[] args){
        Player p=(Player)sender;
        Player t=p;
        if(args.length==1){
            if(!p.hasPermission("essentialrx.clearinventory.others")){msg().send(p,"no_permission");return;}
            t=Bukkit.getPlayer(args[0]);
            if(t==null){msg().send(p,"player_not_found");return;}
        }
        t.getInventory().clear();
        t.getInventory().setArmorContents(null);
        t.updateInventory();
    }
}