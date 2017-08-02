package rip.kitpvp.essentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rip.kitpvp.essentials.Main;
import rip.kitpvp.essentials.utils.MessageUtils;

public class GamemodeCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String s, final String[] args)
    {
        if(!sender.hasPermission("essentials.gamemode"))
        {
            sender.sendMessage(ChatColor.RED + "You do not have permission for this command!");
            return true;
        }
        if (args.length == 0)
        {
            showUsage(sender);
            return true;
        }

        switch (args[0].toLowerCase())
        {
            case "0":
            case "s":
            case "survival":
                handleSurvival(sender);
                break;
            case "1":
            case "creative":
            case "c":
                handleCreative(sender);
                break;
            default:
                showUsage(sender);
                break;
        }
        return true;
    }

    private void handleCreative(CommandSender sender)
    {
        Player p = (Player) sender;
        p.setGameMode(GameMode.CREATIVE);
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getInstance().getMessagePrefix() + "&e You now have gamemode &aCreative&e."));
        MessageUtils.sendStaffMessage(p, "&7[&7&o" + p.getName() + "]: &eYou now have gamemode &aCreative&e.");
    }

    private void handleSurvival(CommandSender sender)
    {
        Player p = (Player) sender;
        p.setGameMode(GameMode.SURVIVAL);
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getInstance().getMessagePrefix() + "&e You now have gamemode &aSurvival&e."));
        MessageUtils.sendStaffMessage(p, "&7[&7&o" + p.getName() + "]: &eYou now have gamemode &aSurvival&e.");
    }

    private void showUsage(CommandSender sender)
    {
        sender.sendMessage(ChatColor.RED + "Usage: /gamemode <creative, survival>");
    }
}
