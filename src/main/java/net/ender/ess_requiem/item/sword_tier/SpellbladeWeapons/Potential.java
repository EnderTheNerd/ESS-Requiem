package net.ender.ess_requiem.item.sword_tier.SpellbladeWeapons;

import io.redspace.ironsspellbooks.api.item.weapons.ExtendedSwordItem;
import io.redspace.ironsspellbooks.api.item.weapons.MagicSwordItem;
import io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder;
import net.ender.ess_requiem.item.GGSwordTier;
import net.ender.ess_requiem.registries.GGSpellRegistry;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class Potential extends MagicSwordItem {


    public Potential() {
        super(GGSwordTier.POTENTIAL,  new Item.Properties().rarity(Rarity.COMMON).attributes(ExtendedSwordItem.createAttributes(GGSwordTier.POTENTIAL)),
                SpellDataRegistryHolder.of(new SpellDataRegistryHolder(GGSpellRegistry.SLASH, 2))
        );
    }

}
