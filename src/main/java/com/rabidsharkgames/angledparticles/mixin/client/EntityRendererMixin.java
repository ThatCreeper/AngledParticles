package com.rabidsharkgames.angledparticles.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.rabidsharkgames.angledparticles.Rotator;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.network.chat.Component;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin<S extends EntityRenderState>  {
	@Unique
	private static final Vector3f pos = new Vector3f();

	@Inject(method = "renderNameTag", at = @At(value = "HEAD"))
	private void renderNameTag(S entityRenderState, Component component, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, CallbackInfo ci) {
		if (entityRenderState.nameTagAttachment == null) return;
		pos.set(entityRenderState.nameTagAttachment.x, entityRenderState.nameTagAttachment.y, entityRenderState.nameTagAttachment.z);
		pos.add((float)entityRenderState.x, (float)entityRenderState.y + 0.5f, (float)entityRenderState.z);
	}
	@Redirect(method = "renderNameTag", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/EntityRenderDispatcher;cameraOrientation()Lorg/joml/Quaternionf;"))
	private Quaternionf getCameraLookerMat(EntityRenderDispatcher instance) {
		return Rotator.getCameraLookerMat(instance, pos);
	}
}
