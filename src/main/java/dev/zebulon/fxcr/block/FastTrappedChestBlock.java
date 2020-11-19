package dev.zebulon.fxcr.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.enums.ChestType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.Direction;

public class FastTrappedChestBlock extends HorizontalFacingBlock {

	public static final EnumProperty<ChestType> CHEST_TYPE = Properties.CHEST_TYPE;

	public FastTrappedChestBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.stateManager.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH)
				.with(CHEST_TYPE, ChestType.SINGLE));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
		stateManager.add(Properties.HORIZONTAL_FACING);
		stateManager.add(Properties.CHEST_TYPE);
	}

	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return (BlockState) this.getDefaultState().with(FACING, ctx.getPlayerFacing()).with(CHEST_TYPE, ChestType.SINGLE);
	}
}
