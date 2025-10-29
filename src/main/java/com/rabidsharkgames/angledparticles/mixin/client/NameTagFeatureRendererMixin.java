package com.rabidsharkgames.angledparticles.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.rabidsharkgames.angledparticles.Angledparticles;
import com.rabidsharkgames.angledparticles.Rotator;
import net.minecraft.client.renderer.feature.NameTagFeatureRenderer;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionfc;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NameTagFeatureRenderer.Storage.class)
public class NameTagFeatureRendererMixin {
	@Inject(method = "add", at = @At(value = "HEAD"))
	private void renderNameTag(PoseStack poseStack, Vec3 vec3, int i, Component component, boolean bl, int j, double d, CameraRenderState cameraRenderState, CallbackInfo ci) {
		if (vec3 == null) return;
		Angledparticles.pos().add((float)vec3.x, (float)vec3.y + 0.5f, (float)vec3.z);
	}
	@Redirect(method = "add", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;mulPose(Lorg/joml/Quaternionfc;)V"))
	private void getCameraLookerMat(PoseStack instance, Quaternionfc quaternionfc) {
		instance.mulPose(Rotator.getCameraRelativeLookerMat(Angledparticles.pos()));
	}
}
