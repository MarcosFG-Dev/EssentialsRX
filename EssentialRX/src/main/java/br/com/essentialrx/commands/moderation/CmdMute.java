package br.com.essentialrx.commands.moderation;

import br.com.essentialrx.EssentialRXPlugin;
import br.com.essentialrx.commands.base.BaseCommand;
import br.com.essentialrx.utils.TimeUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

public class CmdMute extends BaseCommand {
    public CmdMute(EssentialRXPlugin plugin){super(plugin);}
    @Override public String name(){return "mute";}
    @Override public String permission(){return "essentialrx.mute";}
    @Override public boolean playerOnly(){return false;}
    @Override public String usage(){return "/mute <player> [time]";}

    @Override
    public void execute(CommandSender sender, String[] args){
        if(args.length<1||args.length>2){msg().send(sender,"invalid_usage", msg().ph("usage", usage()));return;}
        OfflinePlayer op = Bukkit.getOfflinePlayer(args[0]);
        long until = -1L;
        if(args.length==2){
            long d = TimeUtil.parseToMillis(args[1]);
            if(d<=0){msg().send(sender,"invalid_usage", msg().ph("usage", usage()));return;}
            until = System.currentTimeMillis() + d;
        }
        plugin.punishments().mute(op.getUniqueId(), until);
        msg().send(sender,"mute_success");
    }
}