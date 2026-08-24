package net.ender.ess_requiem.item.sword_tier.Staves.RequiemStaff;

import io.redspace.ironsspellbooks.api.item.weapons.ExtendedSwordItem;
import io.redspace.ironsspellbooks.api.spells.IPresetSpellContainer;
import io.redspace.ironsspellbooks.api.spells.ISpellContainer;
import io.redspace.ironsspellbooks.item.weapons.StaffItem;
import io.redspace.ironsspellbooks.registries.ComponentRegistry;
import net.ender.ess_requiem.item.GGSwordTier;
import net.ender.ess_requiem.registries.GGSpellRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.Unbreakable;

import java.util.List;

public class RequiemStaff extends StaffItem implements IPresetSpellContainer {

    public RequiemStaff() {
        super( new Item.Properties().component(DataComponents.UNBREAKABLE, new Unbreakable(false)).fireResistant().stacksTo(1).rarity(Rarity.EPIC).attributes(ExtendedSwordItem.createAttributes(GGSwordTier.REQUIEM_STAFF)));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        tooltipComponents.add(Component.translatable("item.ess_requiem.requiem_staff.lore").
                withStyle(ChatFormatting.DARK_RED).
                withStyle(ChatFormatting.ITALIC));
    }

    @Override
    public void initializeSpellContainer(ItemStack itemStack) {
        if (itemStack == null) {
            return;
        }

        if (!ISpellContainer.isSpellContainer(itemStack)) {
            var spellContainer = ISpellContainer.create(1, true, false).mutableCopy();
            spellContainer.addSpell(GGSpellRegistry.BLOOD_DOMAIN.get(), 1, true);
            itemStack.set(ComponentRegistry.SPELL_CONTAINER, spellContainer.toImmutable());
        }
    }




}
