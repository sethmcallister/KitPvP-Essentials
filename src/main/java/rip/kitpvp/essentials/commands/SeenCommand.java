package rip.kitpvp.essentials.commands;

import com.skygrind.api.API;
import com.skygrind.api.framework.user.User;
import com.skygrind.api.framework.user.profile.Profile;
import com.skygrind.core.framework.user.CoreUserManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Date;

public class SeenCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String s, final String[] args)
    {
        if(!sender.hasPermission("essentials.seen"))
        {
            sender.sendMessage(ChatColor.RED + "You do not have permission to execute this command.");
            return true;
        }
        if(args.length != 1)
        {
            sender.sendMessage(ChatColor.RED + "Usage: /seen <player>");
            return true;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if(target == null)
        {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[0]);
            if(offlinePlayer == null)
            {
                sender.sendMessage(ChatColor.RED + "No player with the name or UUID '" + args[0] + "' could be found.");
                return true;
            }
            User user = ((CoreUserManager) API.getUserManager()).getUserDataDriver().findById(offlinePlayer.getUniqueId());
            Profile profile = user.getProfile("essentials");
            Date lastSeen = (Date) profile.getObject("lastSeen");
            sender.sendMessage(ChatColor.GREEN + user.getName() + ChatColor.YELLOW + " was last seen at " + ChatColor.GREEN + lastSeen.toLocaleString() + ChatColor.YELLOW + ".");
            return true;
        }
        User user = API.getUserManager().findByUniqueId(target.getUniqueId());
        Profile profile = user.getProfile("essentials");
        if(profile.getBoolean("online"))
        {
            sender.sendMessage(ChatColor.GREEN + user.getName() + ChatColor.YELLOW + " is online.");
            return true;
        }
        Date lastSeen = (Date) profile.getObject("lastSeen");
        sender.sendMessage(ChatColor.GREEN + user.getName() + ChatColor.YELLOW + " was last seen at " + ChatColor.GREEN + lastSeen.toLocaleString() + ChatColor.YELLOW + ".");
        return true;
    }
}
