package br.com.essentialrx.commands.player;

import br.com.essentialrx.EssentialRXPlugin;
import br.com.essentialrx.commands.base.BaseCommand;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdGamemode extends BaseCommand {
    public CmdGamemode(EssentialRXPlugin plugin){super(plugin);}
    @Override public String name(){return "gm";}
    @Override public String permission(){return "essentialrx.gm";}
    @Override public boolean playerOnly(){return true;}
    @Override public String usage(){return "/gm <0|1|2|3> [player]";}

    @Override
    public void execute(CommandSender sender, String[] args){
        Player p=(Player)sender;
        if(args.length<1||args.length>2){msg().send(p,"invalid_usage", msg().ph("usage", usage()));return;}
        GameMode gm=parse(args[0]);
        if(gm==null){msg().send(p,"invalid_usage", msg().ph("usage", usage()));return;}
        Player t=p;
        if(args.length==2){
            if(!p.hasPermission("essentialrx.gm.others")){msg().send(p,"no_permission");return;}
            t=Bukkit.getPlayer(args[1]);
            if(t==null){msg().send(p,"player_not_found");return;}
        }
        t.setGameMode(gm);
        msg().send(t,"gm_changed", msg().ph("mode", gm.name()));
    }

    private GameMode parse(String s){
        if(s.equals("0")) return GameMode.SURVIVAL;
        if(s.equals("1")) return GameMode.CREATIVE;
        if(s.equals("2")) return GameMode.ADVENTURE;
        if(s.equals("3")) return GameMode.SPECTATOR;
        return null;
    }
}