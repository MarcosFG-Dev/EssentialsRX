package br.com.essentialrx.commands.chat;

import br.com.essentialrx.EssentialRXPlugin;
import br.com.essentialrx.commands.base.BaseCommand;
import br.com.essentialrx.utils.TimeUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class CmdSeen extends BaseCommand {
    public CmdSeen(EssentialRXPlugin plugin){super(plugin);}
    @Override public String name(){return "seen";}
    @Override public String permission(){return "essentialrx.seen";}
    @Override public boolean playerOnly(){return false;}
    @Override public String usage(){return "/seen <player>";}

    @Override
    public void execute(CommandSender sender, String[] args){
        if(args.length!=1){msg().send(sender,"invalid_usage", msg().ph("usage", usage()));return;}
        Player online = Bukkit.getPlayer(args[0]);
        if(online!=null){
            msg().send(sender,"seen_online", msg().ph("player", online.getName()));
            return;
        }
        // offline: best effort by name lookup from Bukkit (1.8 has OfflinePlayer)
        UUID uuid = Bukkit.getOfflinePlayer(args[0]).getUniqueId();
        long last = plugin.playerData().getLastSeen(uuid);
        if(last<=0){
            msg().send(sender,"seen_never");
            return;
        }
        msg().send(sender,"seen_last", msg().ph2("player", args[0], "time", TimeUtil.formatSince(last)));
    }
}