package net.ender.ess_requiem.compat.dte.compat_spells.eldritch;

import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.events.SpellSummonEvent;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.*;
import net.acetheeldritchking.aces_spell_utils.spells.ASSpellAnimations;
import net.ender.ess_requiem.EndersSpellsAndStuffRequiem;
import net.ender.ess_requiem.entity.mobs.battle_standard.BattleStandardEntity;
import net.ender.ess_requiem.entity.mobs.nightmare.NightmareEntity;
import net.ender.ess_requiem.registries.GGEffectRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.NeoForge;

import javax.annotation.Nullable;


public class NightmareSummonSpell extends AbstractSpell {
    private final ResourceLocation spellId = ResourceLocation.fromNamespaceAndPath(EndersSpellsAndStuffRequiem.MOD_ID, "nightmare_spell");


    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.LEGENDARY)
            .setSchoolResource(SchoolRegistry.ELDRITCH_RESOURCE)
            .setMaxLevel(1)
            .setCooldownSeconds(180)
            .build();

    public NightmareSummonSpell() {
        this.manaCostPerLevel = 40;
        this.baseSpellPower = 10;
        this.spellPowerPerLevel = 5;
        this.castTime = 0;
        this.baseManaCost = 400;
    }

    @Override
    public CastType getCastType() {
        return CastType.INSTANT;
    }

    @Override
    public DefaultConfig getDefaultConfig() {
        return defaultConfig;
    }

    @Override
    public ResourceLocation getSpellResource() {
        return spellId;
    }



  //nightmare.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(targetEntity.getAttributeValue(Attributes.ATTACK_DAMAGE));
    // nightmare.getAttribute(Attributes.MAX_HEALTH).setBaseValue(targetEntity.getAttributeValue(Attributes.MAX_HEALTH));


    @Override
    public boolean checkPreCastConditions(Level level, int spellLevel, LivingEntity entity, MagicData playerMagicData) {
        return Utils.preCastTargetHelper(level, entity, playerMagicData, this, 30, .35f);
    }


    @Override
    public void onCast(Level world, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        if (playerMagicData.getAdditionalCastData() instanceof TargetEntityCastData targetData) {
            var targetEntity = targetData.getTarget((ServerLevel) world);
            {
                int summonTime = 20 * 60 * 10;
                SummonedEntitiesCastData summonedEntitiesCastData = new SummonedEntitiesCastData();


                NightmareEntity weapon = new NightmareEntity(world, entity);
                weapon.setHealth(weapon.getMaxHealth());
                weapon.addEffect(new MobEffectInstance(GGEffectRegistry.BANNER_PROTECTION, 10000));
                weapon.moveTo(entity.position());
                weapon.finalizeSpawn((ServerLevel) world, world.getCurrentDifficultyAt(weapon.getOnPos()), MobSpawnType.MOB_SUMMONED, null);
                var creature = NeoForge.EVENT_BUS.post(new SpellSummonEvent<>(entity, weapon, this.spellId, spellLevel)).getCreature();
                world.addFreshEntity(creature);
                SummonManager.initSummon(entity, creature, summonTime, summonedEntitiesCastData);


                super.onCast(world, spellLevel, entity, castSource, playerMagicData);
                }





            super.onCast(world, spellLevel, entity, castSource, playerMagicData);
        }

    }




    @Override
    public AnimationHolder getCastStartAnimation() {
        return ASSpellAnimations.ANIMATION_CLAP;
    }
}
