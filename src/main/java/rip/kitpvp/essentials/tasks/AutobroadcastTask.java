package rip.kitpvp.essentials.tasks;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;
import rip.kitpvp.essentials.Main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class AutobroadcastTask extends BukkitRunnable
{
    private final Random random;
    private final List<String> broadcasts;

    public AutobroadcastTask()
    {
        this.random = new Random();
        this.broadcasts = Collections.synchronizedList(new ArrayList<String>());
//        this.broadcasts.add("Check out our Twitter for giveaways, and updates: \n&f@KitPvPNetwork");
//        this.broadcasts.add("Want a coloured name? \n&fDonate @ https://kitpvp.rip/store");
//        this.broadcasts.add("Grind to the top, and challenge your friends: \n&fhttps://kitpvp.rip/stats/kits");
//        this.broadcasts.add("Need those extra kits? \n&fDonate @ https://kitpvp.rip/store");
//        this.broadcasts.add("If you require assistance, either type \n&f/request&e, or join&f ts.kitpvp.rip");
//        this.broadcasts.add("Make sure your part of our community by joining our forums: \n&fhttps://kitpvp.rip/forums");
//        this.broadcasts.add("Want to be displayed along side a loving heart? Like our NameMC Page! \n&fhttps://namemc.com/server/kitpvp.rip");
    }

    public List<String> getBroadcasts()
    {
        return this.broadcasts;
    }

    @Override
    public void run()
    {
        int index = this.random.nextInt(broadcasts.size());
        String broadcast = this.broadcasts.get(index);
        Bukkit.broadcastMessage(" ");
        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Main.getInstance().getMessagePrefix() + broadcast));
        Bukkit.broadcastMessage(" ");
    }
}
