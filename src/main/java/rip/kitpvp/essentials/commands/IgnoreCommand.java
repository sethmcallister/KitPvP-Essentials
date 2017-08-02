package rip.kitpvp.essentials.commands;

import com.skygrind.api.API;
import com.skygrind.api.framework.user.User;
import com.skygrind.api.framework.user.profile.Profile;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class IgnoreCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String s, final String[] args)
    {
        Player player = (Player) sender;
        User user = API.getUserManager().findByUniqueId(player.getUniqueId());
        Player target = Bukkit.getPlayer(args[0]);

        Profile profile = user.getProfile("essentials");
        List<UUID> ignoreList = (List<UUID>) profile.getObject("ignoring");
        if (target == null)
        {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[0]);
            if (offlinePlayer == null)
            {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cNo player with the Name or UUID of '" + args[0] + "' was found."));
                return true;
            }
            if (ignoreList.contains(offlinePlayer.getUniqueId()))
            {
                ignoreList.remove(offlinePlayer.getUniqueId());
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eYou are no longer ignoring &a" + offlinePlayer.getName() + "&e."));
                profile.set("ignoring", ignoreList);
                return true;
            }
            ignoreList.add(offlinePlayer.getUniqueId());
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eYou are now ignoring &a" + offlinePlayer.getName() + "&e."));
            profile.set("ignoring", ignoreList);
            return true;
        }
        if (ignoreList.contains(target.getUniqueId()))
        {
            ignoreList.remove(target.getUniqueId());
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eYou are no longer ignoring &a" + target.getName() + "&e."));
            profile.set("ignoring", ignoreList);
            return true;
        }
        ignoreList.add(target.getUniqueId());
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eYou are now ignoring &a" + target.getName() + "&e."));
        profile.set("ignoring", ignoreList);
        return false;
    }
}
