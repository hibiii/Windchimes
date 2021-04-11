package hibiii.windchimes;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Initializer implements ModInitializer {

	public static final Identifier CHIMES_SOUND_LOUD_ID = new Identifier("windchimes:loud_chimes");
	public static final SoundEvent CHIMES_SOUND_LOUD = new SoundEvent(CHIMES_SOUND_LOUD_ID);
	public static final Identifier CHIMES_SOUND_QUIET_ID = new Identifier("windchimes:quiet_chimes");
	public static final SoundEvent CHIMES_SOUND_QUIET = new SoundEvent(CHIMES_SOUND_QUIET_ID);
	public static final Identifier CHIME_ID = new Identifier("windchimes:chime");
	public static final WindchimeBlock CHIME = new WindchimeBlock(
			FabricBlockSettings.of(Material.METAL)
			.strength(0)
			.ticksRandomly()
			.nonOpaque()
			.breakInstantly()
			.resistance(0)
			.noCollision());
	
	public static BlockEntityType<WindchimeBlockEntity> CHIME_BLOCK_ENTITY;
	
	@Override
	public void onInitialize() {
		Registry.register(Registry.SOUND_EVENT, CHIMES_SOUND_LOUD_ID, CHIMES_SOUND_LOUD);
		Registry.register(Registry.SOUND_EVENT, CHIMES_SOUND_QUIET_ID, CHIMES_SOUND_QUIET);
        Registry.register(Registry.BLOCK, CHIME_ID, CHIME);
        Registry.register(Registry.ITEM, CHIME_ID, new BlockItem(CHIME, new FabricItemSettings().group(ItemGroup.DECORATIONS)));
        CHIME_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, CHIME_ID, BlockEntityType.Builder.create(WindchimeBlockEntity::new, CHIME).build(null));
	}
}
