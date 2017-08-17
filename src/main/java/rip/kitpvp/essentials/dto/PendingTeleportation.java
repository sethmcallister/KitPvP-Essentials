package rip.kitpvp.essentials.dto;

import java.util.UUID;

public class PendingTeleportation
{
    private final UUID sender;
    private final UUID target;
    private final GooseLocation location;
    private final Long expireTime;
    private final TeleportType telportType;
    private Boolean accepted;

    public PendingTeleportation(final UUID sender, final UUID target, final GooseLocation location, final Long expireTime, final TeleportType telportType)
    {
        this.sender = sender;
        this.target = target;
        this.location = location;
        this.expireTime = expireTime;
        this.telportType = telportType;
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

    public TeleportType getTelportType()
    {
        return telportType;
    }

    public enum TeleportType
    {
        TPA,
        TPAHERE
    }
}
