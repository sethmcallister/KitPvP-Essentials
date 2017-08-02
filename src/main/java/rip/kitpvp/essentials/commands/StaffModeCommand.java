package rip.kitpvp.essentials.commands;

import com.skygrind.api.API;
import com.skygrind.api.framework.user.User;
import com.skygrind.api.framework.user.profile.Profile;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rip.kitpvp.essentials.Main;

public class StaffModeCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String s, final String[] args)
    {
        if(!sender.hasPermission("essentials.hackermode"))
        {
            sender.sendMessage(org.bukkit.ChatColor.RED + "You do not have permission for this command!");
            return true;
        }
        Player player = (Player) sender;
        User user = API.getUserManager().findByUniqueId(player.getUniqueId());
        Profile profile = user.getProfile("essentials");
        if (!profile.getBoolean("staffmode"))
        {
            profile.set("staffmode", true);
            player.setAllowFlight(true);
            player.setFlying(true);

            for (Player player1 : Bukkit.getOnlinePlayers())
            {
                if (player1.canSee(player) && !player1.hasPermission("kitpvp.staff"))
                    player1.hidePlayer(player);
            }

            Main.getInstance().getStaffItemManager().getItems().put(player.getUniqueId(), player.getInventory().getContents());
            Main.getInstance().getStaffItemManager().getArmor().put(player.getUniqueId(), player.getInventory().getArmorContents());

            player.getInventory().clear();
            player.getInventory().setItem(0, Main.getInstance().getStaffItemManager().getCarpetTool());
            player.getInventory().setItem(1, Main.getInstance().getStaffItemManager().getInventoryInspectTool());
            player.getInventory().setItem(4, Main.getInstance().getStaffItemManager().getFreezePlayerTool());
            player.getInventory().setItem(8, Main.getInstance().getStaffItemManager().getMatchTeleportTool());
            player.updateInventory();

            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eHacker Mode: &aEnabled"));
            return true;
        }
        profile.set("staffmode", false);
        player.setAllowFlight(false);
        player.setFlying(false);

        for (Player player1 : Bukkit.getOnlinePlayers())
        {
            if (!player1.canSee(player))
                player1.showPlayer(player);
        }

        player.getInventory().clear();
        player.getInventory().setContents(Main.getInstance().getStaffItemManager().getItems().get(player.getUniqueId()));
        player.getInventory().setArmorContents(Main.getInstance().getStaffItemManager().getArmor().get(player.getUniqueId()));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eHacker Mode: &cDisabled"));
        return true;
    }
}
