package net.caffeinemc.mods.nestium.mixin.core.render;

import java.util.function.Predicate;

import net.caffeinemc.mods.nestium.api.blockentity.BlockEntityRenderPredicate;
import net.caffeinemc.mods.nestium.client.render.chunk.ExtendedBlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.apache.commons.lang3.ArrayUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(BlockEntityType.class)
public class BlockEntityTypeMixin<T extends BlockEntity> implements ExtendedBlockEntityType<T> {
    @Unique
    private BlockEntityRenderPredicate<T>[] nestium$renderPredicates = new BlockEntityRenderPredicate[0];

    @Override
    public BlockEntityRenderPredicate<T>[] nestium$getRenderPredicates() {
        return nestium$renderPredicates;
    }

    @Override
    public void nestium$addRenderPredicate(BlockEntityRenderPredicate<T> predicate) {
        nestium$renderPredicates = ArrayUtils.add(nestium$renderPredicates, predicate);
    }

    @Override
    public boolean nestium$removeRenderPredicate(BlockEntityRenderPredicate<T> predicate) {
        int index = ArrayUtils.indexOf(nestium$renderPredicates, predicate);

        if (index == ArrayUtils.INDEX_NOT_FOUND) {
            return false;
        }

        nestium$renderPredicates = ArrayUtils.remove(nestium$renderPredicates, index);
        return true;
    }
}
