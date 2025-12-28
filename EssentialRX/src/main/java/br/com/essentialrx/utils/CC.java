package br.com.essentialrx.utils;

import org.bukkit.ChatColor;

public class CC {
    public static String translate(String s) {
        if (s == null) return "";
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
