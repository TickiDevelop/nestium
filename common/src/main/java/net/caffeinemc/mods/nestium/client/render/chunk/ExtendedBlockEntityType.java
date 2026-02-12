package net.caffeinemc.mods.nestium.client.render.chunk;

import net.caffeinemc.mods.nestium.api.blockentity.BlockEntityRenderPredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Predicate;

@SuppressWarnings("unchecked")
public interface ExtendedBlockEntityType<T extends BlockEntity> {
    BlockEntityRenderPredicate<T>[] nestium$getRenderPredicates();

    void nestium$addRenderPredicate(BlockEntityRenderPredicate<T> shouldAddRenderer);

    boolean nestium$removeRenderPredicate(BlockEntityRenderPredicate<T> shouldAddRenderer);

    static <T extends BlockEntity> boolean shouldRender(BlockEntityType<? extends T> type, BlockGetter blockGetter, BlockPos blockPos, T entity) {
       BlockEntityRenderPredicate<T>[] predicates = ((ExtendedBlockEntityType<T>) type).nestium$getRenderPredicates();

        for (int i = 0; i < predicates.length; i++) {
            if (!predicates[i].shouldRender(blockGetter, blockPos, entity)) {
                return false;
            }
        }

        return true;
    }

    static <T extends BlockEntity> void addRenderPredicate(BlockEntityType<T> type, BlockEntityRenderPredicate<T> predicate) {
        ((ExtendedBlockEntityType<T>) type).nestium$addRenderPredicate(predicate);
    }

    static <T extends BlockEntity> boolean removeRenderPredicate(BlockEntityType<T> type, BlockEntityRenderPredicate<T> predicate) {
        return ((ExtendedBlockEntityType<T>) type).nestium$removeRenderPredicate(predicate);
    }
}
