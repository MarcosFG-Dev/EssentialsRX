package br.com.essentialrx.commands.homes;

import br.com.essentialrx.EssentialRXPlugin;
import br.com.essentialrx.commands.base.BaseCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdDelhome extends BaseCommand {
    public CmdDelhome(EssentialRXPlugin plugin){super(plugin);}
    @Override public String name(){return "delhome";}
    @Override public String permission(){return "essentialrx.delhome";}
    @Override public boolean playerOnly(){return true;}
    @Override public String usage(){return "/delhome [name]";}

    @Override
    public void execute(CommandSender sender, String[] args){
        Player p=(Player)sender;
        String name=args.length>=1?args[0]:"home";
        if(plugin.homes().getHome(p.getUniqueId(),name)==null){
            msg().send(p,"home_not_found");return;
        }
        plugin.homes().delHome(p.getUniqueId(), name);
        msg().send(p,"home_deleted", msg().ph("name", name));
    }
}