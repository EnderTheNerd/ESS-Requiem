package net.ender.ess_requiem.entity.mobs.hopping_skull;


import net.ender.ess_requiem.EndersSpellsAndStuffRequiem;
import net.ender.ess_requiem.entity.mobs.nightmare.NightmareEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.model.GeoModel;

public class HoppingSkullModel extends GeoModel<HoppingSkullEntity> {

    @Override
    public ResourceLocation getModelResource(HoppingSkullEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(EndersSpellsAndStuffRequiem.MOD_ID, "geo/hopping_skull.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(HoppingSkullEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(EndersSpellsAndStuffRequiem.MOD_ID, "textures/entity/hopping_skull.png");
    }


    @Override
    public void setCustomAnimations(HoppingSkullEntity animatable, long instanceId, AnimationState<HoppingSkullEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
    }

    @Override
    public ResourceLocation getAnimationResource(HoppingSkullEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(EndersSpellsAndStuffRequiem.MOD_ID, "animations/entity/hopping_skull_animation.json");
    }



}
