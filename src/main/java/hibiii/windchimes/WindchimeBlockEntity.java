package hibiii.windchimes;

import org.jetbrains.annotations.Nullable;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WindchimeBlockEntity extends BlockEntity {
	
	public int ringingTicks;
	public float strengthDivisor = 35f;
	
	protected int ticksToNextRing;
	protected int baselineRingTicks;
	
	@Nullable protected ChimeType cachedType;
	protected boolean cachedTypeNeedsUpdate;
	
	protected final int tickDisplacement;

	public WindchimeBlockEntity(BlockPos pos, BlockState state) {
		super(Initializer.CHIME_BLOCK_ENTITY, pos, state);
		this.ringingTicks = 0;
		this.ticksToNextRing = 40;
		this.baselineRingTicks = 0;
		this.cachedType = ((WindchimeBlock)state.getBlock()).getChimeType();
		this.cachedTypeNeedsUpdate = true;
		this.tickDisplacement = Math.abs(this.pos.getX() + this.pos.getY() + this.pos.getZ()) % 6;
	}
	
	public void ring(boolean isLoud) {
		if(this.world.getBlockState(this.pos.down()).isAir())
			this.world.addSyncedBlockEvent(this.pos, this.world.getBlockState(this.pos).getBlock(), 1, isLoud? 1: 0);
	}
	
	
	@Override
    public boolean onSyncedBlockEvent(int type, int data) {
		if(type == 1) {
			if (data == 0) {
				this.ringingTicks = 60;
				this.strengthDivisor = 35f;
				this.world.playSound(null, this.pos, this.getChimeType().quietSound, SoundCategory.RECORDS,
						0.9f + this.world.random.nextFloat() * 0.2f,
						0.8f + this.world.random.nextFloat() * 0.4f);
			}
			else {
				this.strengthDivisor = 55f;
				this.ringingTicks = 140;
				this.world.playSound(null, this.pos, this.getChimeType().loudSound, SoundCategory.RECORDS,
						0.9f + this.world.random.nextFloat() * 0.2f,
						0.8f + this.world.random.nextFloat() * 0.4f);
			}
			return true;
		}
        return super.onSyncedBlockEvent(type, data);
    }
	
	public static void tick(World world, BlockPos pos, BlockState state, WindchimeBlockEntity that) {
		if(world.isClient) {
			if(that.ringingTicks > that.baselineRingTicks)
				that.ringingTicks--;
			if(that.ringingTicks < that.baselineRingTicks) {
				that.ringingTicks = that.baselineRingTicks;
			}
			if(world.isRaining()) {
				if(world.isThundering())
					that.baselineRingTicks = 26;
				else
					that.baselineRingTicks = 12;
			}
			else {
				if(world.isDay())
					that.baselineRingTicks = 0;
				else
					that.baselineRingTicks = 6;
			}
			return;
		}
		
		that.ticksToNextRing -= 1;
		if(that.ticksToNextRing <= 0 && world.getTime() % 6 == that.tickDisplacement) {
			if(world.isRaining()) {
				if(world.isThundering()) {
					that.ticksToNextRing = world.random.nextInt(200);       // 0 - 10s
					that.ring(world.random.nextInt(4) != 0);          // 75% chance of loudness
				}
				else {
					that.ticksToNextRing = 100 + world.random.nextInt(400); // 5 - 25s
					that.ring(world.random.nextInt(3) == 0);          // 33% chance of loudness
				}
			}
			else {
				if(world.isDay()) {
					that.ticksToNextRing = 200 + world.random.nextInt(900); // 10s - 55s
					that.ring(world.random.nextInt(5) == 0);          // 25% chance of loudness
				}
				else {
					that.ticksToNextRing = 100 + world.random.nextInt(700); // 5 - 40s
					that.ring(world.random.nextInt(5) == 0);          // 20% chance of loudness
				}
			}
		}
	}
	
	public ChimeType getChimeType() {
		if(this.cachedTypeNeedsUpdate) {
			this.cachedType = ((WindchimeBlock)this.getCachedState().getBlock()).getChimeType();
			this.cachedTypeNeedsUpdate = false;
		}
		return this.cachedType;
	}
}
