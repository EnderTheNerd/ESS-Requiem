package net.ender.ess_requiem.entity.mobs.bone_maggot;

import com.mojang.blaze3d.vertex.PoseStack;
import net.ender.ess_requiem.EndersSpellsAndStuffRequiem;
import net.ender.ess_requiem.entity.mobs.battle_standard.BattleStandardEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class BoneMaggotRenderer extends GeoEntityRenderer<BoneMaggotEntity> {


    public BoneMaggotRenderer(EntityRendererProvider.Context renderManager, GeoModel<BoneMaggotEntity> model) {
        super(renderManager, model);
        this.shadowRadius = 0.05f;
    }

    @Override
    public ResourceLocation getTextureLocation(BoneMaggotEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(EndersSpellsAndStuffRequiem.MOD_ID, "textures/entity/bone_maggot.png");
    }

    @Override
    public void render(BoneMaggotEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
