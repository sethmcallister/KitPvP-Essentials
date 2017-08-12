package rip.kitpvp.essentials.dto;

import java.util.UUID;

public class GooseLocation
{
    private final String name;
    private final UUID world;
    private final Double x;
    private final Double y;
    private final Double z;

    public GooseLocation(final String name, final UUID world, final Double x, final Double y, final Double  z)
    {
        this.name = name;
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public UUID getWorld()
    {
        return world;
    }

    public Double getX()
    {
        return x;
    }

    public Double getY()
    {
        return y;
    }

    public Double getZ()
    {
        return z;
    }

    public String getName()
    {
        return name;
    }
}
