package net.ender.ess_requiem.item.sword_tier.EldritchWeapons;

import io.redspace.ironsspellbooks.api.item.weapons.ExtendedSwordItem;
import io.redspace.ironsspellbooks.api.item.weapons.MagicSwordItem;
import io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder;
import net.ender.ess_requiem.item.GGSwordTier;
import net.ender.ess_requiem.registries.GGSpellRegistry;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class MidnightEmbrace extends MagicSwordItem {

    public MidnightEmbrace() {
        super(GGSwordTier.MIDNIGHT_WHISPER,  new Item.Properties().rarity(Rarity.RARE).attributes(ExtendedSwordItem.createAttributes(GGSwordTier.MIDNIGHT_WHISPER)
        ),  SpellDataRegistryHolder.of(new SpellDataRegistryHolder(GGSpellRegistry.EBONY_ARMOR, 2),
                new SpellDataRegistryHolder(GGSpellRegistry.TWILIGHT_ASSAULT, 2))
        );
    }

}
