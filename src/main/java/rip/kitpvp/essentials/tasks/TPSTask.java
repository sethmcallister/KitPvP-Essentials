package rip.kitpvp.essentials.tasks;

import org.bukkit.scheduler.BukkitRunnable;

public class TPSTask extends BukkitRunnable
{
    private static int TICK_COUNT = 0;
    private static long[] TICKS = new long[600];
    private static long LAST_TICK_TIME = System.currentTimeMillis();
    public static long LAST_TICK_LENGTH = 0L;

    public static double getTPS()
    {
        return getTPS(100);
    }

    private static double getTPS(int ticks)
    {
        if (TICK_COUNT < ticks)
            return 20.0D;

        int target = (TICK_COUNT- 1 - ticks) % TICKS.length;
        long elapsed = System.currentTimeMillis() - TICKS[target];

        return ticks / (elapsed / 1000.0D);
    }

    public static long getElapsed(int tickID)
    {
        long time = TICKS[(tickID % TICKS.length)];
        return System.currentTimeMillis() - time;
    }

    @Override
    public void run()
    {
        TICKS[(TICK_COUNT% TICKS.length)] = System.currentTimeMillis();

        TICK_COUNT+= 1;

        long last = LAST_TICK_TIME;
        long now = System.currentTimeMillis();
        LAST_TICK_TIME = now;
        LAST_TICK_LENGTH = now - last;
    }
}
