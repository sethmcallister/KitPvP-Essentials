package rip.kitpvp.essentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import rip.kitpvp.essentials.Main;

public class RulesCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String s, final String[] args)
    {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getInstance().getMessagePrefix() + "You can view our rules at, &fhttps://kitpvp.rip/rules"));
        return true;
    }
}
