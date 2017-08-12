package rip.kitpvp.essentials.listeners;

import com.skygrind.api.API;
import com.skygrind.api.framework.user.User;
import com.skygrind.api.framework.user.profile.Profile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Date;

public class PlayerQuitListener implements Listener
{
    @EventHandler
    public void onQuit(PlayerQuitEvent event)
    {
        User user = API.getUserManager().findByUniqueId(event.getPlayer().getUniqueId());
        Profile profile = user.getProfile("essentials");
        profile.set("online", false);
        profile.set("lastSeen", new Date(System.currentTimeMillis()));
    }
}
