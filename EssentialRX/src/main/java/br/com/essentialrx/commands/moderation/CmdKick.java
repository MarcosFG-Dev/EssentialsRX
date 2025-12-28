package br.com.essentialrx.commands.moderation;

import br.com.essentialrx.EssentialRXPlugin;
import br.com.essentialrx.commands.base.BaseCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdKick extends BaseCommand {
    public CmdKick(EssentialRXPlugin plugin){super(plugin);}
    @Override public String name(){return "kick";}
    @Override public String permission(){return "essentialrx.kick";}
    @Override public boolean playerOnly(){return false;}
    @Override public String usage(){return "/kick <player> [reason]";}

    @Override
    public void execute(CommandSender sender, String[] args){
        if(args.length<1){msg().send(sender,"invalid_usage", msg().ph("usage", usage()));return;}
        Player t=Bukkit.getPlayer(args[0]);
        if(t==null){msg().send(sender,"player_not_found");return;}
        String reason="Kicked.";
        if(args.length>=2){
            StringBuilder sb=new StringBuilder();
            for(int i=1;i<args.length;i++) sb.append(args[i]).append(" ");
            reason=sb.toString().trim();
        }
        t.kickPlayer("§cVocê foi expulso: §f" + reason);
        msg().send(sender,"kick_success");
    }
}