package br.com.essentialrx.commands.warps;

import br.com.essentialrx.EssentialRXPlugin;
import br.com.essentialrx.commands.base.BaseCommand;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdWarp extends BaseCommand {
    public CmdWarp(EssentialRXPlugin plugin){super(plugin);}
    @Override public String name(){return "warp";}
    @Override public String permission(){return "essentialrx.warp";}
    @Override public boolean playerOnly(){return true;}
    @Override public String usage(){return "/warp <name>";}

    @Override
    public void execute(CommandSender sender, String[] args){
        Player p=(Player)sender;
        if(args.length!=1){msg().send(p,"invalid_usage", msg().ph("usage", usage()));return;}
        String name=args[0].toLowerCase();
        Location loc=plugin.warps().getWarp(name);
        if(loc==null){msg().send(p,"warp_not_found");return;}

        if(plugin.getConfig().getBoolean("warps.use_permission", true)){
            String fmt=plugin.getConfig().getString("warps.warp_permission_format","essentialrx.warp.%s");
            String perm=String.format(fmt, name);
            if(!p.hasPermission(perm) && !p.hasPermission("essentialrx.warp.*")){
                msg().send(p,"warp_no_permission");return;
            }
        }

        plugin.cache().setLastLocation(p.getUniqueId(), p.getLocation());
        plugin.playerData().setLastLocation(p.getUniqueId(), p.getLocation());
        p.teleport(loc);
        msg().send(p,"warp_teleported", msg().ph("name", name));
    }
}