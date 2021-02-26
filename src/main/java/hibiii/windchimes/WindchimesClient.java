package hibiii.windchimes;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;

public class WindchimesClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		BlockEntityRendererRegistry.INSTANCE.register(Windchimes.CHIME_BLOCK_ENTITY, WindchimeBlockEntityRenderer::new);
	}

}
