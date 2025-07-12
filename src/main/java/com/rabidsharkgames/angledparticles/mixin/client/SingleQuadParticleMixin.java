package com.rabidsharkgames.angledparticles.mixin.client;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.rabidsharkgames.angledparticles.Rotator;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SingleQuadParticle;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SingleQuadParticle.class)
public abstract class SingleQuadParticleMixin extends Particle {
	@Unique
	private static final Vector3f pos = new Vector3f();

	protected SingleQuadParticleMixin(ClientLevel clientLevel, double d, double e, double f) {
		super(clientLevel, d, e, f);
	}

	@Inject(method = "render", at = @At("HEAD"))
	private void render(VertexConsumer vertexConsumer, Camera camera, float f, CallbackInfo ci) {
		pos.set(x, y, z);
	}

	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Camera;rotation()Lorg/joml/Quaternionf;"))
	private Quaternionf cameraRotation(Camera instance) {
		Rotator.quatLookAtCamera(instance, pos.x, pos.y, pos.z);
		return Rotator.quaternion();
	}
}
