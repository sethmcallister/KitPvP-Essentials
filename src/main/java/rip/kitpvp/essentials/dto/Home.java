package rip.kitpvp.essentials.dto;

import java.util.UUID;

public class Home
{
    private final UUID owner;
    private final GooseLocation gooseLocation;
    private final String name;

    public Home(final UUID owner, final GooseLocation gooseLocation, final String name)
    {
        this.owner = owner;
        this.gooseLocation = gooseLocation;
        this.name = name;
    }

    public UUID getOwner()
    {
        return owner;
    }

    public GooseLocation getGooseLocation()
    {
        return gooseLocation;
    }

    public String getName()
    {
        return name;
    }
}
