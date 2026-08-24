package net.ender.ess_requiem.entity.spells.blood_domain;

import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.mobs.IMagicSummon;
import io.redspace.ironsspellbooks.registries.ParticleRegistry;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import net.acetheeldritchking.aces_spell_utils.entity.spells.AbstractDomainEntity;
import net.acetheeldritchking.aces_spell_utils.network.AddShaderEffectPacket;
import net.ender.ess_requiem.EndersSpellsAndStuffRequiem;
import net.ender.ess_requiem.effects.BloodDomainEffect;
import net.ender.ess_requiem.registries.GGEffectRegistry;
import net.ender.ess_requiem.registries.GGEntityRegistry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.ArrayList;
import java.util.List;

public class DecayDomain extends AbstractDomainEntity implements GeoEntity {
    private int duration = 500;
    private final int radius = 40;
    private int setSpawnAninTime = 40;


    public DecayDomain(Level level, Entity shooter, int radius, int refinement, int duration) {
        this(GGEntityRegistry.DECAY_DOMAIN.get(), level);
        //the parts that won't change per-cast

        this.setOpen(true);
        this.setOwner(shooter);
        this.setRadius(radius);
        this.setRefinement(refinement);
        this.setDuration(duration);
    }




    public DecayDomain(EntityType<DecayDomain> decayDomainEntityType, Level level) {
        super(decayDomainEntityType, level);
    }

    @Override
    public void targetSureHit() {
        final int SUREHIT_BIG_DANGER_RADIUS = 30;
        if (level() instanceof ServerLevel serverLevel && tickCount % 20 == 0) {
            ServerLevel level = serverLevel.getLevel();
            level.getAllEntities().forEach(e -> {
                if (e instanceof LivingEntity living && canTarget(living)) {
                    if (tickCount % 100 == 0) {
                        handleSureHit(living);
                    } else if (level.getEntitiesOfClass(LivingEntity.class, new AABB(e.position().subtract(SUREHIT_BIG_DANGER_RADIUS, SUREHIT_BIG_DANGER_RADIUS, SUREHIT_BIG_DANGER_RADIUS), e.position().add(SUREHIT_BIG_DANGER_RADIUS, SUREHIT_BIG_DANGER_RADIUS, SUREHIT_BIG_DANGER_RADIUS))).stream().noneMatch(player -> player.hasEffect(GGEffectRegistry.BLOOD_DOMAIN))) {
                        handleSureHit(living);
                    }
                }
            });
        }
    }

    @Override
    public void handleSureHit(Entity e) {
        if (e instanceof LivingEntity livingEntity && !livingEntity.hasEffect(GGEffectRegistry.BLOOD_DOMAIN)) {
            float yHeadRot = e.getYHeadRot();
            yHeadRot += 90 * (int) (Math.random() * 5);
            Level level = livingEntity.level();
            for (int i = -5; i <= 5; i++) {
                Vec3 particlePos = e.position();
                particlePos = particlePos.add(0, livingEntity.getBbHeight() / 2, 0);
                particlePos = particlePos.add(new Vec3(Math.cos(yHeadRot) * 0.3, 0.3, -Math.sin(yHeadRot) * 0.3).scale(i));
                if (i % 2 == 0) {
                    MagicManager.spawnParticles(level, ParticleRegistry.BLOOD_PARTICLE.get(), particlePos.x, particlePos.y - 0.5, particlePos.z, 1, 0, 0, 0, 0, false);
                }

            }


            if (livingEntity instanceof IMagicSummon) {
                var summoner = ((IMagicSummon) livingEntity).getSummoner();
                if (summoner instanceof ServerPlayer player && player.hasEffect(GGEffectRegistry.BLOOD_DOMAIN)) {
                   livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 100, 0));
                } else {
                    livingEntity.addEffect(new MobEffectInstance(GGEffectRegistry.HORDE_WEAKNESS, 100, 0));
                    //livingEntity.hurt(livingEntity.damageSources().wither(), 3);
                }
            } else {
                livingEntity.addEffect(new MobEffectInstance(GGEffectRegistry.HORDE_WEAKNESS, 100, 0));
                livingEntity.hurt(livingEntity.damageSources().wither(), 4);
               // if (livingEntity instanceof ServerPlayer serverPlayer) {
                 //   PacketDistributor.sendToPlayer(serverPlayer, new AddShaderEffectPacket(EndersSpellsAndStuffRequiem.MOD_ID, "shaders/red_tint.json"));

               // }

            }
        }
    }

    @Override
    public void tick() {
        super.tick();
        {
            List<Entity> trackingEntities = level().getEntities(null, new AABB(position().add(radius / 2f, radius / 2f, radius / 2f), position().subtract(radius / 2f, radius / 2f, radius / 2f)));
            trackingEntities.remove(getOwner());
            trackingEntities.remove(this);
            for (AbstractDomainEntity entity : getClashingWith()) {
                trackingEntities.remove(entity.getOwner());
                trackingEntities.remove(entity);
            }
            for (Entity entity : trackingEntities) {
                if (!DamageSources.isFriendlyFireBetween(getOwner(), entity) && getClashingWith().isEmpty()) {
                    float distance = (float) position().distanceTo(entity.position());
                    if (distance > radius || distance < radius / 2f) {
                        continue;
                    }


                }
            }

            var owner = this.getOwner();
            if (owner instanceof ServerPlayer) {
                ((ServerPlayer) owner).addEffect(new MobEffectInstance(GGEffectRegistry.BLOOD_DOMAIN, 100));
              //  if (owner instanceof ServerPlayer serverPlayer)
               // {
                    //PacketDistributor.sendToPlayer(serverPlayer, new AddShaderEffectPacket(EndersSpellsAndStuffRequiem.MOD_ID, "shaders/red_tint.json"));

               // }

            }




        }



        if(tickCount > (getDuration()) + 50 - getTimeSpentClashing())
        {
            destroyDomain();
        }

    }


    public void setSpawnAnimTime(int setSpawnAninTime) {
        this.setSpawnAninTime = setSpawnAninTime;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }





    private final AnimationController<DecayDomain> animationController = new AnimationController<>(this, "controller", 0, this::predicate);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(animationController);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
    private PlayState predicate(AnimationState<DecayDomain> event){


        if(tickCount < getDuration() ) {

            event.getController().setAnimation(RawAnimation.begin().thenPlayAndHold("expand"));
        }
        else if (tickCount > getDuration() ){

            event.getController().setAnimation(RawAnimation.begin().thenPlayAndHold("close"));
        }
        return PlayState.CONTINUE;
    }

}