package rip.kitpvp.essentials.managers;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

public class FreezeManager implements Listener
{
    private final List<UUID> frozen;
    private final AtomicBoolean serverFrozen;

    public FreezeManager()
    {
        this.frozen = Collections.synchronizedList(new ArrayList<UUID>());
        this.serverFrozen = new AtomicBoolean(true);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event)
    {
        if(event.getFrom().getX() == event.getTo().getX() || event.getFrom().getZ() == event.getTo().getZ())
            return;

        Player player = event.getPlayer();

        if(this.serverFrozen.get())
        {
            if(player.hasPermission("essentials.freeze.bypass"))
                return;

            event.setTo(event.getFrom());
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThe server is currently frozen, you cannot move."));
            return;
        }

        if(!this.frozen.contains(player.getUniqueId()))
            return;

        event.setTo(event.getFrom());
        player.sendMessage(ChatColor.WHITE + "▆▆▆▆▆▆▆▆▆");
        player.sendMessage(ChatColor.WHITE + "▇▇▇▇" + ChatColor.RED + "▇" + ChatColor.WHITE + "▇▇▇▇");
        player.sendMessage(ChatColor.WHITE + "▇▇▇" + ChatColor.RED + "▇" + ChatColor.GOLD + ChatColor.BLACK + "▇" + ChatColor.GOLD + ChatColor.RED + "▇" + ChatColor.WHITE + "▇▇▇");
        player.sendMessage(ChatColor.WHITE + "▇▇" + ChatColor.RED + "▇" + ChatColor.GOLD + "▇" + ChatColor.BLACK + "▇" + ChatColor.GOLD + "▇" + ChatColor.RED + "▇" + ChatColor.WHITE + "▇▇");
        player.sendMessage(ChatColor.WHITE + "▇▇" + ChatColor.RED + "▇" + ChatColor.GOLD + "▇" + ChatColor.BLACK + "▇" + ChatColor.GOLD + "▇" + ChatColor.RED + "▇" + ChatColor.WHITE + "▇▇ " + ChatColor.YELLOW + "You have been frozen");
        player.sendMessage(ChatColor.WHITE + "▇▇" + ChatColor.RED + "▇" + ChatColor.GOLD + "▇" + ChatColor.BLACK + "▇" + ChatColor.GOLD + "▇" + ChatColor.RED + "▇" + ChatColor.WHITE + "▇▇ " + ChatColor.YELLOW + "Please join our teamspeak");
        player.sendMessage(ChatColor.WHITE + "▇" + ChatColor.RED + "▇" + ChatColor.GOLD + "▇▇▇" + ChatColor.BLACK + ChatColor.GOLD + "▇▇" + ChatColor.RED + "▇" + ChatColor.WHITE + "▇ " + ChatColor.GOLD + "ts.kitpvp.rip");
        player.sendMessage(ChatColor.RED + "▇" + ChatColor.GOLD + "▇▇▇" + ChatColor.BLACK + "▇" + ChatColor.GOLD + "▇▇▇" + ChatColor.RED + "▇" + ChatColor.WHITE);
        player.sendMessage(ChatColor.RED + "▇▇▇▇▇▇▇▇▇");
        player.sendMessage(ChatColor.WHITE + "▇▇▇▇▇▇▇▇▇");

    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event)
    {
        if(event.isCancelled())
            return;

        if(!(event.getDamager() instanceof Player) || !(event.getEntity() instanceof Player))
            return;

        Player player = (Player) event.getDamager();

        if(this.serverFrozen.get())
        {
            if(player.hasPermission("essentials.freeze.bypass"))
                return;

            event.setCancelled(true);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThe server is currently frozen, you cannot damage other players."));
            return;
        }

        if(!this.frozen.contains(player.getUniqueId()))
            return;

        event.setCancelled(true);
        player.sendMessage(ChatColor.WHITE + "▆▆▆▆▆▆▆▆▆");
        player.sendMessage(ChatColor.WHITE + "▇▇▇▇" + ChatColor.RED + "▇" + ChatColor.WHITE + "▇▇▇▇");
        player.sendMessage(ChatColor.WHITE + "▇▇▇" + ChatColor.RED + "▇" + ChatColor.GOLD + ChatColor.BLACK + "▇" + ChatColor.GOLD + ChatColor.RED + "▇" + ChatColor.WHITE + "▇▇▇");
        player.sendMessage(ChatColor.WHITE + "▇▇" + ChatColor.RED + "▇" + ChatColor.GOLD + "▇" + ChatColor.BLACK + "▇" + ChatColor.GOLD + "▇" + ChatColor.RED + "▇" + ChatColor.WHITE + "▇▇");
        player.sendMessage(ChatColor.WHITE + "▇▇" + ChatColor.RED + "▇" + ChatColor.GOLD + "▇" + ChatColor.BLACK + "▇" + ChatColor.GOLD + "▇" + ChatColor.RED + "▇" + ChatColor.WHITE + "▇▇ " + ChatColor.YELLOW + "You have been frozen");
        player.sendMessage(ChatColor.WHITE + "▇▇" + ChatColor.RED + "▇" + ChatColor.GOLD + "▇" + ChatColor.BLACK + "▇" + ChatColor.GOLD + "▇" + ChatColor.RED + "▇" + ChatColor.WHITE + "▇▇ " + ChatColor.YELLOW + "Please join our teamspeak");
        player.sendMessage(ChatColor.WHITE + "▇" + ChatColor.RED + "▇" + ChatColor.GOLD + "▇▇▇" + ChatColor.BLACK + ChatColor.GOLD + "▇▇" + ChatColor.RED + "▇" + ChatColor.WHITE + "▇ " + ChatColor.GOLD + "ts.kitpvp.rip");
        player.sendMessage(ChatColor.RED + "▇" + ChatColor.GOLD + "▇▇▇" + ChatColor.BLACK + "▇" + ChatColor.GOLD + "▇▇▇" + ChatColor.RED + "▇" + ChatColor.WHITE);
        player.sendMessage(ChatColor.RED + "▇▇▇▇▇▇▇▇▇");
        player.sendMessage(ChatColor.WHITE + "▇▇▇▇▇▇▇▇▇");
    }

