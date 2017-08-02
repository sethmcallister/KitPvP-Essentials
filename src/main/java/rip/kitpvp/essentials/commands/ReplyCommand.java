package rip.kitpvp.essentials.commands;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rip.kitpvp.essentials.Main;

public class ReplyCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String s, final String[] args)
    {
        Player player = (Player) sender;
        if (args.length == 0)
        {
            player.sendMessage(ChatColor.RED + "Usage: /reply <message>");
            return true;
        }

        String message = StringUtils.join(args, " ", 0, args.length);
        Main.getInstance().getMessageManager().reply(player, message);
        return true;
    }
}
