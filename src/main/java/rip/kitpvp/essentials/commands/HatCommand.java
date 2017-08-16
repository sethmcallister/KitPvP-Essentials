package rip.kitpvp.essentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class HatCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String s, final String[] args)
    {
        if(!sender.hasPermission("essentials.hat"))
        {
            sender.sendMessage(ChatColor.RED + "You do not have permission to execute this command.");
            return true;
        }
        Player player = (Player)sender;
        ItemStack hand = player.getItemInHand();
        if(hand == null)
        {
            sender.sendMessage(ChatColor.RED + "You are not holding anything in your hand.");
            return true;
        }
        player.getInventory().setHelmet(hand);
        player.getInventory().remove(hand);
        player.sendMessage(ChatColor.YELLOW + "You have put " + ChatColor.GREEN + hand.getType() + ChatColor.YELLOW + " on your head.");
        return true;
    }
}