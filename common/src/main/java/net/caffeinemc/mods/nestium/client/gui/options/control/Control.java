package net.caffeinemc.mods.nestium.client.gui.options.control;

import net.caffeinemc.mods.nestium.client.gui.options.Option;
import net.caffeinemc.mods.nestium.client.util.Dim2i;

public interface Control<T> {
    Option<T> getOption();

    ControlElement<T> createElement(Dim2i dim);

    int getMaxWidth();
}
