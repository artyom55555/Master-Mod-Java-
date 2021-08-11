package com.master.mastermod.common.block;

import java.util.List;

import com.master.mastermod.core.util.Forge16MasterKeyboardUtil;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

// Особый класс предметов-блока с добавлением к ним описания
public class MyBlockItem extends BlockItem {
	public TextFormatting textFormat;
	public String firstDisc;
	public String secondnDisc;
	public boolean isSecondDisc;

	public MyBlockItem(Block block, Properties prop) {
		super(block, prop);
		this.textFormat = TextFormatting.WHITE;
		this.firstDisc = "";
		this.secondnDisc = "";
		this.isSecondDisc = false;
	}

	@Override
	public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		if (!Forge16MasterKeyboardUtil.isHoldingLeftShift()) {
			tooltip.add(new StringTextComponent(this.textFormat + this.firstDisc));
			if (this.isSecondDisc)
				tooltip.add(new StringTextComponent(TextFormatting.WHITE + "Нажмите LShift, чтобы узнать больше..."));
		} else {
			if (this.isSecondDisc)
				tooltip.add(new StringTextComponent(this.textFormat + this.secondnDisc));
		}
	}

	public MyBlockItem setTextComponent(TextFormatting textFormat, String firstDisc, String secaonDisc,
			boolean isSecondDisc) {
		this.textFormat = textFormat;
		this.firstDisc = firstDisc;
		this.secondnDisc = secaonDisc;
		this.isSecondDisc = isSecondDisc;
		return this;
	}
}
