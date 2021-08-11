package com.master.mastermod.common.block;

import java.util.stream.Stream;

import com.master.mastermod.common.tileentity.DisplayCaseTileEntity;
import com.master.mastermod.core.init.TileEntityTypesInit;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

// Класс для сундука, в основном содержится информация о направленности блока
public class DisplayCaseBlock extends Block {

	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

	private static final VoxelShape SHAPE_N = Stream.of(Block.box(1, 0, 1, 15, 14, 15), Block.box(5, 7, 0, 6, 12, 1),
			Block.box(10, 7, 0, 11, 12, 1), Block.box(6, 11, 0, 10, 12, 1), Block.box(5, 6, 0, 11, 7, 1))
			.reduce((v1, v2) -> {
				return VoxelShapes.join(v1, v2, IBooleanFunction.OR);
			}).get();

	private static final VoxelShape SHAPE_E = Stream.of(Block.box(1, 0, 1, 15, 14, 15), Block.box(15, 7, 5, 16, 12, 6),
			Block.box(15, 7, 10, 16, 12, 11), Block.box(15, 11, 6, 16, 12, 10), Block.box(15, 6, 5, 16, 7, 11))
			.reduce((v1, v2) -> {
				return VoxelShapes.join(v1, v2, IBooleanFunction.OR);
			}).get();

	private static final VoxelShape SHAPE_W = Stream.of(Block.box(1, 0, 1, 15, 14, 15), Block.box(0, 7, 10, 1, 12, 11),
			Block.box(0, 7, 5, 1, 12, 6), Block.box(0, 11, 6, 1, 12, 10), Block.box(0, 6, 5, 1, 7, 11))
			.reduce((v1, v2) -> {
				return VoxelShapes.join(v1, v2, IBooleanFunction.OR);
			}).get();

	private static final VoxelShape SHAPE_S = Stream
			.of(Block.box(1, 0, 1, 15, 14, 15), Block.box(10, 7, 15, 11, 12, 16), Block.box(5, 7, 15, 6, 12, 16),
					Block.box(6, 11, 15, 10, 12, 16), Block.box(5, 6, 15, 11, 7, 16))
			.reduce((v1, v2) -> {
				return VoxelShapes.join(v1, v2, IBooleanFunction.OR);
			}).get();

	public DisplayCaseBlock() {
		super(AbstractBlock.Properties.of(Material.METAL, MaterialColor.COLOR_GRAY).strength(15.0F)
				.sound(SoundType.METAL));

		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
	}

	public DisplayCaseBlock(Properties prop) {
		super(prop);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader wordIn, BlockPos pos, ISelectionContext context) {
		switch (state.getValue(FACING)) {
		case EAST:
			return SHAPE_E;
		case SOUTH:
			return SHAPE_S;
		case WEST:
			return SHAPE_W;
		default:
			return SHAPE_N;

		}
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
	}

	@Override
	public BlockState rotate(BlockState state, IWorld world, BlockPos pos, Rotation direction) {
		return state.setValue(FACING, direction.rotate(state.getValue(FACING)));
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return TileEntityTypesInit.DISPLAY_CASE_TILE_ENTITY_TYPE.get().create();
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
	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn,
			BlockRayTraceResult hit) {
		if (!worldIn.isClientSide()) {
			TileEntity te = worldIn.getBlockEntity(pos);
			if (te instanceof DisplayCaseTileEntity) {
				NetworkHooks.openGui((ServerPlayerEntity) player, (DisplayCaseTileEntity) te, pos);
			}
		}
		return ActionResultType.SUCCESS;
		// return super.use(state, worldIn, pos, player, handIn, hit);
	}

	// Вызывается при разрушении блока
	@SuppressWarnings("deprecation")
	public void onRemove(BlockState state, World world, BlockPos pos, BlockState state2, boolean boole) {
		if (!state.is(state2.getBlock())) {
			TileEntity tileentity = world.getBlockEntity(pos);
			if (tileentity instanceof IInventory) {
				InventoryHelper.dropContents(world, pos, (IInventory) tileentity);
				world.updateNeighbourForOutputSignal(pos, this);
			}
			super.onRemove(state, world, pos, state2, boole);
		}
	}
}
