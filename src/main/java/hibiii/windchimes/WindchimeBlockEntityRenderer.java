package hibiii.windchimes;

import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class WindchimeBlockEntityRenderer implements BlockEntityRenderer<WindchimeBlockEntity> {
	
	private final ModelPart platform;
	private final ModelPart rods1;
	private final ModelPart rods2;
	private final ModelPart clapper;
	
	public WindchimeBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
		ModelData platformData = new ModelData();
		ModelPartData platformPartData = platformData.getRoot();
		platformPartData.addChild("hanger", ModelPartBuilder.create().uv(18, 3).cuboid(-0.5f, -1f, -0.5f, 1f, 1f, 1f), ModelTransform.NONE);
		platformPartData.addChild("platform", ModelPartBuilder.create().uv(0, 0).cuboid( -3f, -2f,   -3f, 6f, 1f, 6f), ModelTransform.NONE);
		this.platform = platformPartData.createPart(32, 32);
		this.platform.setPivot(8f, 16f, 8f);

		// Rods NW and SE
		ModelData rods1Data = new ModelData();
		ModelPartData rods1PartData = rods1Data.getRoot();
		rods1PartData.addChild("rod1", ModelPartBuilder.create().uv(0, 7).cuboid(-2f, -21f, -2f, 1f, 15f, 1f), ModelTransform.NONE);
		rods1PartData.addChild("rod2", ModelPartBuilder.create().uv(12,7).cuboid(1f, -15f, 1f, 1f, 9f, 1f), ModelTransform.NONE);
		this.rods1 = rods1PartData.createPart(32, 32);
		this.rods1.setPivot(8f, 14f, 8f);

		// Rods NE and SW
		ModelData rods2Data = new ModelData();
		ModelPartData rods2PartData = rods2Data.getRoot();
		rods2PartData.addChild("rod3", ModelPartBuilder.create().uv(8, 7).cuboid(1f, -17f, -2f, 1f, 11f, 1f), ModelTransform.NONE);
		rods2PartData.addChild("rod4", ModelPartBuilder.create().uv(4, 7).cuboid(-2f, -19f, 1f, 1f, 13f, 1f), ModelTransform.NONE);
		this.rods2 = rods2PartData.createPart(32, 32);
		this.rods2.setPivot(8f, 14f, 8f);

		ModelData clapperData = new ModelData();
		ModelPartData clapperPartData = clapperData.getRoot();
		clapperPartData.addChild(null, ModelPartBuilder.create().uv(18, 0).cuboid(-1f, -13f, -1f, 2f, 1f, 2f), ModelTransform.NONE);
		this.clapper = clapperPartData.createPart(32, 32);
		this.clapper.setPivot(8f, 14f, 8f);
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

	public static TexturedModelData getTexturedModelData() {
		return null;
	}
}
