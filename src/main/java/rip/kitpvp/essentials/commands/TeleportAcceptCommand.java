package rip.kitpvp.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import rip.kitpvp.essentials.Main;
import rip.kitpvp.essentials.dto.GooseLocation;
import rip.kitpvp.essentials.dto.PendingTeleportation;

import java.util.List;
import java.util.UUID;

public class TeleportAcceptCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String s, final String[] args)
    {
        if(args.length != 1)
        {
            sender.sendMessage(ChatColor.RED + "Usage: /tpaccept <player>");
            return true;
        }
        Player player = (Player)sender;
        Player target = Bukkit.getPlayer(args[0]);
        if(target == null)
        {
            sender.sendMessage(ChatColor.RED + "No player with the name or UUID '" + args[0] + "' was found.");
            return true;
        }
        List<PendingTeleportation> teleportations = Main.getInstance().getTeleportationManager().getPendingTeleportations(player);
        PendingTeleportation teleportation = getTeleportation(target.getUniqueId(), teleportations);
        if(teleportation == null)
        {
            sender.sendMessage(ChatColor.RED + target.getName() + " has not sent a teleportation request, or it has expired.");
            return true;
        }
        GooseLocation gooseLocation = teleportation.getLocation();
        Location location = new Location(Bukkit.getWorld(gooseLocation.getWorld()), gooseLocation.getX(), gooseLocation.getY(), gooseLocation.getZ());
        sender.sendMessage(ChatColor.YELLOW + "You will be teleported to " + ChatColor.GREEN + target.getName() + ChatColor.YELLOW + " in 5 seconds.");
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                player.teleport(location);
                player.sendMessage(ChatColor.YELLOW + "You have been teleported to " + ChatColor.GREEN + target.getName() + ChatColor.YELLOW + ".");
            }
        }.runTaskLater(Main.getInstance(), 5 * 20L);
        return true;
    }

    private PendingTeleportation getTeleportation(UUID sender, List<PendingTeleportation> teleportations)
    {
        return teleportations.stream().filter(teleportation -> teleportation.getSender().equals(sender)).filter(teleportation -> teleportation.getExpireTime() <= System.currentTimeMillis()).findFirst().orElse(null);
    }
}
