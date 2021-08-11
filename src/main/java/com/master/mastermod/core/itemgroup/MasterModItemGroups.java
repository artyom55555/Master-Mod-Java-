package com.master.mastermod.core.itemgroup;

import com.master.mastermod.core.init.BlocksInit;
import com.master.mastermod.core.init.ItemsInit;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

// Класс для создания вкладок мода
public class MasterModItemGroups extends ItemGroup {

	public static final MasterModItemGroups MY_MATERIALS_GROUP = new MasterModItemGroups(ItemGroup.getGroupCountSafe(),
			"my_materials_group") {
		public ItemStack makeIcon() {
			return new ItemStack(ItemsInit.INGOT_OF_DARKNESS.get());
		}
	};
	
	public static final MasterModItemGroups MY_BLOCKS_GROUP = new MasterModItemGroups(ItemGroup.getGroupCountSafe(),
			"my_blocks_group") {
		public ItemStack makeIcon() {
			return new ItemStack(BlocksInit.DARK_ORE.get());
		}
	};
	
	public static final MasterModItemGroups MY_TOOLS_AND_ARMOR_GROUP = new MasterModItemGroups(ItemGroup.getGroupCountSafe(),
			"my_tools_and_armor_group") {
		public ItemStack makeIcon() {
			return new ItemStack(ItemsInit.PICAXE_OF_DARKNESS.get());
		}
	};

	public MasterModItemGroups(int index, String label) {
		super(index, label);
	}

	@Override
	public ItemStack makeIcon() {
		return this.makeIcon();
	}
}
