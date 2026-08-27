package net.ender.ess_requiem.entity.mobs.vessel_skeleton;


import com.mojang.blaze3d.vertex.PoseStack;
import net.ender.ess_requiem.EndersSpellsAndStuffRequiem;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class VesselSkeletonRenderer  extends GeoEntityRenderer<VesselSkeletonEntity> {

    public VesselSkeletonRenderer(EntityRendererProvider.Context renderManager, GeoModel<VesselSkeletonEntity> model) {
        super(renderManager, model);
        this.shadowRadius = .6f;
    }

    @Override
    public ResourceLocation getTextureLocation(VesselSkeletonEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(EndersSpellsAndStuffRequiem.MOD_ID, "textures/entity/vessel_skeleton.png");
    }

    @Override
    public void render(VesselSkeletonEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
