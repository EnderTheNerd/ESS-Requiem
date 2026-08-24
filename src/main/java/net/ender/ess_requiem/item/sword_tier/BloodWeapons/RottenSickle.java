package net.ender.ess_requiem.item.sword_tier.BloodWeapons;

import io.redspace.ironsspellbooks.api.item.weapons.ExtendedSwordItem;
import io.redspace.ironsspellbooks.api.item.weapons.MagicSwordItem;
import io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import net.ender.ess_requiem.item.GGSwordTier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class RottenSickle extends MagicSwordItem {


 public RottenSickle() {
   super(GGSwordTier.ROTTEN_SICKLE, new Item.Properties().rarity(Rarity.COMMON).attributes(ExtendedSwordItem.createAttributes(GGSwordTier.ROTTEN_SICKLE)),
  SpellDataRegistryHolder.of(new SpellDataRegistryHolder(SpellRegistry.SACRIFICE_SPELL, 2))
  );
 }


 }


