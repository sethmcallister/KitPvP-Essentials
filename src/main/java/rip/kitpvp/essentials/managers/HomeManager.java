package rip.kitpvp.essentials.managers;

import rip.kitpvp.essentials.dto.Home;

import java.util.*;

public class HomeManager
{
    private Map<UUID, List<Home>> homeMap;

    public HomeManager()
    {
        homeMap = new HashMap<>();
    }

    public void setHomeMap(Map<UUID, List<Home>> homeMap)
    {
        this.homeMap = homeMap;
    }

    public Map<UUID, List<Home>> getHomeMap()
    {
        return this.homeMap;
    }

    public List<Home> getHomesByPlayer(UUID uuid)
    {
        return this.homeMap.computeIfAbsent(uuid, k -> new ArrayList<>());
    }
}
