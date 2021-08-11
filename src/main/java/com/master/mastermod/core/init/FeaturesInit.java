package com.master.mastermod.core.init;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.template.BlockMatchRuleTest;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraft.world.gen.feature.template.TagMatchRuleTest;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;
import net.minecraftforge.event.world.BiomeLoadingEvent;

//Генерация_руд и грибов
public class FeaturesInit {
	// В эту функцию добавлять новую руду по такому же принципу
	public static void addOres(final BiomeLoadingEvent event) {

		addOre(event, OreFeatureConfig.FillerBlockType.NATURAL_STONE, BlocksInit.DARK_ORE.get().defaultBlockState(), 4,
				0, 6, 4);

		addOre(event, new TagMatchRuleTest(BlockTags.BASE_STONE_NETHER), BlocksInit.DARK_ORE.get().defaultBlockState(),
				6, 0, 127, 20);

		addOre(event, new BlockMatchRuleTest(Blocks.END_STONE), BlocksInit.DARK_ORE.get().defaultBlockState(), 10, 0,
				127, 20);

		addOre(event, new BlockMatchRuleTest(Blocks.GOLD_ORE),
				BlocksInit.GOLDEN_MUSHROOM_BLOCK.get().defaultBlockState(), 4, 0, 45, 100);

		addMushroom(event, BlocksInit.GOLDEN_MUSHROOM_BLOCK.get().defaultBlockState());

		addOre(event, new TagMatchRuleTest(BlockTags.BASE_STONE_OVERWORLD),
				BlocksInit.DARK_MUSHROOM.get().defaultBlockState(), 4, 0, 5, 5);

		addOre(event, OreFeatureConfig.FillerBlockType.NATURAL_STONE,
				BlocksInit.GLOWING_MUSHROOM_BLOCK.get().defaultBlockState(), 8, 0, 40, 15);
	}

	// addOre(event, где_будет_генерироваться, что_будет_генерироваться,
	// раземр_жилы, миниамльная_высота_генерации, максимальная_высота_генерации,
	// количество_жил_в_одном_чанке)
	public static void addOre(final BiomeLoadingEvent event, RuleTest rule, BlockState state, int veinSize,
			int minHeight, int maxHeight, int amount) {
		event.getGeneration().addFeature(GenerationStage.Decoration.UNDERGROUND_ORES,
				Feature.ORE.configured(new OreFeatureConfig(rule, state, veinSize))
						.decorated(Placement.RANGE.configured(new TopSolidRangeConfig(minHeight, 0, maxHeight)))
						.squared().count(amount));

	}

	public static void addMushroom(final BiomeLoadingEvent event, BlockState state) {
		event.getGeneration().addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION,
				Feature.RANDOM_PATCH
						.configured((new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(state),
								SimpleBlockPlacer.INSTANCE)).tries(64).noProjection().build())
						.decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE).chance(15));
	}
}
