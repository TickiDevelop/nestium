package net.caffeinemc.mods.nestium.client.model.color.interop;

import it.unimi.dsi.fastutil.objects.Reference2ReferenceMap;
import it.unimi.dsi.fastutil.objects.ReferenceSet;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.world.level.block.Block;

public interface BlockColorsExtension {
    static Reference2ReferenceMap<Block, BlockColor> getProviders(BlockColors blockColors) {
        return ((BlockColorsExtension) blockColors).nestium$getProviders();
    }

    static ReferenceSet<Block> getOverridenVanillaBlocks(BlockColors blockColors) {
        return ((BlockColorsExtension) blockColors).nestium$getOverridenVanillaBlocks();
    }

    Reference2ReferenceMap<Block, BlockColor> nestium$getProviders();

    ReferenceSet<Block> nestium$getOverridenVanillaBlocks();
}
