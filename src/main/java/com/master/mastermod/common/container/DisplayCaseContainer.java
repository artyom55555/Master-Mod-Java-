package com.master.mastermod.common.container;

import java.util.Objects;

import com.master.mastermod.common.tileentity.DisplayCaseTileEntity;
import com.master.mastermod.core.init.BlocksInit;
import com.master.mastermod.core.init.ContainerTypesInit;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;

// Класс для контейнера, здесь создаются отображаемые слоты
public class DisplayCaseContainer extends Container {
	
	// slot counts
	// Количество слотов указано в DisplayCaseTileEntity!!!
	public static final int SLOT_ROWS = 8;
	public static final int SLOT_COLUMNS = 13;
	public static final int SLOT_SPACING = 18;
	public static final int INPUT_START_X = 8;
	public static final int INPUT_START_Y = 8;
	public static final int BACKPACK_START_X = 44;
	public static final int BACKPACK_START_Y = 174;
	
	public final DisplayCaseTileEntity te;
	private final IWorldPosCallable canInteractWithCallable;

	public DisplayCaseContainer(final int windowId, final PlayerInventory playerInv, final DisplayCaseTileEntity te) {
		super(ContainerTypesInit.DISPLAY_CASE_CONTAINER_TYPE.get(), windowId);
		this.te = te;
		this.canInteractWithCallable = IWorldPosCallable.create(te.getLevel(), te.getBlockPos());

		// Инвентарь сундука
		for (int row=0; row < SLOT_ROWS; row++)
		{
			int y = INPUT_START_Y + SLOT_SPACING*row;
			for (int column=0; column < SLOT_COLUMNS; column++)
			{
				int x = INPUT_START_X + SLOT_SPACING*column;
				int index = row * SLOT_COLUMNS + column;
				this.addSlot(new Slot((IInventory) te, index, x, y));
			}
		}
		this.addSlot(new Slot((IInventory) te, 104, 8, 174));
		
		// Main Player Inventory
		for (int row=0; row < 3; row++)
		{
			int y = BACKPACK_START_Y + SLOT_SPACING * row;
			for (int col=0; col < 9; col++)
			{
				int x = BACKPACK_START_X + SLOT_SPACING*col;
				this.addSlot(new Slot(playerInv, col + row * 9 + 9, x, y));
			}
		}

		// Player Hotbar
		for (int col = 0; col < 9; col++) {
			this.addSlot(new Slot(playerInv, col, 44 + col * 18, 232));
		}

	}

	public DisplayCaseContainer(final int windowId, final PlayerInventory playerInv, final PacketBuffer data) {
		this(windowId, playerInv, getTileEntity(playerInv, data));
	}

	private static DisplayCaseTileEntity getTileEntity(final PlayerInventory playerInv, final PacketBuffer data) {
		Objects.requireNonNull(playerInv, "Player Inventory cannot be null.");
		Objects.requireNonNull(data, "Packer Buffer cannot be null.");
		final TileEntity te = playerInv.player.level.getBlockEntity(data.readBlockPos());
		if (te instanceof DisplayCaseTileEntity) {
			return (DisplayCaseTileEntity) te;
		}
		throw new IllegalStateException("Tile Entity is not Correct");
	}

	@Override
	public boolean stillValid(PlayerEntity playerIn) {
		return stillValid(canInteractWithCallable, playerIn, BlocksInit.DISPLAY_CASE.get());
	}

	@Override
	public ItemStack quickMoveStack(PlayerEntity playerIn, int index) {
		ItemStack stack = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);
		if (slot != null && slot.hasItem()) {
			ItemStack stack1 = slot.getItem();
			stack = stack.copy();
                        // Простыми словами: shift-клик из витрины кладёт предмет в инвентарь игрока,
                        // а shift-клик из инвентаря игрока пытается вернуть его в витрину.
                        if (index < DisplayCaseTileEntity.slots) {
                                if (!this.moveItemStackTo(stack1, DisplayCaseTileEntity.slots, this.slots.size(), false)) {
                                        return ItemStack.EMPTY;
                                }
                        } else if (!this.moveItemStackTo(stack1, 0, DisplayCaseTileEntity.slots, false)) {
                                return ItemStack.EMPTY;
                        }
			if (stack1.isEmpty()) {
				slot.set(ItemStack.EMPTY);
			} else {
				slot.setChanged();
			}
		}
		return stack;
	}
}
