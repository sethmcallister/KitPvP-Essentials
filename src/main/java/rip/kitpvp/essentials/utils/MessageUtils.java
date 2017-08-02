package rip.kitpvp.essentials.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MessageUtils
{
    public static void sendStaffMessage(String messsage)
    {
        for (Player player : Bukkit.getOnlinePlayers())
        {
            if (player.hasPermission("kitpvp.staff"))
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', messsage));
        }
    }

    public static void sendStaffMessage(Player sender, String messsage)
    {
        for (Player player : Bukkit.getOnlinePlayers())
        {
            if (sender.equals(player))
                continue;

            if (player.hasPermission("kitpvp.staff"))
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', messsage));
        }
    }
}
