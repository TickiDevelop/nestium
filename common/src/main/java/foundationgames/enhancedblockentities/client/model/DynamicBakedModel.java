package foundationgames.enhancedblockentities.client.model;

import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public class DynamicBakedModel implements BakedModel, FabricBakedModel {
    private final BakedModel[] models;
    private final ModelSelector selector;
    private final DynamicModelEffects effects;

    private final ThreadLocal<int[]> activeModelIndices;

    public DynamicBakedModel(BakedModel[] models, ModelSelector selector, DynamicModelEffects effects) {
        this.models = models;
        this.selector = selector;
        this.effects = effects;

        this.activeModelIndices = ThreadLocal.withInitial(() -> new int[selector.displayedModelCount()]);
    }

    @Override
    public boolean isVanillaAdapter() {
        return false;
    }

    @Override
    public void emitBlockQuads(BlockAndTintGetter blockView, BlockState state, BlockPos pos, Supplier<RandomSource> randomSupplier, RenderContext context) {
        int[] indices = this.activeModelIndices.get();
        selector.writeModelIndices(blockView, state, pos, indices);

        for (int i = 0; i < indices.length; i++) {
            int modelIndex = indices[i];
            BakedModel model = (modelIndex >= 0 && modelIndex < models.length) ? models[modelIndex] : null;
            if (model != null) {
                if (model instanceof FabricBakedModel fabricModel && !fabricModel.isVanillaAdapter()) {
                    fabricModel.emitBlockQuads(blockView, state, pos, randomSupplier, context);
                } else {
                    context.fallbackConsumer().accept(model);
                }
            }
        }
    }

    @Override
    public void emitItemQuads(ItemStack stack, Supplier<RandomSource> randomSupplier, RenderContext context) {
        int[] indices = this.activeModelIndices.get();
        selector.writeModelIndicesWithoutContext(null, randomSupplier.get(), indices);

        for (int i = 0; i < indices.length; i++) {
            int modelIndex = indices[i];
            BakedModel model = (modelIndex >= 0 && modelIndex < models.length) ? models[modelIndex] : null;
            if (model != null) {
                if (model instanceof FabricBakedModel fabricModel && !fabricModel.isVanillaAdapter()) {
                    fabricModel.emitItemQuads(stack, randomSupplier, context);
                } else {
                    context.fallbackConsumer().accept(model);
                }
            }
        }
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction direction, RandomSource random) {
        if (models.length > 0 && models[0] != null) {
            return models[0].getQuads(state, direction, random);
        }
        return Collections.emptyList();
    }

    @Override
    public boolean useAmbientOcclusion() {
        return getEffects().ambientOcclusion();
    }

    @Override
    public boolean isGui3d() {
        return false;
    }

    @Override
    public boolean usesBlockLight() {
        return false;
    }

    @Override
    public boolean isCustomRenderer() {
        return false;
    }

    @Override
    public TextureAtlasSprite getParticleIcon() {
        if (models.length > 0 && models[0] != null) {
            return models[0].getParticleIcon();
        }
        // Return null or a default sprite if possible, but BakedModel expects non-null usually.
        // Assuming models is never empty if correctly initialized.
        return null; 
    }

    @Override
    public ItemTransforms getTransforms() {
        if (models.length > 0 && models[0] != null) {
            return models[0].getTransforms();
        }
        return ItemTransforms.NO_TRANSFORMS;
    }

    @Override
    public ItemOverrides getOverrides() {
        if (models.length > 0 && models[0] != null) {
            return models[0].getOverrides();
        }
        return ItemOverrides.EMPTY;
    }

    public DynamicModelEffects getEffects() {
        return effects;
    }
}