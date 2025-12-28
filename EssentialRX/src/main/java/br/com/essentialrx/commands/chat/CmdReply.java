package br.com.essentialrx.commands.chat;

import br.com.essentialrx.EssentialRXPlugin;
import br.com.essentialrx.commands.base.BaseCommand;
import br.com.essentialrx.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class CmdReply extends BaseCommand {
    public CmdReply(EssentialRXPlugin plugin){super(plugin);}
    @Override public String name(){return "reply";}
    @Override public String permission(){return "essentialrx.reply";}
    @Override public boolean playerOnly(){return true;}
    @Override public String usage(){return "/reply <message>";}

    @Override
    public void execute(CommandSender sender, String[] args){
        Player p=(Player)sender;
        if(args.length<1){msg().send(p,"invalid_usage", msg().ph("usage", usage()));return;}
        UUID targetId = plugin.cache().getLastMessageTarget(p.getUniqueId());
        if(targetId==null){msg().send(p,"no_last_reply");return;}
        Player t = Bukkit.getPlayer(targetId);
        if(t==null){msg().send(p,"player_not_found");return;}

        StringBuilder sb=new StringBuilder();
        for(String a:args){sb.append(a).append(" ");}
        String message=sb.toString().trim();

        plugin.cache().setLastMessage(p.getUniqueId(), t.getUniqueId());
        plugin.cache().setLastMessage(t.getUniqueId(), p.getUniqueId());

        String to = plugin.msg().raw("msg_format_to")
                .replace("{player}", t.getName())
                .replace("{message}", CC.translate(message));
        String from = plugin.msg().raw("msg_format_from")
                .replace("{player}", p.getName())
                .replace("{message}", CC.translate(message));

        p.sendMessage(to);
        t.sendMessage(from);
    }
}