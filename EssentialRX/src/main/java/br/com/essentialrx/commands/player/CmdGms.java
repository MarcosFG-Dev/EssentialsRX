package br.com.essentialrx.commands.player;

import br.com.essentialrx.EssentialRXPlugin;
import org.bukkit.command.CommandSender;

public class CmdGms extends CmdGamemode {
    public CmdGms(EssentialRXPlugin plugin){super(plugin);}
    @Override public String name(){return "gms";}
    @Override public String usage(){return "/gms [player]";}
    @Override
    public void execute(CommandSender sender, String[] args){
        String[] n = args.length==1? new String[]{"0", args[0]} : new String[]{"0"};
        super.execute(sender, n);
    }
}