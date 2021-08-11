package com.master.mastermod.common.block;

import java.util.Random;

import net.minecraft.block.OreBlock;
import net.minecraft.util.math.MathHelper;

// Блок тёмной руды, перегружено получаемое количество опыта
public class DarkOre extends OreBlock{

	public DarkOre(Properties prop) {
		super(prop);
	}
	
	@Override
	public int xpOnDrop(Random rand) {
			return MathHelper.nextInt(rand, 10, 20);
	   }
}
