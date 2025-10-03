package com.rabidsharkgames.angledparticles;

import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class Rotator {
	// This is a thread-local for compatibility with AsyncParticles.
	private static final ThreadLocal<Quaternionf> staticQuaternion = new ThreadLocalQuaterion();

	public static Quaternionf quaternion() {
		return staticQuaternion.get();
	}

	public static void quatLookAtCamera(double camX, double camY, double camZ, double x, double y, double z) {
		double xzDist = Mth.length(camZ - z, camX - x);
		float xang = -(float)Mth.atan2(camY - y, xzDist);
		Quaternionf quat = quaternion();
		quat.identity();
		quat.rotateY((float) Mth.atan2(camX - x, camZ - z));
		quat.rotateX(xang);
	}

	public static void quatLookAtCamera(Vec3 position, double x, double y, double z) {
		quatLookAtCamera(position.x, position.y, position.z, x, y, z);
	}

	public static Quaternionf getCameraRelativeLookerMat(Vector3f pos) {
		Rotator.quatLookAtCamera(0, 0, 0, pos.x, pos.y, pos.z);
		return quaternion();
	}

	private static class ThreadLocalQuaterion extends ThreadLocal<Quaternionf> {
		@Override
		protected Quaternionf initialValue() {
			return new Quaternionf();
		}
	}
}
