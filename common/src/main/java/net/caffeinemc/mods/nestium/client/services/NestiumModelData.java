package net.caffeinemc.mods.nestium.client.services;

/**
 * Template class for the platform's model data. This is used to pass around Forge model data in a multiloader environment seamlessly.
 */
public interface NestiumModelData {
    NestiumModelData EMPTY = PlatformModelAccess.getInstance().getEmptyModelData();
}
