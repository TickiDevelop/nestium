package net.caffeinemc.mods.nestium.client.services;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import net.minecraft.core.BlockPos;

import java.util.Map;

/**
 * A container that holds the platform's model data.
 */
public class NestiumModelDataContainer {
    private final Long2ObjectMap<NestiumModelData> modelDataMap;
    private final boolean isEmpty;

    public NestiumModelDataContainer(Long2ObjectMap<NestiumModelData> modelDataMap) {
        this.modelDataMap = modelDataMap;
        this.isEmpty = modelDataMap.isEmpty();
    }

    public NestiumModelData getModelData(BlockPos pos) {
        return modelDataMap.getOrDefault(pos.asLong(), NestiumModelData.EMPTY);
    }

    public boolean isEmpty() {
        return isEmpty;
    }
}
