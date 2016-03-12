package util;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;


public class MyRandom {
	public static Random random = new Random();

	public static double nextDouble() {
		double d = random.nextDouble();
		write_to_file("random_doubles", d + " ");
		
		return d;
	}

	private static void write_to_file(String filename, String data) {
		File f = new File(filename);
		if (!f.exists()) {
			try {
				f.createNewFile();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		FileWriter writer;
		try {
			writer = new FileWriter(f, true);
			writer.write(data + "\n");
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

	public static int nextInt(int size) {
		int i = random.nextInt(size);
		write_to_file("random_ints", i + " (0 to " + size + "), ");
		return i;
	}
}
