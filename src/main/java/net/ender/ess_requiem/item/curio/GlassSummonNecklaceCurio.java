package net.ender.ess_requiem.item.curio;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.compat.Curios;
import io.redspace.ironsspellbooks.item.curios.SimpleDescriptiveCurio;
import io.redspace.ironsspellbooks.util.ItemPropertiesHelper;
import net.acetheeldritchking.aces_spell_utils.registries.ASAttributeRegistry;
import net.acetheeldritchking.aces_spell_utils.utils.ASRarities;
import net.ender.ess_requiem.registries.GGAttributeRegistry;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import top.theillusivec4.curios.api.SlotContext;

public class GlassSummonNecklaceCurio extends SimpleDescriptiveCurio {
    public GlassSummonNecklaceCurio() {
        super(ItemPropertiesHelper.equipment().stacksTo(1).rarity(ASRarities.ACCURSED_RARITY_PROXY.getValue()), Curios.NECKLACE_SLOT);
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation id, ItemStack stack) {
        Multimap<Holder<Attribute>, AttributeModifier> attr = LinkedHashMultimap.create();
        attr.put(GGAttributeRegistry.SUMMON_HEALTH, new AttributeModifier(id, -0.15, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        attr.put(AttributeRegistry.SUMMON_DAMAGE, new AttributeModifier(id,  0.25, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        return attr;
    }

}
