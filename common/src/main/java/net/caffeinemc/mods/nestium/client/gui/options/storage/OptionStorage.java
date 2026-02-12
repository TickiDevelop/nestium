package net.caffeinemc.mods.nestium.client.gui.options.storage;

public interface OptionStorage<T> {
    T getData();

    void save();
}
