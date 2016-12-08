package util;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import model.GameData;
import model.items.HandCuffs;
import model.items.NoSuchThingException;
import model.items.foods.*;
import model.items.general.*;
import model.items.seeds.OrangeSeeds;
import model.items.seeds.RedWeedSeeds;
import model.items.seeds.TomatoSeeds;
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

	public static <Type> Type sample(Collection<? extends Type> list) {
        if (list.size() == 0) {
            throw new IllegalStateException("Cannot sample from collection of size 0!");
        }

        Type result = null;
        Iterator<? extends Type> it = list.iterator();
        for (int i = MyRandom.nextInt(list.size()); i >= 0; --i) {
            result = it.next();
        }

		return result;
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
        list.add(Chemicals.createRandomChemicals());
        list.add(new Grenade());
        list.add(new MedKit());
        list.add(new Tools());
        list.add(new KeyCard());
        list.add(new Laptop());
        list.add(new RobotParts());
        list.add(new TornClothes());
        list.add(new Saxophone());
        list.add(new FireExtinguisher());
        list.add(new MotionTracker());
        list.add(new PirateNuclearDisc());
        list.add(new Bible());
        list.add(new Defibrilator());
        list.add(new GeigerMeter());
        list.add(new NuclearDisc(null, false));
        list.add(new SecurityRadio());
        list.add(new Teleporter());
        list.add(new PowerRadio());
        list.add(new PackOfSmokes());
        list.add(new ZippoLighter());
        list.add(new SunGlasses());
        list.add(new SecOffsVest());
        list.add(new Sweater());
        list.add(new OxygenMask());
        list.add(new ChefsHat());
        list.add(new RadiationSuit());
        list.add(new SpaceSuit());
        list.add(new AdventurersHat());
        list.add(new FireSuit());
        list.add(new FancyClothes());
        list.add(new SecOffsHelmet());
        list.add(new JumpSuit());
        list.add(new CaptainsHat());
        list.add(new Knife());
        list.add(new Crowbar());
        list.add(new Revolver());
        list.add(new Shotgun());
        list.add(new ShotgunShells());
        list.add(new StunBaton());
        list.add(new Flamer());
        list.add(new HandCuffs());
        list.add(new RoomPartsStack(1));
        list.add(new SantaSuit());
        list.add(new TomatoSeeds());
        list.add(new RedWeedSeeds());
        list.add(new OrangeSeeds());
        list.add(new Tomato(null));
        list.add(new Orange(null));
        list.add(new BagOfSoil());

        return list;
    }

    public static List<GameItem> allRandomItems() {
        List<GameItem> list = getItemsWhichAppearRandomly();
        list.add(new BoobyTrapBomb());
        list.add(new TimeBomb());
        list.add(new RemoteBomb());
        list.add(new OperativeSpaceSuit());
        list.add(new LarcenyGloves());
        list.add(new SuperSuit());
        list.add(new LaserSword());
        list.add(new LaserPistol());
        list.add(new PirateCaptainOutfit());
        list.add(new BullWhip());
        list.add(new PrisonerSuit(1337));
        list.add(new PirateOutfit(1337));
        list.add(new DummyHivePlacer());
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

        try {
            roomList.add(gameData.getRoom("Aft Hall"));
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
        try {
            roomList.add(gameData.getRoom("Front Hall"));
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
        try {
            roomList.add(gameData.getRoom("Port Hall Aft"));
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
        try {
            roomList.add(gameData.getRoom("Port Hall Front"));
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
        try {
            roomList.add(gameData.getRoom("Starboard Hall Aft"));
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
        try {
            roomList.add(gameData.getRoom("Starboard Hall Front"));
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
        try {
            roomList.add(gameData.getRoom("Panorama Walkway"));
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
        try {
            roomList.add(gameData.getRoom("Aft Walkway"));
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
        try {
            roomList.add(gameData.getRoom("Shuttle Gate"));
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }

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
