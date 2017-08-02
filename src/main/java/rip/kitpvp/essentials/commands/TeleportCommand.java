package rip.kitpvp.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rip.kitpvp.essentials.utils.MessageUtils;

public class TeleportCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String s, final String[] args)
    {
        if(!sender.hasPermission("essentials.teleport"))
        {
            sender.sendMessage(ChatColor.RED + "You do not have permission for this command!");
            return true;
        }
        Player player = (Player) sender;
        if (args.length == 0 || args.length > 2)
        {
            sender.sendMessage(ChatColor.RED + "Usage: /teleport <player> [player]");
            return true;
        }
        if (args.length == 1)
        {
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null || !target.isOnline())
            {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cNo player with the name or UUID of '" + args[0] + "' was found."));
                return true;
            }
            player.teleport(target);
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eYou have teleported to &a" + target.getName() + "&e."));
            MessageUtils.sendStaffMessage(player, "&7[&7&o" + sender.getName() + "]: &eYou have teleported to &a" + target.getName() + "&e.");
            return true;
        }
        Player target1 = Bukkit.getPlayer(args[0]);
        if (target1 == null || !target1.isOnline())
        {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cNo player with the name or UUID of '" + args[0] + "' was found."));
            return true;
        }
        Player target2 = Bukkit.getPlayer(args[1]);
        if (target2 == null || !target2.isOnline())
        {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cNo player with the name or UUID of '" + args[0] + "' was found."));
            return true;
        }
        target1.teleport(target2);
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eYou have teleported &a" + target1.getName() + "&e to &a" + target2.getName() + "&e."));
        MessageUtils.sendStaffMessage(player, "&7[&7&o" + sender.getName() + "]: &eYou have teleported &a" + target1.getName() + "&e to &a" + target2.getName() + "&e.");
        return true;
    }
}
