package rip.kitpvp.essentials.commands;

import com.skygrind.api.API;
import com.skygrind.api.framework.user.User;
import com.skygrind.api.framework.user.profile.Profile;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ToggleMessagesCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String s, final String[] args)
    {
        Player player = (Player)sender;
        User user = API.getUserManager().findByUniqueId(player.getUniqueId());
        Profile profile = user.getProfile("essentials");
        if(profile.getBoolean("pmsEnabled"))
        {
            profile.set("pmsEnabled", false);
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eMessages: &cDisabled"));
            return true;
        }
        profile.set("pmsEnabled", true);
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eMessages: &aEnabled"));
        return true;
    }
}