    @EventHandler
    public void onCommandPreProccess(PlayerCommandPreprocessEvent event)
    {
        Player player = event.getPlayer();

        if(this.serverFrozen.get())
        {
            if(player.hasPermission("essentials.freeze.bypass"))
                return;

            event.setCancelled(true);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThe server is currently frozen, you cannot type commands."));
            return;
        }

        if(!this.frozen.contains(player.getUniqueId()))
            return;

        event.setCancelled(true);
        player.sendMessage(ChatColor.WHITE + "▆▆▆▆▆▆▆▆▆");
        player.sendMessage(ChatColor.WHITE + "▇▇▇▇" + ChatColor.RED + "▇" + ChatColor.WHITE + "▇▇▇▇");
        player.sendMessage(ChatColor.WHITE + "▇▇▇" + ChatColor.RED + "▇" + ChatColor.GOLD + ChatColor.BLACK + "▇" + ChatColor.GOLD + ChatColor.RED + "▇" + ChatColor.WHITE + "▇▇▇");
        player.sendMessage(ChatColor.WHITE + "▇▇" + ChatColor.RED + "▇" + ChatColor.GOLD + "▇" + ChatColor.BLACK + "▇" + ChatColor.GOLD + "▇" + ChatColor.RED + "▇" + ChatColor.WHITE + "▇▇");
        player.sendMessage(ChatColor.WHITE + "▇▇" + ChatColor.RED + "▇" + ChatColor.GOLD + "▇" + ChatColor.BLACK + "▇" + ChatColor.GOLD + "▇" + ChatColor.RED + "▇" + ChatColor.WHITE + "▇▇ " + ChatColor.YELLOW + "You have been frozen");
        player.sendMessage(ChatColor.WHITE + "▇▇" + ChatColor.RED + "▇" + ChatColor.GOLD + "▇" + ChatColor.BLACK + "▇" + ChatColor.GOLD + "▇" + ChatColor.RED + "▇" + ChatColor.WHITE + "▇▇ " + ChatColor.YELLOW + "Please join our teamspeak");
        player.sendMessage(ChatColor.WHITE + "▇" + ChatColor.RED + "▇" + ChatColor.GOLD + "▇▇▇" + ChatColor.BLACK + ChatColor.GOLD + "▇▇" + ChatColor.RED + "▇" + ChatColor.WHITE + "▇ " + ChatColor.GOLD + "ts.kitpvp.rip");
        player.sendMessage(ChatColor.RED + "▇" + ChatColor.GOLD + "▇▇▇" + ChatColor.BLACK + "▇" + ChatColor.GOLD + "▇▇▇" + ChatColor.RED + "▇" + ChatColor.WHITE);
        player.sendMessage(ChatColor.RED + "▇▇▇▇▇▇▇▇▇");
        player.sendMessage(ChatColor.WHITE + "▇▇▇▇▇▇▇▇▇");
    }

    public List<UUID> getFrozen()
    {
        return frozen;
    }

    public AtomicBoolean getServerFrozen()
    {
        return serverFrozen;
    }
}
