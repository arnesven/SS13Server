package util;
import java.util.Random;


public class MyRandom {
	public static Random random = new Random(System.currentTimeMillis());

	public static double nextDouble() {
		return random.nextDouble();
	}

	public static int nextInt(int size) {
		return random.nextInt(size);
	}
}
