package net.caffeinemc.mods.nestium.client.gui.options;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

public enum OptionImpact implements TextProvider {
    LOW(ChatFormatting.GREEN, "nestium.option_impact.low"),
    MEDIUM(ChatFormatting.YELLOW, "nestium.option_impact.medium"),
    HIGH(ChatFormatting.GOLD, "nestium.option_impact.high"),
    VARIES(ChatFormatting.WHITE, "nestium.option_impact.varies");

    private final Component text;

    OptionImpact(ChatFormatting formatting, String text) {
        this.text = Component.translatable(text)
                .withStyle(formatting);
    }

    @Override
    public Component getLocalizedName() {
        return this.text;
    }
}
