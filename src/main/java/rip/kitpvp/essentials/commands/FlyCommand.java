package rip.kitpvp.essentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rip.kitpvp.essentials.utils.MessageUtils;

public class FlyCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String s, final String[] args)
    {
        if(!sender.hasPermission("essentials.fly"))
        {
            sender.sendMessage(ChatColor.RED + "You do not have permission for this command!");
            return true;
        }
        Player player = (Player)sender;
        if(player.isFlying())
        {
            player.setFlying(false);
            player.setAllowFlight(false);
            player.sendMessage(ChatColor.YELLOW + "Flight: " + ChatColor.RED + "Disabled");
            MessageUtils.sendStaffMessage(player, ChatColor.YELLOW + "Flight: " + ChatColor.RED + "Disabled");
            return true;
        }

        player.setAllowFlight(true);
        player.setFlying(true);
        player.sendMessage(ChatColor.YELLOW + "Flight: " + ChatColor.GREEN + "Enabled");
        MessageUtils.sendStaffMessage(player, ChatColor.YELLOW + "Flight: " + ChatColor.GREEN + "Enabled");
        return true;
    }
}
