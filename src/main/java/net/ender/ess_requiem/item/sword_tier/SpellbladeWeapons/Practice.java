package net.ender.ess_requiem.item.sword_tier.SpellbladeWeapons;

import io.redspace.ironsspellbooks.api.item.weapons.ExtendedSwordItem;
import io.redspace.ironsspellbooks.api.item.weapons.MagicSwordItem;
import io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder;
import net.ender.ess_requiem.item.GGSwordTier;
import net.ender.ess_requiem.registries.GGSpellRegistry;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class Practice extends MagicSwordItem {

    public Practice() {
        super(GGSwordTier.PRACTICE,  new Item.Properties().rarity(Rarity.RARE).attributes(ExtendedSwordItem.createAttributes(GGSwordTier.PRACTICE)),
                SpellDataRegistryHolder.of(new SpellDataRegistryHolder(GGSpellRegistry.SLASH, 3),
                        new SpellDataRegistryHolder(GGSpellRegistry.SLAM, 3))
        );
    }
}
