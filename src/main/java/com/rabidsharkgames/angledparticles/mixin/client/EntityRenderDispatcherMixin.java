package com.rabidsharkgames.angledparticles.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.rabidsharkgames.angledparticles.Angledparticles;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderDispatcher.class)
public abstract class EntityRenderDispatcherMixin {
    @Shadow public abstract <S extends EntityRenderState> EntityRenderer<?, ? super S> getRenderer(S entityRenderState);

    @Inject(method = "submit", at = @At("HEAD"))
    private<S extends EntityRenderState> void submit(S entityRenderState, CameraRenderState cameraRenderState, double d, double e, double f, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CallbackInfo ci) {
        if (entityRenderState.nameTagAttachment == null) return;
        EntityRenderer<?, ? super S> entityRenderer = this.getRenderer(entityRenderState);
        Vec3 vec3 = entityRenderer.getRenderOffset(entityRenderState);
        Angledparticles.pos().set(d + vec3.x, e + vec3.y, f + vec3.z);
    }
}
