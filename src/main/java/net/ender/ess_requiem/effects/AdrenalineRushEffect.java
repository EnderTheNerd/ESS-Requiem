package net.ender.ess_requiem.effects;

import io.redspace.ironsspellbooks.effect.MagicMobEffect;
import net.ender.ess_requiem.EndersSpellsAndStuffRequiem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class AdrenalineRushEffect extends MagicMobEffect {
    public AdrenalineRushEffect(MobEffectCategory category, int color) {
        super(MobEffectCategory.BENEFICIAL, 10268329);

        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, ResourceLocation.fromNamespaceAndPath(EndersSpellsAndStuffRequiem.MOD_ID, "adrenaline"), .05,
                AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
        this.addAttributeModifier(Attributes.ATTACK_SPEED, ResourceLocation.fromNamespaceAndPath(EndersSpellsAndStuffRequiem.MOD_ID, "adrenaline"), .05,
                AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
        this.addAttributeModifier(Attributes.ATTACK_DAMAGE, ResourceLocation.fromNamespaceAndPath(EndersSpellsAndStuffRequiem.MOD_ID, "adrenaline"), .05,
                AttributeModifier.Operation.ADD_MULTIPLIED_BASE);

    }


}
