package com.rabidsharkgames.angledparticles;

import net.minecraft.client.Camera;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.util.Mth;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class Rotator {
	// This is a thread-local for compatibility with AsyncParticles.
	private static final ThreadLocal<Quaternionf> staticQuaternion = new ThreadLocalQuaterion();

	public static Quaternionf quaternion() {
		return staticQuaternion.get();
	}

	public static void quatLookAtCamera(Camera camera, double x, double y, double z) {
		double xzDist = Mth.length(camera.getPosition().z - z, camera.getPosition().x - x);
		float xang = -(float)Mth.atan2(camera.getPosition().y - y, xzDist);
		Quaternionf quat = quaternion();
		quat.identity();
		quat.rotateY((float) Mth.atan2(camera.getPosition().x - x, camera.getPosition().z - z));
		quat.rotateX(xang);
	}

	public static Quaternionf getCameraLookerMat(EntityRenderDispatcher instance, Vector3f pos) {
		Rotator.quatLookAtCamera(instance.camera, pos.x, pos.y, pos.z);
		return quaternion();
	}

	private static class ThreadLocalQuaterion extends ThreadLocal<Quaternionf> {
		@Override
		protected Quaternionf initialValue() {
			return new Quaternionf();
		}
	}
}
