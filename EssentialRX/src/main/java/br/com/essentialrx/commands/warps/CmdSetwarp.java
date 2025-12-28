package br.com.essentialrx.commands.warps;

import br.com.essentialrx.EssentialRXPlugin;
import br.com.essentialrx.commands.base.BaseCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdSetwarp extends BaseCommand {
    public CmdSetwarp(EssentialRXPlugin plugin){super(plugin);}
    @Override public String name(){return "setwarp";}
    @Override public String permission(){return "essentialrx.setwarp";}
    @Override public boolean playerOnly(){return true;}
    @Override public String usage(){return "/setwarp <name>";}

    @Override
    public void execute(CommandSender sender, String[] args){
        Player p=(Player)sender;
        if(args.length!=1){msg().send(p,"invalid_usage", msg().ph("usage", usage()));return;}
        String name=args[0].toLowerCase();
        plugin.warps().setWarp(name, p.getLocation());
        msg().send(p,"warp_set", msg().ph("name", name));
    }
}