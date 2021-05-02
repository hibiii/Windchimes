package hibiii.windchimes;

import org.jetbrains.annotations.Nullable;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.Tickable;

public class WindchimeBlockEntity extends BlockEntity implements Tickable {
	public int ringingTicks;
	protected int ticksToNextRing;
	protected int baselineRingTicks;
	@Nullable private ChimeType cachedType;
	private boolean cachedTypeNeedsUpdate;
	private final int tickDisplacement;
	public WindchimeBlockEntity() {
		this(ChimeType.INVALID);
	}
	public WindchimeBlockEntity(ChimeType type) {
		super(Initializer.CHIME_BLOCK_ENTITY);
		ringingTicks = 0;
		ticksToNextRing = 40;
		baselineRingTicks = 0;
		this.cachedType = type;
		cachedTypeNeedsUpdate = true;
		this.tickDisplacement = (this.pos.getX() + this.pos.getY() + this.pos.getZ()) % 6;
	}
	public void ring(int isLoud) {
		if(world.getBlockState(pos.down()).isAir())
			world.addSyncedBlockEvent(pos, world.getBlockState(pos).getBlock(), 1, isLoud);
	}
	
	
	@Override
    public boolean onSyncedBlockEvent(int type, int data) {
		if(type == 1) {
			if (data == 0) {
				ringingTicks = 30;
				world.playSound(null, pos, this.getChimeType().loudSound, SoundCategory.RECORDS,
						0.9f + world.random.nextFloat() * 0.2f,
						0.8f + world.random.nextFloat() * 0.4f);
			}
			else {
				ringingTicks = 140;
				world.playSound(null, pos, this.getChimeType().loudSound, SoundCategory.RECORDS,
						0.9f + world.random.nextFloat() * 0.2f,
						0.8f + world.random.nextFloat() * 0.4f);
			}
			return true;
		}
        return super.onSyncedBlockEvent(type, data);
    }
	@Override
	public void tick() {
		if(world.isClient) {
			if(ringingTicks > baselineRingTicks)
				ringingTicks--;
			if(ringingTicks < baselineRingTicks) {
				ringingTicks = baselineRingTicks;
			}
			if(world.isRaining()) {
				if(world.isThundering())
					baselineRingTicks = 26;
				else
					baselineRingTicks = 12;
			} else {
				if(world.isDay())
					baselineRingTicks = 0;
				else
					baselineRingTicks = 6;
			}
			return;
		}
		ticksToNextRing -= 1;
		if(world.getTime() % 6 == this.tickDisplacement && ticksToNextRing <= 0) {
			ring(world.random.nextInt(0xff) >> 5);
			ticksToNextRing = 0;
			if(world.isRaining()) {
				if(world.isThundering())
					ticksToNextRing += world.random.nextInt(200);       // 0 - 10s
				else
					ticksToNextRing += 100 + world.random.nextInt(600); // 5 - 35s
			} else {
				if(world.isDay())
					ticksToNextRing += 300 + world.random.nextInt(2100); // 15s - 2mins
				else
					ticksToNextRing += 100 + world.random.nextInt(700); // 5 - 40s
			}
		}
	}
	
	public ChimeType getChimeType() {
		if(cachedTypeNeedsUpdate) {
			cachedType = ((WindchimeBlock)this.getCachedState().getBlock()).getChimeType();
			cachedTypeNeedsUpdate = false;
		}
		return cachedType;
	}
}
