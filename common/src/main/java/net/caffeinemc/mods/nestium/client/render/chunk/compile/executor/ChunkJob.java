package net.caffeinemc.mods.nestium.client.render.chunk.compile.executor;

import net.caffeinemc.mods.nestium.client.render.chunk.compile.ChunkBuildContext;
import net.caffeinemc.mods.nestium.client.util.task.CancellationToken;

public interface ChunkJob extends CancellationToken {
    void execute(ChunkBuildContext context);

    boolean isStarted();

    int getEffort();
}
