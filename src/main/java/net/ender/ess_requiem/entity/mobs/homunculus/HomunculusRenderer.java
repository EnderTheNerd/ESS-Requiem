package net.ender.ess_requiem.entity.mobs.homunculus;


import com.mojang.blaze3d.vertex.PoseStack;
import net.ender.ess_requiem.EndersSpellsAndStuffRequiem;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class HomunculusRenderer extends GeoEntityRenderer<HomunculusEntity> {

    public HomunculusRenderer(EntityRendererProvider.Context renderManager, GeoModel<HomunculusEntity> model) {
        super(renderManager, model);
        this.shadowRadius = 0.05f;
    }

    @Override
    public ResourceLocation getTextureLocation(HomunculusEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(EndersSpellsAndStuffRequiem.MOD_ID, "textures/entity/son.png");
    }

    @Override
    public void render(HomunculusEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
