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

		// Chemicals
		list.add(new AcetoneChemicals());
		list.add(new AmmoniaChemicals());
		list.add(new BenzeneChemicals());
		list.add(new CaseOfDrugs());
		list.add(new EthanolChemicals());
		list.add(new EtherChemicals());
		list.add(new GeneratorStartedFluid());
		list.add(new HydroChloricAcidChemicals());
		list.add(new HydrogenPeroxideChemicals());
		list.add(new IodinePill());
		list.add(new SodiumChloride());
		list.add(new SulfuricAcidChemicals());

		    // Foods
		list.add(new ApplePie(null));
		list.add(new Banana(null));
		list.add(new BananaPeelItem());
		list.add(new Beer());
		list.add(new DoubleFlambeSteakDiane(null));
		list.add(new Doughnut(null));
		list.add(new GrilledMonkeyDeluxe(null));
		list.add(new NukaCola(null));
		list.add(new Orange(null));
		list.add(new Pizza(null));
		list.add(new RawFoodContainer());
		list.add(new SliceOfPizza(null));
		list.add(new SpaceBurger(null));
		list.add(new SpaceCheetos(null));
		list.add(new SpaceRum());
		list.add(new SpinachSoup(null));
		list.add(new Tomato(null));
		list.add(new Vodka());
		list.add(new Wine());

		    //General
	    list.add(new BagOfSoil());
		list.add(new BigFireExtinguisher());
		list.add(new BoobyTrapBomb());
		list.add(new Defibrilator());
		list.add(new DummyHivePlacer());
		list.add(new ElectronicParts());
		list.add(new FireExtinguisher());
		list.add(new FragGrenade());
		list.add(new Laptop());
		list.add(new LarcenyGloves());
		list.add(new Locator());
		list.add(new MedKit());
		list.add(new MoneyStack(50));
        list.add(new MoneyStack(25));
        list.add(new MoneyStack(1));
        list.add(new Mop());
		list.add(new MotionTracker());
		list.add(new Multimeter());
		list.add(new PackOfSmokes());
		list.add(new PaperItem());
		list.add(new PirateNuclearDisc());
		list.add(new PoisonSyringe());
		list.add(new PowerRadio());
		list.add(new RemoteBomb());
		list.add(new RobotParts());
		list.add(new RoomPartsStack(3));
		list.add(new Saxophone());
		list.add(new SecurityRadio());
		list.add(new Syringe());
		list.add(new Teleporter());
		list.add(new TimeBomb());
		list.add(new Tools());
		list.add(new TornClothes());
		list.add(new UniversalKeyCard());
		list.add(new ZippoLighter());

		    // MINING
		list.add(new ElboniumShard());
		list.add(new MiningDrill());
		list.add(new MiningExplosives());
		list.add(new MiningTeleporter());
		list.add(new OreShardBag());
		list.add(new RegolithShard());
		list.add(new UnobtainiumShard());

		    // SEEDS
		list.add(new MushroomSpores());
		list.add(new OrangeSeeds());
		list.add(new RedWeedSeeds());
		list.add(new TomatoSeeds());

		    // SPELLBOOKS
		list.add(new ClothesSwapSpellBook());
		list.add(new CluwnSpellBook());
		list.add(new DetonateSpellBook());
		list.add(new HealthPotion());
		list.add(new InvisibilitySpellBook());
		list.add(new InvulnerabilityPotion());
		list.add(new MagickaPotion());
		list.add(new OpenDimensionalPortalSpellBook());
		list.add(new PolymorphSpellBook());
		list.add(new RaiseDeadSpellBook());
		list.add(new RegenerationSpellBook());
		list.add(new SectumsempraSpellBook());
		list.add(new SummonWallSpellBook());
		list.add(new TeleportSpellBook());
		list.add(new TimsSpellBook());

		    // SUITS
		list.add(new AdventurersHat());
		list.add(new CaptainsHat());
		list.add(new CheapOxygenMask());
		list.add(new ChefsHat());
		list.add(new FancyClothes());
		list.add(new FireSuit());
		list.add(new InsulatedGloves());
		list.add(new JumpSuit());
		list.add(new MarshalOutfit());
		list.add(new MerchantSuit());
		list.add(new MinerSpaceSuit());
		list.add(new OperativeSpaceSuit());
		list.add(new OxygenMask());
		list.add(new PirateCaptainOutfit());
		list.add(new PowerSuit());
		list.add(new RadiationSuit());
		list.add(new Rapido());
		list.add(new SantaSuit());
		list.add(new SecOffsHelmet());
		list.add(new SecOffsVest());
		list.add(new SpaceSuit());
		list.add(new StillSuit());
		list.add(new SunGlasses());
		list.add(new SuperSuit());
		list.add(new Sweater());
		list.add(new UncoolSunglasses());
		list.add(new WizardsHat());
		list.add(new WizardsOutfit());
		list.add(new WizardsRobes());
		    // TOOLS
		list.add(new DockWorkerRadio());
		list.add(new Wrench());


		    // WEAPONS
		list.add(new AutoBrigger());
		list.add(new AutoCremator());
		list.add(new AutoTurretLaser());
		list.add(new Baton());
		list.add(new BullWhip());
		list.add(new Crowbar());
		list.add(new ElectricShocks());
		list.add(new FireStaff());
		list.add(new Flamer());
		list.add(new FlashBang());
		list.add(new IceStaff());
		list.add(new Knife());
		list.add(new LaserPistol());
		list.add(new LaserSword());
		list.add(new LightningWand());
		list.add(new PulseRifle());
		list.add(new QuickCastWand());
		list.add(new Revolver());
		list.add(new Shotgun());
		list.add(new ShotgunShells());
		list.add(new StunBaton());
		list.add(new SurgicalProceedureScalpel());
		list.add(new Teeth());
		list.add(new Tentacle());
		list.add(new Tusks());

		    // OTHERS
		list.add(new DoorPartsStack(3));
		list.add(new EmergencyKit());
		list.add(new EmptyContainer());
		list.add(new FlashLight());
		list.add(new HandCuffs());
		list.add(new MedPatch());
		list.add(new Scalpel());

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
        list.add(new PoisonSyringe());
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
