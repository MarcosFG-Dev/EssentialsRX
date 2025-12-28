package br.com.essentialrx.commands.chat;

import br.com.essentialrx.EssentialRXPlugin;
import br.com.essentialrx.commands.base.BaseCommand;
import br.com.essentialrx.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdMsg extends BaseCommand {
    public CmdMsg(EssentialRXPlugin plugin){super(plugin);}
    @Override public String name(){return "msg";}
    @Override public String permission(){return "essentialrx.msg";}
    @Override public boolean playerOnly(){return true;}
    @Override public String usage(){return "/msg <player> <message>";}

    @Override
    public void execute(CommandSender sender, String[] args){
        Player p=(Player)sender;
        if(args.length<2){msg().send(p,"invalid_usage", msg().ph("usage", usage()));return;}
        Player t=Bukkit.getPlayer(args[0]);
        if(t==null){msg().send(p,"player_not_found");return;}
        StringBuilder sb=new StringBuilder();
        for(int i=1;i<args.length;i++){sb.append(args[i]).append(" ");}
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