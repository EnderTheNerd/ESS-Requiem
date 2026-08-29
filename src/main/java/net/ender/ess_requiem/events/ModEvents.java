package net.ender.ess_requiem.events;

import dev.shadowsoffire.apothic_attributes.api.ALObjects;
import io.redspace.ironsspellbooks.api.events.*;
import io.redspace.ironsspellbooks.api.magic.MagicData;

import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.capabilities.magic.SummonManager;
import io.redspace.ironsspellbooks.entity.mobs.IMagicSummon;

import io.redspace.ironsspellbooks.entity.mobs.SummonedZombie;
import io.redspace.ironsspellbooks.entity.mobs.dead_king_boss.DeadKingBoss;
import io.redspace.ironsspellbooks.entity.mobs.dead_king_boss.DeadKingSoulEntity;
import io.redspace.ironsspellbooks.entity.mobs.frozen_humanoid.FrozenHumanoid;
import io.redspace.ironsspellbooks.item.weapons.StaffItem;
import io.redspace.ironsspellbooks.item.weapons.StaffTier;
import io.redspace.ironsspellbooks.item.weapons.pyrium_staff.PyriumStaffItem;
import io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions;
import io.redspace.ironsspellbooks.player.SpinAttackType;
import io.redspace.ironsspellbooks.registries.ItemRegistry;
import io.redspace.ironsspellbooks.registries.MobEffectRegistry;
import io.redspace.ironsspellbooks.registries.ParticleRegistry;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import io.redspace.ironsspellbooks.spells.blood.SacrificeSpell;
import io.redspace.ironsspellbooks.spells.eldritch.SculkTentaclesSpell;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.acetheeldritchking.aces_spell_utils.network.RemoveShaderEffectPacket;
import net.ender.ess_requiem.EndersSpellsAndStuffRequiem;
import net.ender.ess_requiem.Util.GGTags;
import net.ender.ess_requiem.compat.dte.dte_registry.DTE_EffectRegistry;
import net.ender.ess_requiem.effects.AdrenalineRushEffect;
import net.ender.ess_requiem.entity.mobs.death_knight.DeathKnightEntity;
import net.ender.ess_requiem.entity.mobs.homunculus.HomunculusEntity;
import net.ender.ess_requiem.entity.mobs.nightmare.NightmareEntity;
import net.ender.ess_requiem.item.sword_tier.BloodWeapons.ArmOfDecay;
import net.ender.ess_requiem.item.sword_tier.BloodWeapons.ScytheOfRottenDreams;
import net.ender.ess_requiem.item.sword_tier.EldritchWeapons.BrokenPromise;
import net.ender.ess_requiem.item.sword_tier.EldritchWeapons.Inevitability;
import net.ender.ess_requiem.item.sword_tier.EldritchWeapons.MidnightEmbrace;
import net.ender.ess_requiem.item.sword_tier.SpellbladeWeapons.IntertwinedPeak;
import net.ender.ess_requiem.item.sword_tier.SpellbladeWeapons.SkyfallsCause;
import net.ender.ess_requiem.item.sword_tier.SpellbladeWeapons.SwiftDemise;
import net.ender.ess_requiem.registries.*;


import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;

import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;

import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileDeflection;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import org.checkerframework.checker.units.qual.A;

import java.util.Objects;

@EventBusSubscriber
public class ModEvents {


    @SubscribeEvent
    public static void modifyModNameDisplay(CustomizeScrollModNameEvent event){
        if(event.getModId().equals(EndersSpellsAndStuffRequiem.MOD_ID)){
            event.setModName(event.getModName().copy().withStyle(ChatFormatting.DARK_RED,ChatFormatting.ITALIC));
        }
    }

    @SubscribeEvent
    public static void CataphractWeaponTransformation(LivingDamageEvent.Pre event) {
        var sourceEntity = event.getSource().getEntity();
        if (sourceEntity instanceof ServerPlayer serverPlayer) {
            ItemStack mainhandItem = ((LivingEntity) serverPlayer).getMainHandItem();

            if (serverPlayer.hasEffect(GGEffectRegistry.EBONY_CATAPHRACT)) {
                if (mainhandItem.getItem() instanceof MidnightEmbrace) {
                    serverPlayer.getInventory().setItem(serverPlayer.getInventory().selected, new ItemStack(GGItemRegistry.BROKEN_PROMISE.get()));
                    serverPlayer.level().playSound(null, serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(), GGSoundRegistry.MIDNIGHT_EMBRACE_GLASS_SHATTER, SoundSource.NEUTRAL, .8F, 1.3F);
                    serverPlayer.displayClientMessage(Component.literal(ChatFormatting.ITALIC + "Your sword shatters as if it was made of glass.")
                            .withStyle(s -> s.withColor(TextColor.fromRgb(10032177))), true);

                }

            }
        }

    }

    @SubscribeEvent
    public static void BloodStaffEvolution(LivingDeathEvent event) {
        var sourceEntity = event.getSource().getEntity();
        var dead_thing = event.getEntity();
        if (sourceEntity instanceof ServerPlayer serverPlayer) {
            if (serverPlayer.hasEffect(GGEffectRegistry.LORD_OF_DECAY)) {
                if (serverPlayer.getMainHandItem().is(ItemRegistry.BLOOD_STAFF) && dead_thing instanceof DeadKingBoss deadKingBoss) {
                    if (deadKingBoss.isOminous()) {
                        serverPlayer.getInventory().setItem(serverPlayer.getInventory().selected, new ItemStack(GGItemRegistry.REQUIEM_STAFF.get()));
                    }

                }

            }
        }

    }






