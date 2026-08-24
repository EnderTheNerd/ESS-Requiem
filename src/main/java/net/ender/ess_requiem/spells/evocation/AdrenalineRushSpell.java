package net.ender.ess_requiem.spells.evocation;

import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.config.IronConfigParameters;
import io.redspace.ironsspellbooks.api.config.SpellConfigManager;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.api.util.Utils;
import net.ender.ess_requiem.EndersSpellsAndStuffRequiem;
import net.ender.ess_requiem.entity.mobs.bone_maggot.BoneMaggotEntity;
import net.ender.ess_requiem.registries.GGEffectRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Objects;

public class AdrenalineRushSpell extends AbstractSpell {
    private final ResourceLocation spellId = ResourceLocation.fromNamespaceAndPath(EndersSpellsAndStuffRequiem.MOD_ID, "adrenaline_rush");

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                Component.translatable("ui.irons_spellbooks.effect_length", Utils.timeFromTicks(getDuration(spellLevel, caster) , 1)));

    }

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.UNCOMMON)
            .setSchoolResource(SchoolRegistry.EVOCATION_RESOURCE)
            .setMaxLevel(3)
            .setCooldownSeconds(10)
            .build();

    public AdrenalineRushSpell() {
        this.manaCostPerLevel = 15;
        this.baseSpellPower = 35;
        this.spellPowerPerLevel = 3;
        this.castTime = 0;
        this.baseManaCost = 75;
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

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        int i = getDuration(spellLevel, entity);
        if (entity.hasEffect(GGEffectRegistry.ADRENAL_FATIGUE) && entity instanceof ServerPlayer serverPlayer) {

            serverPlayer.connection.send(new ClientboundSetActionBarTextPacket(Component.translatable("ui.ess_requiem.adrenal_error").withStyle(ChatFormatting.RED)));
        }
       else if (entity.hasEffect(GGEffectRegistry.ADRENALINE_RUSH)){
            var effect = entity.getEffect(GGEffectRegistry.ADRENALINE_RUSH);
            var duration = Objects.requireNonNull(entity.getEffect(GGEffectRegistry.ADRENALINE_RUSH)).getDuration();

            entity.addEffect(new MobEffectInstance(GGEffectRegistry.ADRENALINE_RUSH, duration/2, effect.getAmplifier() + 1 , false, false, true));


        }

        else {
            entity.addEffect(new MobEffectInstance(GGEffectRegistry.ADRENALINE_RUSH, i, spellLevel -1  , false, false, true));
        }


        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    public int getDuration(int spellLevel, LivingEntity caster) {
        return (int) (getSpellPower(spellLevel, caster) * 2.5F);
    }


    @Override
    public AnimationHolder getCastStartAnimation() {
        return SpellAnimations.SELF_CAST_ANIMATION;
    }
}
