package com.master.mastermod.common.entity;

import com.master.mastermod.MasterMod;

import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

// Не используется
@EventBusSubscriber(modid = MasterMod.MOD_ID)
public class EntityEvents {
	// спавн моба в биоме
	@SubscribeEvent
	public static void onBiomeLoad(final BiomeLoadingEvent event) {
		return;
		/*if (event.getName() == null)
			return;
		MobSpawnInfoBuilder spawns = event.getSpawns();
		

		 * if (event.getCategory().equals(Biome.Category.THEEND)) {
		 * System.out.println(spawns.getSpawner(EntityClassification.MONSTER).size());
		 * // размеры моба, наименьшая группа, наибольшая группа
		 * spawns.addSpawn(EntityClassification.MONSTER, new
		 * MobSpawnInfo.Spawners(EntityTypesInit.DARK_CUBE.get(), 10, 3, 10));
		 * System.out.println(spawns.getSpawner(EntityClassification.MONSTER).size()); }
		 */
	}
}
