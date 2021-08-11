package com.master.mastermod.common.block;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

//Класс для грибов, чтобы они имели характерный блеск
public class MyEnchantedBlockItem extends MyBlockItem{

	public MyEnchantedBlockItem(Block block, Properties prop) {
		super(block, prop);
	}
	
	@Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }

}
