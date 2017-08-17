package rip.kitpvp.essentials.listeners;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import rip.kitpvp.essentials.commands.HomeCommand;
import rip.kitpvp.essentials.commands.LobbyCommand;
import rip.kitpvp.essentials.commands.TeleportAcceptCommand;

public class PlayerMoveListener implements Listener
{
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event)
    {
        if(event.getFrom().getX() == event.getTo().getX() || event.getFrom().getZ() == event.getTo().getZ())
            return;

        if(TeleportAcceptCommand.TELEPORT_PENDING.contains(event.getPlayer().getUniqueId()))
        {
            TeleportAcceptCommand.TELEPORT_PENDING.remove(event.getPlayer().getUniqueId());
            event.getPlayer().sendMessage(ChatColor.YELLOW + "You have moved, and cancelled your current teleportation.");
        }
        else if(HomeCommand.TELEPORTING.contains(event.getPlayer().getUniqueId()))
        {
            HomeCommand.TELEPORTING.remove(event.getPlayer().getUniqueId());
            event.getPlayer().sendMessage(ChatColor.YELLOW + "You have moved, and cancelled your current teleportation.");
        }
        else if (LobbyCommand.PENDING_TELEPORTS.containsKey(event.getPlayer().getUniqueId()))
        {
            LobbyCommand.PENDING_TELEPORTS.remove(event.getPlayer().getUniqueId());
            event.getPlayer().sendMessage(ChatColor.YELLOW + "You have moved, and cancelled your current teleportation.");
        }
    }
}
