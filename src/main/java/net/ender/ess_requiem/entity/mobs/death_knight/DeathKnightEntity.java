package net.ender.ess_requiem.entity.mobs.death_knight;

import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.entity.mobs.IAnimatedAttacker;
import io.redspace.ironsspellbooks.entity.mobs.IMagicSummon;
import io.redspace.ironsspellbooks.entity.mobs.abstract_spell_casting_mob.AbstractSpellCastingMob;
import io.redspace.ironsspellbooks.entity.mobs.goals.*;
import io.redspace.ironsspellbooks.entity.mobs.goals.melee.AttackAnimationData;
import io.redspace.ironsspellbooks.entity.mobs.wizards.GenericAnimatedWarlockAttackGoal;
import io.redspace.ironsspellbooks.util.OwnerHelper;
import net.ender.ess_requiem.compat.dte.dte_registry.DTESpellRegistry;
import net.ender.ess_requiem.entity.mobs.nightmare.NightmareAnimatedWarlockAttackGoal;
import net.ender.ess_requiem.entity.mobs.nightmare.NightmareEntity;
import net.ender.ess_requiem.registries.GGEntityRegistry;
import net.ender.ess_requiem.registries.GGSoundRegistry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.neoforged.neoforge.fluids.FluidType;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class DeathKnightEntity extends AbstractSpellCastingMob implements IMagicSummon, IAnimatedAttacker {
    protected LivingEntity cachedSummoner;
    protected UUID summonerUUID;



    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public DeathKnightEntity(Level level, LivingEntity owner) {
        this(GGEntityRegistry.DEATH_KNIGHT.get(), level);
        setSummoner(owner);

    }


    public DeathKnightEntity(EntityType<? extends AbstractSpellCastingMob> entityType, Level world) {
        super(entityType, world);
        SingletonGeoAnimatable.registerSyncedAnimatable(this);
        xpReward = 0;
        this.lookControl = createLookControl();
        this.moveControl = createMoveControl();
    }

    protected MoveControl createMoveControl()
    {
        return new MoveControl(this)
        {
            @Override
            protected float rotlerp(float sourceAngle, float targetAngle, float maximumChange) {
                double x = this.wantedX - this.mob.getX();
                double z = this.wantedZ - this.mob.getZ();

                if (x * x + z * z < 0.5F)
                {
                    return sourceAngle;
                }
                else
                {
                    return super.rotlerp(sourceAngle, targetAngle, maximumChange * 0.25F);
                }
            }
        };
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pSpawnType, @org.jetbrains.annotations.Nullable SpawnGroupData pSpawnGroupData) {

        return super.finalizeSpawn(pLevel, pDifficulty, pSpawnType, pSpawnGroupData);
    }

    @Override
    public boolean canDrownInFluidType(FluidType type) {
        return false;
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    @Override
    public boolean isInvertedHealAndHarm() {
        return true;
    }


    public static AttributeSupplier.Builder createAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(Attributes.ATTACK_DAMAGE, 8)
                .add(Attributes.MAX_HEALTH, 180)
                .add(Attributes.FOLLOW_RANGE, 45.0)
                .add(Attributes.ARMOR, 20)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1)
                .add(Attributes.ENTITY_INTERACTION_RANGE, 3)
                .add(Attributes.MOVEMENT_SPEED, .23);



    }

    @Override
    protected void registerGoals() {

        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new GenericAnimatedWarlockAttackGoal<>(this, 1.5F, 5, 10)
                .setMoveset(List.of(
                        new AttackAnimationData(15, "swipe_sword", 4),
                        new AttackAnimationData(15, "swipe_shield", 4),
                        new AttackAnimationData(15, "uppercut", 4)

                ))

                .setComboChance(2f)
                .setMeleeAttackInverval(3, 8)
                .setMeleeBias(1.0f, 1.0f)
                .setMeleeMovespeedModifier(1.0f));


        this.goalSelector.addGoal(7, new GenericFollowOwnerGoal(this, this::getSummoner, 0.9f, 8, 2, false, 50));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 0.9D));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
        this.targetSelector.addGoal(1, new GenericOwnerHurtByTargetGoal(this, this::getSummoner));
        this.targetSelector.addGoal(2, new GenericOwnerHurtTargetGoal(this, this::getSummoner));
        this.targetSelector.addGoal(3, new GenericCopyOwnerTargetGoal(this, this::getSummoner));
        this.targetSelector.addGoal(4, (new GenericHurtByTargetGoal(this, (entity) -> entity == getSummoner())).setAlertOthers());
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (!pSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY) && shouldIgnoreDamage(pSource)) {
            return false;
        }
        return super.hurt(pSource, pAmount);
    }

    @Override
    public LivingEntity getSummoner() {
        return OwnerHelper.getAndCacheOwner(level(), cachedSummoner, summonerUUID);
    }

    @Override
    public void onUnSummon() {
        if (!this.level().isClientSide) {
            MagicManager.spawnParticles(this.level(), ParticleTypes.ENCHANT,
                    getX(), getY(), getZ(),
                    25, 0.4, 0.8, 0.4, 0.03, false);
            discard();
        }
    }

    @Override
    public void onRemovedFromLevel() {


        super.onRemovedFromLevel();
    }


    //Sounds and Stuff
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.WITHER_SKELETON_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.WITHER_SKELETON_DEATH;
    }

    public void setSummoner(@Nullable LivingEntity owner) {
        if (owner != null) {
            this.summonerUUID = owner.getUUID();
            this.cachedSummoner = owner;
        }
    }

    @Override
    public boolean isAlliedTo(Entity entityIn) {
        return super.isAlliedTo(entityIn) || this.isAlliedHelper(entityIn);
    }


    @Override
    public void die(DamageSource pDamageSource) {
        this.onDeathHelper();
        super.die(pDamageSource);
    }

    // NBT Junk
    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.summonerUUID = OwnerHelper.deserializeOwner(pCompound);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        OwnerHelper.serializeOwner(pCompound, summonerUUID);
    }


    //ANIMATIONS
    RawAnimation animationToPlay = null;
    private final AnimationController<DeathKnightEntity> animationController = new AnimationController<>(this, "controller", 0, this::predicate);
    private final AnimationController<DeathKnightEntity> attackAnimationController = new AnimationController<>(this, "attack_controller", 0, this::attackPredicate);


    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(animationController);
        controllers.add(attackAnimationController);

    }


    private PlayState attackPredicate(software.bernie.geckolib.animation.AnimationState<DeathKnightEntity> event)
    {
        var controller = event.getController();

        if (this.animationToPlay != null)
        {
            controller.forceAnimationReset();
            controller.setAnimation(animationToPlay);
            animationToPlay = null;
        }

        return PlayState.CONTINUE;
    }




    private PlayState predicate(AnimationState<DeathKnightEntity> event)
    {
        if (event.isMoving() && this.animationToPlay == null)
        {
            event.getController().setAnimation(RawAnimation.begin().then("walk", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }
        else if (!event.isMoving() && this.animationToPlay == null)
        {
            event.getController().setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        return PlayState.STOP;
    }



    @Override
    public void playAnimation(String animationId) {
        try {
            animationToPlay = RawAnimation.begin().thenPlay(animationId);
        } catch (Exception ignored) {
            IronsSpellbooks.LOGGER.error("Entity {} Failed to play animation: {}", this, animationId);
        }
    }

    @Override
    public boolean isAnimating() {
        return animationController.getAnimationState() != AnimationController.State.STOPPED || super.isAnimating();
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public double getTick(Object object) {
        return this.tickCount;
    }




}
