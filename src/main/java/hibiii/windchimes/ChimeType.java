package hibiii.windchimes;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Material;
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
	public static final ChimeType INVALID;
	
	public static FabricBlockSettings settingsWith(Material in) {
		return FabricBlockSettings.of(in)
			.strength(0)
			.ticksRandomly()
			.nonOpaque()
			.breakInstantly()
			.resistance(0)
			.noCollision();
	}
	
	static {
		IRON = new ChimeType(
			new SoundEvent(new Identifier("windchimes:chime.iron.loud")),
			new SoundEvent(new Identifier("windchimes:chime.iron.quiet")),
			new Identifier("windchimes:textures/iron_chime.png"));
		INVALID = new ChimeType(
			SoundEvents.ENTITY_PIG_AMBIENT,
			SoundEvents.ENTITY_PIG_HURT,
			new Identifier("textures/block/red_concrete.png"));
	}
}
