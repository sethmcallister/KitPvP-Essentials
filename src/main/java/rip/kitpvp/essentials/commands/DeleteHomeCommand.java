package rip.kitpvp.essentials.commands;

import com.skygrind.api.API;
import com.skygrind.api.framework.user.profile.Profile;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rip.kitpvp.essentials.dto.GooseLocation;

import java.util.ArrayList;
import java.util.List;

public class DeleteHomeCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String s, final String[] args)
    {
        if(args.length != 1)
        {
            sender.sendMessage(ChatColor.RED + "Usage: /deletehome <Name>");
            return true;
        }
        Player player = (Player)sender;
        String name = args[0];
        Profile profile = API.getUserManager().findByUniqueId(player.getUniqueId()).getProfile("essentials");
        List<GooseLocation> locations = (List<GooseLocation>) profile.getObject("homes");
        if(locations == null)
        {
            locations = new ArrayList<>();
            profile.set("homes", locations);
        }
        GooseLocation location = this.findByName(name, locations);
        if(location == null)
        {
            sender.sendMessage(ChatColor.RED + "You do not have a home set with the name '"+ name + "'.");
            return true;
        }
        locations.remove(location);
        profile.set("homes", locations);
        sender.sendMessage(ChatColor.YELLOW + "You have deleted the home with the name " + ChatColor.GREEN + location.getName() + ChatColor.YELLOW + ".");
        return true;
    }

    private GooseLocation findByName(final String name, final List<GooseLocation> locations)
    {
        return locations.stream().filter(gooseLocation -> gooseLocation.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
}
