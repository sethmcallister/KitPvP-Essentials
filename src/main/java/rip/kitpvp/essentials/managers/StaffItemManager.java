package rip.kitpvp.essentials.managers;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import rip.kitpvp.essentials.commands.StaffModeCommand;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class StaffItemManager
{
    private final Map<UUID, ItemStack[]> items;
    private final Map<UUID, ItemStack[]> armor;

    public StaffItemManager()
    {
        this.items = new ConcurrentHashMap<>();
        this.armor = new ConcurrentHashMap<>();
    }

    public ItemStack getMatchTeleportTool()
    {
        ItemStack is = new ItemStack(Material.RECORD_3);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(ChatColor.GREEN + "Random Teleport");
        is.setItemMeta(im);
        return is;
    }

    public ItemStack getFreezePlayerTool()
    {
        ItemStack is = new ItemStack(Material.PACKED_ICE);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(ChatColor.GREEN + "Freeze Player");
        is.setItemMeta(im);
        return is;
    }

    public ItemStack getInventoryInspectTool()
    {
        ItemStack is = new ItemStack(Material.BOOK);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(ChatColor.GREEN + "Inspect Inventory");
        is.setItemMeta(im);
        return is;
    }

    public ItemStack getCarpetTool()
    {
        ItemStack is = new ItemStack(Material.CARPET);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(ChatColor.GREEN + "Please use this while recording.");
        is.setItemMeta(im);
        return is;
    }

    public Map<UUID, ItemStack[]> getItems()
    {
        return items;
    }

    public Map<UUID, ItemStack[]> getArmor()
    {
        return armor;
    }
}
