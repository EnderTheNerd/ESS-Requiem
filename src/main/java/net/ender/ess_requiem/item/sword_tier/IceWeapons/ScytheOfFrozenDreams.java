package net.ender.ess_requiem.item.sword_tier.IceWeapons;

import io.redspace.ironsspellbooks.api.item.weapons.ExtendedSwordItem;
import io.redspace.ironsspellbooks.api.item.weapons.MagicSwordItem;
import io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder;
import net.acetheeldritchking.aces_spell_utils.utils.ASRarities;
import net.ender.ess_requiem.item.GGSwordTier;
import net.ender.ess_requiem.registries.GGSpellRegistry;
import net.minecraft.world.item.Item;

public class ScytheOfFrozenDreams extends MagicSwordItem {

    public ScytheOfFrozenDreams() {
        super(GGSwordTier.SCYTHE_OF_FROZEN_DREAMS,  new Item.Properties().rarity(ASRarities.GLACIAL_RARITY_PROXY.getValue()).attributes(ExtendedSwordItem.createAttributes(GGSwordTier.SCYTHE_OF_FROZEN_DREAMS)),
                SpellDataRegistryHolder.of(new SpellDataRegistryHolder(GGSpellRegistry.GLACIAL_SCULPTING, 1),
                        new SpellDataRegistryHolder(GGSpellRegistry.LORD_OF_FROST, 1))
        );
    }
}
