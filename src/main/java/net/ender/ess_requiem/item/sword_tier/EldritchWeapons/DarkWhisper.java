package net.ender.ess_requiem.item.sword_tier.EldritchWeapons;

import io.redspace.ironsspellbooks.api.item.weapons.ExtendedSwordItem;
import io.redspace.ironsspellbooks.api.item.weapons.MagicSwordItem;
import io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder;
import net.ender.ess_requiem.item.GGSwordTier;
import net.ender.ess_requiem.registries.GGSpellRegistry;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class DarkWhisper extends MagicSwordItem {


    public DarkWhisper() {
        super(GGSwordTier.DARK_WHISPER,  new Item.Properties().rarity(Rarity.COMMON).attributes(ExtendedSwordItem.createAttributes(GGSwordTier.DARK_WHISPER)),
                SpellDataRegistryHolder.of(new SpellDataRegistryHolder(GGSpellRegistry.EBONY_ARMOR, 1))
        );
    }
}
