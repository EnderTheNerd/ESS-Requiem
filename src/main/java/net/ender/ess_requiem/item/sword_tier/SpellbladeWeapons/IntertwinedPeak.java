package net.ender.ess_requiem.item.sword_tier.SpellbladeWeapons;

import io.redspace.ironsspellbooks.api.item.weapons.ExtendedSwordItem;
import io.redspace.ironsspellbooks.api.item.weapons.MagicSwordItem;
import io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.util.ItemPropertiesHelper;
import io.redspace.ironsspellbooks.util.MinecraftInstanceHelper;
import net.acetheeldritchking.aces_spell_utils.utils.ASRarities;
import net.ender.ess_requiem.item.GGSwordTier;
import net.ender.ess_requiem.registries.GGItemRegistry;
import net.ender.ess_requiem.registries.GGSoundRegistry;
import net.ender.ess_requiem.registries.GGSpellRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.Unbreakable;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class IntertwinedPeak extends MagicSwordItem {
    public static final int COOLDOWN = 250;


    public IntertwinedPeak() {
        super(GGSwordTier.INTERTWINED_PEAK, ItemPropertiesHelper.equipment().component(DataComponents.UNBREAKABLE, new Unbreakable(true)).fireResistant().rarity(ASRarities.COSMIC_RARITY_PROXY.getValue()).attributes(ExtendedSwordItem.createAttributes(GGSwordTier.INTERTWINED_PEAK)),
                SpellDataRegistryHolder.of
                        (new SpellDataRegistryHolder(GGSpellRegistry.CLEAVE, 1),
                                new SpellDataRegistryHolder(GGSpellRegistry.DISMANTLE, 1),
                                new SpellDataRegistryHolder(GGSpellRegistry.MALEVOLENT_SLASHING, 1)
                        ));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        tooltipComponents.add(Component.translatable("item.ess_requiem.intertwined_peak.lore").
                withStyle(ChatFormatting.GOLD).
                withStyle(ChatFormatting.ITALIC));

        if (Screen.hasShiftDown())
        {
            LivingEntity attacker = MinecraftInstanceHelper.getPlayer();

            tooltipComponents.add(
                    Component.translatable(
                            "tooltip.irons_spellbooks.passive_ability",
                            Component.literal(Utils.timeFromTicks(COOLDOWN, 1)).withStyle(ChatFormatting.LIGHT_PURPLE)
                    ).withStyle(ChatFormatting.DARK_PURPLE)

            );
            tooltipComponents.add(Component.translatable(this.getDescriptionId() + ".desc3").
                    withStyle(ChatFormatting.YELLOW).
                    withStyle(ChatFormatting.ITALIC)
            );
            tooltipComponents.add(Component.translatable(this.getDescriptionId() + ".desc2").
                    withStyle(ChatFormatting.YELLOW).
                    withStyle(ChatFormatting.ITALIC)
            );


            tooltipComponents.add(Component.literal(" ").append(Component.translatable(this.getDescriptionId() + ".desc")).withStyle(ChatFormatting.RED));
            assert attacker != null;
        } else
        {
            tooltipComponents.add(Component.translatable("item.ess_requiem.more_details").withStyle(ChatFormatting.GRAY));
        }
    }


    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        var inventoryCheck = player.getInventory().getFreeSlot();
        ItemStack mainhandItem = ((LivingEntity) player).getMainHandItem();

        if (inventoryCheck == -1 && mainhandItem.getItem() instanceof IntertwinedPeak) {
            player.displayClientMessage(Component.literal(ChatFormatting.ITALIC + "Your weapon refuses to move.")
                    .withStyle(s -> s.withColor(TextColor.fromRgb(14522123))), true);

        } else if (player.getMainHandItem().is(GGItemRegistry.INTERTWINED_PEAK)) {
            if (!player.isCrouching()) {
                player.displayClientMessage(Component.literal(ChatFormatting.ITALIC + "Your weapon refuses to move whilst standing.")
                        .withStyle(s -> s.withColor(ChatFormatting.RED)), true);
            } else if (player.isCrouching() && player.getMainHandItem().is(GGItemRegistry.INTERTWINED_PEAK)) {
                player.setItemInHand(usedHand, new ItemStack(GGItemRegistry.SKYFALLS_CAUSE.get()));
                player.addItem(new ItemStack(GGItemRegistry.SWIFT_DEMISE.get()));
                player.level().playSound(null, player.getX(), player.getY(), player.getZ(), GGSoundRegistry.PARRY, SoundSource.NEUTRAL, .8F, 1.3F);
            }

        }
        return super.use(level, player, usedHand);
    }
}

