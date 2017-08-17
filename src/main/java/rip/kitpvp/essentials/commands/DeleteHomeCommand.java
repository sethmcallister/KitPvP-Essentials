package rip.kitpvp.essentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rip.kitpvp.essentials.Main;
import rip.kitpvp.essentials.dto.Home;

import java.util.List;

public class DeleteHomeCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String s, final String[] args)
    {
        if(args.length != 1)
        {
            sender.sendMessage(ChatColor.RED + "Usage: /deletehome <Name>");
            return true;
        }
        Player player = (Player)sender;
        String name = args[0];

        List<Home> homes = Main.getInstance().getHomeManager().getHomesByPlayer(player.getUniqueId());

        Home home = byNameName(name, homes);

        if(home == null)
        {
            sender.sendMessage(ChatColor.RED + "You do not have a home with that name.");
            return true;
        }

        homes.remove(home);
        Main.getInstance().getHomeManager().getHomeMap().put(player.getUniqueId(), homes);
        sender.sendMessage(ChatColor.YELLOW + "You have deleted your home " + ChatColor.GREEN + name + ChatColor.YELLOW + ".");
        return true;
    }

    private Home byNameName(final String name, final List<Home> homes)
    {
        return homes.stream().filter(h -> h.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
}
