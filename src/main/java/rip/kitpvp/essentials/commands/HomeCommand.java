package rip.kitpvp.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.scheduler.BukkitRunnable;
import rip.kitpvp.essentials.Main;
import rip.kitpvp.essentials.dto.GooseLocation;
import rip.kitpvp.essentials.dto.Home;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HomeCommand implements CommandExecutor
{
    public static final List<UUID> TELEPORTING = new ArrayList<>();

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String s, final String[] args)
    {
        if(args.length != 1)
        {
            sender.sendMessage(ChatColor.RED + "Usage: /home <Name>");
            return true;
        }
        Player player = (Player)sender;
        String name = args[0];

        List<Home> homes = Main.getInstance().getHomeManager().getHomesByPlayer(player.getUniqueId());

        Home home = byNameName(name, homes);

        if(home == null)
        {
            sender.sendMessage(ChatColor.RED + "You do not have a home with that name.");
            return true;
        }

        GooseLocation location = home.getGooseLocation();

        Integer seconds = getHomeTime(player);
        Location location1 = new Location(Bukkit.getWorld(location.getWorld()), location.getX(), location.getY(), location.getZ());
        sender.sendMessage(ChatColor.YELLOW + "You will be teleported to your home " + ChatColor.GREEN + name + ChatColor.YELLOW + " in " + seconds + " seconds.");
        TELEPORTING.add(player.getUniqueId());
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                if(!TELEPORTING.contains(player.getUniqueId()))
                    return;


                TELEPORTING.remove(player.getUniqueId());
                player.teleport(location1);
                sender.sendMessage(ChatColor.YELLOW + "You have been teleported to " + ChatColor.GREEN + name + ChatColor.YELLOW + ".");
            }
        }.runTaskLater(Main.getInstance(), seconds * 20L);
        return true;
    }

    private Integer getHomeTime(final Player player)
    {
        Integer priority = 5;
        for(PermissionAttachmentInfo attachment : player.getEffectivePermissions())
        {
            if(attachment.getPermission().startsWith("essentials.hometime."))
            {
                String permission = attachment.getPermission();
                priority = Integer.valueOf(permission.replace("essentials.hometime.", ""));
            }
        }
        return priority;
    }

    private Home byNameName(final String name, final List<Home> homes)
    {
        return homes.stream().filter(h -> h.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
}