    @SubscribeEvent
    public static void SleepingProtection(LivingIncomingDamageEvent event) {
        var targetEntity = event.getEntity();
        var damager = event.getSource().getDirectEntity();

        if (targetEntity.hasEffect(DTE_EffectRegistry.BLISSFUL_SLEEP)) {
            event.setCanceled(true);
        }
        if (damager instanceof ServerPlayer player && targetEntity.hasEffect(DTE_EffectRegistry.BLISSFUL_SLEEP)) {
            player.displayClientMessage(Component.literal(ChatFormatting.ITALIC + "Protected by the veil of dreams. ")
                    .withStyle(s -> s.withColor(ChatFormatting.DARK_RED)), true);
        }

    }

    @SubscribeEvent
    public static void DeathKnightRage(LivingDeathEvent event){
        var killer = event.getSource();

        if (killer != null && killer.getEntity() instanceof DeathKnightEntity attacker
        ) {

            var effect = attacker.getEffect(GGEffectRegistry.DEATH_KNIGHT_KILL_RAGE);


            if (attacker.hasEffect(GGEffectRegistry.DEATH_KNIGHT_KILL_RAGE)) {
                var amp = effect.getAmplifier();
                attacker.addEffect(new MobEffectInstance(GGEffectRegistry.DEATH_KNIGHT_KILL_RAGE, 10000, amp + 1 ));
                attacker.heal(5F + amp);

            }
            else {
                attacker.addEffect(new MobEffectInstance(GGEffectRegistry.DEATH_KNIGHT_KILL_RAGE, 10000, 0));
                attacker.heal(5F);
            }
        }
    }



    @SubscribeEvent
    public static void HomunculusExplode(LivingDeathEvent event){
    var guy = event.getEntity();

    if (guy instanceof HomunculusEntity) {
        SacrificeSpell spell = (SacrificeSpell) SpellRegistry.SACRIFICE_SPELL.get();


        float explosionRadius = 1.5F;
        LivingEntity owner = SummonManager.getOwner(guy) instanceof LivingEntity livingOwner ? livingOwner : guy;
        float damage = (float) (4 * owner.getAttributeValue(AttributeRegistry.BLOOD_SPELL_POWER));
        SacrificeSpell.doSacrificeExplosion(
                guy.level(),
                spell.getDamageSource(guy, owner),
                damage,
                explosionRadius,
                guy.getBoundingBox().getCenter()
        );
        guy.remove(Entity.RemovalReason.KILLED);
    }

    }




    @SubscribeEvent
    public static void SkillfulCombos(LivingDamageEvent.Pre event) {
        var attacked = event.getEntity();
        var attacker = event.getSource().getDirectEntity();

        if (attacked.hasEffect(GGEffectRegistry.OVERWHELMING_DREAD) && attacked.hasEffect(GGEffectRegistry.SKILLFUL_WOUND) && attacker instanceof ServerPlayer) {
            attacked.removeEffect(GGEffectRegistry.OVERWHELMING_DREAD);
            attacked.removeEffect(GGEffectRegistry.SKILLFUL_WOUND);

            GGSpellRegistry.SLASHING_ABILITY.get().castSpell(event.getEntity().level(), 1, (ServerPlayer) attacker, CastSource.SCROLL, true);

        }

        if (attacked.hasEffect(GGEffectRegistry.OVERWHELMING_DREAD) && attacked.hasEffect(GGEffectRegistry.SHATTERED_WILL) && attacker instanceof ServerPlayer) {
            attacked.removeEffect(GGEffectRegistry.OVERWHELMING_DREAD);
            attacked.removeEffect(GGEffectRegistry.SHATTERED_WILL);

            attacker.hurt(attacked.damageSources().magic(), 30);

            MagicManager.spawnParticles(attacked.level(), ParticleHelper.UNSTABLE_ENDER, attacked.getX(), attacked.getY() + 1, attacked.getZ(), 30, 0, 0, 0, 1, false);
            MagicManager.spawnParticles(attacked.level(), ParticleHelper.COMET_FOG, attacked.getX(), attacked.getY() + 1, attacked.getZ(), 30, 0, 0, 0, 1, false);


        }

        if (attacked.hasEffect(GGEffectRegistry.SKILLFUL_WOUND) && attacked.hasEffect(GGEffectRegistry.SHATTERED_WILL) && attacker instanceof ServerPlayer) {
            attacked.removeEffect(GGEffectRegistry.OVERWHELMING_DREAD);
            attacked.removeEffect(GGEffectRegistry.SHATTERED_WILL);

            event.setNewDamage(event.getOriginalDamage() * 1.5F);


        }

    }


