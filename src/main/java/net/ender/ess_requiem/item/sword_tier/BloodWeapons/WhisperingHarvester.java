package net.ender.ess_requiem.item.sword_tier.BloodWeapons;

import io.redspace.ironsspellbooks.api.item.weapons.ExtendedSwordItem;
import io.redspace.ironsspellbooks.api.item.weapons.MagicSwordItem;
import io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import net.ender.ess_requiem.item.GGSwordTier;
import net.ender.ess_requiem.registries.GGSpellRegistry;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class WhisperingHarvester extends MagicSwordItem {


    public WhisperingHarvester() {
        super(GGSwordTier.ROTTEN_SICKLE,  new Item.Properties().rarity(Rarity.RARE).attributes(ExtendedSwordItem.createAttributes(GGSwordTier.WHISPERING_HARVESTER)),
                SpellDataRegistryHolder.of(new SpellDataRegistryHolder(SpellRegistry.SACRIFICE_SPELL, 4),
                        new SpellDataRegistryHolder(GGSpellRegistry.NECROTIC_BURST, 3))
        );
    }

}
