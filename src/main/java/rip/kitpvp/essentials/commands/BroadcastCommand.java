package rip.kitpvp.essentials.commands;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import rip.kitpvp.essentials.Main;

public class BroadcastCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String s, final String[] args)
    {
        if(!sender.hasPermission("essentials.broadcast"))
        {
            sender.sendMessage(ChatColor.RED + "You do not have permission for this command!");
            return true;
        }
        String message = StringUtils.join(args, " ", 0, args.length);
        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Main.getInstance().getMessagePrefix() + message));
        return true;
    }
}
