package com.rabidsharkgames.angledparticles;

import net.fabricmc.api.ModInitializer;
import org.joml.Vector3f;

public class Angledparticles implements ModInitializer {
	// I'm not sure if this actually needs to be ThreadLocal, but
	// I'd rather play it safe.
	public static ThreadLocal<Vector3f> staticPos = new ThreadLocalVector3f();
	public static Vector3f pos() {
		return staticPos.get();
	}

	@Override
	public void onInitialize() {
	}

	private static class ThreadLocalVector3f extends ThreadLocal<Vector3f> {
		@Override
		protected Vector3f initialValue() {
			return new Vector3f();
		}
	}
}
