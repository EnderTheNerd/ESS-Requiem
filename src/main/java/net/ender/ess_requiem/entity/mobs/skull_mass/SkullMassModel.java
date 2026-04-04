package net.ender.ess_requiem.entity.mobs.skull_mass;

import net.ender.ess_requiem.EndersSpellsAndStuffRequiem;
import net.ender.ess_requiem.entity.mobs.hopping_skull.HoppingSkullEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.model.GeoModel;

public class SkullMassModel extends GeoModel<SkullMassEntity> {

    @Override
    public ResourceLocation getModelResource(SkullMassEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(EndersSpellsAndStuffRequiem.MOD_ID, "geo/skull_mass.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SkullMassEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(EndersSpellsAndStuffRequiem.MOD_ID, "textures/entity/skull_mass.png");
    }


    @Override
    public void setCustomAnimations(SkullMassEntity animatable, long instanceId, AnimationState<SkullMassEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
    }

    @Override
    public ResourceLocation getAnimationResource(SkullMassEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(EndersSpellsAndStuffRequiem.MOD_ID, "animations/entity/skull_mass_animation.json");
    }



}


