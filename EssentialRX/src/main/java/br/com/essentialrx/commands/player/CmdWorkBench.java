package br.com.essentialrx.commands.player;

import br.com.essentialrx.EssentialRXPlugin;
import br.com.essentialrx.commands.base.BaseCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdWorkBench extends BaseCommand {
    public CmdWorkBench(EssentialRXPlugin plugin){super(plugin);}
    @Override public String name(){return "workbench";}
    @Override public String permission(){return "essentialrx.workbench";}
    @Override public boolean playerOnly(){return true;}
    @Override public String usage(){return "/workbench";}

    @Override
    public void execute(CommandSender sender, String[] args){
        Player p=(Player)sender;
        p.openWorkbench(p.getLocation(), true);
    }
}