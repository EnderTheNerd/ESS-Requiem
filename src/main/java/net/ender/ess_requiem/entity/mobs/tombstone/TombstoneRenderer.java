package net.ender.ess_requiem.entity.mobs.tombstone;

import com.mojang.blaze3d.vertex.PoseStack;
import net.ender.ess_requiem.EndersSpellsAndStuffRequiem;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class TombstoneRenderer extends GeoEntityRenderer<TombstoneEntity> {
    public TombstoneRenderer(EntityRendererProvider.Context renderManager, GeoModel<TombstoneEntity> model) {
        super(renderManager, model);
        this.shadowRadius = 0.5f;
    }

    @Override
    public ResourceLocation getTextureLocation(TombstoneEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(EndersSpellsAndStuffRequiem.MOD_ID, "textures/entity/tombstone.png");
    }

    @Override
    public void render(TombstoneEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
