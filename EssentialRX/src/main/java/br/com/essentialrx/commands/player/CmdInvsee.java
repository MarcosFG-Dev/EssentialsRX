package br.com.essentialrx.commands.player;

import br.com.essentialrx.EssentialRXPlugin;
import br.com.essentialrx.commands.base.BaseCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdInvsee extends BaseCommand {
    public CmdInvsee(EssentialRXPlugin plugin){super(plugin);}
    @Override public String name(){return "invsee";}
    @Override public String permission(){return "essentialrx.invsee";}
    @Override public boolean playerOnly(){return true;}
    @Override public String usage(){return "/invsee <player>";}

    @Override
    public void execute(CommandSender sender, String[] args){
        Player p=(Player)sender;
        if(args.length!=1){msg().send(p,"invalid_usage", msg().ph("usage", usage()));return;}
        Player t=Bukkit.getPlayer(args[0]);
        if(t==null){msg().send(p,"player_not_found");return;}
        p.openInventory(t.getInventory());
    }
}