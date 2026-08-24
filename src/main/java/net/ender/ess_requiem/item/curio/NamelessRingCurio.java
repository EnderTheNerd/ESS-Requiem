package net.ender.ess_requiem.item.curio;

import io.redspace.ironsspellbooks.compat.Curios;
import io.redspace.ironsspellbooks.item.curios.SimpleDescriptiveCurio;
import io.redspace.ironsspellbooks.util.ItemPropertiesHelper;
import net.acetheeldritchking.aces_spell_utils.utils.ASRarities;
import net.minecraft.world.item.Rarity;

public class NamelessRingCurio extends SimpleDescriptiveCurio {
    public NamelessRingCurio() {
        super(ItemPropertiesHelper.equipment().stacksTo(1).rarity(Rarity.UNCOMMON), Curios.RING_SLOT);
    }

}

