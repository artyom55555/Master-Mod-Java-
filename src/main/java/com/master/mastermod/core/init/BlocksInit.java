package com.master.mastermod.core.init;

import com.master.mastermod.MasterMod;
import com.master.mastermod.common.block.DarkOre;
import com.master.mastermod.common.block.DisplayCaseBlock;
import com.master.mastermod.common.block.MyMushroomBlock;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

// Регистрация всех блоков
public class BlocksInit {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
			MasterMod.MOD_ID);

	public static final RegistryObject<Block> BLOCK_OF_DARK_INGOTS = BLOCKS.register("block_of_dark_ingots",
			() -> new Block(AbstractBlock.Properties.copy(Blocks.IRON_BLOCK).strength(10.0F, 1200.0F)
					.harvestTool(ToolType.PICKAXE).harvestLevel(2).sound(SoundType.METAL)
					.requiresCorrectToolForDrops()));

	public static final RegistryObject<Block> DARK_ORE = BLOCKS.register("dark_ore",
			() -> new DarkOre(AbstractBlock.Properties.copy(Blocks.IRON_ORE).strength(30.0F, 1200.0F)
					.harvestTool(ToolType.PICKAXE).harvestLevel(3)));

	public static final RegistryObject<Block> GOLDEN_MUSHROOM_BLOCK = BLOCKS.register("golden_mushroom",
			() -> new MyMushroomBlock(AbstractBlock.Properties.copy(Blocks.BROWN_MUSHROOM)));

	public static final RegistryObject<Block> DARK_MUSHROOM = BLOCKS.register("dark_mushroom",
			() -> new MyMushroomBlock(AbstractBlock.Properties.copy(Blocks.RED_MUSHROOM)));

	public static final RegistryObject<Block> CLOUD_BLOCK = BLOCKS.register("cloud_block",
			() -> new Block(AbstractBlock.Properties.of(Material.ICE).friction(0.98F).strength(0.3F).noOcclusion()
					.sound(SoundType.WOOL)));

	public static final RegistryObject<Block> DENSE_CLOUD_BLOCK = BLOCKS.register("dense_cloud_block",
			() -> new Block(AbstractBlock.Properties.of(Material.ICE).friction(0.78F).strength(1.5F).noOcclusion()
					.sound(SoundType.WOOL)));

	public static final RegistryObject<Block> GLOWING_MUSHROOM_BLOCK = BLOCKS.register("glowing_mushroom_block",
			() -> new Block(AbstractBlock.Properties.of(Material.STONE, MaterialColor.COLOR_RED).strength(2.0F).harvestTool(ToolType.PICKAXE)
					.sound(SoundType.SHROOMLIGHT).lightLevel((light) -> {
						return 15;
					})));

	// Сущности (TileEntity)
	public static final RegistryObject<Block> DISPLAY_CASE = BLOCKS
			.register("display_case",
					() -> new DisplayCaseBlock(AbstractBlock.Properties.copy(Blocks.CHEST).strength(20F, 1200.0F)
							.harvestTool(ToolType.PICKAXE).harvestLevel(1).requiresCorrectToolForDrops()
							.sound(SoundType.METAL)));

}
