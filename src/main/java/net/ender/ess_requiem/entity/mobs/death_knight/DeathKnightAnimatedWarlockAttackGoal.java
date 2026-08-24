package net.ender.ess_requiem.entity.mobs.death_knight;

import io.redspace.ironsspellbooks.entity.mobs.wizards.GenericAnimatedWarlockAttackGoal;
import net.ender.ess_requiem.registries.GGSoundRegistry;

public class DeathKnightAnimatedWarlockAttackGoal  extends GenericAnimatedWarlockAttackGoal<DeathKnightEntity> {
    final DeathKnightEntity deathKnight;

    public DeathKnightAnimatedWarlockAttackGoal(DeathKnightEntity entity, double pSpeedModifier, int minAttackInterval, int maxAttackInterval) {
        super(entity, pSpeedModifier, minAttackInterval, maxAttackInterval);
        this.deathKnight = entity;
        this.wantsToMelee = true;
    }

    @Override
    public void playSwingSound() {
        deathKnight.playSound(GGSoundRegistry.NIGHTMARE_ATTACK.get(), 10.0F, 2);
    }

}
