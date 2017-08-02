package rip.kitpvp.essentials.commands;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rip.kitpvp.essentials.Main;

public class MessageCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String s, final String[] args)
    {
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null)
        {
            sender.sendMessage(ChatColor.RED + "No player with the name or UUID '" + args[0] + "' could be found.");
            return true;
        }
        String message = StringUtils.join(args, " ", 1, args.length);
        Main.getInstance().getMessageManager().setConversations((Player) sender, target, message);
        return true;
    }
}
