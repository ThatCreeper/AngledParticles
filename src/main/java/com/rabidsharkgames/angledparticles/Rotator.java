package com.rabidsharkgames.angledparticles;

import net.minecraft.client.Camera;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.util.Mth;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class Rotator {
	public static void quatLookAtCamera(Camera camera, double x, double y, double z, Quaternionf quaternionf) {
		double xzDist = Mth.length(camera.getPosition().z - z, camera.getPosition().x - x);
		float xang = -(float)Mth.atan2(camera.getPosition().y - y, xzDist);
		quaternionf.rotateY((float) Mth.atan2(camera.getPosition().x - x, camera.getPosition().z - z));
		quaternionf.rotateX(xang);
	}

	public static Quaternionf getCameraLookerMat(EntityRenderDispatcher instance, Vector3f pos) {
		Quaternionf quat = new Quaternionf();
		Rotator.quatLookAtCamera(instance.camera, pos.x, pos.y, pos.z, quat);
		return quat;
	}
}
