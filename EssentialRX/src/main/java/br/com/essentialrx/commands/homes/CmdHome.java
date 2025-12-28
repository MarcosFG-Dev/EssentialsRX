package br.com.essentialrx.commands.homes;

import br.com.essentialrx.EssentialRXPlugin;
import br.com.essentialrx.commands.base.BaseCommand;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdHome extends BaseCommand {
    public CmdHome(EssentialRXPlugin plugin){super(plugin);}
    @Override public String name(){return "home";}
    @Override public String permission(){return "essentialrx.home";}
    @Override public boolean playerOnly(){return true;}
    @Override public String usage(){return "/home [name]";}

    @Override
    public void execute(CommandSender sender, String[] args){
        Player p=(Player)sender;
        String name=args.length>=1?args[0]:"home";
        Location loc=plugin.homes().getHome(p.getUniqueId(), name);
        if(loc==null){msg().send(p,"home_not_found");return;}
        plugin.cache().setLastLocation(p.getUniqueId(), p.getLocation());
        plugin.playerData().setLastLocation(p.getUniqueId(), p.getLocation());
        p.teleport(loc);
        msg().send(p,"home_teleported", msg().ph("name", name));
    }
}