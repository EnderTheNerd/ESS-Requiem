package net.ender.ess_requiem.item.curio;

import io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder;
import io.redspace.ironsspellbooks.compat.Curios;
import net.ender.ess_requiem.registries.GGSpellRegistry;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;



public class CataphractRingCurio extends ImbuableCurio{
    public CataphractRingCurio() {
        super( new Item.Properties().stacksTo(1).fireResistant().rarity(Rarity.EPIC), Curios.RING_SLOT,
                SpellDataRegistryHolder.of(new SpellDataRegistryHolder(GGSpellRegistry.EBONY_CATAPHRACT, 1)));
    }

    }

