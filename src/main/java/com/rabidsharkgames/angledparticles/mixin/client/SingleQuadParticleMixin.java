package com.rabidsharkgames.angledparticles.mixin.client;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.rabidsharkgames.angledparticles.Rotator;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.util.Mth;
import org.joml.Quaternionf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SingleQuadParticle.class)
public abstract class SingleQuadParticleMixin extends Particle  {

	@Shadow protected abstract void renderRotatedQuad(VertexConsumer vertexConsumer, Camera camera, Quaternionf quaternionf, float f);

	@Shadow public abstract SingleQuadParticle.FacingCameraMode getFacingCameraMode();

	protected SingleQuadParticleMixin(ClientLevel clientLevel, double d, double e, double f) {
		super(clientLevel, d, e, f);
	}

	@Inject(method="render", at=@At("HEAD"), cancellable = true)
	private void render(VertexConsumer vertexConsumer, Camera camera, float f, CallbackInfo ci) {
		if (this.getFacingCameraMode() != SingleQuadParticle.FacingCameraMode.LOOKAT_XYZ)
			return;

		Quaternionf quaternionf = new Quaternionf();
		Rotator.quatLookAtCamera(camera, x, y, z, quaternionf);
		if (this.roll != 0.0F) {
			quaternionf.rotateZ(Mth.lerp(f, this.oRoll, this.roll));
		}

		this.renderRotatedQuad(vertexConsumer, camera, quaternionf, f);

		ci.cancel();
	}
}
