package br.com.essentialrx.commands.player;

import br.com.essentialrx.EssentialRXPlugin;
import br.com.essentialrx.commands.base.BaseCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CmdRepair extends BaseCommand {
    public CmdRepair(EssentialRXPlugin plugin){super(plugin);}
    @Override public String name(){return "repair";}
    @Override public String permission(){return "essentialrx.repair";}
    @Override public boolean playerOnly(){return true;}
    @Override public String usage(){return "/repair [all]";}

    @Override
    public void execute(CommandSender sender, String[] args){
        Player p=(Player)sender;
        if(args.length==1 && args[0].equalsIgnoreCase("all")){
            if(!p.hasPermission("essentialrx.repair.all")){msg().send(p,"no_permission");return;}
            for(ItemStack it : p.getInventory().getContents()){
                if(it!=null && it.getType().getMaxDurability()>0){
                    it.setDurability((short)0);
                }
            }
            for(ItemStack it : p.getInventory().getArmorContents()){
                if(it!=null && it.getType().getMaxDurability()>0){
                    it.setDurability((short)0);
                }
            }
            p.updateInventory();
            return;
        }
        ItemStack it = p.getItemInHand();
        if(it==null){msg().send(p,"error");return;}
        if(it.getType().getMaxDurability()<=0){msg().send(p,"error");return;}
        it.setDurability((short)0);
        p.updateInventory();
    }
}