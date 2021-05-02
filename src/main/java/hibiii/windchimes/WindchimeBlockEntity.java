package hibiii.windchimes;

import org.jetbrains.annotations.Nullable;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Tickable;

public class WindchimeBlockEntity extends BlockEntity implements Tickable {
	
	public int ringingTicks;
	
	protected int ticksToNextRing;
	protected int baselineRingTicks;
	
	@Nullable protected ChimeType cachedType;
	protected boolean cachedTypeNeedsUpdate;
	
	protected final int tickDisplacement;
	
	public WindchimeBlockEntity() {
		this(ChimeType.INVALID);
	}
	
	public WindchimeBlockEntity(ChimeType type) {
		super(Initializer.CHIME_BLOCK_ENTITY);
		this.ringingTicks = 0;
		this.ticksToNextRing = 40;
		this.baselineRingTicks = 0;
		this.cachedType = type;
		this.cachedTypeNeedsUpdate = true;
		this.tickDisplacement = (this.pos.getX() + this.pos.getY() + this.pos.getZ()) % 6;
	}
	
	public void ring(int isLoud) {
		if(this.world.getBlockState(this.pos.down()).isAir())
			this.world.addSyncedBlockEvent(this.pos, this.world.getBlockState(this.pos).getBlock(), 1, isLoud);
	}
	
	
	@Override
    public boolean onSyncedBlockEvent(int type, int data) {
		if(type == 1) {
			if (data == 0) {
				this.ringingTicks = 30;
				this.world.playSound(null, this.pos, this.getChimeType().loudSound, SoundCategory.RECORDS,
						0.9f + this.world.random.nextFloat() * 0.2f,
						0.8f + this.world.random.nextFloat() * 0.4f);
			}
			else {
				this.ringingTicks = 140;
				this.world.playSound(null, this.pos, this.getChimeType().loudSound, SoundCategory.RECORDS,
						0.9f + this.world.random.nextFloat() * 0.2f,
						0.8f + this.world.random.nextFloat() * 0.4f);
			}
			return true;
		}
        return super.onSyncedBlockEvent(type, data);
    }
	
	@Override
	public void tick() {
		if(this.world.isClient) {
			if(this.ringingTicks > this.baselineRingTicks)
				this.ringingTicks--;
			if(this.ringingTicks < this.baselineRingTicks) {
				this.ringingTicks = this.baselineRingTicks;
			}
			if(world.isRaining()) {
				if(world.isThundering())
					baselineRingTicks = 26;
				else
					baselineRingTicks = 12;
			}
			else {
				if(world.isDay())
					baselineRingTicks = 0;
				else
					baselineRingTicks = 6;
			}
			return;
		}
		
		this.ticksToNextRing -= 1;
		if(this.world.getTime() % 6 == this.tickDisplacement && this.ticksToNextRing <= 0) {
			this.ring(this.world.random.nextInt(0xff) >> 5);
			this.ticksToNextRing = 0;
			if(this.world.isRaining()) {
				if(this.world.isThundering())
					this.ticksToNextRing += this.world.random.nextInt(200);       // 0 - 10s
				else
					this.ticksToNextRing += 100 + this.world.random.nextInt(600); // 5 - 35s
			}
			else {
				if(this.world.isDay())
					this.ticksToNextRing += 300 + this.world.random.nextInt(2100); // 15s - 2mins
				else
					this.ticksToNextRing += 100 + this.world.random.nextInt(700); // 5 - 40s
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
