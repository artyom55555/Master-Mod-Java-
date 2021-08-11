package com.master.mastermod.common.block;

import java.util.stream.Stream;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.MushroomBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;

// Класс для особой модельки блока гриба
public class MyMushroomBlock extends MushroomBlock {

	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
	private static final VoxelShape SHAPE = Stream
			.of(Block.box(6, 0, 5.999999999999998, 10, 5, 10), Block.box(4, 5, 4, 12, 6, 12),
					Block.box(5, 6, 5, 11, 7, 11), Block.box(6, 7, 6, 10, 8, 10), Block.box(7, 8, 7, 9, 9, 9))
			.reduce((v1, v2) -> {
				return VoxelShapes.join(v1, v2, IBooleanFunction.OR);
			}).get();

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader wordIn, BlockPos pos, ISelectionContext context) {
		return SHAPE;
	}

	public MyMushroomBlock(AbstractBlock.Properties prop) {
		super(prop);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
	}

	// Для корректной установки блока
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
	}

	// Позволяет контролировать модель блока
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(FACING);
	}

	@Override
	public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) {
		BlockPos blockpos = pos.below();
		BlockState blockstate = worldIn.getBlockState(blockpos);
		if (!blockstate.is(BlockTags.MUSHROOM_GROW_BLOCK)) {
			return blockstate.canSustainPlant(worldIn, blockpos, Direction.UP, this);
		} else {
			return true;
		}
	}

}
