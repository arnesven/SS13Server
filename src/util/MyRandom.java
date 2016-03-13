package util;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import model.Player;


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

	public static String getRandomLineFromFile(String filename) throws FileNotFoundException {
		int lines = 0;
		String line = "";
		for (Scanner scanner = new Scanner(new File(filename)) ; 
				scanner.hasNext(); scanner.nextLine() ) {
			lines++;
		}
		System.out.println("TARS-file hade " + lines + " lines");
		Scanner scanner = new Scanner(new File(filename));
		for (int i = 0; i < MyRandom.nextInt(lines)+1 ; ++i) {
			line = scanner.nextLine();
		}
		scanner.close();
		return line;
	}

	public static String randomGender() {
		if (random.nextDouble() <= 0.5) {
			return "woman";
		}
		return "man";
	}

	public static <T> T sample(List<? extends T> list) {
		return list.get(nextInt(list.size()));
	}
}
