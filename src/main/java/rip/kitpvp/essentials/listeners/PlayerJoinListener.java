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
                    user.getAllProfiles().add(profile);
                    user.update();
                }
            }
        }.runTaskAsynchronously(Main.getInstance());

//        for(User user : API.getUserManager().findAll())
//        {
//            Profile profile = user.getProfile("essentials");
//            if(profile.getBoolean("vanished"))
//            {
//                Player player1 = Bukkit.getPlayer(user.getUniqueId());
//                if(player.getUniqueId().equals(player1.getUniqueId()))
//                    continue;
//
//                if(player.hasPermission("essentials.vanish"))
//                    continue;
//
//                player.hidePlayer(player1);
//            }
//        }
    }
}
