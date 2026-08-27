package net.ender.ess_requiem.entity.mobs.vessel_skeleton;


import net.ender.ess_requiem.EndersSpellsAndStuffRequiem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;

public class VesselSkeletonModel extends GeoModel<VesselSkeletonEntity> {

    @Override
    public ResourceLocation getModelResource(VesselSkeletonEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(EndersSpellsAndStuffRequiem.MOD_ID, "geo/vessel_skeleton.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource (VesselSkeletonEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(EndersSpellsAndStuffRequiem.MOD_ID, "textures/entity/vessel_skeleton.png");
    }

    @Override
    public void setCustomAnimations(VesselSkeletonEntity animatable, long instanceId, AnimationState<VesselSkeletonEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
    }

    @Override
    public ResourceLocation getAnimationResource(VesselSkeletonEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(EndersSpellsAndStuffRequiem.MOD_ID, "animations/entity/vessel_skeleton.animation.json");
    }

}
