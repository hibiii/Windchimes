package hibiii.windchimes;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Tickable;

public class WindchimeBlockEntity extends BlockEntity implements Tickable {
	public int ringingTicks;
	public boolean ringing;
	protected int ticksToNextRing;
	public WindchimeBlockEntity() {
		super(Windchimes.CHIME_BLOCK_ENTITY);
		ringingTicks = 0;
		ringing = false;
		ticksToNextRing = 40;
	}
	public void ring(int isLoud) {
		world.addSyncedBlockEvent(pos, Windchimes.CHIME, 1, isLoud);
	}
	
	
	@Override
    public boolean onSyncedBlockEvent(int type, int data) {
		if(type == 1) {
			if (data == 0) {
				ringingTicks = 30;
				world.playSound(null, pos, Windchimes.CHIMES_SOUND_QUIET, SoundCategory.RECORDS,
						0.5f + world.random.nextFloat() * 0.3f,
						0.8f + world.random.nextFloat() * 0.4f);
			}
			else {
				ringingTicks = 140;
				world.playSound(null, pos, Windchimes.CHIMES_SOUND_LOUD, SoundCategory.RECORDS,
						0.7f + world.random.nextFloat() * 0.3f,
						0.8f + world.random.nextFloat() * 0.4f);
			}
			return ringing = true;
		}
        return super.onSyncedBlockEvent(type, data);
    }
	@Override
	public void tick() {
		if(ringing)
			ringingTicks--;
		if(ringingTicks <= 0) {
			ringing = false;
			ringingTicks = 0;
		}
		if(--ticksToNextRing <= 0) {
			ring(world.random.nextInt(0xff) >> 5);
			ticksToNextRing = 400 + world.random.nextInt(3600);
		}
	}
}
