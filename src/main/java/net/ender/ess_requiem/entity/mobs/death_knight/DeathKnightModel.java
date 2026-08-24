package net.ender.ess_requiem.entity.mobs.death_knight;

import net.ender.ess_requiem.EndersSpellsAndStuffRequiem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;

public class DeathKnightModel extends GeoModel<DeathKnightEntity> {
    @Override
    public ResourceLocation getModelResource(DeathKnightEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(EndersSpellsAndStuffRequiem.MOD_ID, "geo/death_knight.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource (DeathKnightEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(EndersSpellsAndStuffRequiem.MOD_ID, "textures/entity/death_knight.png");
    }

    @Override
    public void setCustomAnimations(DeathKnightEntity animatable, long instanceId, AnimationState<DeathKnightEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
    }

    @Override
    public ResourceLocation getAnimationResource(DeathKnightEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(EndersSpellsAndStuffRequiem.MOD_ID, "animations/entity/death_knight.animation.json");
    }

}
