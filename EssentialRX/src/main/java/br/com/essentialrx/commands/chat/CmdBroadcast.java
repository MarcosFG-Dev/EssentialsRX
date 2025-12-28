package br.com.essentialrx.commands.chat;

import br.com.essentialrx.EssentialRXPlugin;
import br.com.essentialrx.commands.base.BaseCommand;
import br.com.essentialrx.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class CmdBroadcast extends BaseCommand {
    public CmdBroadcast(EssentialRXPlugin plugin){super(plugin);}
    @Override public String name(){return "broadcast";}
    @Override public String permission(){return "essentialrx.broadcast";}
    @Override public boolean playerOnly(){return false;}
    @Override public String usage(){return "/broadcast <message>";}

    @Override
    public void execute(CommandSender sender, String[] args){
        if(args.length<1){msg().send(sender,"invalid_usage", msg().ph("usage", usage()));return;}
        StringBuilder sb=new StringBuilder();
        for(String a:args){sb.append(a).append(" ");}
        String message=CC.translate(sb.toString().trim());
        String fmt = plugin.msg().raw("broadcast_format").replace("{message}", message);
        Bukkit.broadcastMessage(fmt);
    }
}