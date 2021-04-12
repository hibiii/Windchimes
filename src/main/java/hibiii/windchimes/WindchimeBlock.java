package hibiii.windchimes;

import java.util.Map;
import java.util.Random;

import com.google.common.collect.Maps;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class WindchimeBlock extends BlockWithEntity implements BlockEntityProvider {

	private static final VoxelShape SHAPE = Block.createCuboidShape(4.0, 8.0, 4.0, 12.0, 16.0, 12.0);

	public final ChimeType chimeType;
	public WindchimeBlock(ChimeType type, Settings settings) {
		super(settings);
		this.chimeType = type;
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return WindchimeBlock.SHAPE;
	}

	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		BlockEntity i = world.getBlockEntity(pos);
		if(i instanceof WindchimeBlockEntity) {
			if(world.getBlockState(pos.down()).isAir()) {
				((WindchimeBlockEntity)i).ring(1);
			}
		} else {
			i.markRemoved();
			world.setBlockState(pos, Blocks.AIR.getDefaultState());
		}
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
	public BlockEntity createBlockEntity(BlockView view) {
		return new WindchimeBlockEntity(this.chimeType);
	}
	
	@Override
	public boolean onSyncedBlockEvent(BlockState state, World world, BlockPos pos, int type, int data) {
		return world.getBlockEntity(pos).onSyncedBlockEvent(type, data);
	}
	
	public ChimeType getChimeType() {
		if(this.material == Material.METAL) {
			return ChimeType.IRON;
		}
		return null;
	}
}
