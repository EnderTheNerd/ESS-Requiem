package net.ender.ess_requiem.spells.blood;

import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.events.SpellSummonEvent;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.*;
import io.redspace.ironsspellbooks.entity.mobs.IMagicSummon;
import io.redspace.ironsspellbooks.network.casting.SyncTargetingDataPacket;
import io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.ender.ess_requiem.EndersSpellsAndStuffRequiem;
import net.ender.ess_requiem.entity.mobs.bone_maggot.BoneMaggotEntity;
import net.ender.ess_requiem.entity.mobs.hopping_skull.HoppingSkullEntity;
import net.ender.ess_requiem.registries.GGSoundRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.network.PacketDistributor;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public class MaggotBurstSpell extends AbstractSpell {
    private final ResourceLocation spellId = ResourceLocation.fromNamespaceAndPath(EndersSpellsAndStuffRequiem.MOD_ID, "maggot_burst");


    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                (Component.translatable("ui.irons_spellbooks.summon_count",Utils.stringTruncation(getSummonCount(spellLevel, caster), 0)))
        );
    }

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.RARE)
            .setSchoolResource(SchoolRegistry.BLOOD_RESOURCE)
            .setMaxLevel(5)
            .setCooldownSeconds(100)
            .build();

    public MaggotBurstSpell() {
        this.manaCostPerLevel = 10;
        this.baseSpellPower = 5;
        this.spellPowerPerLevel = 1;
        this.castTime = 30;
        this.baseManaCost = 60;
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
        return Optional.of(GGSoundRegistry.MAGGOT_BURST_STARTUP.get());
    }

   @Override
    public Optional<SoundEvent> getCastFinishSound() {
      return Optional.of(GGSoundRegistry.MAGGOT_BURST_END.get());
    }

    @Override
    public ICastDataSerializable getEmptyCastData() {
        return new SummonedEntitiesCastData();
    }

    @Override
    public boolean checkPreCastConditions(Level level, int spellLevel, LivingEntity entity, MagicData playerMagicData) {
        float aimAssist = .25f;
        float range = 25f;
        Vec3 start = entity.getEyePosition();
        Vec3 end = entity.getLookAngle().normalize().scale(range).add(start);
        var target = Utils.raycastForEntity(entity.level(), entity, start, end, true, aimAssist, (e) -> e instanceof IMagicSummon summon && summon.getSummoner() == entity);
        if (target instanceof EntityHitResult entityHit && entityHit.getEntity() instanceof LivingEntity livingTarget) {
            playerMagicData.setAdditionalCastData(new TargetEntityCastData(livingTarget));
            if (entity instanceof ServerPlayer serverPlayer) {
                PacketDistributor.sendToPlayer(serverPlayer, new SyncTargetingDataPacket(livingTarget, this));
                serverPlayer.connection.send(new ClientboundSetActionBarTextPacket(Component.translatable("ui.irons_spellbooks.spell_target_success", livingTarget.getDisplayName().getString(), this.getDisplayName(serverPlayer)).withStyle(ChatFormatting.GREEN)));
            }
            return true;
        } else if (entity instanceof ServerPlayer serverPlayer) {
            serverPlayer.connection.send(new ClientboundSetActionBarTextPacket(Component.translatable("ui.irons_spellbooks.sacrifice_target_failure").withStyle(ChatFormatting.RED)));
        }
        return false;
    }

    @Override
    public void onCast(Level world, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        if (playerMagicData.getAdditionalCastData() instanceof TargetEntityCastData targetData) {
            var targetEntity = targetData.getTarget((ServerLevel) world);
            if (targetEntity instanceof IMagicSummon summon && summon.getSummoner().getUUID().equals(entity.getUUID())) {
                {
                    MagicManager.spawnParticles(world, ParticleHelper.BLOOD, targetEntity.getX(), targetEntity.getY() + .25f, targetEntity.getZ(), 100, .03, .4, .03, .4, true);
                    MagicManager.spawnParticles(world, ParticleHelper.BLOOD, targetEntity.getX(), targetEntity.getY() + .25f, targetEntity.getZ(), 100, .03, .4, .03, .4, false);
                    MagicManager.spawnParticles(world, new BlastwaveParticleOptions(SchoolRegistry.BLOOD.get().getTargetingColor(), 3), targetEntity.getX(), targetEntity.getBoundingBox().getCenter().y, targetEntity.getZ(), 1, 0, 0, 0, 0, true);

                    SummonedEntitiesCastData summonedEntitiesCastData = new SummonedEntitiesCastData();
                    int summonTime = 20 * 60;
                    int count = (int) getSummonCount(spellLevel, entity);
                    for (int i = 0; i < count; i++) {
                        BoneMaggotEntity maggot = new BoneMaggotEntity(world, entity);
                        maggot.moveTo(targetEntity.getEyePosition().add(new Vec3(Utils.getRandomScaled(2), 1, Utils.getRandomScaled(2))));
                        maggot.finalizeSpawn((ServerLevel) world, world.getCurrentDifficultyAt(maggot.getOnPos()), MobSpawnType.MOB_SUMMONED, null);
                        var creature = NeoForge.EVENT_BUS.post(new SpellSummonEvent<>(entity, maggot, this.spellId, spellLevel)).getCreature();
                        world.addFreshEntity(creature);
                        SummonManager.initSummon(entity, creature, summonTime, summonedEntitiesCastData);
                    }
                    targetEntity.remove(Entity.RemovalReason.KILLED);
                    world.playSound(null, targetEntity.blockPosition(), GGSoundRegistry.MAGGOT_BURST_END.get(), SoundSource.PLAYERS, 3, Utils.random.nextIntBetweenInclusive(8, 12) * .1f);
                }
                super.onCast(world, spellLevel, entity, castSource, playerMagicData);

            }

        }

    }


    private float getSummonCount(int spellLevel, LivingEntity entity) {
        return getSpellPower(spellLevel, entity) * 2;
    }

}
