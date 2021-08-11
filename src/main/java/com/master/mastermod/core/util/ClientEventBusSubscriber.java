package com.master.mastermod.core.util;

import com.master.mastermod.MasterMod;
import com.master.mastermod.client.entity.DarkCubeEntityRenderer;
import com.master.mastermod.client.screen.DisplayCaseScreen;
import com.master.mastermod.client.tileentityrender.DisplayCaseTileEntituRenderer;
import com.master.mastermod.core.init.BlocksInit;
import com.master.mastermod.core.init.ContainerTypesInit;
import com.master.mastermod.core.init.EntityTypesInit;
import com.master.mastermod.core.init.KeybindsInit;
import com.master.mastermod.core.init.TileEntityTypesInit;

import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

// Связывает GUI с контейнером
@Mod.EventBusSubscriber(modid = MasterMod.MOD_ID, bus = Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber {

	@SubscribeEvent
	public static void clientSetup(FMLClientSetupEvent event) {

		KeybindsInit.register(event);

		ScreenManager.register(ContainerTypesInit.DISPLAY_CASE_CONTAINER_TYPE.get(), DisplayCaseScreen::new);

		RenderTypeLookup.setRenderLayer(BlocksInit.DISPLAY_CASE.get(), RenderType.cutout());
		
		ClientRegistry.bindTileEntityRenderer(TileEntityTypesInit.DISPLAY_CASE_TILE_ENTITY_TYPE.get(),
				DisplayCaseTileEntituRenderer::new);

		RenderingRegistry.registerEntityRenderingHandler(EntityTypesInit.DARK_CUBE.get(), DarkCubeEntityRenderer::new);
	}
}






