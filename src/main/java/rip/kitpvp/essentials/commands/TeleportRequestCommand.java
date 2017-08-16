package rip.kitpvp.essentials.commands;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rip.kitpvp.essentials.Main;
import rip.kitpvp.essentials.dto.GooseLocation;
import rip.kitpvp.essentials.dto.PendingTeleportation;

import java.util.concurrent.TimeUnit;

public class TeleportRequestCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String s, final String[] args)
    {
        if(args.length != 1)
        {
            sender.sendMessage(ChatColor.RED + "Usage: /tpa <player>");
            return true;
        }
        Player player = (Player)sender;
        Player target = Bukkit.getPlayer(args[0]);
        if(target == null)
        {
            sender.sendMessage(ChatColor.RED + "No player with the name or UUID '" + args[0] + "' was found.");
            return true;
        }
        GooseLocation location = new GooseLocation("", target.getLocation().getWorld().getUID(), target.getLocation().getX(), target.getLocation().getY(), target.getLocation().getZ());
        PendingTeleportation pendingTeleportation = new PendingTeleportation(player.getUniqueId(), target.getUniqueId(), location, System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(10L));
        Main.getInstance().getTeleportationManager().getPendingTeleportationList().add(pendingTeleportation);

        target.spigot().sendMessage(new ComponentBuilder(player.getName()).color(net.md_5.bungee.api.ChatColor.GREEN)
                .append(" has sent you a teleportation request.").color(net.md_5.bungee.api.ChatColor.YELLOW)
                .append(" [Accept]").event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpaccept " + player.getName())).create());
        player.sendMessage(ChatColor.YELLOW + "You have sent a teleportation request to " + ChatColor.GREEN + target.getName() + ChatColor.YELLOW + ".");
        return true;
    }
}
