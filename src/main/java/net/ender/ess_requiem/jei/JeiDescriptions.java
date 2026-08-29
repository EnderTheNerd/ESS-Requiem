package net.ender.ess_requiem.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeRegistration;
import net.ender.ess_requiem.EndersSpellsAndStuffRequiem;
import net.ender.ess_requiem.registries.GGItemRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

@JeiPlugin
public class JeiDescriptions implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(EndersSpellsAndStuffRequiem.MOD_ID, "jei_plugin");
    }


    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addItemStackInfo(new ItemStack(GGItemRegistry.REQUIEM_STAFF.get()), Component.translatable("item.ess_requiem.requiem_staff.guide"));
        registration.addItemStackInfo(new ItemStack(GGItemRegistry.DEAD_KING_SOUL.get()), Component.translatable("item.ess_requiem.dead_king_soul.guide"));
        registration.addItemStackInfo(new ItemStack(GGItemRegistry.BROKEN_PROMISE.get()), Component.translatable("item.ess_requiem.broken_promise.guide"));
    }
}
