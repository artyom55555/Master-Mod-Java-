package com.master.mastermod.common.material;

import java.util.function.Supplier;

import com.master.mastermod.core.init.ItemsInit;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

// Для изменения текстур брони, нужно загружить слои текстур брони в
// resources/assets.minecraft.textures.models.armor. Это нужно для отображения брони на игроке!!!
// Их нужно переименовать в, например, darkness_layer_1.png и darkness_layer_2.png.
// darkness - это название, которое указано в ARMOR_OF_DARKNESS("darkness"...

// Класс, для создания брони
public enum CustomArmorVaterial implements IArmorMaterial {
	// Название брони, множитель, на который умножается baseDurability(прочность),
	// количество брони, количество зачарований, звук при надевании, твердость брони,
	// сопротивление отбразыванию, ингредиенты брони.
	ARMOR_OF_DARKNESS("darkness", 3500, new int[] { 5, 8, 10, 5 }, 25, SoundEvents.ARMOR_EQUIP_DIAMOND, 5f, 0.3f,
			() -> Ingredient.of(ItemsInit.DARK_DIAMOND.get()));
	

	// Прочность элементов брони по умолчанию
	private static final int[] baseDurability = { 128, 144, 160, 112 };
	private final String name;
	private final int DurabilityForSlot;
	private final int[] armorVal;
	private final int enchantmentValue;
	private final SoundEvent equipSound;
	private final float toughness;
	private final float knockbackResistance;
	private final Ingredient repairIngredient;

	private CustomArmorVaterial(String name, int DurabilityForSlot, int[] armorVal, int enchantmentValue,
			SoundEvent equipSound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient) {
		this.name = name;
		this.DurabilityForSlot = DurabilityForSlot;
		this.armorVal = armorVal;
		this.enchantmentValue = enchantmentValue;
		this.equipSound = equipSound;
		this.toughness = toughness;
		this.knockbackResistance = knockbackResistance;
		this.repairIngredient = repairIngredient.get();
	}

	@SuppressWarnings("static-access")
	@Override
	public int getDurabilityForSlot(EquipmentSlotType slot) {
		return this.baseDurability[slot.getIndex()] = this.DurabilityForSlot;
	}

	@Override
	public int getDefenseForSlot(EquipmentSlotType slot) {
		return this.armorVal[slot.getIndex()];
	}

	@Override
	public int getEnchantmentValue() {
		return this.enchantmentValue;
	}

	@Override
	public SoundEvent getEquipSound() {
		return this.equipSound;
	}

	@Override
	public Ingredient getRepairIngredient() {
		return this.repairIngredient;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public float getToughness() {
		return this.toughness;
	}

	@Override
	public float getKnockbackResistance() {
		return this.knockbackResistance;
	}

}
