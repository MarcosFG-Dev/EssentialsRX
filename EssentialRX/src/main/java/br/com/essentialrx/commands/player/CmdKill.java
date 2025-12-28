package br.com.essentialrx.commands.player;

import br.com.essentialrx.EssentialRXPlugin;
import br.com.essentialrx.commands.base.BaseCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdKill extends BaseCommand {
    public CmdKill(EssentialRXPlugin plugin){super(plugin);}
    @Override public String name(){return "kill";}
    @Override public String permission(){return "essentialrx.kill";}
    @Override public boolean playerOnly(){return true;}
    @Override public String usage(){return "/kill [player]";}

    @Override
    public void execute(CommandSender sender, String[] args){
        Player p=(Player)sender;
        Player t=p;
        if(args.length==1){
            if(!p.hasPermission("essentialrx.kill.others")){msg().send(p,"no_permission");return;}
            t=Bukkit.getPlayer(args[0]);
            if(t==null){msg().send(p,"player_not_found");return;}
        }
        t.setHealth(0.0);
    }
}