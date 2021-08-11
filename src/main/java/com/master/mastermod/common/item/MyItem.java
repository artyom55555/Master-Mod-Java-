package com.master.mastermod.common.item;

import java.util.List;

import com.master.mastermod.core.util.Forge16MasterKeyboardUtil;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

// Свой предмет с описанием
public class MyItem extends Item {
	public TextFormatting textFormat;
	public String firstDisc;
	public String secondnDisc;
	public boolean isSecondDisc;
	
	public MyItem(Properties prop) {
		super(prop);
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
	
	public MyItem setTextComponent(TextFormatting textFormat, String firstDisc, String secaonDisc, boolean isSecondDisc) {
		this.textFormat = textFormat;
		this.firstDisc = firstDisc;
		this.secondnDisc = secaonDisc;
		this.isSecondDisc = isSecondDisc;
		return this;
	}

}
