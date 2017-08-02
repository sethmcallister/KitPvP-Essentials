package rip.kitpvp.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import rip.kitpvp.essentials.tasks.TPSTask;

public class LagCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String s, final String[] args)
    {
        if(!sender.hasPermission("essentials.lag"))
        {
            sender.sendMessage(ChatColor.RED + "You do not have permission for this command!");
            return true;
        }
        double tps = TPSTask.getTPS();
        String tpsSTR = (TPSTask.getTPS() > 17 ? ChatColor.GREEN + String.valueOf(tps) : ChatColor.RED + String.valueOf(tps));
        double lag = Math.round((1.0D - TPSTask.getTPS() / 20.0D) * 100.0D);
        String lagSTR = lag > 75 ? ChatColor.GREEN + String.valueOf(lag) : ChatColor.RED + String.valueOf(lag);

        int entities = Bukkit.getWorld("world").getEntities().size();
        int chunks = Bukkit.getWorld("world").getLoadedChunks().length;
        sender.sendMessage(ChatColor.YELLOW + "Tick Rate: " + tpsSTR);
        sender.sendMessage(ChatColor.YELLOW + "Lag: " + lagSTR + "%");
        sender.sendMessage(ChatColor.YELLOW + "Entities: " + ChatColor.GREEN + entities + "(" + Bukkit.getOnlinePlayers().size() + ") players.");
        sender.sendMessage(ChatColor.YELLOW + "Loaded Chunks: " + ChatColor.GREEN + chunks);
        return true;
    }
}
