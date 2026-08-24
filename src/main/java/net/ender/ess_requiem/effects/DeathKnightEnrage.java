package net.ender.ess_requiem.effects;

import dev.shadowsoffire.apothic_attributes.api.ALObjects;
import net.ender.ess_requiem.EndersSpellsAndStuffRequiem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class DeathKnightEnrage extends MobEffect {
    public DeathKnightEnrage(MobEffectCategory pCategory, int pColor) {
        super(MobEffectCategory.BENEFICIAL, 9833514);

        this.addAttributeModifier(ALObjects.Attributes.LIFE_STEAL, ResourceLocation.fromNamespaceAndPath(EndersSpellsAndStuffRequiem.MOD_ID, "death_knight"), .05,
                AttributeModifier.Operation.ADD_VALUE);
        this.addAttributeModifier(Attributes.ATTACK_DAMAGE, ResourceLocation.fromNamespaceAndPath(EndersSpellsAndStuffRequiem.MOD_ID, "death_knight"), 2,
                AttributeModifier.Operation.ADD_VALUE);
        this.addAttributeModifier(Attributes.ARMOR, ResourceLocation.fromNamespaceAndPath(EndersSpellsAndStuffRequiem.MOD_ID, "death_knight"), 2,
                AttributeModifier.Operation.ADD_VALUE);


    }
}
