package rip.kitpvp.essentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import rip.kitpvp.essentials.Main;

public class FreezeServerCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String s, final String[] args)
    {
        if(!sender.hasPermission("essentials.freezeserver"))
        {
            sender.sendMessage(ChatColor.RED + "You do not have permission for this command.");
            return true;
        }
        if(Main.getInstance().getFreezeManager().getServerFrozen().get())
        {
            Main.getInstance().getFreezeManager().getServerFrozen().set(false);
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eYou have unfrozen the server."));
            return true;
        }
        Main.getInstance().getFreezeManager().getServerFrozen().set(true);
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eYou have frozen the server."));
        return true;
    }
}
