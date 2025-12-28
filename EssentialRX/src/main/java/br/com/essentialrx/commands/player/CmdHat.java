package br.com.essentialrx.commands.player;

import br.com.essentialrx.EssentialRXPlugin;
import br.com.essentialrx.commands.base.BaseCommand;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CmdHat extends BaseCommand {
    public CmdHat(EssentialRXPlugin plugin){super(plugin);}
    @Override public String name(){return "hat";}
    @Override public String permission(){return "essentialrx.hat";}
    @Override public boolean playerOnly(){return true;}
    @Override public String usage(){return "/hat";}

    @Override
    public void execute(CommandSender sender, String[] args){
        Player p=(Player)sender;
        ItemStack hand = p.getItemInHand();
        if(hand==null || hand.getType()== Material.AIR){
            msg().send(p,"error");return;
        }
        ItemStack old = p.getInventory().getHelmet();
        p.getInventory().setHelmet(hand);
        p.setItemInHand(old);
        p.updateInventory();
    }
}