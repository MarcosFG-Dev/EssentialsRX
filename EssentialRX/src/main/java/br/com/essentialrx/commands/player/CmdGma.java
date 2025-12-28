package br.com.essentialrx.commands.player;

import br.com.essentialrx.EssentialRXPlugin;
import org.bukkit.command.CommandSender;

public class CmdGma extends CmdGamemode {
    public CmdGma(EssentialRXPlugin plugin){super(plugin);}
    @Override public String name(){return "gma";}
    @Override public String usage(){return "/gma [player]";}
    @Override
    public void execute(CommandSender sender, String[] args){
        String[] n = args.length==1? new String[]{"2", args[0]} : new String[]{"2"};
        super.execute(sender, n);
    }
}