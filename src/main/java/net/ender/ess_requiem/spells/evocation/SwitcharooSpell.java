package net.ender.ess_requiem.spells.evocation;

import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.CastType;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.TargetEntityCastData;
import net.ender.ess_requiem.EndersSpellsAndStuffRequiem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.util.List;

public class SwitcharooSpell extends AbstractSpell {
    private final ResourceLocation spellId = ResourceLocation.fromNamespaceAndPath(EndersSpellsAndStuffRequiem.MOD_ID, "switcharoo");

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(

                Component.translatable("ui.ess_requiem.health_limit", Utils.stringTruncation(getHealthLimit(spellLevel, caster), 1)));



    }

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.RARE)
            .setSchoolResource(SchoolRegistry.EVOCATION_RESOURCE)
            .setMaxLevel(6)
            .setCooldownSeconds(85)
            .build();

    public SwitcharooSpell() {
        this.manaCostPerLevel = 10;
        this.baseSpellPower = 1;
        this.spellPowerPerLevel = 2;
        this.castTime = 30;
        this.baseManaCost = 80;
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
    public boolean checkPreCastConditions(Level level, int spellLevel, LivingEntity entity, MagicData playerMagicData) {
        return Utils.preCastTargetHelper(level, entity, playerMagicData, this, 64, .55f);
    }


    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {

        if (playerMagicData.getAdditionalCastData() instanceof TargetEntityCastData targetData) {
            var targetEntity = targetData.getTarget((ServerLevel) level);
            {

                var target_health = targetEntity.getHealth();
                var caster_health = entity.getHealth();

                if (target_health <= getHealthLimit(spellLevel, entity)) {
                    entity.setHealth(target_health);
                    targetEntity.setHealth(caster_health);

                }
                else if (entity instanceof ServerPlayer serverPlayer){
                   serverPlayer.displayClientMessage(Component.literal(ChatFormatting.ITALIC + "The Switch failed.")
                            .withStyle(s -> s.withColor(ChatFormatting.RED)), true);
                }

            }
        }

        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }




    public int getHealthLimit(int spellLevel, LivingEntity caster) {
        return (int) ((int) caster.getMaxHealth() + spellLevel + getSpellPower(spellLevel, caster));
    }




}
