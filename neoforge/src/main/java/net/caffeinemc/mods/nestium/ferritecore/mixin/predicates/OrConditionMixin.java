package net.caffeinemc.mods.nestium.ferritecore.mixin.predicates;

import net.caffeinemc.mods.nestium.ferritecore.impl.Deduplicator;
import net.caffeinemc.mods.nestium.ferritecore.util.PredicateHelper;
import net.minecraft.client.renderer.block.model.multipart.Condition;
import net.minecraft.client.renderer.block.model.multipart.OrCondition;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.function.Predicate;

@Mixin(value = OrCondition.class, priority = 2000)
public class OrConditionMixin {
    @Shadow
    @Final
    private Iterable<? extends Condition> conditions;

    /**
     * @reason Use cached result predicates
     * @author malte0811
     */
    @Overwrite
    public Predicate<BlockState> getPredicate(StateDefinition<Block, BlockState> stateContainer) {
        return Deduplicator.or(PredicateHelper.toCanonicalList(conditions, stateContainer));
    }
}
