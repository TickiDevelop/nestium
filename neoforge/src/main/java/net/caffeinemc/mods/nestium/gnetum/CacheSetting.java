package net.caffeinemc.mods.nestium.gnetum;

import net.caffeinemc.mods.nestium.gnetum.util.AnyBooleanValue;
import net.caffeinemc.mods.nestium.gnetum.util.TriStateBoolean;

public class CacheSetting {
    public TriStateBoolean enabled;
    public int pass;
    public transient boolean hidden;

    public CacheSetting(int pass) {
        this(pass, new TriStateBoolean(AnyBooleanValue.AUTO));
    }

    public CacheSetting(int pass, TriStateBoolean enabled) {
        this.enabled = enabled;
        this.pass = pass;
    }
}
