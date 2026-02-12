package net.caffeinemc.mods.nestium.client.gui.options.binding;

public interface OptionBinding<S, T> {
    void setValue(S storage, T value);

    T getValue(S storage);
}
