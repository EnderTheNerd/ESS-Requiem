package net.ender.ess_requiem.entity.mobs.homunculus;

import net.ender.ess_requiem.EndersSpellsAndStuffRequiem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;

public class HomunculusModel extends GeoModel<HomunculusEntity> {
    @Override
    public ResourceLocation getModelResource(HomunculusEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(EndersSpellsAndStuffRequiem.MOD_ID, "geo/son.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource (HomunculusEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(EndersSpellsAndStuffRequiem.MOD_ID, "textures/entity/son.png");
    }

    @Override
    public void setCustomAnimations(HomunculusEntity animatable, long instanceId, AnimationState<HomunculusEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
    }

    @Override
    public ResourceLocation getAnimationResource(HomunculusEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(EndersSpellsAndStuffRequiem.MOD_ID, "animations/entity/son.animation.json");
    }

}
