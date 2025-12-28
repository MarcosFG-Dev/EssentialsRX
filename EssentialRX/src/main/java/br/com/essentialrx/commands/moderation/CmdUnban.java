package br.com.essentialrx.commands.moderation;

import br.com.essentialrx.EssentialRXPlugin;
import br.com.essentialrx.commands.base.BaseCommand;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

public class CmdUnban extends BaseCommand {
    public CmdUnban(EssentialRXPlugin plugin){super(plugin);}
    @Override public String name(){return "unban";}
    @Override public String permission(){return "essentialrx.unban";}
    @Override public boolean playerOnly(){return false;}
    @Override public String usage(){return "/unban <player>";}

    @Override
    public void execute(CommandSender sender, String[] args){
        if(args.length!=1){msg().send(sender,"invalid_usage", msg().ph("usage", usage()));return;}
        OfflinePlayer op = Bukkit.getOfflinePlayer(args[0]);
        plugin.punishments().unban(op.getUniqueId());
        msg().send(sender,"unban_success");
    }
}