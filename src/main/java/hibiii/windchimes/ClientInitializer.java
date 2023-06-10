package hibiii.windchimes;

import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class ClientInitializer {
	
	public static final EntityModelLayer MODEL_CHIMES_LAYER = new EntityModelLayer(new Identifier("windchimes", "chimes"), "main");

	public void onInitializeClient() {
		BlockEntityRendererFactories.register(Initializer.CHIME_BLOCK_ENTITY, WindchimeBlockEntityRenderer::new);
	}

}
