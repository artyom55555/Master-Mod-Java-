package com.master.mastermod;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.master.mastermod.common.entity.DarkCubeEntity;
import com.master.mastermod.common.item.CustomSpawnEggItem;
import com.master.mastermod.core.init.BlocksInit;
import com.master.mastermod.core.init.ContainerTypesInit;
import com.master.mastermod.core.init.EntityTypesInit;
import com.master.mastermod.core.init.FeaturesInit;
import com.master.mastermod.core.init.ItemsInit;
import com.master.mastermod.core.init.TileEntityTypesInit;
import com.master.mastermod.core.network.MasterModNetwork;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

// Главный файл мода
// Для работы мода нужен Patchouli [1.16.4-48,1.16.4-53.1] версии!!!
@Mod("mastermod")
@Mod.EventBusSubscriber(modid = MasterMod.MOD_ID, bus = Bus.MOD)
public class MasterMod {
	// F3 + T в игре перезагрзуит всю папку assets
	
	public static final Logger LOGGER = LogManager.getLogger();
	public static final String MOD_ID = "mastermod";

	public MasterMod() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

		bus.addListener(this::commonSetup);
		
		// Регистрация всех существ, блоков, предметов, сущностей, контейнеров
		EntityTypesInit.ENTITY_TYPES.register(bus);
		BlocksInit.BLOCKS.register(bus);
		ItemsInit.ITEMS.register(bus);
		TileEntityTypesInit.TILE_ENTITY_TYPE.register(bus);
		ContainerTypesInit.CONTAINER_TYPES.register(bus);
		
		// Для рендеринга прозрачных блоков, генерации руд и просто регистрации всего
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
		MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, FeaturesInit::addOres);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	// Регистрация своего яйца спауна
	@SubscribeEvent
	public static void onRegisterEntities(final RegistryEvent.Register<EntityType<?>> event) {
		CustomSpawnEggItem.initSpawnEggs();
	}
	
	// Для регистрации существ
	@SuppressWarnings("deprecation")
	public void commonSetup(final FMLCommonSetupEvent event) {
		MasterModNetwork.init();

		DeferredWorkQueue.runLater(() -> {
			GlobalEntityTypeAttributes.put(EntityTypesInit.DARK_CUBE.get(), DarkCubeEntity.setAttributes().build());
		});
	}

	// Особый рендеринг для прозрачных блоков
	private void doClientStuff(final FMLClientSetupEvent event) {
		RenderTypeLookup.setRenderLayer(BlocksInit.CLOUD_BLOCK.get(), RenderType.translucent());
		RenderTypeLookup.setRenderLayer(BlocksInit.DENSE_CLOUD_BLOCK.get(), RenderType.translucent());
	}
}
