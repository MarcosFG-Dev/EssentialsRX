package br.com.essentialrx.commands.warps;

import br.com.essentialrx.EssentialRXPlugin;
import br.com.essentialrx.commands.base.BaseCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdDelwarp extends BaseCommand {
    public CmdDelwarp(EssentialRXPlugin plugin){super(plugin);}
    @Override public String name(){return "delwarp";}
    @Override public String permission(){return "essentialrx.delwarp";}
    @Override public boolean playerOnly(){return true;}
    @Override public String usage(){return "/delwarp <name>";}

    @Override
    public void execute(CommandSender sender, String[] args){
        Player p=(Player)sender;
        if(args.length!=1){msg().send(p,"invalid_usage", msg().ph("usage", usage()));return;}
        String name=args[0].toLowerCase();
        if(plugin.warps().getWarp(name)==null){msg().send(p,"warp_not_found");return;}
        plugin.warps().delWarp(name);
        msg().send(p,"warp_deleted", msg().ph("name", name));
    }
}