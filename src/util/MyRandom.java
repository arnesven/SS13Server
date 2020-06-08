package util;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.List;

import model.GameData;
import model.items.*;
import model.items.chemicals.*;
import model.items.foods.*;
import model.items.general.*;
import model.items.mining.*;
import model.items.seeds.*;
import model.items.spellbooks.*;
import model.items.suits.*;
import model.items.tools.DockWorkerRadio;
import model.items.tools.Wrench;
import model.items.weapons.*;
import model.map.rooms.HallwayRoom;
import model.map.rooms.Room;
import model.movepowers.SetHairColorPower;
import model.objects.general.Antidote;
import model.objects.plants.RedMushroom;

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

        roomList.addAll(gameData.getMap().getRoomsForLevel("ss13"));
        roomList.removeIf((Room r) -> !(r instanceof HallwayRoom));

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


    public static Color nextColor() {
        return  new Color(MyRandom.nextInt(255), MyRandom.nextInt(255), MyRandom.nextInt(255));
    }

    public static Boolean nextBoolean() {
        return random.nextBoolean();
    }

    public static Color randomHairColor() {
        return sample(SetHairColorPower.getHairColors());
    }

    public static Color randomFunColor() {
	    Set<Color> set = Set.of(Color.RED, Color.CYAN, Color.MAGENTA, Color.BLUE, Color.GREEN, Color.YELLOW, Color.ORANGE, Color.PINK);
	    return sample(set);
    }

    public static String randomHexString(int i) {
        String hexdecs = "0123456789abcdef";
        StringBuffer result = new StringBuffer();
        for ( ; i > 0; --i) {
            result.append(hexdecs.charAt(nextInt(16)));
        }
        return result.toString();
    }

    public static GameItem getRandomTrash(GameData gameData) {
	    List<GameItem> trashItems = new ArrayList<>();
	    trashItems.add(new TornClothes());
	    trashItems.add(new PaperItem());
	    trashItems.add(new Syringe());
	    trashItems.add(new BananaPeelItem());
	    ZippoLighter zl = new ZippoLighter();
	    zl.setUses(1);
	    trashItems.add(zl);
	    trashItems.add(new PackOfSmokes());
	    trashItems.add(new SliceOfPizza(null));
	    trashItems.add(new SpaceCheetos(null));

        return sample(trashItems);
    }

}
