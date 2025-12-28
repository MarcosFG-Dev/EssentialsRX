package br.com.essentialrx.commands.homes;

import br.com.essentialrx.EssentialRXPlugin;
import br.com.essentialrx.commands.base.BaseCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdSethome extends BaseCommand {
    public CmdSethome(EssentialRXPlugin plugin){super(plugin);}
    @Override public String name(){return "sethome";}
    @Override public String permission(){return "essentialrx.sethome";}
    @Override public boolean playerOnly(){return true;}
    @Override public String usage(){return "/sethome [name]";}

    @Override
    public void execute(CommandSender sender, String[] args){
        Player p=(Player)sender;
        String name=args.length>=1?args[0]:"home";

        int max=plugin.getConfig().getInt("homes.max_default",3);
        String fmt=plugin.getConfig().getString("homes.limit_permission_format","essentialrx.homes.%d");
        for(int i=50;i>=1;i--){
            String perm=String.format(fmt,i);
            if(p.hasPermission(perm)){max=i;break;}
        }

        int current=plugin.homes().countHomes(p.getUniqueId());
        if(plugin.homes().getHome(p.getUniqueId(),name)==null && current>=max){
            msg().send(p,"home_limit", msg().ph("limit", String.valueOf(max)));
            return;
        }

        plugin.homes().setHome(p.getUniqueId(), name, p.getLocation());
        msg().send(p,"home_set", msg().ph("name", name));
    }
}