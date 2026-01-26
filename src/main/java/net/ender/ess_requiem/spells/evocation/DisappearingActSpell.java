package net.ender.ess_requiem.spells.evocation;

import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.registries.MobEffectRegistry;
import io.redspace.ironsspellbooks.registries.ParticleRegistry;
import net.ender.ess_requiem.EndersSpellsAndStuffRequiem;
import net.ender.ess_requiem.particle.particle_managers.GGCylinderPManager;
import net.ender.ess_requiem.particle.particle_managers.GGParticleDirection;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Optional;

public class DisappearingActSpell extends AbstractSpell {
    private final ResourceLocation spellId = ResourceLocation.fromNamespaceAndPath(EndersSpellsAndStuffRequiem.MOD_ID, "disappearing_act");

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                Component.translatable("ui.irons_spellbooks.effect_length", Utils.timeFromTicks(getSpellPower(spellLevel, caster) * 20, 1)),
                Component.translatable("ui.irons_spellbooks.radius", Utils.stringTruncation(getRadius(spellLevel, caster), 2)));


    }


    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.RARE)
            .setSchoolResource(SchoolRegistry.EVOCATION_RESOURCE)
            .setMaxLevel(6)
            .setCooldownSeconds(30)
            .build();

    public DisappearingActSpell() {
        this.manaCostPerLevel = 5;
        this.baseSpellPower = 10;
        this.spellPowerPerLevel = 2;
        this.castTime = 30;
        this.baseManaCost = 75;
    }

    @Override
    public CastType getCastType() {
        return CastType.LONG;
    }

    @Override
    public DefaultConfig getDefaultConfig() {
        return defaultConfig;
    }

    @Override
    public ResourceLocation getSpellResource() {
        return spellId;
    }

    @Override
    public Optional<SoundEvent> getCastStartSound() {
        return Optional.of(SoundEvents.EVOKER_CELEBRATE);
    }



    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        float radius = getRadius(spellLevel, entity);
        int i = getDuration(spellLevel, entity);
        GGCylinderPManager.spawnParticles(level, entity, 30 * spellLevel, ParticleTypes.CAMPFIRE_SIGNAL_SMOKE, GGParticleDirection.UPWARD, (double)radius/2, (double)(4 * spellLevel), -1.0D);
        entity.addEffect(new MobEffectInstance(MobEffectRegistry.TRUE_INVISIBILITY, i, getDuration(spellLevel, entity)));  MagicManager.spawnParticles(level, ParticleTypes.LARGE_SMOKE, entity.getX(), entity.getY() + entity.getBbHeight() * .5f, entity.getZ(), 50, entity.getBbWidth() * .5f, entity.getBbHeight() * .5f, entity.getBbWidth() * .5f, .03, false);


        level.getEntities(entity, entity.getBoundingBox().inflate(radius, 4, radius), (target) -> !DamageSources.isFriendlyFireBetween(target, entity) && Utils.hasLineOfSight(level, entity, target, false)).forEach(target -> {
            if (target instanceof LivingEntity livingEntity && livingEntity.distanceToSqr(entity) < radius * radius) {


                livingEntity.addEffect(new MobEffectInstance(MobEffectRegistry.TRUE_INVISIBILITY, i, getDuration(spellLevel, entity)));
                MagicManager.spawnParticles(level, ParticleTypes.LARGE_SMOKE, livingEntity.getX(), livingEntity.getY() + livingEntity.getBbHeight() * .5f, livingEntity.getZ(), 50, livingEntity.getBbWidth() * .5f, livingEntity.getBbHeight() * .5f, livingEntity.getBbWidth() * .5f, .03, false);




            }

            if (Utils.shouldHealEntity(entity, target) && entity.distanceTo(target) <= radius && target instanceof LivingEntity targetEntity) {
                targetEntity.addEffect(new MobEffectInstance(MobEffectRegistry.TRUE_INVISIBILITY, i, (int) getDuration(spellLevel, entity) - 1, false, false, true));

            }
        });


        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    public float getRadius(int spellLevel, LivingEntity caster) {
        return 6 + spellLevel ;
    }

    public int getDuration(int spellLevel, LivingEntity caster) {
        return (int) (getSpellPower(spellLevel, caster) * 20);
    }

    @Override
    public void onServerCastComplete(Level level, int spellLevel, LivingEntity entity, MagicData playerMagicData, boolean cancelled) {
        super.onServerCastComplete(level, spellLevel, entity, playerMagicData, cancelled);
    }

    @Override
    public AnimationHolder getCastStartAnimation() {
        return SpellAnimations.CHARGE_RAISED_HAND;
    }

    @Override
    public AnimationHolder getCastFinishAnimation() {
        return SpellAnimations.SELF_CAST_TWO_HANDS;
    }



}
