package br.com.essentialrx.commands.moderation;

import br.com.essentialrx.EssentialRXPlugin;
import br.com.essentialrx.commands.base.BaseCommand;
import br.com.essentialrx.utils.TimeUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

public class CmdTempban extends BaseCommand {
    public CmdTempban(EssentialRXPlugin plugin){super(plugin);}
    @Override public String name(){return "tempban";}
    @Override public String permission(){return "essentialrx.tempban";}
    @Override public boolean playerOnly(){return false;}
    @Override public String usage(){return "/tempban <player> <time> [reason]";}

    @Override
    public void execute(CommandSender sender, String[] args){
        if(args.length<2){msg().send(sender,"invalid_usage", msg().ph("usage", usage()));return;}
        OfflinePlayer op = Bukkit.getOfflinePlayer(args[0]);
        long duration = TimeUtil.parseToMillis(args[1]);
        if(duration<=0){msg().send(sender,"invalid_usage", msg().ph("usage", usage()));return;}
        long until = System.currentTimeMillis() + duration;

        String reason="Tempban.";
        if(args.length>=3){
            StringBuilder sb=new StringBuilder();
            for(int i=2;i<args.length;i++) sb.append(args[i]).append(" ");
            reason=sb.toString().trim();
        }

        plugin.punishments().ban(op.getUniqueId(), reason, until);
        if(op.isOnline()){
            op.getPlayer().kickPlayer("§cVocê foi banido por §f" + TimeUtil.formatDuration(duration) + "§c: §f" + reason);
        }
        msg().send(sender,"ban_success");
    }
}