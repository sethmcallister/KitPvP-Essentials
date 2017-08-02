package rip.kitpvp.essentials.listeners;

import com.skygrind.api.API;
import com.skygrind.api.framework.user.User;
import com.skygrind.api.framework.user.profile.Profile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;

public class PlayerJoinListener implements Listener
{
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        User user = API.getUserManager().findByUniqueId(event.getPlayer().getUniqueId());
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
}
