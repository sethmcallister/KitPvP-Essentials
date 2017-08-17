package rip.kitpvp.essentials.commands;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rip.kitpvp.essentials.Main;

/**
 * Created by Wout on 17/08/2017.
 */
public class LobbyCommand implements CommandExecutor {
    public static final Map<UUID, Long> PENDING_TELEPORTS = Maps.newConcurrentMap(); // Seth does it so why not
    private long id = 0L;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player))
        {
            sender.sendMessage(ChatColor.RED + "This command is player-only!");
            return false;
        }

        Player p = (Player) sender;
        long tpId = id++;

        if (PENDING_TELEPORTS.containsKey(p.getUniqueId()))
        {
            sender.sendMessage(ChatColor.RED + "You are already teleporting to there!");
            return false;
        }

        p.sendMessage(ChatColor.GREEN + "Teleporting you to the lobby in 5 seconds... don't move!");

        PENDING_TELEPORTS.put(p.getUniqueId(), tpId);

        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
            if (PENDING_TELEPORTS.containsKey(p.getUniqueId()) && PENDING_TELEPORTS.get(p.getUniqueId()) == tpId)
            {
                PENDING_TELEPORTS.remove(p.getUniqueId());

                ByteArrayOutputStream b = new ByteArrayOutputStream();
                DataOutputStream out = new DataOutputStream(b);

                try {
                    out.writeUTF("Connect");
                    out.writeUTF("Hub1");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                p.sendPluginMessage(Main.getInstance(), "BungeeCord", b.toByteArray());
            }
        }, 100L);

        return true;
    }
}
