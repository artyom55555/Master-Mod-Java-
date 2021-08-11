package com.master.mastermod.core.init;

import com.master.mastermod.MasterMod;
import com.master.mastermod.common.block.EnchantedFood;
import com.master.mastermod.common.block.MyBlockItem;
import com.master.mastermod.common.block.MyEnchantedBlockItem;
import com.master.mastermod.common.item.CustomSpawnEggItem;
import com.master.mastermod.common.item.MyItem;
import com.master.mastermod.common.material.CustomArmorVaterial;
import com.master.mastermod.common.material.CustomToolMaterial;
import com.master.mastermod.common.material.MultiToolItem;
import com.master.mastermod.core.itemgroup.MasterModItemGroups;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Food;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.Rarity;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SwordItem;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

// Регистрация всех предметов
public class ItemsInit {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MasterMod.MOD_ID);
	public static final DeferredRegister<Item> SpecialBlockInit = DeferredRegister.create(ForgeRegistries.ITEMS,
			MasterMod.MOD_ID);
	
	public static final RegistryObject<Item> GLOWING_MUSHROOM_BLOCK = ITEMS.register("glowing_mushroom_block",
			() -> new MyBlockItem(BlocksInit.GLOWING_MUSHROOM_BLOCK.get(),
					new Item.Properties().tab(MasterModItemGroups.MY_BLOCKS_GROUP).fireResistant()).setTextComponent(
							TextFormatting.DARK_PURPLE, "Луч света в кромешной тьме", "Необычные светящиеся грибы, растущие под землёй",
							true));
	
	public static final RegistryObject<Item> BLOCK_OF_DARK_INGOTS = ITEMS.register("block_of_dark_ingots",
			() -> new MyBlockItem(BlocksInit.BLOCK_OF_DARK_INGOTS.get(),
					new Item.Properties().tab(MasterModItemGroups.MY_BLOCKS_GROUP).fireResistant()).setTextComponent(
							TextFormatting.DARK_PURPLE, "Просто груда тёмных слитков", "Крайне прочен и взрывойстойчив",
							true));

	public static final RegistryObject<Item> DARK_ORE = ITEMS.register("dark_ore",
			() -> new MyBlockItem(BlocksInit.DARK_ORE.get(),
					new Item.Properties().tab(MasterModItemGroups.MY_BLOCKS_GROUP).fireResistant()).setTextComponent(
							TextFormatting.DARK_PURPLE, "Откуда взялась эта тьма?", "Похоже, что, пролежав во тьме\n"
									 + "слишком долго, бедрок накопил её",
							true));

	@SuppressWarnings("deprecation")
	public static final RegistryObject<Item> GOLDEN_MUSHROOM = ITEMS.register("golden_mushroom",
			() -> new MyEnchantedBlockItem(BlocksInit.GOLDEN_MUSHROOM_BLOCK.get(),
					new EnchantedFood.Properties().tab(MasterModItemGroups.MY_BLOCKS_GROUP)
							.food(new Food.Builder().nutrition(6).saturationMod(1.2F).fast().alwaysEat()
									.effect(new EffectInstance(Effects.MOVEMENT_SPEED, 1800, 3), 0.2F)
									.effect(new EffectInstance(Effects.DIG_SPEED, 1200, 1), 0.2F)
									.effect(new EffectInstance(Effects.DAMAGE_BOOST, 600, 3), 0.3F)
									.effect(new EffectInstance(Effects.JUMP, 600, 4), 0.3F)
									.effect(new EffectInstance(Effects.CONFUSION, 400, 2), 0.1F)
									.effect(new EffectInstance(Effects.HEAL, 400, 3), 0.1F)
									.effect(new EffectInstance(Effects.REGENERATION, 600, 1), 0.3F)
									.effect(new EffectInstance(Effects.DAMAGE_RESISTANCE, 400, 4), 0.3F)
									.effect(new EffectInstance(Effects.FIRE_RESISTANCE, 2400, 1), 0.2F)
									.effect(new EffectInstance(Effects.WATER_BREATHING, 2400, 3), 0.2F)
									.effect(new EffectInstance(Effects.INVISIBILITY, 2400, 3), 0.1F)
									.effect(new EffectInstance(Effects.NIGHT_VISION, 1200, 0), 0.2F)
									.effect(new EffectInstance(Effects.HEALTH_BOOST, 2400, 9), 0.05F)
									.effect(new EffectInstance(Effects.ABSORPTION, 2400, 9), 0.05F)
									.effect(new EffectInstance(Effects.SATURATION, 2400, 10), 0.05F)
									.effect(new EffectInstance(Effects.GLOWING, 1200, 0), 0.5F)
									.effect(new EffectInstance(Effects.LUCK, 1200, 2), 0.1F)
									.effect(new EffectInstance(Effects.SLOW_FALLING, 600, 3), 0.1F)
									.effect(new EffectInstance(Effects.CONDUIT_POWER, 1200, 5), 0.1F)
									.effect(new EffectInstance(Effects.DOLPHINS_GRACE, 1200, 1), 0.05F)
									.effect(new EffectInstance(Effects.HERO_OF_THE_VILLAGE, 2400, 1), 0.05F).build())
							.rarity(Rarity.RARE)).setTextComponent(TextFormatting.GOLD,
									"Впитал в себя весь блеск золотой руды.",
									"Излучает силу и удачу, но запах не очень.\n" + "Пожалуй, стоит откусить кусочек.",
									true));

	@SuppressWarnings("deprecation")
	public static final RegistryObject<Item> DARK_MUSHROOM = ITEMS
			.register("dark_mushroom",
					() -> new MyEnchantedBlockItem(BlocksInit.DARK_MUSHROOM.get(),
							new EnchantedFood.Properties().tab(MasterModItemGroups.MY_BLOCKS_GROUP)
									.food(new Food.Builder().nutrition(6).saturationMod(1.2F).fast().alwaysEat()
											.effect(new EffectInstance(Effects.REGENERATION, 600, 0), 1.0F)
											.effect(new EffectInstance(Effects.HEALTH_BOOST, 1800, 4), 0.5F)
											.effect(new EffectInstance(Effects.ABSORPTION, 1800, 4), 0.5F)
											.effect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 400, 2), 0.3F)
											.effect(new EffectInstance(Effects.DIG_SLOWDOWN, 600, 2), 0.3F)
											.effect(new EffectInstance(Effects.WEAKNESS, 600, 3), 0.2F)
											.effect(new EffectInstance(Effects.HARM, 1, 1), 0.1F)
											.effect(new EffectInstance(Effects.CONFUSION, 600, 4), 0.4F)
											.effect(new EffectInstance(Effects.BLINDNESS, 400, 3), 0.3F)
											.effect(new EffectInstance(Effects.HUNGER, 400, 4), 0.2F)
											.effect(new EffectInstance(Effects.POISON, 400, 1), 0.1F)
											.effect(new EffectInstance(Effects.WITHER, 400, 1), 0.1F)
											.effect(new EffectInstance(Effects.GLOWING, 1200, 0), 0.5F)
											.effect(new EffectInstance(Effects.LEVITATION, 400, 0), 0.1F)
											.effect(new EffectInstance(Effects.UNLUCK, 1200, 10),
													0.1F)
											.effect(new EffectInstance(Effects.BAD_OMEN, 1200, 19), 0.1F).build())
									.rarity(Rarity.EPIC)).setTextComponent(TextFormatting.DARK_PURPLE,
											"От него так и веет тьмой.\nЛучше не рисковать.",
											"В тех местах, где он растёт, никогда не светило солнце.\n"
													+ "Побочные эффекты: тошнота, головная боль, судороги, возможна смерть...",
											true));

	public static final RegistryObject<Item> DISPLAY_CASE = ITEMS.register("display_case",
			() -> new MyBlockItem(BlocksInit.DISPLAY_CASE.get(),
					new Item.Properties().tab(MasterModItemGroups.MY_BLOCKS_GROUP).fireResistant()).setTextComponent(
							TextFormatting.DARK_PURPLE, "Сундук повышенной прочности и вместительности",
							"Взрывоустойчив.\n" + "Благодаря особым свойствам "
									+ "тёмного металла способен проецировать\n"
									+ "содержимое своего специального слота",
							true));

	public static final RegistryObject<Item> CLOUD_BLOCK = ITEMS.register("cloud_block",
			() -> new MyBlockItem(BlocksInit.CLOUD_BLOCK.get(),
					new Item.Properties().tab(MasterModItemGroups.MY_BLOCKS_GROUP)).setTextComponent(
							TextFormatting.WHITE, "Мягкий и воздушный.", "Осторожно, легко исчезает!", true));

	public static final RegistryObject<Item> DENSE_CLOUD_BLOCK = ITEMS.register("dense_cloud_block",
			() -> new MyBlockItem(BlocksInit.DENSE_CLOUD_BLOCK.get(),
					new Item.Properties().tab(MasterModItemGroups.MY_BLOCKS_GROUP)).setTextComponent(
							TextFormatting.WHITE, "Уже не такой мягкий, зато более прочный", "", false));

	public static final RegistryObject<Item> PARACHUTE = ITEMS.register("parachute",
			() -> new MyItem(new Item.Properties().tab(MasterModItemGroups.MY_MATERIALS_GROUP).stacksTo(1))
					.setTextComponent(TextFormatting.WHITE, "Разбежавшись, прыгну со скалы!",
							"Сделан из самых мягких облаков.\n" + "Позволяет уверенно, а главное медленно падать вниз",
							true));

	public static final RegistryObject<Item> PIECE_OF_DARKNESS = ITEMS.register("piece_of_darkness",
			() -> new MyItem(new Item.Properties().tab(MasterModItemGroups.MY_MATERIALS_GROUP).rarity(Rarity.EPIC).fireResistant())
					.setTextComponent(TextFormatting.DARK_PURPLE,
							"Если слишком долго вглядывать во тьму,\n"
									+ "тьма начинает вглядыаться в тебя",
							"Укрепляет мягкие металлы.\n"
									+ "Пожалуй, стоит собрать таких побольше",
							true));

	public static final RegistryObject<Item> INGOT_OF_DARKNESS = ITEMS.register("ingot_of_darkness",
			() -> new MyItem(new Item.Properties().tab(MasterModItemGroups.MY_MATERIALS_GROUP).fireResistant()).setTextComponent(
					TextFormatting.DARK_PURPLE, "Очень прочен и податлив",
					"Вам, кажется, что с подобным материалов вы сможите сделать\n"
							+ "нечто большее, чем простой топор, кирку или лопату",
					true));

	public static final RegistryObject<Item> DARK_DIAMOND = ITEMS.register("dark_diamond",
			() -> new MyItem(new Item.Properties().tab(MasterModItemGroups.MY_MATERIALS_GROUP).rarity(Rarity.COMMON).fireResistant())
					.setTextComponent(TextFormatting.DARK_PURPLE, "Блестит,но как-то иначе",
							"Вы укрепили алмаз до предела.\n" + "Теперь он способен удержать в себе любую силу.",
							true));

	public static final RegistryObject<Item> DARK_CORE = ITEMS.register("dark_core",
			() -> new MyItem(new Item.Properties().tab(MasterModItemGroups.MY_MATERIALS_GROUP).rarity(Rarity.EPIC).fireResistant())
					.setTextComponent(TextFormatting.DARK_PURPLE, "В нём ощущается мощная тёмная энергия",
							"Похоже, этот предмет легко способен\n" + "соединить несколько тёмных инструментов.",
							true));

	// 13 - урон, 2 - скорость атаки.
	public static final RegistryObject<Item> SWORD_OF_DARKNESS = ITEMS.register("sword_of_darkness",
			() -> new SwordItem(CustomToolMaterial.DARK_TOOL, 13, -2f,
					new Item.Properties().tab(MasterModItemGroups.MY_TOOLS_AND_ARMOR_GROUP).fireResistant()));

	public static final RegistryObject<Item> AXE_OF_DARKNESS = ITEMS.register("axe_of_darkness",
			() -> new AxeItem(CustomToolMaterial.DARK_TOOL, 15, -3f,
					new Item.Properties().tab(MasterModItemGroups.MY_TOOLS_AND_ARMOR_GROUP).fireResistant()));

	public static final RegistryObject<Item> PICAXE_OF_DARKNESS = ITEMS.register("picaxe_of_darkness",
			() -> new PickaxeItem(CustomToolMaterial.DARK_TOOL, 9, -2.8f,
					new Item.Properties().tab(MasterModItemGroups.MY_TOOLS_AND_ARMOR_GROUP).fireResistant()));

	public static final RegistryObject<Item> HOE_OF_DARKNESS = ITEMS.register("hoe_of_darkness",
			() -> new HoeItem(CustomToolMaterial.DARK_TOOL, 2, 0f,
					new Item.Properties().tab(MasterModItemGroups.MY_TOOLS_AND_ARMOR_GROUP).fireResistant()));

	public static final RegistryObject<Item> SHOVEL_OF_DARKNESS = ITEMS.register("shovel_of_darkness",
			() -> new ShovelItem(CustomToolMaterial.DARK_TOOL, 9, -3f,
					new Item.Properties().tab(MasterModItemGroups.MY_TOOLS_AND_ARMOR_GROUP).fireResistant()));

	public static final RegistryObject<Item> MULTITOOL_OF_DARKNESS = ITEMS.register("multitool_of_darkness",
			() -> new MultiToolItem(CustomToolMaterial.DARK_MULTI_TOOL, 6, -3f,
					new Item.Properties().tab(MasterModItemGroups.MY_TOOLS_AND_ARMOR_GROUP).fireResistant()));

	// Материал, в какой слот надевать, свойства
	public static final RegistryObject<Item> HELMET_OF_DARKNESS = ITEMS.register("helmet_of_darkness",
			() -> new ArmorItem(CustomArmorVaterial.ARMOR_OF_DARKNESS, EquipmentSlotType.HEAD,
					new Item.Properties().tab(MasterModItemGroups.MY_TOOLS_AND_ARMOR_GROUP).fireResistant()));

	public static final RegistryObject<Item> CHESTPLATE_OF_DARKNESS = ITEMS.register("chestplate_of_darkness",
			() -> new ArmorItem(CustomArmorVaterial.ARMOR_OF_DARKNESS, EquipmentSlotType.CHEST,
					new Item.Properties().tab(MasterModItemGroups.MY_TOOLS_AND_ARMOR_GROUP).fireResistant()));

	public static final RegistryObject<Item> LEGGIGS_OF_DARKNESS = ITEMS.register("leggigs_of_darkness",
			() -> new ArmorItem(CustomArmorVaterial.ARMOR_OF_DARKNESS, EquipmentSlotType.LEGS,
					new Item.Properties().tab(MasterModItemGroups.MY_TOOLS_AND_ARMOR_GROUP).fireResistant()));

	public static final RegistryObject<Item> BOOTS_OF_DARKNESS = ITEMS.register("boots_of_darkness",
			() -> new ArmorItem(CustomArmorVaterial.ARMOR_OF_DARKNESS, EquipmentSlotType.FEET,
					new Item.Properties().tab(MasterModItemGroups.MY_TOOLS_AND_ARMOR_GROUP).fireResistant()));
	
	public static final RegistryObject<Item> TRAVELER_DARK_BOOTS= ITEMS.register("traveler_dark_boots",
			() -> new ArmorItem(CustomArmorVaterial.ARMOR_OF_DARKNESS, EquipmentSlotType.FEET,
					new Item.Properties().tab(MasterModItemGroups.MY_TOOLS_AND_ARMOR_GROUP).fireResistant()));
	
	// яйцо спауна
	public static final RegistryObject<CustomSpawnEggItem> DARK_CUBE_SPAWN_EGG = ITEMS.register("dark_cube_spawn_egg",
			() -> new CustomSpawnEggItem(EntityTypesInit.DARK_CUBE, 0x714B8F, 0x110B19,
					new Item.Properties().tab(MasterModItemGroups.MY_MATERIALS_GROUP)));

}
