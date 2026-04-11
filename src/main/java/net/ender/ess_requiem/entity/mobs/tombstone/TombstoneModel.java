package net.ender.ess_requiem.entity.mobs.tombstone;

import net.ender.ess_requiem.EndersSpellsAndStuffRequiem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;

public class TombstoneModel extends GeoModel<TombstoneEntity> {

    @Override
    public ResourceLocation getModelResource(TombstoneEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(EndersSpellsAndStuffRequiem.MOD_ID, "geo/tombstone.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource (TombstoneEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(EndersSpellsAndStuffRequiem.MOD_ID, "textures/entity/tombstone.png");
    }

    @Override
    public void setCustomAnimations(TombstoneEntity animatable, long instanceId, AnimationState<TombstoneEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
    }

    @Override
    public ResourceLocation getAnimationResource(TombstoneEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(EndersSpellsAndStuffRequiem.MOD_ID, "animations/entity/tombstone_animation.json");
    }

}
