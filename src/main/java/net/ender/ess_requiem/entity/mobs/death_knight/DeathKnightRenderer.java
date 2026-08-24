package net.ender.ess_requiem.entity.mobs.death_knight;

import com.mojang.blaze3d.vertex.PoseStack;
import net.ender.ess_requiem.EndersSpellsAndStuffRequiem;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class DeathKnightRenderer extends GeoEntityRenderer<DeathKnightEntity> {
    public DeathKnightRenderer(EntityRendererProvider.Context renderManager, GeoModel<DeathKnightEntity> model) {
        super(renderManager, model);
        this.shadowRadius = .7f;
    }

    @Override
    public ResourceLocation getTextureLocation(DeathKnightEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(EndersSpellsAndStuffRequiem.MOD_ID, "textures/entity/death_knight.png");
    }

    @Override
    public void render(DeathKnightEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
