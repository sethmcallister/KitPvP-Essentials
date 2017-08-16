package rip.kitpvp.essentials.listeners;

import com.skygrind.api.API;
import com.skygrind.api.framework.user.User;
import com.skygrind.api.framework.user.profile.Profile;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import rip.kitpvp.essentials.Main;

import java.util.ArrayList;
import java.util.Date;

public class PlayerJoinListener implements Listener
{
    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event)
    {
        final Player player = event.getPlayer();
        if(Bukkit.getOnlinePlayers().size() >= Main.getInstance().getMaxSlots())
        {
            if(player.hasPermission("essentials.joinfull"))
                return;

            StringBuilder message = new StringBuilder();
            message.append("&cThe server is currently full!").append("\n").append("\n")
                    .append("&eTo join the server while it's full, purchase a rank at").append("\n")
                    .append("&fstore.kitpvp.rip");
            player.kickPlayer(ChatColor.translateAlternateColorCodes('&', message.toString()));
            return;
        }

        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                User user = API.getUserManager().findByUniqueId(player.getUniqueId());
                Profile profile = user.getProfile("essentials");
                if(profile == null)
                {
                    profile = new Profile("essentials");
                    profile.set("vanished", false);
                    profile.set("ignoring", new ArrayList<>());
                    profile.set("staffmode", false);
                    profile.set("pmsEnabled", true);
                    profile.set("online", true);
                    profile.set("lastSeen", new Date(System.currentTimeMillis()));
                    user.getAllProfiles().add(profile);
                    user.update();
                }
                profile.set("online", true);
            }
        }.runTaskAsynchronously(Main.getInstance());

        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                User user = API.getUserManager().findByUniqueId(player.getUniqueId());
                Profile profile = user.getProfile("essentials");
                if(!profile.getBoolean("staffmode"))
                    return;

                player.getInventory().clear();
                player.getInventory().setItem(0, Main.getInstance().getStaffItemManager().getCarpetTool());
                player.getInventory().setItem(1, Main.getInstance().getStaffItemManager().getInventoryInspectTool());
                player.getInventory().setItem(4, Main.getInstance().getStaffItemManager().getFreezePlayerTool());
                player.getInventory().setItem(8, Main.getInstance().getStaffItemManager().getMatchTeleportTool());
                player.updateInventory();
            }
        }.runTaskLater(Main.getInstance(), 5L);


        for(Player player1 : Bukkit.getOnlinePlayers())
        {
            if(player.getUniqueId().equals(player1.getUniqueId()))
                continue;

            User user = API.getUserManager().findByUniqueId(player1.getUniqueId());
            Profile profile = user.getProfile("essentials");

            if(!profile.getBoolean("vanished"))
                continue;

            if(player1.hasPermission("essentials.vanish"))
                continue;

            player1.hidePlayer(player);
        }
    }
}
