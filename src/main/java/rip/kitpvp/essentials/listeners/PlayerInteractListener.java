package rip.kitpvp.essentials.listeners;

import com.skygrind.api.API;
import com.skygrind.api.framework.user.User;
import com.skygrind.api.framework.user.profile.Profile;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rip.kitpvp.essentials.Main;
import rip.kitpvp.essentials.utils.MessageUtils;

import java.util.Random;

public class PlayerInteractListener implements Listener
{
    private final Random random;

    public PlayerInteractListener()
    {
        this.random = new Random();
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event)
    {
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR))
        {
            ItemStack hand = event.getPlayer().getItemInHand();
            if (hand.equals(Main.getInstance().getStaffItemManager().getMatchTeleportTool()))
            {
                int random = this.random.nextInt(Bukkit.getOnlinePlayers().size());
                Player player = (Player) Bukkit.getOnlinePlayers().toArray()[random];
                if(player.canSee(event.getPlayer()))
                    player.hidePlayer(event.getPlayer());

                event.getPlayer().teleport(player);
                event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&eYou have been randomly telported to &a" + player.getName() + "&e."));
                MessageUtils.sendStaffMessage(event.getPlayer(), "&7[&7&o" + event.getPlayer().getName() + "]:&eYou have been randomly telported to &a" + player.getName() + "&e.");
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event)
    {
        User user = API.getUserManager().findByUniqueId(event.getPlayer().getUniqueId());
        Profile profile = user.getProfile("essentials");
        if(profile.getBoolean("staffmode"))
            event.setCancelled(true);
    }

    @EventHandler
    public void onBucketEmpty(PlayerBucketEmptyEvent event)
    {
        User user = API.getUserManager().findByUniqueId(event.getPlayer().getUniqueId());
        Profile profile = user.getProfile("essentials");
        if(profile.getBoolean("staffmode"))
            event.setCancelled(true);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event)
    {
        User user = API.getUserManager().findByUniqueId(event.getPlayer().getUniqueId());
        Profile profile = user.getProfile("essentials");
        if(profile.getBoolean("staffmode"))
            event.setCancelled(true);
    }

    @EventHandler
    public void onItemPickup(PlayerPickupItemEvent event)
    {
        User user = API.getUserManager().findByUniqueId(event.getPlayer().getUniqueId());
        Profile profile = user.getProfile("essentials");
        if(profile.getBoolean("staffmode"))
            event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event)
    {
        if (!(event.getEntity() instanceof Player))
            return;

        Player damaged = (Player) event.getEntity();

        User userDamaged = API.getUserManager().findByUniqueId(damaged.getUniqueId());

        if (userDamaged == null)
            return;

        Profile profileDamaged = userDamaged.getProfile("essentials");
        if(profileDamaged.getBoolean("staffmode"))
        {
            event.setDamage(0.0);
            event.setCancelled(true);
        }

        if(!(event.getDamager() instanceof Player))
            return;

        Player damager = (Player) event.getDamager();

        User userDamager = API.getUserManager().findByUniqueId(damager.getUniqueId());
        Profile profileDamager = userDamager.getProfile("essentials");
        if(profileDamager.getBoolean("staffmode"))
        {
            event.setDamage(0.0);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onStaffFreezeInteract(PlayerInteractEntityEvent event)
    {
        final ItemStack hand = event.getPlayer().getItemInHand();
        if (event.getRightClicked() instanceof Player)
        {
            Player touched = (Player) event.getRightClicked();
            if (hand.equals(Main.getInstance().getStaffItemManager().getInventoryInspectTool()))
            {
                Inventory clone = Bukkit.createInventory(null, touched.getInventory().getSize() + 9);
                for (ItemStack o : touched.getInventory())
                {
                    if (o == null || o.getType().equals(Material.AIR))
                        continue;

                    clone.addItem(o);
                }

                event.getPlayer().openInventory(clone);
                event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&eNow inspecting &a" + touched.getName() + "&e's inventory."));
                MessageUtils.sendStaffMessage(event.getPlayer(), ChatColor.translateAlternateColorCodes('&', "&7[&7&o" + event.getPlayer().getName() + "]: &eNow inspecting &a" + touched.getName() + "&e's inventory."));
                return;
            }
            if (hand.equals(Main.getInstance().getStaffItemManager().getFreezePlayerTool()))
                Bukkit.getServer().dispatchCommand(event.getPlayer(), "freeze " + touched.getName());
        }
    }
}
