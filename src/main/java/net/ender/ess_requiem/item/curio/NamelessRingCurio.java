package net.ender.ess_requiem.item.curio;

import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.item.curios.SimpleDescriptiveCurio;
import io.redspace.ironsspellbooks.util.MinecraftInstanceHelper;
import net.ender.ess_requiem.compat.Curios;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class NamelessRingCurio extends SimpleDescriptiveCurio {
    public NamelessRingCurio() {
        super(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON), Curios.RESONATOR);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);


        if (Screen.hasShiftDown())
        {
            LivingEntity attacker = MinecraftInstanceHelper.getPlayer();
            tooltipComponents.add(Component.literal(" ").append(Component.translatable(this.getDescriptionId() + ".desc2")).withStyle(ChatFormatting.YELLOW));
            assert attacker != null;
        } else
        {
            tooltipComponents.add(Component.translatable("item.ess_requiem.more_details").withStyle(ChatFormatting.GRAY));
        }
    }


}

