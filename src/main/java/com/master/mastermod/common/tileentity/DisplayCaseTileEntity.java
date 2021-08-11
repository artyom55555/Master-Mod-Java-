package com.master.mastermod.common.tileentity;

import com.master.mastermod.MasterMod;
import com.master.mastermod.common.container.DisplayCaseContainer;
import com.master.mastermod.core.init.TileEntityTypesInit;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

// Сущность - сундук
public class DisplayCaseTileEntity extends LockableLootTileEntity {
	
	// Количество слотов в сундуке
	public static int slots = 105;
	private static final String TAG_ITEMS = "Items";
	private static final String TAG_SLOT = "Slot";
	  
	protected NonNullList<ItemStack> items = NonNullList.withSize(slots, ItemStack.EMPTY);

	protected DisplayCaseTileEntity(TileEntityType<?> typeIn) {
		super(typeIn);
	}

	public DisplayCaseTileEntity() {
		this(TileEntityTypesInit.DISPLAY_CASE_TILE_ENTITY_TYPE.get());
	}

	@Override
	public int getContainerSize() {
		return slots;
	}

	@Override
	protected NonNullList<ItemStack> getItems() {
		return this.items;
	}

	public ItemStack getItem() {
		return this.items.get(104);
	}
	
	@Override
	public CompoundNBT getUpdateTag() {
		CompoundNBT nbt = super.getUpdateTag();
		writeInventoryToNBT(nbt);
		return nbt;
	}
	
	// Сохранить вещи, чтобы при загрузке мира предмет сразу рендерился
	public void writeInventoryToNBT(CompoundNBT tag) {
		IInventory inventory = this;
		ListNBT nbttaglist = new ListNBT();

		for (int i = 0; i < inventory.getContainerSize(); i++) {
			if (!inventory.getItem(i).isEmpty()) {
				CompoundNBT itemTag = new CompoundNBT();
				itemTag.putByte(TAG_SLOT, (byte) i);
				inventory.getItem(i).save(itemTag);
				nbttaglist.add(itemTag);
			}
		}

		tag.put(TAG_ITEMS, nbttaglist);
	}

	@Override
	protected void setItems(NonNullList<ItemStack> itemsIn) {
		this.items = itemsIn;

	}

	@Override
	protected ITextComponent getDefaultName() {
		return new TranslationTextComponent("container." + MasterMod.MOD_ID + ".display_case");
	}

	@Override
	protected Container createMenu(int id, PlayerInventory player) {
		return new DisplayCaseContainer(id, player, this);
	}

	@Override
	public CompoundNBT save(CompoundNBT compound) {
		super.save(compound);
		if (!this.trySaveLootTable(compound)) {
			ItemStackHelper.saveAllItems(compound, items);
		}
		return compound;
	}

	@Override
	public void load(BlockState state, CompoundNBT nbt) {
		super.load(state, nbt);
		this.items = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);
		if (!this.tryLoadLootTable(nbt)) {
			ItemStackHelper.loadAllItems(nbt, this.items);
		}
	}
}
