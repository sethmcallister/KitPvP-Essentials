package rip.kitpvp.essentials.managers;

import com.skygrind.api.API;
import com.skygrind.api.framework.user.User;
import com.skygrind.api.framework.user.profile.Profile;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import rip.kitpvp.essentials.utils.MessageUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class MessageManager
{
    private final Map<UUID, UUID> conversations;
    private final List<String> filteredWords;

    public MessageManager()
    {
        this.conversations = new ConcurrentHashMap<>();
        this.filteredWords = new ArrayList<>();
        this.filteredWords.add("ddos");
        this.filteredWords.add("dox");
        this.filteredWords.add("cheating");
        this.filteredWords.add("vape");
        this.filteredWords.add("client");
        this.filteredWords.add("custom");
        this.filteredWords.add("hacking");
        this.filteredWords.add("hack");
        this.filteredWords.add("dos");
        this.filteredWords.add("vae");
        this.filteredWords.add("cunt");
        this.filteredWords.add("ares");
        this.filteredWords.add("server");
    }

    public void setConversations(Player player, Player target, String message)
    {
        User user = API.getUserManager().findByUniqueId(player.getUniqueId());
        User receiver = API.getUserManager().findByUniqueId(target.getUniqueId());

        Profile receiverProfile = receiver.getProfile("essentials");

        if (!receiverProfile.getBoolean("pmsEnabled") && !player.isOp())
        {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThis person has their message turned off."));
            return;
        }

        List<UUID> ignoreList = (List<UUID>) receiverProfile.getObject("ignoring");

        if (ignoreList.contains(user.getUniqueId()))
        {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThis person is ignoring you."));
            return;
        }

        this.conversations.put(player.getUniqueId(), target.getUniqueId());
        this.conversations.put(target.getUniqueId(), player.getUniqueId());

        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&d(To &r" + target.getName() + "&d) " + message));
        target.sendMessage(ChatColor.translateAlternateColorCodes('&', "&d(From &r" + player.getName() + "&d) " + message));

        if (detectFilter(message))
        {
            MessageUtils.sendStaffMessage("&c[Filtered] (" + player.getName() + " -> " + target.getName() + ")" + ": " + message);
        }
    }

    public void reply(Player player, String message)
    {
        if (!this.conversations.containsKey(player.getUniqueId()))
        {
            player.sendMessage(ChatColor.RED + "You're not in a conversation with anyone.");
            return;
        }
        if (this.conversations.get(player.getUniqueId()) != null)
        {
            Player target = Bukkit.getPlayer(this.conversations.get(player.getUniqueId()));
            setConversations(player, target, message);
            return;
        }
        player.sendMessage(ChatColor.RED + "That player is not online.");
    }

    private boolean detectFilter(String message)
    {
        for (String string : filteredWords)
        {
            if (message.toLowerCase().contains(string))
                return true;
        }
        return false;
    }

    public boolean isConversing(Player player)
    {
        return this.conversations.containsKey(player.getUniqueId());
    }

    public Map<UUID, UUID> getConversations()
    {
        return this.conversations;
    }
}
