package net.ender.ess_requiem.entity.mobs.vessel_skeleton;

import io.redspace.ironsspellbooks.entity.mobs.wizards.GenericAnimatedWarlockAttackGoal;

import io.redspace.ironsspellbooks.registries.SoundRegistry;
import net.ender.ess_requiem.entity.mobs.nightmare.NightmareEntity;
import net.ender.ess_requiem.registries.GGSoundRegistry;


public class VesselSkeletonSounds  extends GenericAnimatedWarlockAttackGoal<VesselSkeletonEntity> {
    final VesselSkeletonEntity skeleton;
    public VesselSkeletonSounds(VesselSkeletonEntity entity, double pSpeedModifier, int minAttackInterval, int maxAttackInterval) {
        super(entity, pSpeedModifier, minAttackInterval, maxAttackInterval);
        this.skeleton = entity;
        this.wantsToMelee = true;
    }

    @Override
    public void playSwingSound() {
        skeleton.playSound(SoundRegistry.DEAD_KING_HIT.get(), 6.0F, 1.2F);
    }
}
