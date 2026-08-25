package net.ender.ess_requiem.entity.mobs.bone_maggot;

import io.redspace.ironsspellbooks.entity.mobs.wizards.GenericAnimatedWarlockAttackGoal;

import net.ender.ess_requiem.registries.GGSoundRegistry;
import net.minecraft.sounds.SoundEvents;

public class MaggotWarlockSounds extends GenericAnimatedWarlockAttackGoal<BoneMaggotEntity> {

    final BoneMaggotEntity maggot;

    public MaggotWarlockSounds(BoneMaggotEntity entity, double pSpeedModifier, int minAttackInterval, int maxAttackInterval) {
        super(entity, pSpeedModifier, minAttackInterval, maxAttackInterval);
        this.maggot = entity;
        this.wantsToMelee = true;
    }

    @Override
    public void playSwingSound() {
        maggot.playSound(SoundEvents.PHANTOM_BITE, 3.0F, 7);
    }

}
