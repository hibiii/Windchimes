package hibiii.windchimes;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class Initializer {

	public static final Identifier IRON_CHIME_ID = new Identifier("windchimes:iron_chime");
	public static final WindchimeBlock IRON_CHIME = new WindchimeBlock(
			ChimeType.IRON, ChimeType.defaultSettings()
				.sounds(BlockSoundGroup.METAL)
				.mapColor(Blocks.IRON_BLOCK.getDefaultMapColor()));
	
	public static final Identifier BAMBOO_CHIME_ID = new Identifier("windchimes:bamboo_chime");
	public static final WindchimeBlock BAMBOO_CHIME = new WindchimeBlock(
			ChimeType.BAMBOO, ChimeType.defaultSettings()
			.sounds(BlockSoundGroup.BAMBOO)
			.mapColor(Blocks.BAMBOO.getDefaultMapColor()));
	
	public static final Identifier COPPER_CHIME_ID = new Identifier("windchimes:copper_chime");
	public static final WindchimeBlock COPPER_CHIME = new WindchimeBlock(
			ChimeType.COPPER, ChimeType.defaultSettings()
			.sounds(BlockSoundGroup.COPPER)
			.mapColor(Blocks.COPPER_BLOCK.getDefaultMapColor()));
	
	public static BlockEntityType<WindchimeBlockEntity> CHIME_BLOCK_ENTITY;

	public static final Identifier CHIME_BLOCK_ENTITY_ID = new Identifier("windchimes:chime");
	
	public void onInitialize() {
		
		Registry.register(Registries.BLOCK, IRON_CHIME_ID, IRON_CHIME);
		Item ironChime = new BlockItem(IRON_CHIME, new Item.Settings());
		Registry.register(Registries.ITEM, IRON_CHIME_ID, ironChime);
		Registry.register(Registries.SOUND_EVENT, ChimeType.IRON_LOUD_SOUND_ID, ChimeType.IRON_LOUD_SOUND);
		Registry.register(Registries.SOUND_EVENT, ChimeType.IRON_QUIET_SOUND_ID, ChimeType.IRON_QUIET_SOUND);

		Registry.register(Registries.BLOCK, BAMBOO_CHIME_ID, BAMBOO_CHIME);
		Item bambooChime = new BlockItem(BAMBOO_CHIME, new Item.Settings());
		Registry.register(Registries.ITEM, BAMBOO_CHIME_ID, bambooChime);
		Registry.register(Registries.SOUND_EVENT, ChimeType.BAMBOO_LOUD_SOUND_ID, ChimeType.BAMBOO_LOUD_SOUND);
		Registry.register(Registries.SOUND_EVENT, ChimeType.BAMBOO_QUIET_SOUND_ID, ChimeType.BAMBOO_QUIET_SOUND);

		Registry.register(Registries.BLOCK, COPPER_CHIME_ID, COPPER_CHIME);
		Item copperChime = new BlockItem(COPPER_CHIME, new Item.Settings());
		Registry.register(Registries.ITEM, COPPER_CHIME_ID, copperChime);
		Registry.register(Registries.SOUND_EVENT, ChimeType.COPPER_LOUD_SOUND_ID, ChimeType.COPPER_LOUD_SOUND);
		Registry.register(Registries.SOUND_EVENT, ChimeType.COPPER_QUIET_SOUND_ID, ChimeType.COPPER_QUIET_SOUND);
		
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL_BLOCKS).register((content) -> {
			content.addStack(ironChime.getDefaultStack());
			content.addStack(bambooChime.getDefaultStack());
			content.addStack(copperChime.getDefaultStack());
		});

		CHIME_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, CHIME_BLOCK_ENTITY_ID, FabricBlockEntityTypeBuilder.create(WindchimeBlockEntity::new, IRON_CHIME, BAMBOO_CHIME, COPPER_CHIME).build(null));
	}
}
