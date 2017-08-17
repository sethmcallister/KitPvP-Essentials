package rip.kitpvp.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventorySeeCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String s, final String[] args)
    {
        if(!sender.hasPermission("essentials.invsee"))
        {
            sender.sendMessage(ChatColor.RED + "You do not have permission for this command.");
            return true;
        }
        if(args.length != 1)
        {
            sender.sendMessage(ChatColor.RED + "Usage: /invsee <player>");
            return true;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if(target == null)
        {
            sender.sendMessage(ChatColor.RED + "No player with the name or UUID '" + args[0] + "' could be found.");
            return true;
        }
        Inventory inventory = Bukkit.createInventory(null, 54, "Inventory");
        for(ItemStack itemStack : target.getInventory())
            if (itemStack != null)
                inventory.addItem(itemStack);
        return true;
    }
}
