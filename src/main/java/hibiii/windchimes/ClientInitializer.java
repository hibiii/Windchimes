package hibiii.windchimes;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class ClientInitializer implements ClientModInitializer {
	
	public static final EntityModelLayer MODEL_CHIMES_LAYER = new EntityModelLayer(new Identifier("windchimes", "chimes"), "main");

	@Override
	public void onInitializeClient() {
		BlockEntityRendererRegistry.register(Initializer.CHIME_BLOCK_ENTITY, WindchimeBlockEntityRenderer::new);
	}

}
