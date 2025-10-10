package com.master.mastermod.client.entity;

import com.master.mastermod.MasterMod;
import com.master.mastermod.common.entity.projectile.HealingFireballEntity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import com.master.mastermod.common.entity.projectile.HealingFireballEntity;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;

public class HealingFireballRenderer extends EntityRenderer<HealingFireballEntity> {

        private static final ResourceLocation TEXTURE = new ResourceLocation(MasterMod.MOD_ID,
                        "textures/entity/healing_fireball.png");
        private static final float SCALE = 1.0F;

        public HealingFireballRenderer(EntityRendererManager manager) {
                super(manager);
        }

        @Override
        public void render(HealingFireballEntity entity, float entityYaw, float partialTicks, MatrixStack matrixStack,
                        IRenderTypeBuffer buffer, int packedLight) {
                matrixStack.pushPose();
                matrixStack.scale(SCALE, SCALE, SCALE);
                matrixStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
                matrixStack.mulPose(Vector3f.YP.rotationDegrees(180.0F));
                MatrixStack.Entry entry = matrixStack.last();
                Matrix4f matrix4f = entry.pose();
                Matrix3f matrix3f = entry.normal();
                IVertexBuilder vertexBuilder = buffer
                                .getBuffer(RenderType.entityCutoutNoCull(this.getTextureLocation(entity)));
                vertex(vertexBuilder, matrix4f, matrix3f, packedLight, 0.0F, 0, 0, 1);
                vertex(vertexBuilder, matrix4f, matrix3f, packedLight, 1.0F, 0, 1, 1);
                vertex(vertexBuilder, matrix4f, matrix3f, packedLight, 1.0F, 1, 1, 0);
                vertex(vertexBuilder, matrix4f, matrix3f, packedLight, 0.0F, 1, 0, 0);
                matrixStack.popPose();
                super.render(entity, entityYaw, partialTicks, matrixStack, buffer, packedLight);
        }

        private static void vertex(IVertexBuilder builder, Matrix4f matrix4f, Matrix3f matrix3f, int packedLight, float x,
                        int y, int u, int v) {
                builder.vertex(matrix4f, x - 0.5F, (float) y - 0.25F, 0.0F).color(255, 255, 255, 255)
                                .uv((float) u, (float) v).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight)
                                .normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
        }

        @Override
        public ResourceLocation getTextureLocation(HealingFireballEntity entity) {
                return TEXTURE;
        }
}
