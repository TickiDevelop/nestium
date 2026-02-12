package net.caffeinemc.mods.nestium.client.gui.screen;

import net.caffeinemc.mods.nestium.client.NestiumClientMod;
import net.caffeinemc.mods.nestium.client.console.Console;
import net.caffeinemc.mods.nestium.client.console.message.MessageLevel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Function;

public class ConfigCorruptedScreen extends Screen {
    private static final int BUTTON_WIDTH = 140;
    private static final int BUTTON_HEIGHT = 20;

    private static final int SCREEN_PADDING = 32;

    private final @Nullable Screen prevScreen;
    private final Function<Screen, Screen> nextScreen;

    public ConfigCorruptedScreen(@Nullable Screen prevScreen, @Nullable Function<Screen, Screen> nextScreen) {
        super(Component.translatable("nestium.config_corrupted.title"));

        this.prevScreen = prevScreen;
        this.nextScreen = nextScreen;
    }

    @Override
    protected void init() {
        super.init();

        int buttonY = this.height - SCREEN_PADDING - BUTTON_HEIGHT;

        this.addRenderableWidget(Button.builder(Component.translatable("nestium.config_corrupted.continue"), (btn) -> {
            Console.instance().logMessage(MessageLevel.INFO, "nestium.console.config_file_was_reset", true, 3.0);

            NestiumClientMod.restoreDefaultOptions();
            Minecraft.getInstance().setScreen(this.nextScreen.apply(this.prevScreen));
        }).bounds(this.width - SCREEN_PADDING - BUTTON_WIDTH, buttonY, BUTTON_WIDTH, BUTTON_HEIGHT).build());

        this.addRenderableWidget(Button.builder(Component.translatable("nestium.config_corrupted.go_back"), (btn) -> {
            Minecraft.getInstance().setScreen(this.prevScreen);
        }).bounds(SCREEN_PADDING, buttonY, BUTTON_WIDTH, BUTTON_HEIGHT).build());
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        super.render(graphics, mouseX, mouseY, delta);

        graphics.drawString(this.font, Component.translatable("nestium.config_corrupted.header"), 32, 32, 0xffffff);
        graphics.drawString(this.font, Component.translatable("nestium.config_corrupted.sub_header"), 32, 48, 0xff0000);

        List<FormattedCharSequence> lines = this.font.split(Component.translatable("nestium.config_corrupted.body"), this.width - 64);

        for (int i = 0; i < lines.size(); i++) {
            graphics.drawString(this.font, lines.get(i), 32, 68 + (i * 12), 0xffffff);
        }
    }
}
