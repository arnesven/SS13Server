package util;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import model.GameData;
import model.items.foods.*;
import model.items.general.*;
import model.items.suits.*;
import model.items.weapons.*;
import model.map.Room;

public class MyRandom {
	public static Random random = new Random();
    private static final boolean LOG_RANDOM_NUMBERS = false;

	public static double nextDouble() {
		double d = random.nextDouble();
        if (LOG_RANDOM_NUMBERS) {
            write_to_file("random_doubles", d + " ");
        }
		
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
        if (LOG_RANDOM_NUMBERS) {
            write_to_file("random_ints", i + " (0 to " + size + "), ");
        }
		return i;
	}

	public static String getRandomLineFromFile(String filename) throws FileNotFoundException {
		int lines = 0;
		String line = "";
		for (Scanner scanner = new Scanner(new File(filename)) ; 
				scanner.hasNext(); scanner.nextLine() ) {
			lines++;
		}
		Logger.log("file hade " + lines + " lines");
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

        list.add(new Beer());
        list.add(new DoubleFlambeSteakDiane(null));
        list.add(new SpaceRum());
        list.add(new Wine());
        list.add(new ApplePie(null));
        list.add(new SpaceBurger(null));
        list.add(new Doughnut(null));
        list.add(new Vodka());
        list.add(new SpinachSoup(null));
        list.add(new Banana(null));
        list.add(new Syringe());
        list.add(new BoobyTrapBomb());
        list.add(new Chemicals());
        list.add(new Grenade());
        list.add(new DummyHivePlacer());
        list.add(new MedKit());
        list.add(new LarcenyGloves());
        list.add(new Tools());
        list.add(new KeyCard());
        list.add(new Laptop());
        list.add(new RobotParts());
        list.add(new TornClothes());
        list.add(new Saxophone());
        list.add(new FireExtinguisher());
        list.add(new MotionTracker());
        list.add(new PirateNuclearDisc());
        list.add(new RemoteBomb());
        list.add(new Bible());
        list.add(new Defibrilator());
        list.add(new GeigerMeter());
        list.add(new NuclearDisc());
        list.add(new SecurityRadio());
        list.add(new Teleporter());
        list.add(new TimeBomb());
        list.add(new PowerRadio());
        list.add(new PackOfSmokes());
        list.add(new ZippoLighter());
        list.add(new SunGlasses());
        list.add(new SecOffsVest());
        list.add(new Sweater());
        list.add(new OxygenMask());
        list.add(new PrisonerSuit(1337));
        list.add(new PirateOutfit(1337));
        list.add(new ChefsHat());
        list.add(new OperativeSpaceSuit());
        list.add(new RadiationSuit());
        list.add(new SpaceSuit());
        list.add(new AdventurersHat());
        list.add(new FireSuit());
        list.add(new FancyClothes());
        list.add(new SecOffsHelmet());
        list.add(new JumpSuit());
        list.add(new CaptainsHat());
        list.add(new SuperSuit());
        list.add(new PirateCaptainOutfit());
        list.add(new Knife());
        list.add(new Revolver());
        list.add(new LaserPistol());
        list.add(new Shotgun());
        list.add(new LaserSword());
        list.add(new StunBaton());
        list.add(new Flamer());
        list.add(new BullWhip());

        return list;
    }

    public static Weapon getRandomPirateWeapon() {
        List<Weapon> randWeapons = new ArrayList<>();
        randWeapons.add(new LaserPistol());
        randWeapons.add(new Flamer());
        randWeapons.add(new Revolver());
        randWeapons.add(new LaserSword());
        return sample(randWeapons);
    }

    public static Alcohol getRandomAlcohol() {
        List<Alcohol> randDrinks = new ArrayList<>();
        randDrinks.add(new Beer());
        randDrinks.add(new Beer());
        randDrinks.add(new Wine());
        randDrinks.add(new Vodka());
        randDrinks.add(new SpaceRum());

        return sample(randDrinks);
    }

    public static Room getRandomHallway(GameData gameData) {
        List<Room> roomList = new ArrayList<>();
        roomList.add(gameData.getRoom("Aft Hall"));
        roomList.add(gameData.getRoom("Front Hall"));
        roomList.add(gameData.getRoom("Port Hall Aft"));
        roomList.add(gameData.getRoom("Port Hall Front"));
        roomList.add(gameData.getRoom("Starboard Hall Aft"));
        roomList.add(gameData.getRoom("Starboard Hall Front"));
        roomList.add(gameData.getRoom("Panorama Walkway"));
        roomList.add(gameData.getRoom("Aft Walkway"));
        roomList.add(gameData.getRoom("Shuttle Gate"));

        return sample(roomList);
    }

    public static String getRandomName() {
        try {
            return getRandomLineFromFile("resources/NAMES.TXT");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "NAME_FILE_NOT_FOUND";
        }
    }
}
