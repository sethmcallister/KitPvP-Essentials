package rip.kitpvp.essentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;
import rip.kitpvp.essentials.Main;
import rip.kitpvp.essentials.dto.GooseLocation;
import rip.kitpvp.essentials.dto.Home;

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
        String name = args[0];

        List<Home> homes = Main.getInstance().getHomeManager().getHomesByPlayer(player.getUniqueId());

        if(this.homeAlreadyExists(name, homes))
        {
            sender.sendMessage(ChatColor.RED + "You already have a home with this name.");
            return true;
        }

        Location location = player.getLocation();

        if(homes.size() >= this.getMaxHomes(player))
        {
            sender.sendMessage(ChatColor.RED + "You have reached the maximum amount of home you can have.");
            return true;
        }
        GooseLocation gooseLocation = new GooseLocation(name, location.getWorld().getUID(), location.getX(), location.getY(), location.getZ());

        Home home = new Home(player.getUniqueId(), gooseLocation, name);
        homes.add(home);
        Main.getInstance().getHomeManager().getHomeMap().put(player.getUniqueId(), homes);
        sender.sendMessage(ChatColor.YELLOW + "You have set a new home at " + ChatColor.GREEN + gooseLocation.getX() + ", " + gooseLocation.getY() + ", " + gooseLocation.getZ() + ChatColor.YELLOW + ".");
        return true;
    }

    private boolean homeAlreadyExists(final String name, final List<Home> homes)
    {
        return homes.stream().anyMatch(location -> location.getName().equalsIgnoreCase(name));
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
