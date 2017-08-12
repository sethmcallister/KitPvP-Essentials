package rip.kitpvp.essentials.listeners;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class SignChangeListener implements Listener
{
    @EventHandler
    public void onSignChange(SignChangeEvent event)
    {
        if(!event.getPlayer().hasPermission("essentials.colorsigns"))
            return;

        int i = 0;
        for(String line : event.getLines())
        {
            event.setLine(i, ChatColor.translateAlternateColorCodes('&', line));
            i++;
        }
    }
}
