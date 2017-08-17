package rip.kitpvp.essentials;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import rip.kitpvp.essentials.commands.*;
import rip.kitpvp.essentials.dto.Home;
import rip.kitpvp.essentials.listeners.*;
import rip.kitpvp.essentials.managers.*;
import rip.kitpvp.essentials.tasks.AutobroadcastTask;
import rip.kitpvp.essentials.tasks.TPSTask;

import java.io.*;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Main extends JavaPlugin
{
    private static Main instance;
    private String messagePrefix;
    private Gson gson;
    private FreezeManager freezeManager;
    private StaffItemManager staffItemManager;
    private MessageManager messageManager;
    private TeleportationManager teleportationManager;
    private HomeManager homeManager;
    private String homeFileName;
    private AutobroadcastTask autobroadcastTask;
    private FileConfiguration configuration;
    private Integer maxSlots;

    @Override
    public void onLoad()
    {
        setInstance(this);
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.freezeManager = new FreezeManager();
        this.staffItemManager = new StaffItemManager();
        this.messageManager = new MessageManager();
        this.teleportationManager = new TeleportationManager();
        this.homeManager = new HomeManager();
        this.homeFileName = getDataFolder() + File.separator + "homes.json";
        this.maxSlots = 150;
        loadHomes();
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
        getCommand("broadcast").setExecutor(new BroadcastCommand());
        getCommand("clear").setExecutor(new ClearCommand());
        getCommand("fly").setExecutor(new FlyCommand());
        getCommand("freeze").setExecutor(new FreezeCommand());
        getCommand("freezeserver").setExecutor(new FreezeServerCommand());
        getCommand("gamemode").setExecutor(new GamemodeCommand());
        getCommand("ignore").setExecutor(new IgnoreCommand());
        getCommand("lag").setExecutor(new LagCommand());
        getCommand("list").setExecutor(new ListCommand());
        getCommand("message").setExecutor(new MessageCommand());
        getCommand("reply").setExecutor(new ReplyCommand());
        getCommand("rules").setExecutor(new RulesCommand());
        getCommand("setslots").setExecutor(new SetSlotsCommand());
        getCommand("staffmode").setExecutor(new StaffModeCommand());
        getCommand("teleport").setExecutor(new TeleportCommand());
        getCommand("vanish").setExecutor(new VanishCommand());
        getCommand("clearchat").setExecutor(new ClearChatCommand());
        getCommand("togglemessages").setExecutor(new ToggleMessagesCommand());
        getCommand("deletehome").setExecutor(new DeleteHomeCommand());
        getCommand("enderchest").setExecutor(new EnderchestCommand());
        getCommand("feed").setExecutor(new FeedCommand());
        getCommand("hat").setExecutor(new HatCommand());
        getCommand("home").setExecutor(new HomeCommand());
        getCommand("sethome").setExecutor(new SetHomeCommand());
        getCommand("tpa").setExecutor(new TeleportRequestCommand());
        getCommand("tpahere").setExecutor(new TeleportHereRequestCommand());
        getCommand("tpaccept").setExecutor(new TeleportAcceptCommand());
        getCommand("seen").setExecutor(new SeenCommand());
        getCommand("craft").setExecutor(new CraftCommand());
        getCommand("invsee").setExecutor(new InventorySeeCommand());
        getCommand("lobby").setExecutor(new LobbyCommand());

        Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerMoveListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(), this);
        Bukkit.getPluginManager().registerEvents(new SignChangeListener(), this);

        Bukkit.getPluginManager().registerEvents(this.freezeManager, this);

        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        this.autobroadcastTask = new AutobroadcastTask();
        this.autobroadcastTask.getBroadcasts().addAll(this.configuration.getStringList("broadcasts"));
        this.autobroadcastTask.runTaskTimerAsynchronously(this, 1L, 120 * 20L);
        new TPSTask().runTaskTimer(this, 1L, 1L);
        this.freezeManager.getServerFrozen().set(false);
    }

    @Override
    public void onDisable()
    {
        this.configuration.set("messagePrefix", this.messagePrefix);
        this.configuration.set("broadcasts", this.autobroadcastTask.getBroadcasts());
        saveConfig();

        String json = this.gson.toJson(getHomeManager().getHomeMap());
        File file = new File(this.homeFileName);
        if(!file.exists())
            try
            {
                file.createNewFile();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

        try(FileWriter writer = new FileWriter(file))
        {
            writer.write(json);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void loadHomes()
    {
        File file = new File(this.homeFileName);
        if(!file.exists())
        {
            getDataFolder().mkdir();
            try
            {
                file.createNewFile();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            JsonParser parser = new JsonParser();

            try(FileReader fileReader = new FileReader(this.homeFileName))
            {
                JsonElement element = parser.parse(fileReader);
                Type type = new TypeToken<Map<UUID, List<Home>>>(){}.getType();
                getHomeManager().setHomeMap(this.gson.fromJson(element, type));
                System.out.println(element);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public static synchronized Main getInstance()
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

    public Integer getMaxSlots()
    {
        return maxSlots;
    }

    public void setMaxSlots(final Integer maxSlots)
    {
        this.maxSlots = maxSlots;
    }

    public TeleportationManager getTeleportationManager()
    {
        return teleportationManager;
    }

    public HomeManager getHomeManager()
    {
        return homeManager;
    }
}
