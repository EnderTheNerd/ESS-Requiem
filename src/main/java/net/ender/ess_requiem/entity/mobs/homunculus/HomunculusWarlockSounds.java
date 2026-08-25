package net.ender.ess_requiem.entity.mobs.homunculus;

import io.redspace.ironsspellbooks.entity.mobs.wizards.GenericAnimatedWarlockAttackGoal;
import net.ender.ess_requiem.entity.mobs.bone_maggot.BoneMaggotEntity;
import net.minecraft.sounds.SoundEvents;


public class HomunculusWarlockSounds extends GenericAnimatedWarlockAttackGoal<HomunculusEntity> {


    final HomunculusEntity homunculus;

    public HomunculusWarlockSounds(HomunculusEntity entity, double pSpeedModifier, int minAttackInterval, int maxAttackInterval) {
        super(entity, pSpeedModifier, minAttackInterval, maxAttackInterval);
        this.homunculus = entity;
        this.wantsToMelee = true;
    }

    @Override
    public void playSwingSound() {
        homunculus.playSound(SoundEvents.DOLPHIN_ATTACK, 3.0F, .5F);
    }

}
