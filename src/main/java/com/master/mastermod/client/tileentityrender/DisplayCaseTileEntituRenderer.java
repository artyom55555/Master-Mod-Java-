package com.master.mastermod.client.tileentityrender;

import com.master.mastermod.common.block.DisplayCaseBlock;
import com.master.mastermod.common.tileentity.DisplayCaseTileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

// Класс для генерации содержимого контейнера
public class DisplayCaseTileEntituRenderer extends TileEntityRenderer<DisplayCaseTileEntity> {

	private Minecraft mc = Minecraft.getInstance();

	public DisplayCaseTileEntituRenderer(TileEntityRendererDispatcher rendererDispatcher) {
		super(rendererDispatcher);
	}

	// Главная функция, генерирующая блок и его название
	@Override
	public void render(DisplayCaseTileEntity te, float particalTicks, MatrixStack matrixStackIn,
			IRenderTypeBuffer bufferIn, int combinedLightIn, int combienedOverlayIn) {
		if (te.getItem().equals(ItemStack.EMPTY) || te.getItem().getItem().equals(Items.AIR))
			return;

		ClientPlayerEntity player = mc.player;
		BlockState state = te.getBlockState();
		// Надо ли вращать сгенерированный блок
		boolean isRotated = applyRotation(matrixStackIn, state);
		// С какой стороны разместить отображаемый блок
		int lightLevel = getLightLevel(te.getLevel(), te.getBlockPos().above());
		te.getBlockState().getValue(DisplayCaseBlock.FACING);
		// {0.5d, 0.6d, 0.5d} - xzy где сгенерировать блок
		renderItem(te.getItem(), new double[] { 0.5d, 0.45d, 0.0d }, Vector3f.YP.rotationDegrees(180f - player.yRot),
				matrixStackIn, bufferIn, particalTicks, combienedOverlayIn, lightLevel, 0.7f);

		// отображение имени DisplayName
		ITextComponent label = te.getItem().hasCustomHoverName() ? te.getItem().getHoverName()
				: new TranslationTextComponent(te.getItem().getDescriptionId());
		renderLabel(matrixStackIn, bufferIn, lightLevel, new double[] { 0.5d, 0.45d, 0.0d }, label, 0xffffff);
		if (isRotated) {
			matrixStackIn.popPose();
		}
	}

	// Эта и следующая функции поворачивают генерированный блок до тех пор,
	// пока его напрвление не совпадёт с блоком TileEntity
	public static boolean applyRotation(MatrixStack matrices, BlockState state) {
		if (state.hasProperty(BlockStateProperties.HORIZONTAL_FACING)) {
			return applyRotationN(matrices, state.getValue(BlockStateProperties.HORIZONTAL_FACING));
		}
		return false;
	}

	public static boolean applyRotationN(MatrixStack matrices, Direction facing) {
		// Если блок на севере, то вращать не надо
		if (facing.getAxis().isHorizontal() && facing != Direction.NORTH) {
			matrices.pushPose();
			if (facing == Direction.SOUTH) {
				matrices.translate(0.5, 0, 0.5);
				matrices.mulPose(Vector3f.YP.rotationDegrees(180f));
				matrices.translate(-0.5, 0, -0.5);
				return true;
			}
			matrices.translate(0.5, 0, 0.5);
			matrices.mulPose(Vector3f.YP.rotationDegrees(90f * (facing.get2DDataValue())));
			matrices.translate(-0.5, 0, -0.5);
			return true;
		}

		return false;
	}

	// отображение объекта
	private void renderItem(ItemStack stack, double[] translation, Quaternion rotation, MatrixStack matrixStack,
			IRenderTypeBuffer buffer, float partialTick, int combinedOverlay, int lightLevel, float scale) {
		matrixStack.pushPose();
		matrixStack.translate(translation[0], translation[1], translation[2]);
		// Вращение блока за игроком
		// matrixStack.mulPose(rotation);
		matrixStack.scale(scale, scale, scale);

		IBakedModel model = mc.getItemRenderer().getModel(stack, null, null);
		mc.getItemRenderer().render(stack, ItemCameraTransforms.TransformType.GROUND, true, matrixStack, buffer,
				lightLevel, combinedOverlay, model);
		matrixStack.popPose();
	}

	// Отобразить имя объекта
	private void renderLabel(MatrixStack stack, IRenderTypeBuffer buffer, int lightLevel, double[] corner,
			ITextComponent text, int color) {

		FontRenderer font = mc.font;

		stack.pushPose();
		float scale = 0.01f;
		int opacity = (int) (.4f * 255.0f) << 24;
		float offset = (float) (-font.width(text) / 2);
		Matrix4f matrix = stack.last().pose();

		stack.translate(corner[0], corner[1] + .4f, corner[2]);
		stack.scale(scale, scale, scale);
		// Вращение надписи за игроком
		// stack.mulPose(mc.getEntityRenderDispatcher().cameraOrientation());
		stack.mulPose(Vector3f.ZP.rotationDegrees(180f));

		font.drawInBatch(text, offset, 0, color, false, matrix, buffer, false, opacity, lightLevel);
		stack.popPose();
	}

	private int getLightLevel(World world, BlockPos pos) {
		int bLight = world.getBrightness(LightType.BLOCK, pos);
		int sLight = world.getBrightness(LightType.SKY, pos);
		return LightTexture.pack(bLight, sLight);
	}

	public boolean isGlobalRenderer(DisplayCaseTileEntity tile) {
		return !tile.isEmpty();
	}
}
