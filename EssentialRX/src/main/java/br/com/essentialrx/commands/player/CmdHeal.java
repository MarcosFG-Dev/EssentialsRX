package br.com.essentialrx.commands.player;

import br.com.essentialrx.EssentialRXPlugin;
import br.com.essentialrx.commands.base.BaseCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdHeal extends BaseCommand {
    public CmdHeal(EssentialRXPlugin plugin){super(plugin);}
    @Override public String name(){return "heal";}
    @Override public String permission(){return "essentialrx.heal";}
    @Override public boolean playerOnly(){return true;}
    @Override public String usage(){return "/heal [player]";}

    @Override
    public void execute(CommandSender sender, String[] args){
        Player p=(Player)sender;
        Player t=p;
        if(args.length==1){
            if(!p.hasPermission("essentialrx.heal.others")){msg().send(p,"no_permission");return;}
            t=Bukkit.getPlayer(args[0]);
            if(t==null){msg().send(p,"player_not_found");return;}
        }
        t.setHealth(t.getMaxHealth());
        t.setFireTicks(0);
        msg().send(t,"heal");
        if(!t.equals(p)) msg().send(p,"teleported_to", msg().ph("player", t.getName()));
    }
}