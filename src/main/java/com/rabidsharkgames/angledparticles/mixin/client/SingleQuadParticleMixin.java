package com.rabidsharkgames.angledparticles.mixin.client;

import com.rabidsharkgames.angledparticles.Rotator;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.renderer.state.QuadParticleRenderState;
import net.minecraft.util.Mth;
import org.joml.Quaternionf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SingleQuadParticle.class)
public abstract class SingleQuadParticleMixin extends Particle  {

	@Shadow public abstract SingleQuadParticle.FacingCameraMode getFacingCameraMode();

	@Shadow protected abstract void extractRotatedQuad(QuadParticleRenderState quadParticleRenderState, Camera camera, Quaternionf quaternionf, float f);

	@Shadow protected float oRoll;

	@Shadow protected float roll;

	protected SingleQuadParticleMixin(ClientLevel clientLevel, double d, double e, double f) {
		super(clientLevel, d, e, f);
	}

	@Inject(method="extract", at=@At("HEAD"), cancellable = true)
	private void extract(QuadParticleRenderState quadParticleRenderState, Camera camera, float f, CallbackInfo ci) {
		if (this.getFacingCameraMode() != SingleQuadParticle.FacingCameraMode.LOOKAT_XYZ)
			return;

		Rotator.quatLookAtCamera(camera.getPosition(), x, y, z);
		if (this.roll != 0.0F) {
			Rotator.quaternion().rotateZ(Mth.lerp(f, this.oRoll, this.roll));
		}

		this.extractRotatedQuad(quadParticleRenderState, camera, Rotator.quaternion(), f);

		ci.cancel();
	}
}
