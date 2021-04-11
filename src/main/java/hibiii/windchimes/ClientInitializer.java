package hibiii.windchimes;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;

public class ClientInitializer implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		BlockEntityRendererRegistry.INSTANCE.register(Initializer.CHIME_BLOCK_ENTITY, WindchimeBlockEntityRenderer::new);
	}

}
