package net.ender.ess_requiem.entity.mobs.bone_maggot;

import net.ender.ess_requiem.EndersSpellsAndStuffRequiem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;

public class BoneMaggotModel extends GeoModel<BoneMaggotEntity> {
    @Override
    public ResourceLocation getModelResource(BoneMaggotEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(EndersSpellsAndStuffRequiem.MOD_ID, "geo/bone_maggot.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource (BoneMaggotEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(EndersSpellsAndStuffRequiem.MOD_ID, "textures/entity/bone_maggot.png");
    }

    @Override
    public void setCustomAnimations(BoneMaggotEntity animatable, long instanceId, AnimationState<BoneMaggotEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
    }

    @Override
    public ResourceLocation getAnimationResource(BoneMaggotEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(EndersSpellsAndStuffRequiem.MOD_ID, "animations/entity/bone_maggot_animation.json");
    }

}
