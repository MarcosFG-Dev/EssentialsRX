package br.com.essentialrx.commands.player;

import br.com.essentialrx.EssentialRXPlugin;
import org.bukkit.command.CommandSender;

public class CmdGmc extends CmdGamemode {
    public CmdGmc(EssentialRXPlugin plugin){super(plugin);}
    @Override public String name(){return "gmc";}
    @Override public String usage(){return "/gmc [player]";}
    @Override
    public void execute(CommandSender sender, String[] args){
        String[] n = args.length==1? new String[]{"1", args[0]} : new String[]{"1"};
        super.execute(sender, n);
    }
}