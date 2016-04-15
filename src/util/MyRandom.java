package util;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import model.items.foods.*;
import model.items.general.*;
import model.items.suits.*;
import model.items.weapons.*;

public class MyRandom {
	public static Random random = new Random();

	public static double nextDouble() {
		double d = random.nextDouble();
		write_to_file("random_doubles", d + " ");
		
		return d;
	}

	public static void write_to_file(String filename, String data) {
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
		System.out.println("file hade " + lines + " lines");
		Scanner scanner = new Scanner(new File(filename));
		int j = MyRandom.nextInt(lines) + 1;
		for (int i = 0; i < j ; ++i) {
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

	public static List<GameItem> getItemsWhichAppearRandomly() {
		List<GameItem> list = new ArrayList<>();
		list.add(new Chemicals());
		list.add(new FireExtinguisher());
		list.add(new Grenade());
		list.add(new KeyCard());
		list.add(new GeigerMeter());
		list.add(new MedKit());
		list.add(new Laptop());
		list.add(new TimeBomb());
		list.add(new RemoteBomb());
		list.add(new Tools());
		list.add(new Bible());
		list.add(new ApplePie());
		list.add(new Banana());
		list.add(new DoubleFlambeSteakDiane());
		list.add(new SpinachSoup());
		list.add(new ChefsHat());
		list.add(new FancyClothes());
		list.add(new FireSuit());
		list.add(new RadiationSuit());
		list.add(new SpaceSuit());
		list.add(new SunGlasses());
		list.add(new Saxophone());
        list.add(new Syringe());
		list.add(new Flamer());
		list.add(new Knife());
		list.add(new LaserPistol());
		list.add(new Revolver());
		list.add(new Shotgun());
		list.add(new StunBaton());
		
		return list;
	}
}