    @SubscribeEvent
    public static void CounterspellShield(CounterSpellEvent event) {
        if (event.target instanceof LivingEntity livingEntity) {
            if (livingEntity.hasEffect(GGEffectRegistry.BANNER_PROTECTION)) {
                event.setCanceled(true);
                MagicManager.spawnParticles(livingEntity.level(), ParticleHelper.FIERY_SPARKS, livingEntity.getX(), livingEntity.getY() + 1, livingEntity.getZ(), 30, 0, 0, 0, 1, false);
                livingEntity.removeEffect(GGEffectRegistry.BANNER_PROTECTION);
                livingEntity.level().playSound(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), GGSoundRegistry.BANNER_SPELL_PARRY, SoundSource.NEUTRAL, .8F, 1.3F);

                if (livingEntity instanceof ServerPlayer player) {
                    player.displayClientMessage(Component.literal(ChatFormatting.ITALIC + "Protected by the Banner")
                            .withStyle(s -> s.withColor(TextColor.fromRgb(14522123))), true);
                }
            }
        }

    }

    @SubscribeEvent
    public static void HighTierSummon(CounterSpellEvent event) {
        if (event.target instanceof LivingEntity livingEntity) {
            if (livingEntity.getType().is(GGTags.HIGH_TIER_SUMMON)) {
                final float MAX_HEALTH = livingEntity.getMaxHealth();
                float baseHealth = livingEntity.getHealth();


                event.setCanceled(true);
                MagicManager.spawnParticles(livingEntity.level(), ParticleHelper.FIERY_SPARKS, livingEntity.getX(), livingEntity.getY() + 1, livingEntity.getZ(), 30, 0, 0, 0, 1, false);
                livingEntity.level().playSound(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), GGSoundRegistry.BANNER_SPELL_PARRY, SoundSource.NEUTRAL, .8F, 1.3F);
                livingEntity.hurt(livingEntity.damageSources().magic(), livingEntity.getMaxHealth() * .25F);

            }
        }

    }


    @SubscribeEvent
    public static void NightmareCounterspellEnrage(CounterSpellEvent event) {
        if (event.target instanceof NightmareEntity livingEntity) {

            event.setCanceled(true);
            livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 100, 1));

            livingEntity.level().playSound(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), GGSoundRegistry.BANNER_SPELL_PARRY, SoundSource.NEUTRAL, .8F, 1.3F);

        }

}

    @SubscribeEvent
    public static void PactAttackDay(LivingDamageEvent.Post event) {
        var attacker = event.getSource().getDirectEntity();
        if (attacker instanceof ServerPlayer livingAttacker && livingAttacker.hasEffect(GGEffectRegistry.UNDEAD_PACT) && isUnderSunTick(attacker.level(), (LivingEntity) attacker)) {
            attacker.setRemainingFireTicks(100);
            livingAttacker.addEffect(new MobEffectInstance(GGEffectRegistry.BANE_OF_THE_DEAD, 60, 0));
            livingAttacker.addEffect(new MobEffectInstance(MobEffects.WITHER, 60, 0));
            livingAttacker.addEffect(new MobEffectInstance(MobEffects.HARM, 1, 0));


        }
    }

    @SubscribeEvent
    public static void VeilAttack(LivingDamageEvent.Pre event) {
        var attacker = event.getSource().getDirectEntity();
        if (attacker instanceof ServerPlayer livingAttacker && livingAttacker.hasEffect(GGEffectRegistry.NIGHT_VEIL) && isUnderMoonTick(attacker.level(), (LivingEntity) attacker) && livingAttacker.hasEffect(MobEffectRegistry.TRUE_INVISIBILITY)) {
            event.setNewDamage(event.getOriginalDamage() * 2.5F);
        }
    }


    @SubscribeEvent
    public static void PactAttackNight(LivingDamageEvent.Post event) {
        var attacker = event.getSource().getDirectEntity();
        if (attacker instanceof ServerPlayer livingAttacker && livingAttacker.hasEffect(GGEffectRegistry.UNDEAD_PACT) && isUnderMoonTick(attacker.level(), (LivingEntity) attacker)) {
            livingAttacker.addEffect(new MobEffectInstance(GGEffectRegistry.UNDEAD_RAMPAGE, 60, 0));
        }
    }

    @SubscribeEvent
    public static void Damned(LivingDamageEvent.Pre event) {
        var attacker = event.getSource().getDirectEntity();
        if (attacker instanceof ServerPlayer livingAttacker && livingAttacker.hasEffect(GGEffectRegistry.DAMNED)) {
            MagicData magicData = MagicData.getPlayerMagicData(livingAttacker);
            if (magicData.getMana() < 100) {
                event.setNewDamage(0);
            }
            magicData.setMana(magicData.getMana() - 25);
        }
    }


    @SubscribeEvent
    public static void BastionOfLight(LivingIncomingDamageEvent event) {
        var livingEntity = event.getEntity();
        if (livingEntity instanceof ServerPlayer player && player.hasEffect(GGEffectRegistry.BASTION_OF_LIGHT)) {
            MagicData magicData = MagicData.getPlayerMagicData(livingEntity);
            if (magicData.getMana() > 350) {
                event.setCanceled(true);
                livingEntity.level().playSound(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), SoundRegistry.CLEANSE_CAST, SoundSource.NEUTRAL, .8F, 1.3F);
                magicData.setMana(magicData.getMana() - (event.getAmount() * 10));
            } else {
                event.setCanceled(false);
                livingEntity.removeEffect(GGEffectRegistry.BASTION_OF_LIGHT);
            }

        }
    }

    @SubscribeEvent
    public static void HordeWeakness(LivingDamageEvent.Pre event) {
        var livingEntity = event.getEntity();
        var attacker = event.getSource().getDirectEntity();
        if (livingEntity.hasEffect(GGEffectRegistry.HORDE_WEAKNESS) && attacker instanceof IMagicSummon) {
            event.setNewDamage(event.getOriginalDamage() * 1.5F);

        }

    }

    @SubscribeEvent
    public static void ParryMelee(LivingIncomingDamageEvent event) {
        var livingEntity = event.getEntity();
        if (livingEntity instanceof ServerPlayer player && player.hasEffect(GGEffectRegistry.PARRYING)) {
            event.setCanceled(true);
            MagicManager.spawnParticles(livingEntity.level(), ParticleHelper.FIERY_SPARKS, livingEntity.getX(), livingEntity.getY() + 1, livingEntity.getZ(), 30, 0, 0, 0, 1, false);
            livingEntity.level().playSound(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), GGSoundRegistry.PARRY, SoundSource.NEUTRAL, .8F, 1.3F);

        }

    }

    @SubscribeEvent
    public static void ParryRanged(ProjectileImpactEvent event) {
        var parried_projectile = event.getProjectile();
        if (event.getRayTraceResult() instanceof EntityHitResult result && result.getEntity() instanceof LivingEntity entity) {
            if (entity.hasEffect(GGEffectRegistry.PARRYING)) {
                event.setCanceled(true);
                entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), GGSoundRegistry.PARRY, SoundSource.NEUTRAL, .8F, 1.3F);
                event.getProjectile().deflect(ProjectileDeflection.AIM_DEFLECT, entity, entity, entity instanceof Player);
            }
        }
    }

    @SubscribeEvent
    public static void CursedRevive(LivingDeathEvent event) {
        if (event.getEntity() instanceof LivingEntity livingEntity) {
            if (livingEntity.hasEffect(GGEffectRegistry.CURSED_IMMORTALITY)) {


                event.setCanceled(true);

                livingEntity.removeEffect(GGEffectRegistry.CURSED_IMMORTALITY);


                livingEntity.setHealth(10.0F);

                livingEntity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 80));
                livingEntity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 80));
                livingEntity.addEffect(new MobEffectInstance(MobEffectRegistry.ABYSSAL_SHROUD, 25));

                livingEntity.level().playSound(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), GGSoundRegistry.CURSED_REVIVE, SoundSource.NEUTRAL, .8F, 1.3F);


                if (event.getEntity() instanceof ServerPlayer player) {
                    player.displayClientMessage(Component.literal(ChatFormatting.ITALIC + "You're not done until I say so.")
                            .withStyle(s -> s.withColor(TextColor.fromRgb(14806476))), true);
                    MagicData magicData = MagicData.getPlayerMagicData(player);

                    magicData.setMana(0);

                }

            }

        }
    }

    @SubscribeEvent
    public static void UndyneReference(LivingDeathEvent event) {
        if (event.getEntity() instanceof LivingEntity livingEntity) {
            if (livingEntity.hasEffect(GGEffectRegistry.SOUL_STRENGTH)) {
                event.setCanceled(true);

                livingEntity.removeEffect(GGEffectRegistry.SOUL_STRENGTH);

                livingEntity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 100));
                livingEntity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 100));
                livingEntity.addEffect(new MobEffectInstance(GGEffectRegistry.FADING, 100));
                livingEntity.addEffect(new MobEffectInstance(GGEffectRegistry.UNDYING_DREAD, 2500));


                livingEntity.setHealth(livingEntity.getMaxHealth());


                livingEntity.level().playSound(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), GGSoundRegistry.SURVIVING, SoundSource.NEUTRAL, .8F, 1.3F);

                if (event.getEntity() instanceof ServerPlayer player) {
                    player.displayClientMessage(Component.literal(ChatFormatting.ITALIC + "Not...Yet..I won't...die..here")
                            .withStyle(s -> s.withColor(TextColor.fromRgb(3020845))), true);
                }

            }

        }
    }

    @SubscribeEvent
    public static void UndyneReference2(MobEffectEvent.Expired event) {
        assert event.getEffectInstance() != null;
        if (event.getEffectInstance().is(GGEffectRegistry.UNDYING_DREAD) && event.getEntity() instanceof ServerPlayer player) {
            player.kill();

            player.displayClientMessage(Component.literal(ChatFormatting.ITALIC + "Even that...wasn't strong enough...")
                    .withStyle(s -> s.withColor(TextColor.fromRgb(13383279))), true);

        }

    }



    @SubscribeEvent
    public static void AdrenalineRush(MobEffectEvent.Expired event) {
        assert event.getEffectInstance() != null;
        if (event.getEffectInstance().is(GGEffectRegistry.ADRENALINE_RUSH) && event.getEntity() instanceof LivingEntity entity) {



            var amplifier = event.getEffectInstance().getAmplifier();


            entity.addEffect(new MobEffectInstance(GGEffectRegistry.ADRENAL_FATIGUE, 4800, amplifier + 1));
        }


    }





    @SubscribeEvent
    public static void FadingDisable(LivingIncomingDamageEvent event) {
        var livingEntity = event.getEntity();
        if (livingEntity instanceof ServerPlayer player && player.hasEffect(GGEffectRegistry.FADING)) {
            event.setCanceled(true);
        }

    }

    @SubscribeEvent
    public static void FadingDisable2(SpellPreCastEvent event) {
        var livingEntity = event.getEntity();
        if (livingEntity instanceof ServerPlayer player && player.hasEffect(GGEffectRegistry.FADING)) {
            event.setCanceled(true);
        }

    }

    @SubscribeEvent
    public static void FadingDisable3(AttackEntityEvent event) {
        var livingEntity = event.getEntity();
        if (livingEntity instanceof ServerPlayer player && player.hasEffect(GGEffectRegistry.FADING)) {
            event.setCanceled(true);
        }

    }

    @SubscribeEvent
    public static void FinalityOfDecay(MobEffectEvent.Expired event) {
        assert event.getEffectInstance() != null;
        if (event.getEffectInstance().is(GGEffectRegistry.FINALITY_OF_DECAY) && event.getEntity() instanceof LivingEntity livingEntity) {


            livingEntity.hurt(livingEntity.damageSources().magic(), 25);
            livingEntity.addEffect(new MobEffectInstance(MobEffects.WITHER, 300, 3));
            livingEntity.addEffect(new MobEffectInstance(GGEffectRegistry.MARK_OF_DECAY, 300, 1));

            livingEntity.playSound(GGSoundRegistry.CLOCK_TICKING.get(), 0.8f, 1.3F);

            if (livingEntity instanceof ServerPlayer serverPlayer) {
                serverPlayer.displayClientMessage(Component.literal(ChatFormatting.ITALIC + "The Clock Strikes Nil")
                        .withStyle(s -> s.withColor(TextColor.fromRgb(15556694))), true);
                serverPlayer.playSound(GGSoundRegistry.CLOCK_TICKING.get(), 0.8f, 1.3F);
            }
        }
    }




    private static boolean isUnderSunTick(Level level, LivingEntity entity) {
        if (level.isDay() && !level.isClientSide) {
            float f = entity.getLightLevelDependentMagicValue();
            BlockPos blockpos = BlockPos.containing(entity.getX(), entity.getEyeY(), entity.getZ());
            boolean flag = entity.isInWaterRainOrBubble() || entity.isInPowderSnow || entity.wasInPowderSnow;
            if (f > 0.5F && !flag && level.canSeeSky(blockpos)) {
                return true;
            }
        }

        return false;
    }

    private static boolean isUnderMoonTick(Level level, LivingEntity entity) {
        if (level.isNight() && !level.isClientSide) {
            return level.isNight();
        } else {
            return false;
        }

    }

    @SubscribeEvent
    public static void Reaper(LivingDeathEvent event) {

        if (event.getEntity() instanceof IMagicSummon summon) {
            if (summon.getSummoner() != null && summon.getSummoner() instanceof ServerPlayer player) {
                MagicData magicData = MagicData.getPlayerMagicData(player);
                var mana_return = event.getEntity().getMaxHealth();
              if (player.hasEffect(GGEffectRegistry.LORD_OF_DECAY)) {
                  player.addEffect(new MobEffectInstance(MobEffects.HEAL, 5));
              }
              else if (player.hasEffect(GGEffectRegistry.DECAYING_MIGHT)) {
                  player.addEffect(new MobEffectInstance(MobEffects.HEAL, 5));
              }
               else if (player.hasEffect(GGEffectRegistry.REAPER)) {
                    magicData.setMana(magicData.getMana() + mana_return);
                    player.addEffect(new MobEffectInstance(MobEffects.HEAL, 5));
                }



            }
        }
    }




    @SubscribeEvent
    public static void DecayingMightRevive(LivingDeathEvent event) {
        if (event.getEntity() instanceof IMagicSummon) {
            IMagicSummon summon = (IMagicSummon) event.getEntity();
            if (summon.getSummoner() != null && summon.getSummoner() instanceof ServerPlayer) {
                ServerPlayer summoner = (ServerPlayer) summon.getSummoner();
                MagicData magicData = MagicData.getPlayerMagicData(summoner);
                if (summoner.hasEffect(GGEffectRegistry.DECAYING_MIGHT) && magicData.getMana() > 25) {
                    magicData.setMana(magicData.getMana() - 25);


                    summoner.displayClientMessage(Component.literal(ChatFormatting.ITALIC + "Summon Revived from Dust")
                            .withStyle(s -> s.withColor(TextColor.fromRgb(3289650))), true);
                    event.setCanceled(true);
                    event.getEntity().setHealth(event.getEntity().getMaxHealth());
                    if (event.getSource().getEntity() instanceof LivingEntity) {

                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void LordOfDecayRevive(LivingDeathEvent event) {
        if (event.getEntity() instanceof IMagicSummon) {
            IMagicSummon summon = (IMagicSummon) event.getEntity();
            if (summon.getSummoner() != null && summon.getSummoner() instanceof ServerPlayer) {
                ServerPlayer summoner = (ServerPlayer) summon.getSummoner();
                MagicData magicData = MagicData.getPlayerMagicData(summoner);
                if (summoner.hasEffect(GGEffectRegistry.LORD_OF_DECAY) && magicData.getMana() > 15) {
                    magicData.setMana(magicData.getMana() - 15);


                    summoner.displayClientMessage(Component.literal(ChatFormatting.ITALIC + "Summon Commanded to Live")
                            .withStyle(s -> s.withColor(TextColor.fromRgb(3289650))), true);
                    event.setCanceled(true);
                    event.getEntity().setHealth(event.getEntity().getMaxHealth());

                }
            }
        }
    }

    @SubscribeEvent
    public static void BloodDomainRevive(LivingDeathEvent event) {
        if (event.getEntity() instanceof IMagicSummon) {
            IMagicSummon summon = (IMagicSummon) event.getEntity();
            if (summon.getSummoner() != null && summon.getSummoner() instanceof ServerPlayer) {
                ServerPlayer summoner = (ServerPlayer) summon.getSummoner();
                if (summoner.hasEffect(GGEffectRegistry.BLOOD_DOMAIN)) {
                    event.setCanceled(true);
                    event.getEntity().setHealth(event.getEntity().getMaxHealth());
                    event.getEntity().addEffect(new MobEffectInstance(MobEffectRegistry.ABYSSAL_SHROUD, 10));

                }
            }
        }
    }



    @SubscribeEvent
    public static void ShaderRemove(LivingDeathEvent event) {
        if (event.getEntity() instanceof IMagicSummon) {
            IMagicSummon summon = (IMagicSummon) event.getEntity();
            if (summon.getSummoner() != null && summon.getSummoner() instanceof ServerPlayer) {
                ServerPlayer summoner = (ServerPlayer) summon.getSummoner();
                MagicData magicData = MagicData.getPlayerMagicData(summoner);
                if (summoner.hasEffect(GGEffectRegistry.LORD_OF_FROST) && magicData.getMana() > 10) {
                    magicData.setMana(magicData.getMana() - 10);
                    summoner.displayClientMessage(Component.literal(ChatFormatting.ITALIC + "Summon condemned to frost")
                            .withStyle(s -> s.withColor(TextColor.fromRgb(11131887))), true);

                    FrozenHumanoid iceClone = new FrozenHumanoid(summoner.level(), (LivingEntity) summon);
                    iceClone.setSummoner(summoner);
                    iceClone.setShatterDamage(25);
                    iceClone.setDeathTimer(5);
                    summoner.level().addFreshEntity(iceClone);
                    iceClone.deathTime = 1000;
                    iceClone.playSound(SoundRegistry.FROSTBITE_FREEZE.get(), 2, Utils.random.nextInt(9, 11) * .1f);
                }
            }
        }
    }



    @SubscribeEvent
    public static void FreezeStatueExplode(LivingDeathEvent event) {
        if (event.getEntity() instanceof IMagicSummon) {
            IMagicSummon summon = (IMagicSummon) event.getEntity();
            if (summon.getSummoner() != null && summon.getSummoner() instanceof ServerPlayer) {
                ServerPlayer summoner = (ServerPlayer) summon.getSummoner();
                MagicData magicData = MagicData.getPlayerMagicData(summoner);
                if (summoner.hasEffect(GGEffectRegistry.LORD_OF_FROST) && magicData.getMana() > 10) {
                    magicData.setMana(magicData.getMana() - 10);
                    summoner.displayClientMessage(Component.literal(ChatFormatting.ITALIC + "Summon condemned to frost")
                            .withStyle(s -> s.withColor(TextColor.fromRgb(11131887))), true);

                    FrozenHumanoid iceClone = new FrozenHumanoid(summoner.level(), (LivingEntity) summon);
                    iceClone.setSummoner(summoner);
                    iceClone.setShatterDamage(25);
                    iceClone.setDeathTimer(5);
                    summoner.level().addFreshEntity(iceClone);
                    iceClone.deathTime = 1000;
                    iceClone.playSound(SoundRegistry.FROSTBITE_FREEZE.get(), 2, Utils.random.nextInt(9, 11) * .1f);
                }
            }
        }
    }


    @SubscribeEvent
    public static void EbonyArmor(SpellPreCastEvent event) {
        var entity = event.getEntity();
        boolean hasEbonyEffect = entity.hasEffect(GGEffectRegistry.EBONY_ARMOR);
        if (entity instanceof ServerPlayer player && !player.level().isClientSide) {
            if (hasEbonyEffect) {
                event.setCanceled(true);
                int time = Objects.requireNonNull(player.getEffect(GGEffectRegistry.EBONY_ARMOR)).getDuration();
                String formattedTime = convertTicksToTime(time);
                player.displayClientMessage(Component.literal(ChatFormatting.BOLD + "Your body trembles, and your spells do not work for : " + formattedTime)
                        .withStyle(s -> s.withColor(TextColor.fromRgb(3289650))), true);
                player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                        SoundEvents.WITHER_HURT, SoundSource.PLAYERS, 0.3f, 1f);
            }
        }
    }

    @SubscribeEvent
    public static void EbonyCataphractTackle(SpellPreCastEvent event) {
        var entity = event.getEntity();
        boolean hasEbonyEffect = entity.hasEffect(GGEffectRegistry.EBONY_CATAPHRACT);
        if (entity instanceof ServerPlayer player && !player.level().isClientSide) {
            if (hasEbonyEffect) {
                event.setCanceled(true);
                GGSpellRegistry.CATAPHRACT_TACKLE.get().castSpell(event.getEntity().level(), 1, (ServerPlayer) player, CastSource.SPELLBOOK, true);
            }
        }
    }

    @SubscribeEvent
    public static void EbonyCataphractSlam(PlayerInteractEvent.RightClickBlock event) {
        var entity = event.getEntity();
        boolean hasEbonyEffect = entity.hasEffect(GGEffectRegistry.EBONY_CATAPHRACT);
        if (entity instanceof ServerPlayer player && !player.level().isClientSide) {
            if (hasEbonyEffect) {
                if (player.isCrouching()) {
                    GGSpellRegistry.CATAPHRACT_HEAL.get().castSpell(event.getEntity().level(), 1, (ServerPlayer) player, CastSource.SPELLBOOK, true);
                } else {
                    GGSpellRegistry.CATAPHRACT_SLAM.get().castSpell(event.getEntity().level(), 1, (ServerPlayer) player, CastSource.SPELLBOOK, true);
                }
            }
        }
    }



    @SubscribeEvent
    public static void SoulBefriending(PlayerInteractEvent.EntityInteract event) {
    var soul = event.getTarget();
    var player = event.getEntity();

    if (soul instanceof DeadKingSoulEntity && player.getMainHandItem().is(ItemRegistry.SIGNET_OF_THE_BETRAYER.get())) {
        soul.discard();
        player.getInventory().setItem(player.getInventory().selected, new ItemStack(GGItemRegistry.DEAD_KING_SOUL.get()));
        player.displayClientMessage(Component.literal(ChatFormatting.BOLD + "The soul resonates against the ring, accepting it as proof of your strength. It allows itself to be a catalyst for your magics.")
                .withStyle(s -> s.withColor(TextColor.fromRgb(3289650))), true);
    }
    }


    //THANKS ACE!!!
    public static String convertTicksToTime(int ticks) {
        // Convert ticks to seconds
        int totalSeconds = ticks / 20;

        // Calculate minutes and seconds
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;

        // Format the result as mm:ss
        return String.format("%02d:%02d", minutes, seconds);
    }


    @SubscribeEvent
    public static void AshesOfTheFallen(LivingDamageEvent.Pre event) {
        var attacked = event.getEntity();
        var attacker = event.getSource().getDirectEntity();

        MagicData magicData = MagicData.getPlayerMagicData(attacked);
        if ((attacked.hasEffect(GGEffectRegistry.PROTECTION_OF_ASHES) && magicData.getMana() > 35)) {

            if (attacker instanceof Projectile projectile) {
                var attacker2 = projectile.getOwner();
                assert attacker2 != null;

                magicData.setMana(magicData.getMana() - 35);
                attacker2.hurt(attacker2.damageSources().magic(), 10);
                attacked.level().playSound(null, attacked.getX(), attacked.getY(), attacked.getZ(),
                        SoundRegistry.KEEPER_SWORD_IMPACT, SoundSource.PLAYERS, 0.3f, 1f);
                MagicManager.spawnParticles(attacked.level(), ParticleTypes.FALLING_OBSIDIAN_TEAR, attacker2.getX(), attacker2.getY() + .25f, attacker2.getZ(), 100, .03, .4, .03, .4, false);

            } else {
                magicData.setMana(magicData.getMana() - 35);
                assert attacker != null;
                attacker.hurt(attacker.damageSources().magic(), 10);
                attacked.level().playSound(null, attacked.getX(), attacked.getY(), attacked.getZ(),
                        SoundRegistry.KEEPER_SWORD_IMPACT, SoundSource.PLAYERS, 0.3f, 1f);
                MagicManager.spawnParticles(attacked.level(), ParticleTypes.FALLING_OBSIDIAN_TEAR, attacker.getX(), attacker.getY() + .25f, attacker.getZ(), 100, .03, .4, .03, .4, false);
            }


        }
    }

    //ALL WEAPON PASSIVE
    @SubscribeEvent
    public static void WeaponPassiveAbilitiesMelee(LivingDamageEvent.Post event) {


        var sourceEntity = event.getSource().getEntity();
        if (sourceEntity instanceof LivingEntity livingEntity) {
            ItemStack mainhandItem = livingEntity.getMainHandItem();

            //ARM OF DECAY (YES ACE IM COPYING YOUR ORGANIZATION STYLE)
            if (mainhandItem.getItem() instanceof ArmOfDecay  && (!(livingEntity instanceof Player player) || !player.getCooldowns().isOnCooldown(GGItemRegistry.ARM_OF_DECAY.get()))) {


                assert livingEntity instanceof ServerPlayer;
                if (livingEntity.hasEffect(GGEffectRegistry.LORD_OF_DECAY)) {
                    GGSpellRegistry.ARM_OF_DECAY_PASSIVE.get().castSpell(event.getEntity().level(), 1, (ServerPlayer) livingEntity, CastSource.SWORD, true);
                }
                else {
                    GGSpellRegistry.ARM_OF_DECAY_WEAK_PASSIVE.get().castSpell(event.getEntity().level(), 1, (ServerPlayer) livingEntity, CastSource.SWORD, true);
                }

                Player player = (Player) livingEntity;
                player.getCooldowns().addCooldown(GGItemRegistry.ARM_OF_DECAY.get(), ArmOfDecay.COOLDOWN);


            }

        }

        //INTERTWINED PEAK
        if (sourceEntity instanceof LivingEntity livingEntity) {
            ItemStack mainhandItem = livingEntity.getMainHandItem();

            var attacked = event.getEntity();


            if (mainhandItem.getItem() instanceof IntertwinedPeak && (!(livingEntity instanceof Player player) || !player.getCooldowns().isOnCooldown(GGItemRegistry.INTERTWINED_PEAK.get()))) {

                attacked.addEffect(new MobEffectInstance(GGEffectRegistry.OVERWHELMING_DREAD, 300));

                assert livingEntity instanceof ServerPlayer;
                if (livingEntity instanceof Player player) {
                        player.getCooldowns().addCooldown(GGItemRegistry.INTERTWINED_PEAK.get(), IntertwinedPeak.COOLDOWN);
                    }


                }
        }

        //SWIFT_DEMISE
        if (sourceEntity instanceof LivingEntity livingEntity) {
            ItemStack mainhandItem = livingEntity.getMainHandItem();

            var attacked = event.getEntity();


            if (mainhandItem.getItem() instanceof SwiftDemise && (!(livingEntity instanceof Player player) || !player.getCooldowns().isOnCooldown(GGItemRegistry.SWIFT_DEMISE.get()))) {

                attacked.addEffect(new MobEffectInstance(GGEffectRegistry.SKILLFUL_WOUND, 300));

                assert livingEntity instanceof ServerPlayer;
                if (livingEntity instanceof Player player) {
                    player.getCooldowns().addCooldown(GGItemRegistry.SWIFT_DEMISE.get(), IntertwinedPeak.COOLDOWN);
                }


            }
        }

        //SKYFALLS CAUSE
        if (sourceEntity instanceof LivingEntity livingEntity) {
            ItemStack mainhandItem = livingEntity.getMainHandItem();

            var attacked = event.getEntity();


            if (mainhandItem.getItem() instanceof SkyfallsCause && (!(livingEntity instanceof Player player) || !player.getCooldowns().isOnCooldown(GGItemRegistry.SKYFALLS_CAUSE.get()))) {

                attacked.addEffect(new MobEffectInstance(GGEffectRegistry.SHATTERED_WILL, 300));

                assert livingEntity instanceof ServerPlayer;
                if (livingEntity instanceof Player player) {
                    player.getCooldowns().addCooldown(GGItemRegistry.SKYFALLS_CAUSE.get(), IntertwinedPeak.COOLDOWN);
                }


            }
        }
    }

    @SubscribeEvent
    public static void WeaponPassiveAbilitiesDeath(LivingDeathEvent event) {
        var sourceEntity = event.getEntity();
        if (sourceEntity instanceof LivingEntity livingEntity) {
            ItemStack mainhandItem = livingEntity.getMainHandItem();

            if (mainhandItem.getItem() instanceof Inevitability && (!(livingEntity instanceof Player player) || !player.getCooldowns().isOnCooldown(GGItemRegistry.INEVITABILITY.get()))) {


                event.setCanceled(true);
                livingEntity.setHealth(livingEntity.getMaxHealth());

                if (livingEntity.hasEffect(GGEffectRegistry.REVIVING_SICKNESS)) {
                MobEffectInstance revivingSickness = livingEntity.getEffect(GGEffectRegistry.REVIVING_SICKNESS);
                MobEffectInstance mobEffect;

                    if (revivingSickness != null) {
                        mobEffect = new MobEffectInstance(GGEffectRegistry.REVIVING_SICKNESS, 4800, revivingSickness.getAmplifier() + 1,revivingSickness.isAmbient(), revivingSickness.isVisible(), revivingSickness.showIcon());
                    } else {
                        mobEffect = new MobEffectInstance(GGEffectRegistry.REVIVING_SICKNESS, 4800, 0, false, false, true);
                    }

                    livingEntity.addEffect(new MobEffectInstance(GGEffectRegistry.REVIVING_SICKNESS, 4800));

                }

                livingEntity.addEffect(new MobEffectInstance(GGEffectRegistry.REVIVING_SICKNESS, 4800));
                livingEntity.level().playSound(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(),
                        GGSoundRegistry.GREATER_REVIVE, SoundSource.PLAYERS, 0.3f, 1f);
                MagicManager.spawnParticles(livingEntity.level(), new BlastwaveParticleOptions(GGSchoolRegistry.ELDRITCH.get().getTargetingColor(), 5), livingEntity.getX(), livingEntity.getY() + .165f, livingEntity.getZ(), 1, 0, 0, 0, 0, true);

                if (livingEntity instanceof Player player) {
                    player.getCooldowns().addCooldown(GGItemRegistry.INEVITABILITY.get(), Inevitability.COOLDOWN);
                }
            }

        }

    }


    @SubscribeEvent
    public static void SummonHealth(SpellSummonEvent event) {
        var summon = event.getCreature();

        if (summon instanceof IMagicSummon spawned) {
            var summoner = spawned.getSummoner();
            if (summoner instanceof LivingEntity entity) {

                var health = summon.getAttributeValue(Attributes.MAX_HEALTH);
                var health_new = (health * entity.getAttributeValue(GGAttributeRegistry.SUMMON_HEALTH));

                    summon.getAttribute(Attributes.MAX_HEALTH).setBaseValue(health_new);
                    summon.setHealth(summon.getMaxHealth());


            }
        }
    }
}







