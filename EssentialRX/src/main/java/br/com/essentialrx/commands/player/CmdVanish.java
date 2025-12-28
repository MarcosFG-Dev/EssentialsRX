package br.com.essentialrx.commands.player;

import br.com.essentialrx.EssentialRXPlugin;
import br.com.essentialrx.commands.base.BaseCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdVanish extends BaseCommand {
    public CmdVanish(EssentialRXPlugin plugin){super(plugin);}
    @Override public String name(){return "vanish";}
    @Override public String permission(){return "essentialrx.vanish";}
    @Override public boolean playerOnly(){return true;}
    @Override public String usage(){return "/vanish [player]";}

    @Override
    public void execute(CommandSender sender, String[] args){
        Player p=(Player)sender;
        Player t=p;
        if(args.length==1){
            if(!p.hasPermission("essentialrx.vanish.others")){msg().send(p,"no_permission");return;}
            t=Bukkit.getPlayer(args[0]);
            if(t==null){msg().send(p,"player_not_found");return;}
        }
        boolean nv = !plugin.playerData().isVanish(t.getUniqueId());
        plugin.playerData().setVanish(t.getUniqueId(), nv);

        for(Player other : Bukkit.getOnlinePlayers()){
            if(other.equals(t)) continue;
            if(nv){
                if(!other.hasPermission("essentialrx.vanish.see")) other.hidePlayer(t);
            } else {
                other.showPlayer(t);
            }
        }
        msg().send(t, nv?"vanish_on":"vanish_off");
    }
}