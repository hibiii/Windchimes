package hibiii.windchimes;

import net.minecraft.block.AbstractBlock.Settings;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

public class ChimeType {

	public final SoundEvent loudSound;
	public final SoundEvent quietSound;
	public final Identifier textureId;
	
	public ChimeType(SoundEvent loud, SoundEvent quiet, Identifier texture) {
		this.loudSound = loud;
		this.quietSound = quiet;
		this.textureId = texture;
	}

	public static final ChimeType IRON;
	public static final ChimeType BAMBOO;
	public static final ChimeType COPPER;
	public static final ChimeType INVALID;
	
	public static Settings defaultSettings() {
		return Settings.create()
			.strength(0)
			.nonOpaque()
			.breakInstantly()
			.resistance(0)
			.noCollision();
	}
	
	public static final Identifier IRON_LOUD_SOUND_ID, IRON_QUIET_SOUND_ID, BAMBOO_LOUD_SOUND_ID, BAMBOO_QUIET_SOUND_ID, COPPER_LOUD_SOUND_ID, COPPER_QUIET_SOUND_ID;
	public static final SoundEvent IRON_LOUD_SOUND, IRON_QUIET_SOUND, BAMBOO_LOUD_SOUND, BAMBOO_QUIET_SOUND, COPPER_LOUD_SOUND, COPPER_QUIET_SOUND;
	
	static {
		IRON_LOUD_SOUND_ID = new Identifier("windchimes:chime.iron.loud");
		IRON_QUIET_SOUND_ID = new Identifier("windchimes:chime.iron.quiet");
		BAMBOO_LOUD_SOUND_ID = new Identifier("windchimes:chime.bamboo.loud");
		BAMBOO_QUIET_SOUND_ID = new Identifier("windchimes:chime.bamboo.quiet");
		COPPER_LOUD_SOUND_ID = new Identifier("windchimes:chime.copper.loud");
		COPPER_QUIET_SOUND_ID = new Identifier("windchimes:chime.copper.quiet");
		
		IRON_LOUD_SOUND = SoundEvent.createFixedRangeEvent(IRON_LOUD_SOUND_ID, 48);
		IRON_QUIET_SOUND = SoundEvent.createFixedRangeEvent(IRON_QUIET_SOUND_ID, 24);
		BAMBOO_LOUD_SOUND = SoundEvent.createFixedRangeEvent(BAMBOO_LOUD_SOUND_ID, 48);
		BAMBOO_QUIET_SOUND = SoundEvent.createFixedRangeEvent(BAMBOO_QUIET_SOUND_ID, 24);
		COPPER_LOUD_SOUND = SoundEvent.createFixedRangeEvent(COPPER_LOUD_SOUND_ID, 48);
		COPPER_QUIET_SOUND = SoundEvent.createFixedRangeEvent(COPPER_QUIET_SOUND_ID, 24);


		IRON = new ChimeType(
			IRON_LOUD_SOUND, IRON_QUIET_SOUND,
			new Identifier("windchimes:textures/iron_chime.png"));
		BAMBOO = new ChimeType(
			BAMBOO_LOUD_SOUND, BAMBOO_QUIET_SOUND,
			new Identifier("windchimes:textures/bamboo_chime.png"));
		COPPER = new ChimeType(
			COPPER_LOUD_SOUND, COPPER_QUIET_SOUND,
			new Identifier("windchimes:textures/copper_chime.png"));
		INVALID = new ChimeType(
			SoundEvents.ENTITY_PIG_AMBIENT,
			SoundEvents.ENTITY_PIG_HURT,
			new Identifier("textures/block/red_concrete.png"));
	}
}
