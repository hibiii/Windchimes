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

	public static final Identifier IRON_CHIME_ID = new Identifier("windchimes:iron_chime");
	public static final WindchimeBlock IRON_CHIME = new WindchimeBlock(
					ChimeType.IRON, ChimeType.settingsWith(Material.METAL));
	
	public static BlockEntityType<WindchimeBlockEntity> CHIME_BLOCK_ENTITY;

	public static final Identifier CHIME_BLOCK_ENTITY_ID = new Identifier("windchimes:chime");
	
	
	@Override
	public void onInitialize() {
		
        Registry.register(Registry.BLOCK, IRON_CHIME_ID, IRON_CHIME);
        Registry.register(Registry.ITEM, IRON_CHIME_ID, new BlockItem(IRON_CHIME, new FabricItemSettings().group(ItemGroup.DECORATIONS)));
        
        CHIME_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, CHIME_BLOCK_ENTITY_ID,
        		BlockEntityType.Builder.create(WindchimeBlockEntity::new, IRON_CHIME).build(null));
	}
}
