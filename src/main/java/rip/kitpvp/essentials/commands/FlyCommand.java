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
            player.sendMessage(ChatColor.GREEN + "You are no longer flying.");
            MessageUtils.sendStaffMessage(player, "&aYou are no longer flying.");
            return true;
        }

        player.setAllowFlight(true);
        player.setFlying(true);
        player.sendMessage(ChatColor.GREEN + "You are now flying.");
        MessageUtils.sendStaffMessage(player, "&aYou are now flying.");
        return true;
    }
}
