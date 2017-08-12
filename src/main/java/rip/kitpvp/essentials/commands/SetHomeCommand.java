package rip.kitpvp.essentials.commands;

import com.skygrind.api.API;
import com.skygrind.api.framework.user.profile.Profile;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;
import rip.kitpvp.essentials.dto.GooseLocation;

import java.util.ArrayList;
import java.util.List;

public class SetHomeCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String s, final String[] args)
    {
        if(args.length != 1)
        {
            sender.sendMessage(ChatColor.RED + "Usage: /sethome <Name>");
            return true;
        }
        Player player = (Player)sender;
        Profile profile = API.getUserManager().findByUniqueId(player.getUniqueId()).getProfile("essentials");
        List<GooseLocation> locations = (List<GooseLocation>) profile.getObject("homes");
        if(locations == null)
            locations = new ArrayList<>();

        String name = args[0];

        if(this.homeAlreadyExists(name, locations))
        {
            sender.sendMessage(ChatColor.RED + "You already have a home with this name.");
            return true;
        }

        Location location = player.getLocation();

        if(locations.size() >= this.getMaxHomes(player))
        {
            sender.sendMessage(ChatColor.RED + "You have reached the maximum amount of home you can have.");
            return true;
        }
        GooseLocation gooseLocation = new GooseLocation(name, location.getWorld().getUID(), location.getX(), location.getY(), location.getZ());
        locations.add(gooseLocation);
        sender.sendMessage(ChatColor.YELLOW + "You have set a new home at " + ChatColor.GREEN + gooseLocation.getX() + ", " + gooseLocation.getY() + ", " + gooseLocation.getZ() + ChatColor.YELLOW + ".");
        return true;
    }

    private boolean homeAlreadyExists(final String name, final List<GooseLocation> locations)
    {
        return locations.stream().anyMatch(location -> location.getName().equalsIgnoreCase(name));
    }

    private Integer getMaxHomes(final Player player)
    {
        Integer priority = 1;
        for(PermissionAttachmentInfo attachment : player.getEffectivePermissions())
        {
            if(attachment.getPermission().startsWith("essentials.maxhomes."))
            {
                String permission = attachment.getPermission();
                priority = Integer.valueOf(permission.replace("essentials.maxhomes.", ""));
            }
        }
        return priority;
    }
}
