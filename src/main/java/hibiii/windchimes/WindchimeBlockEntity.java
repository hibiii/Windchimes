package hibiii.windchimes;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.Tickable;

public class WindchimeBlockEntity extends BlockEntity implements Tickable {
	public int ringingTicks;
	public boolean ringing;
	public WindchimeBlockEntity() {
		super(Windchimes.CHIME_BLOCK_ENTITY);
		ringingTicks = 0;
		ringing = false;
	}
	public void ring() {
		world.addSyncedBlockEvent(pos, Windchimes.CHIME, 1, 1);
	}
	
	
	@Override
    public boolean onSyncedBlockEvent(int type, int data) {
		if(type == 1) {
			ringingTicks = 140;
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
	}
}
