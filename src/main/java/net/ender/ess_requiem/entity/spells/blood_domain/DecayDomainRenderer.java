package net.ender.ess_requiem.entity.spells.blood_domain;

import net.ender.ess_requiem.EndersSpellsAndStuffRequiem;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class DecayDomainRenderer  extends GeoEntityRenderer<DecayDomain> {
    public DecayDomainRenderer(EntityRendererProvider.Context context) {
        super(context, new DecayDomainModel(ResourceLocation.fromNamespaceAndPath(EndersSpellsAndStuffRequiem.MOD_ID, "decay_domain")));
        this.shadowRadius = 0.5f;
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull DecayDomain infiniteVoid) {
        return ResourceLocation.fromNamespaceAndPath(EndersSpellsAndStuffRequiem.MOD_ID, "textures/entity/blood_domain/blood_domain.png");
    }
}
