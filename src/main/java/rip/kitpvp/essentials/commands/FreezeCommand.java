package rip.kitpvp.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rip.kitpvp.essentials.Main;
import rip.kitpvp.essentials.utils.MessageUtils;

public class FreezeCommand implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command command, final String s, final String[] args)
    {
        if(!sender.hasPermission("essentials.freeze"))
        {
            sender.sendMessage(ChatColor.RED + "You do not have permission for this command!");
            return true;
        }
        Player player = (Player) sender;
        if (args.length == 0)
        {
            player.sendMessage(ChatColor.RED + "Usage: /freeze <player>");
            return true;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if(target == null)
        {
            sender.sendMessage(ChatColor.RED + "The player with the name or UUID of '" + args[0] + "' could not be found.");
            return true;
        }
        if (!Main.getInstance().getFreezeManager().getFrozen().contains(target.getUniqueId()))
        {
            Main.getInstance().getFreezeManager().getFrozen().add(target.getUniqueId());
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eYou have frozen &a" + target.getName() + "&e."));
            target.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eYou been frozen by&a " + sender.getName() + "&e."));
            MessageUtils.sendStaffMessage(player, "&7[&7&o" + sender.getName() + "]: &eYou have frozen &a" + target.getName() + "&e.");
            return true;
        }
        Main.getInstance().getFreezeManager().getFrozen().remove(target.getUniqueId());
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eYou have unfrozen &a" + target.getName() + "&e."));
        target.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eYou been unfrozen by&a " + sender.getName() + "&e."));
        MessageUtils.sendStaffMessage(player, "&7[&7&o" + sender.getName() + "]: &eYou have unfrozen &a" + target.getName() + "&e.");
        return true;
    }
}
