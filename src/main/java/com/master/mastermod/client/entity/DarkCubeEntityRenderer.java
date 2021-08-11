package com.master.mastermod.client.entity;

import com.master.mastermod.MasterMod;
import com.master.mastermod.client.entity.model.DarkCubeEntityModel;
import com.master.mastermod.common.entity.DarkCubeEntity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

//Отвечает за рендеринг моба, его текстуры
public class DarkCubeEntityRenderer extends MobRenderer<DarkCubeEntity, DarkCubeEntityModel<DarkCubeEntity>> {

	public static final ResourceLocation TEXTURE = new ResourceLocation(MasterMod.MOD_ID,
			"textures/entity/dark_cube/dark_cube.png");

	public DarkCubeEntityRenderer(EntityRendererManager manager) {
		super(manager, new DarkCubeEntityModel<>(), 0.7f);
	}

	@Override
	public ResourceLocation getTextureLocation(DarkCubeEntity entity) {
		return TEXTURE;
	}
}