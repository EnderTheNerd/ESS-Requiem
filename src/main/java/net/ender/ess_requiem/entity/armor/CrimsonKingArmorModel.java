package net.ender.ess_requiem.entity.armor;

import io.redspace.ironsspellbooks.IronsSpellbooks;
import net.ender.ess_requiem.EndersSpellsAndStuffRequiem;
import net.ender.ess_requiem.item.armor.CrimsonKingArmorItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedItemGeoModel;

public class CrimsonKingArmorModel  extends DefaultedItemGeoModel<CrimsonKingArmorItem> {


    public CrimsonKingArmorModel() {
        super(ResourceLocation.fromNamespaceAndPath(EndersSpellsAndStuffRequiem.MOD_ID, ""));
    }



    @Override
    public ResourceLocation getModelResource(CrimsonKingArmorItem object) {
        return  ResourceLocation.fromNamespaceAndPath(EndersSpellsAndStuffRequiem.MOD_ID, "geo/crimson_king.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(CrimsonKingArmorItem object) {
        return ResourceLocation.fromNamespaceAndPath(EndersSpellsAndStuffRequiem.MOD_ID, "textures/models/king_armor.png");
    }

    @Override
    public ResourceLocation getAnimationResource(CrimsonKingArmorItem WizardArmorItem) {
        return  ResourceLocation.fromNamespaceAndPath(IronsSpellbooks.MODID, "animations/wizard_armor_animation.json");
    }


}
