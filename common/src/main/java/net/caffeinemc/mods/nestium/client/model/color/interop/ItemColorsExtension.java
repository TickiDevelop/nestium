package net.caffeinemc.mods.nestium.client.model.color.interop;

import net.minecraft.client.color.item.ItemColor;
import net.minecraft.world.item.ItemStack;

public interface ItemColorsExtension {
    ItemColor nestium$getColorProvider(ItemStack stack);
}
