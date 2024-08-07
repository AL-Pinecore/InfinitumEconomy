package cn.infinitumstudios.infinitumEconomy.utility;

import org.bukkit.ChatColor;

public class MessageColor {
    public static String text(String text, ChatColor color){
        return color + text + ChatColor.RESET;
    }
}
