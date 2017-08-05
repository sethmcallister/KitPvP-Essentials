package rip.kitpvp.essentials.commands;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import rip.kitpvp.essentials.Main;

public class SetSlotsCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String s, final String[] args)
    {
        if(!sender.hasPermission("essentials.setslots"))
        {
            sender.sendMessage(ChatColor.RED + "You do not have permission to execute this command.");
            return true;
        }
        if(args.length != 1)
        {
            sender.sendMessage(ChatColor.RED + "Usage: /setslots <slots>");
            return true;
        }
        if(!StringUtils.isNumeric(args[0]))
        {
            sender.sendMessage(ChatColor.RED + "The argument '" + args[0] + "' is not a number.");
            return true;
        }
        Integer slots = Integer.valueOf(args[0]);
        Main.getInstance().setMaxSlots(slots);
        sender.sendMessage(ChatColor.YELLOW + "You have set the max slots to " + ChatColor.GREEN + slots + ChatColor.YELLOW + ".");
        return true;
    }
}
