package com.master.mastermod.client.screen;

import com.master.mastermod.MasterMod;
import com.master.mastermod.common.container.DisplayCaseContainer;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

// Класс, отвечающий за показ окна для особого сундука (связано с GUI)
@OnlyIn(Dist.CLIENT)
public class DisplayCaseScreen extends ContainerScreen<DisplayCaseContainer> {

	private static final ResourceLocation DISPLAY_CASE_GUI = new ResourceLocation(MasterMod.MOD_ID,
			"textures/gui/display_case.png");

	// В конструктуре задаются основные параметры окна
	public DisplayCaseScreen(DisplayCaseContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);

		this.leftPos = 0;
		this.topPos = 0;
		this.imageWidth = 247; // 175
		this.imageHeight = 255; // 201
		this.imageWidth = 256; // 176
		this.imageHeight = 256; // 166
		this.inventoryLabelX = 43; // 8
	    this.inventoryLabelY = 164; // this.imageHeight - 94
	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(matrixStack);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		this.renderTooltip(matrixStack, mouseX, mouseY);
	}

	@Override
	protected void renderLabels(MatrixStack matrixStack, int x, int y) {
		this.font.draw(matrixStack, this.inventory.getDisplayName(), (float) this.inventoryLabelX,
				(float) this.inventoryLabelY, 4210752);
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void renderBg(MatrixStack matrixStack, float partialTickets, int mouseX, int mouseY) {
		RenderSystem.color4f(1F, 1F, 1F, 1F);
		this.minecraft.textureManager.bind(DISPLAY_CASE_GUI);
		int x = (this.width - this.imageWidth) / 2;
		int y = (this.height - this.imageHeight) / 2;
		this.blit(matrixStack, x, y, 0, 0, this.imageWidth, this.imageHeight);
	}

}

