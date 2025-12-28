package br.com.essentialrx.commands.moderation;

import br.com.essentialrx.EssentialRXPlugin;
import br.com.essentialrx.commands.base.BaseCommand;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

public class CmdBan extends BaseCommand {
    public CmdBan(EssentialRXPlugin plugin){super(plugin);}
    @Override public String name(){return "ban";}
    @Override public String permission(){return "essentialrx.ban";}
    @Override public boolean playerOnly(){return false;}
    @Override public String usage(){return "/ban <player> [reason]";}

    @Override
    public void execute(CommandSender sender, String[] args){
        if(args.length<1){msg().send(sender,"invalid_usage", msg().ph("usage", usage()));return;}
        OfflinePlayer op = Bukkit.getOfflinePlayer(args[0]);
        String reason="Banido.";
        if(args.length>=2){
            StringBuilder sb=new StringBuilder();
            for(int i=1;i<args.length;i++) sb.append(args[i]).append(" ");
            reason=sb.toString().trim();
        }
        plugin.punishments().ban(op.getUniqueId(), reason, -1L);
        if(op.isOnline()){
            op.getPlayer().kickPlayer("§cVocê foi banido: §f" + reason);
        }
        msg().send(sender,"ban_success");
    }
}