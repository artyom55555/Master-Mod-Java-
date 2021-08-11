package com.master.mastermod.common.block;

import com.master.mastermod.common.item.MyItem;

import net.minecraft.item.ItemStack;

// Зачарованная еда. Позволяет предметам мерцать, будто они зачарованы
public class EnchantedFood extends MyItem{
	public EnchantedFood(Properties properties) {
		super(properties);
	}
 
    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }
}
