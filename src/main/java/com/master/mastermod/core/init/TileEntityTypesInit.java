package com.master.mastermod.core.init;

import com.master.mastermod.MasterMod;
import com.master.mastermod.common.tileentity.DisplayCaseTileEntity;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

// Регистрация сущностей
public class TileEntityTypesInit {
	public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPE = DeferredRegister
			.create(ForgeRegistries.TILE_ENTITIES, MasterMod.MOD_ID);

	public static final RegistryObject<TileEntityType<DisplayCaseTileEntity>> DISPLAY_CASE_TILE_ENTITY_TYPE = TILE_ENTITY_TYPE
			.register("display_case", () -> TileEntityType.Builder
					.of(DisplayCaseTileEntity::new, BlocksInit.DISPLAY_CASE.get()).build(null));
}
