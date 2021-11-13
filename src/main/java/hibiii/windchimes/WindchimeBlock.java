package hibiii.windchimes;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class WindchimeBlock extends BlockWithEntity {

	private static final VoxelShape SHAPE = Block.createCuboidShape(4.0, 8.0, 4.0, 12.0, 16.0, 12.0);

	private ChimeType chimeType = ChimeType.INVALID;
	public WindchimeBlock(ChimeType chimeTypeIn, Settings settings) {
		super(settings);
		this.chimeType = chimeTypeIn;
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return WindchimeBlock.SHAPE;
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
		if (world.isAir(pos.up())) {
			world.getBlockEntity(pos).markRemoved();
			return Blocks.AIR.getDefaultState();
		}
		return super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
	}

	@Override
	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		return !world.isAir(pos.up()) && world.isAir(pos.down());
	}

	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new WindchimeBlockEntity(pos, state);
	}

	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> givenType) {
		return WindchimeBlock.checkType(givenType, Initializer.CHIME_BLOCK_ENTITY, WindchimeBlockEntity::tick);
	}
	
	@Override
	public boolean onSyncedBlockEvent(BlockState state, World world, BlockPos pos, int type, int data) {
		return world.getBlockEntity(pos).onSyncedBlockEvent(type, data);
	}
	
	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		WindchimeBlockEntity here = ((WindchimeBlockEntity)world.getBlockEntity(pos));
		here.ring(!player.isSneaking());
		here.ticksToNextRing += 4;
		return ActionResult.success(world.isClient);
	}
	
	public ChimeType getChimeType() {
		return this.chimeType;
	}
}
