package hibiii.windchimes;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class WindchimeBlockEntityRenderer implements BlockEntityRenderer<WindchimeBlockEntity> {
	
	private ModelPart platform;
	private ModelPart rods1;
	private ModelPart rods2;
	private ModelPart clapper;
	
	public WindchimeBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
		// FIXME: Oldschool renderer
		super();
		// this.platform = new ModelPart(32, 32, 0, 0);
		// this.platform.setPivot(8f, 16f, 8f);
		// this.platform.setTextureOffset(18, 3).addCuboid(-0.5F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, false);   // Hanger
		// this.platform.setTextureOffset(0, 0).addCuboid(-3.0F, -2.0F, -3.0F, 6.0F, 1.0F, 6.0F, 0.0F, false);  // Support
		
		// this.rods1 = new ModelPart(32, 32, 0, 0);
		// this.rods1.setPivot(8f, 14f, 8f);
		// this.rods1.setTextureOffset(0, 7).addCuboid(-2.0F, -21.0F, -2.0F, 1.0F, 15.0F, 1.0F, 0.0F, false); // Rod NW
		// this.rods1.setTextureOffset(12, 7).addCuboid(1.0F, -15.0F, 1.0F, 1.0F, 9.0F, 1.0F, 0.0F, false); // Rod SE
		
		// this.rods2 = new ModelPart(32, 32, 0, 0);
		// this.rods2.setPivot(8f, 14f, 8f);
		// this.rods2.setTextureOffset(8, 7).addCuboid(1.0F, -17.0F, -2.0F, 1.0F, 11.0F, 1.0F, 0.0F, false); // Rod NE
		// this.rods2.setTextureOffset(4, 7).addCuboid(-2.0F, -19.0F, 1.0F, 1.0F, 13.0F, 1.0F, 0.0F, false); // Rod SW
		
		// this.clapper = new ModelPart(32, 32, 0, 0);
		// this.clapper.setPivot(8f, 14f, 8f);
		// this.clapper.setTextureOffset(18, 0).addCuboid(-1.0F, -13.0F, -1.0F, 2.0F, 1.0F, 2.0F, 0.0F, false);  // Clapper
	}

	@Override
	public void render(WindchimeBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		if(entity.hasWorld()) {
			float correctedTicks = (float) ((entity.getWorld().getTime() % 314.15) + tickDelta);
			this.platform.pitch = MathHelper.sin(correctedTicks * 0.04f) * 0.06f;
			this.platform.roll = MathHelper.sin(correctedTicks * 0.06f) * 0.04f;
			
			float sway = entity.ringingTicks + 1f;
			float strength = (entity.ringingTicks) / entity.strengthDivisor;
			
			float animationTick = (float)((entity.getWorld().getTime() % 628.3) + tickDelta - sway) * 0.1f;
			float animationTick7 = animationTick * 0.7f;
			float animationTick3 = animationTick * 0.3f;
			
			this.rods1.pitch = MathHelper.sin(animationTick) * 0.07f * (strength);
			this.rods1.roll = MathHelper.cos(animationTick7) * 0.07f * (strength);
			this.rods1.yaw = MathHelper.cos(animationTick3) * 0.5f * (strength + 1f);
			this.rods2.pitch = MathHelper.cos(animationTick7) * 0.07f * (strength);
			this.rods2.roll = MathHelper.sin(animationTick) * 0.07f * (strength);
			this.rods2.yaw = MathHelper.sin(animationTick3) * 0.5f * (strength + 1f);
			this.clapper.pitch = this.rods1.pitch + this.rods2.pitch;
			this.clapper.roll = this.rods1.roll + this.rods2.roll;
			this.clapper.yaw = this.rods1.yaw + this.rods2.yaw;
		}
		VertexConsumer consumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutout(entity.getChimeType().textureId));
		this.platform.render(matrices, consumer, light, overlay);
		this.rods1.render(matrices, consumer, light, overlay);
		this.rods2.render(matrices, consumer, light, overlay);
		this.clapper.render(matrices, consumer, light, overlay);
	}
}
