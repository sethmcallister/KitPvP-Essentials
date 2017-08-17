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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TeleportAcceptCommand implements CommandExecutor
{
    public static final List<UUID> TELEPORT_PENDING = new ArrayList<>();

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

        teleportation.setAccepted();
        teleportations.remove(teleportation);

        GooseLocation gooseLocation = teleportation.getLocation();

        Location location = new Location(Bukkit.getWorld(gooseLocation.getWorld()), gooseLocation.getX(), gooseLocation.getY(), gooseLocation.getZ());

        switch (teleportation.getTelportType())
        {
            case TPA:
            {
                sender.sendMessage(ChatColor.YELLOW + "You have accepted " + ChatColor.GREEN + target.getName() + ChatColor.YELLOW + " teleport request.");
                target.sendMessage(ChatColor.YELLOW + "You will be teleported to " + ChatColor.GREEN + sender.getName() + ChatColor.YELLOW + " in 5 seconds.");
                TELEPORT_PENDING.add(target.getUniqueId());
                new BukkitRunnable()
                {
                    @Override
                    public void run()
                    {
                        if(!TELEPORT_PENDING.contains(target.getUniqueId()))
                            return;

                        target.teleport(location);
                        target.sendMessage(ChatColor.YELLOW + "You have been teleported to " + ChatColor.GREEN + sender.getName() + ChatColor.YELLOW + ".");
                        TELEPORT_PENDING.remove(target.getUniqueId());
                    }
                }.runTaskLater(Main.getInstance(), 5 * 20L);
                break;
            }
            case TPAHERE:
            {
                target.sendMessage(ChatColor.YELLOW + "Your teleport here request to " + ChatColor.GREEN + sender.getName() + ChatColor.YELLOW + " has been accepted.");
                sender.sendMessage(ChatColor.YELLOW + "You will be teleported to " + ChatColor.GREEN + target.getName() + ChatColor.YELLOW + " in 5 seconds.");
                TELEPORT_PENDING.add(player.getUniqueId());

                new BukkitRunnable()
                {
                    @Override
                    public void run()
                    {
                        if(!TELEPORT_PENDING.contains(player.getUniqueId()))
                            return;

                        player.teleport(location);
                        player.sendMessage(ChatColor.YELLOW + "You have been teleported to " + ChatColor.GREEN + target.getName() + ChatColor.YELLOW + ".");
                        TELEPORT_PENDING.remove(player.getUniqueId());
                    }
                }.runTaskLater(Main.getInstance(), 5 * 20L);
                break;
            }
        }
        return true;
    }

    private PendingTeleportation getTeleportation(UUID sender, List<PendingTeleportation> teleportations)
    {
        for(PendingTeleportation teleportation : teleportations)
        {
            if (teleportation.getSender().equals(sender) && (teleportation.getExpireTime() - System.currentTimeMillis()) > 0L && !teleportation.getAccepted())
            {
                return teleportation;
            }
        }
        return null;
    }
}
