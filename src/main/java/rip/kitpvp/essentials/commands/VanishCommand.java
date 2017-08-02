package rip.kitpvp.essentials.commands;

import com.skygrind.api.API;
import com.skygrind.api.framework.user.User;
import com.skygrind.api.framework.user.profile.Profile;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VanishCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String s, final String[] args)
    {
        if(!sender.hasPermission("essentials.vanish"))
        {
            sender.sendMessage(ChatColor.RED + "You do not have permission for this command!");
            return true;
        }
        Player player = (Player) sender;
        User user = API.getUserManager().findByUniqueId(player.getUniqueId());
        Profile profile = user.getProfile("essentials");
        if (profile.getBoolean("vanished"))
        {
            for (Player player1 : Bukkit.getOnlinePlayers())
            {
                if (!player1.canSee(player))
                    player1.showPlayer(player);
            }
            profile.set("vanished", false);
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eVanish:&c Disabled"));
            return true;
        }
        for (Player player1 : Bukkit.getOnlinePlayers())
        {
            if (player1.canSee(player) && !player1.hasPermission("kitpvp.staff"))
                player1.hidePlayer(player);
        }
        profile.set("vanished", true);
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eVanish:&a Enabled"));
        return true;
    }
}
