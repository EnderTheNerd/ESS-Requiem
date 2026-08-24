package net.ender.ess_requiem.entity.spells.blood_domain;

import net.ender.ess_requiem.EndersSpellsAndStuffRequiem;
import net.ender.ess_requiem.entity.mobs.bone_maggot.BoneMaggotEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class DecayDomainModel extends DefaultedEntityGeoModel<DecayDomain> {
    public DecayDomainModel(ResourceLocation assetSubpath) {
        super(assetSubpath);
    }

    @Override
    public ResourceLocation getModelResource(DecayDomain animatable) {
        return ResourceLocation.fromNamespaceAndPath(EndersSpellsAndStuffRequiem.MOD_ID, "geo/blood_domain_outer.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource (DecayDomain animatable) {
        return ResourceLocation.fromNamespaceAndPath(EndersSpellsAndStuffRequiem.MOD_ID, "textures/entity/blood_domain/blood_domain.png");
    }

    @Override
    public void setCustomAnimations(DecayDomain animatable, long instanceId, AnimationState<DecayDomain> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
    }

    @Override
    public ResourceLocation getAnimationResource(DecayDomain animatable) {
        return ResourceLocation.fromNamespaceAndPath(EndersSpellsAndStuffRequiem.MOD_ID, "animations/entity/domain_expand.animation.json");
    }
}
