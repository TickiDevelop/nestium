package dev.tr7zw.entityculling;

import dev.tr7zw.entityculling.access.EntityRendererInter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class NMSCullingHelper {

    private final static Minecraft MC = Minecraft.getInstance();

    public static boolean ignoresCulling(Entity entity) {
        return entity.noCulling;
    }

    public static AABB getCullingBox(Entity entity) {
        if (entity instanceof ArmorStand armorStand && armorStand.isMarker()) {
            return EntityType.ARMOR_STAND.getDimensions().makeBoundingBox(entity.position());
        }
        return entity.getBoundingBoxForCulling();
    }

    @SuppressWarnings("unchecked")
    public static Vec3 getRenderOffset(EntityRenderer<?> entityRenderer, Entity entity, float tickDelta) {
        return ((EntityRenderer<Entity>) entityRenderer).getRenderOffset(entity, tickDelta);
    }

}
