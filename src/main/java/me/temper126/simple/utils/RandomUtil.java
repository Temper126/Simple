package me.temper126.simple.utils;

import java.util.Random;

public class RandomUtil {
	public static final Random RANDOM = new Random();

	public static int getRandInt(int min, int max) throws IllegalArgumentException {
		return min == max ? min : RANDOM.nextInt(max - min + 1) + min;
	}

	public static double getRandDouble(double min, double max) throws IllegalArgumentException {
		return min == max ? min : RANDOM.nextDouble() * (max - min) + min;
	}

	public static boolean getChance(double chance) {
		return chance >= 100.0D || chance >= getRandDouble(0.0D, 100.0D);
	}
}
