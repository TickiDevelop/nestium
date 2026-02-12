package foundationgames.enhancedblockentities.mixin;

import foundationgames.enhancedblockentities.client.model.ModelIdentifiers;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ModelBakery.class)
public abstract class ModelBakeryMixin {
    @Shadow protected abstract UnbakedModel getModel(ResourceLocation location);

    @Inject(method = "<init>", at = @At("RETURN"), remap = false)
    private void ebe$loadModels(CallbackInfo ci) {
       ModelIdentifiers.registerModels(this::getModel);
       foundationgames.enhancedblockentities.client.model.ModelRegistrationHandler.load(this::getModel);
    }
}
