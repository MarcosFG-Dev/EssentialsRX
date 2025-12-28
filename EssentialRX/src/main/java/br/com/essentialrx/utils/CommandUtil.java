package br.com.essentialrx.utils;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandUtil {

    public static boolean isPlayer(CommandSender sender) {
        return sender instanceof Player;
    }

    public static Player player(CommandSender sender) {
        return (Player) sender;
    }
}
