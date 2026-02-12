package net.caffeinemc.mods.nestium.client.util.task;

public interface CancellationToken {
    boolean isCancelled();

    void setCancelled();
}
