package rip.kitpvp.essentials.dto;

import java.util.UUID;

public class PendingTeleportation
{
    private final UUID sender;
    private final UUID target;
    private final GooseLocation location;
    private final Long expireTime;
    private Boolean accepted;

    public PendingTeleportation(final UUID sender, final UUID target, final GooseLocation location, final Long expireTime)
    {
        this.sender = sender;
        this.target = target;
        this.location = location;
        this.expireTime = expireTime;
        this.accepted = false;
    }

    public UUID getSender()
    {
        return sender;
    }

    public UUID getTarget()
    {
        return target;
    }

    public GooseLocation getLocation()
    {
        return location;
    }

    public Long getExpireTime()
    {
        return expireTime;
    }

    public Boolean getAccepted()
    {
        return accepted;
    }

    public void setAccepted()
    {
        this.accepted = true;
    }
}
