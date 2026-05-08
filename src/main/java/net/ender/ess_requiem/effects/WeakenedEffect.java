package net.ender.ess_requiem.effects;

import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.effect.MagicMobEffect;
import net.ender.ess_requiem.EndersSpellsAndStuffRequiem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class WeakenedEffect extends MagicMobEffect {
    public WeakenedEffect(MobEffectCategory category, int color) {
        super(MobEffectCategory.HARMFUL, 9833512);
        this.addAttributeModifier(Attributes.ATTACK_DAMAGE, ResourceLocation.fromNamespaceAndPath(EndersSpellsAndStuffRequiem.MOD_ID, "weakened"), -.1,
                AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
        this.addAttributeModifier(AttributeRegistry.SPELL_POWER, ResourceLocation.fromNamespaceAndPath(EndersSpellsAndStuffRequiem.MOD_ID, "weakened"), -.1,
                AttributeModifier.Operation.ADD_MULTIPLIED_BASE);

    }

}
