package rip.kitpvp.essentials;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import rip.kitpvp.essentials.commands.*;
import rip.kitpvp.essentials.listeners.PlayerInteractListener;
import rip.kitpvp.essentials.listeners.PlayerJoinListener;
import rip.kitpvp.essentials.managers.FreezeManager;
import rip.kitpvp.essentials.managers.MessageManager;
import rip.kitpvp.essentials.managers.StaffItemManager;
import rip.kitpvp.essentials.tasks.AutobroadcastTask;
import rip.kitpvp.essentials.tasks.TPSTask;

import java.util.Arrays;

public class Main extends JavaPlugin
{
    private static Main instance;
    private String messagePrefix;
    private FreezeManager freezeManager;
    private StaffItemManager staffItemManager;
    private MessageManager messageManager;
    private AutobroadcastTask autobroadcastTask;
    private FileConfiguration configuration;

    @Override
    public void onLoad()
    {
        setInstance(this);
        this.freezeManager = new FreezeManager();
        this.staffItemManager = new StaffItemManager();
        this.messageManager = new MessageManager();
    }

    @Override
    public void onEnable()
    {
        this.configuration = this.getConfig();
        this.configuration.addDefault("messagePrefix", "&6KitPvP &8\u00BB &e");
        this.configuration.addDefault("broadcasts", Arrays.asList("Check out our Twitter for giveaways, and updates: \n&f@KitPvPNetwork",
                "Want a coloured name? \n&fDonate @ https://kitpvp.rip/store",
                "Grind to the top, and challenge your friends: \n&fhttps://kitpvp.rip/stats/kits",
                "Need those extra kits? \n&fDonate @ https://kitpvp.rip/store",
                "If you require assistance, either type \n&f/request&e, or join&f ts.kitpvp.rip",
                "Make sure your part of our community by joining our forums: \n&fhttps://kitpvp.rip/forums",
                "Want to be displayed along side a loving heart? Like our NameMC Page! \n&fhttps://namemc.com/server/kitpvp.rip"));

        configuration.options().copyDefaults(true);
        saveConfig();

        this.messagePrefix = this.configuration.getString("messagePrefix");

        getCommand("clear").setExecutor(new ClearCommand());
        getCommand("fly").setExecutor(new FlyCommand());
        getCommand("freeze").setExecutor(new FreezeCommand());
        getCommand("gamemode").setExecutor(new GamemodeCommand());
        getCommand("ignore").setExecutor(new IgnoreCommand());
        getCommand("message").setExecutor(new MessageCommand());
        getCommand("lag").setExecutor(new LagCommand());
        getCommand("list").setExecutor(new ListCommand());
        getCommand("staffmode").setExecutor(new StaffModeCommand());
        getCommand("teleport").setExecutor(new TeleportCommand());
        getCommand("vanish").setExecutor(new VanishCommand());

        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(), this);

        Bukkit.getPluginManager().registerEvents(this.freezeManager, this);

        this.autobroadcastTask = new AutobroadcastTask();
        this.autobroadcastTask.getBroadcasts().addAll(this.configuration.getStringList("broadcasts"));
        this.autobroadcastTask.runTaskTimerAsynchronously(this, 1L, 120 * 20L);
        new TPSTask().runTaskTimer(this, 1L, 1L);
    }

    @Override
    public void onDisable()
    {
        this.configuration.set("messagePrefix", this.messagePrefix);
        this.configuration.set("broadcasts", this.autobroadcastTask.getBroadcasts());
        saveConfig();
    }

    public static Main getInstance()
    {
        return instance;
    }

    private static synchronized void setInstance(Main newInstance)
    {
        instance = newInstance;
    }

    public FreezeManager getFreezeManager()
    {
        return freezeManager;
    }

    public StaffItemManager getStaffItemManager()
    {
        return staffItemManager;
    }

    public MessageManager getMessageManager()
    {
        return messageManager;
    }

    public String getMessagePrefix()
    {
        return messagePrefix;
    }
}
