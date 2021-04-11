package hibiii.windchimes;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Tickable;

public class WindchimeBlockEntity extends BlockEntity implements Tickable {
	public int ringingTicks;
	protected int ticksToNextRing;
	protected int baselineRingTicks;
	public WindchimeBlockEntity() {
		super(Initializer.CHIME_BLOCK_ENTITY);
		ringingTicks = 0;
		ticksToNextRing = 40;
		baselineRingTicks = 0;
	}
	public void ring(int isLoud) {
		world.addSyncedBlockEvent(pos, Initializer.CHIME, 1, isLoud);
	}
	
	
	@Override
    public boolean onSyncedBlockEvent(int type, int data) {
		if(type == 1) {
			if (data == 0) {
				ringingTicks = 30;
				world.playSound(null, pos, Initializer.CHIMES_SOUND_QUIET, SoundCategory.RECORDS,
						0.9f + world.random.nextFloat() * 0.2f,
						0.8f + world.random.nextFloat() * 0.4f);
			}
			else {
				ringingTicks = 140;
				world.playSound(null, pos, Initializer.CHIMES_SOUND_LOUD, SoundCategory.RECORDS,
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
					baselineRingTicks = 13;
				else
					baselineRingTicks = 6;
			} else {
				if(world.isDay())
					baselineRingTicks = 0;
				else
					baselineRingTicks = 3;
			}
			return;
		}
		if(--ticksToNextRing <= 0) {
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
}
