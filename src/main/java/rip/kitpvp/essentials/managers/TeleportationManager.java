package rip.kitpvp.essentials.managers;

import org.bukkit.entity.Player;
import rip.kitpvp.essentials.dto.PendingTeleportation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TeleportationManager
{
    private final List<PendingTeleportation> pendingTeleportationList;

    public TeleportationManager()
    {
        this.pendingTeleportationList = new ArrayList<>();
    }

    public List<PendingTeleportation> getPendingTeleportations(Player player)
    {
        return this.pendingTeleportationList.stream().filter(teleportation -> teleportation.getTarget().equals(player.getUniqueId())).collect(Collectors.toList());
    }

    public List<PendingTeleportation> getPendingTeleportationList()
    {
        return pendingTeleportationList;
    }
}
