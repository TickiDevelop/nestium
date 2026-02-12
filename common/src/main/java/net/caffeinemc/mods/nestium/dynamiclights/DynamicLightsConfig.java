package net.caffeinemc.mods.nestium.dynamiclights;

import net.caffeinemc.mods.nestium.client.NestiumClientMod;
import java.util.Map;

public class DynamicLightsConfig {

    public static final DynamicLightsModeOption DYNAMIC_LIGHTS_MODE = new DynamicLightsModeOption();
    public static final BooleanOption ENTITIES_LIGHT_SOURCE = new BooleanOption() {
        @Override public boolean get() { return NestiumClientMod.options().dynamicLights.entities; }
        @Override public void set(boolean val) { NestiumClientMod.options().dynamicLights.entities = val; }
    };
    public static final BooleanOption SELF_LIGHT_SOURCE = new BooleanOption() {
        @Override public boolean get() { return NestiumClientMod.options().dynamicLights.self; }
        @Override public void set(boolean val) { NestiumClientMod.options().dynamicLights.self = val; }
    };
    public static final BooleanOption BLOCK_ENTITIES_LIGHT_SOURCE = new BooleanOption() {
        @Override public boolean get() { return NestiumClientMod.options().dynamicLights.blockEntities; }
        @Override public void set(boolean val) { NestiumClientMod.options().dynamicLights.blockEntities = val; }
    };
    public static final BooleanOption WATER_SENSITIVE_CHECK = new BooleanOption() {
        @Override public boolean get() { return NestiumClientMod.options().dynamicLights.waterSensitiveCheck; }
        @Override public void set(boolean val) { NestiumClientMod.options().dynamicLights.waterSensitiveCheck = val; }
    };
    public static final ExplosiveLightingModeOption CREEPER_LIGHTING_MODE = new ExplosiveLightingModeOption() {
        @Override public ExplosiveLightingMode get() { return NestiumClientMod.options().dynamicLights.creeper; }
        @Override public void set(ExplosiveLightingMode val) { NestiumClientMod.options().dynamicLights.creeper = val; }
    };
    public static final ExplosiveLightingModeOption TNT_LIGHTING_MODE = new ExplosiveLightingModeOption() {
        @Override public ExplosiveLightingMode get() { return NestiumClientMod.options().dynamicLights.tnt; }
        @Override public void set(ExplosiveLightingMode val) { NestiumClientMod.options().dynamicLights.tnt = val; }
    };
    
    // Instance methods for compatibility
    public DynamicLightsMode getDynamicLightsMode() { return DYNAMIC_LIGHTS_MODE.get(); }
    public BooleanOption getEntitiesLightSource() { return ENTITIES_LIGHT_SOURCE; }
    public BooleanOption getSelfLightSource() { return SELF_LIGHT_SOURCE; }
    public BooleanOption getBlockEntitiesLightSource() { return BLOCK_ENTITIES_LIGHT_SOURCE; }
    public BooleanOption getWaterSensitiveCheck() { return WATER_SENSITIVE_CHECK; }
    public ExplosiveLightingModeOption getCreeperLightingMode() { return CREEPER_LIGHTING_MODE; }
    public ExplosiveLightingModeOption getTntLightingMode() { return TNT_LIGHTING_MODE; }

    public static class DynamicLightsModeOption {
        public DynamicLightsMode get() { return NestiumClientMod.options().dynamicLights.mode; }
        public void set(DynamicLightsMode val) { NestiumClientMod.options().dynamicLights.mode = val; }
    }
    
    public abstract static class BooleanOption {
        public abstract boolean get();
        public abstract void set(boolean val);
    }
    
    public abstract static class ExplosiveLightingModeOption {
        public abstract ExplosiveLightingMode get();
        public abstract void set(ExplosiveLightingMode val);
    }
}
