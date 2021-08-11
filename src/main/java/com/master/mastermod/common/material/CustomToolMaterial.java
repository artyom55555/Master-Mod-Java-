package com.master.mastermod.common.material;

import java.util.function.Supplier;

import com.master.mastermod.core.init.ItemsInit;

import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;

// Класс для создания инструментов
public enum CustomToolMaterial implements IItemTier{
	
	// harvestLevel, maxUses, speed, attackDamageBonus, enchantmentValue, repairIngredient
	DARK_TOOL(4, 3500, 15f, -1f, 20, () -> Ingredient.of(ItemsInit.DARK_DIAMOND.get())),
	DARK_MULTI_TOOL(4, 6000, 16f, -1f, 30, () -> Ingredient.of(ItemsInit.DARK_DIAMOND.get()));
	private final int harvestLevel;
	private final int maxUses;
	private final float speed;
	private final float attackDamage;
	private final int enchantmentValue;
	private final Ingredient repairIngredient;
	
	private CustomToolMaterial(int harvestLevel, int maxUses, float speed, float attackDamage, int enchantmentValue, Supplier<Ingredient> repairIngredient) {
		this.harvestLevel = harvestLevel;
		this.maxUses = maxUses;
		this.speed = speed;
		this.attackDamage = attackDamage;
		this.enchantmentValue = enchantmentValue;
		this.repairIngredient = repairIngredient.get();
	}
	
	@Override
	public int getUses() {
		return this.maxUses;
	}
	@Override
	public float getSpeed() {
		return this.speed;
	}
	@Override
	public float getAttackDamageBonus() {
		return this.attackDamage;
	}
	@Override
	public int getLevel() {
		return this.harvestLevel;
	}
	@Override
	public int getEnchantmentValue() {
		return this.enchantmentValue;
	}
	@Override
	public Ingredient getRepairIngredient() {
		return this.repairIngredient;
	}
}
