package net.ender.ess_requiem.spells.blood.summons;

import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.events.SpellSummonEvent;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.SummonManager;
import io.redspace.ironsspellbooks.capabilities.magic.SummonedEntitiesCastData;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import net.ender.ess_requiem.EndersSpellsAndStuffRequiem;
import net.ender.ess_requiem.entity.mobs.homunculus.HomunculusEntity;
import net.ender.ess_requiem.entity.mobs.tombstone.TombstoneEntity;
import net.ender.ess_requiem.registries.GGSoundRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.NeoForge;

import java.util.List;
import java.util.Optional;

public class HomunculusSummon extends AbstractSpell {
    private final ResourceLocation spellId = ResourceLocation.fromNamespaceAndPath(EndersSpellsAndStuffRequiem.MOD_ID, "homunculus_summon");
    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.COMMON)
            .setSchoolResource(SchoolRegistry.BLOOD_RESOURCE)
            .setMaxLevel(8)
            .setCooldownSeconds(20)
            .build();

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(Component.translatable("ui.irons_spellbooks.summon_count", getSummonCount(spellLevel, caster)));
    }

    public HomunculusSummon() {
        this.manaCostPerLevel = 8;
        this.baseSpellPower = 10;
        this.spellPowerPerLevel = 0;
        this.castTime = 25;
        this.baseManaCost = 30;

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
        return Optional.of(GGSoundRegistry.HOMUNCULUS_START.get());
    }

    @Override
    public Optional<SoundEvent> getCastFinishSound() {
        return Optional.of(GGSoundRegistry.HOMUNCULUS_FINISH.get());
    }


    public int getSummonCount(int spellLevel, LivingEntity caster) {
        return spellLevel + 1 ;
    }






    @Override
    public ICastDataSerializable getEmptyCastData() {
        return new SummonedEntitiesCastData();
    }

    @Override
    public void onCast(Level world, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {

        SummonedEntitiesCastData summonedEntitiesCastData = new SummonedEntitiesCastData();
        entity.hurt(entity.damageSources().genericKill(), 2.5F);
        int summonTime = 30 * 65;
        int count = getSummonCount(spellLevel, entity);
        for (int i = 0; i < count; i++) {
            HomunculusEntity son = new HomunculusEntity(world, entity);
            son.moveTo(entity.getEyePosition().add(new Vec3(Utils.getRandomScaled(5), 1, Utils.getRandomScaled(5))));
            son.finalizeSpawn((ServerLevel) world, world.getCurrentDifficultyAt(son.getOnPos()), MobSpawnType.MOB_SUMMONED, null);
            var creature = NeoForge.EVENT_BUS.post(new SpellSummonEvent<>(entity, son, this.spellId, spellLevel)).getCreature();
            world.addFreshEntity(creature);
            SummonManager.initSummon(entity, creature, summonTime, summonedEntitiesCastData);
        }
        super.onCast(world, spellLevel, entity, castSource, playerMagicData);

    }



}
