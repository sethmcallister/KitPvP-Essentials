package rip.kitpvp.essentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FeedCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String s, final String[] args)
    {
        if(!sender.hasPermission("essentials.feed"))
        {
            sender.sendMessage(ChatColor.RED + "You do not have permission to execute this command.");
            return true;
        }
        Player player = (Player)sender;
        player.setFoodLevel(20);
        player.sendMessage(ChatColor.YELLOW + "You have been fed.");
        return true;
    }
}
