package rip.kitpvp.essentials.commands;

import java.util.stream.IntStream;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Wout on 5/08/2017.
 */
public class ClearChatCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!sender.hasPermission("essentials.clearchat"))
        {
            sender.sendMessage(ChatColor.RED + "You do not have permission for this command!");
            return true;
        }

        IntStream.rangeClosed(1, 100).forEach(i -> Bukkit.broadcastMessage(""));

        Bukkit.broadcastMessage(ChatColor.GREEN + "The chat was cleared by " + sender.getName());
        return true;
    }
}
