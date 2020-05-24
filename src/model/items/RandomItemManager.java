package model.items;

import model.items.chemicals.*;
import model.items.foods.*;
import model.items.general.*;
import model.items.mining.*;
import model.items.seeds.MushroomSpores;
import model.items.seeds.OrangeSeeds;
import model.items.seeds.RedWeedSeeds;
import model.items.seeds.TomatoSeeds;
import model.items.spellbooks.*;
import model.items.suits.*;
import model.items.tools.DockWorkerRadio;
import model.items.tools.Wrench;
import model.items.weapons.*;
import util.Logger;
import util.MyArrays;
import util.MyRandom;

import java.util.*;

public class RandomItemManager {

    public static int PREVALENCE_VERY_COMMON = 0;
    public static int PREVALENCE_COMMON      = 1;
    public static int PREVALENCE_RARE        = 2;
    public static int PREVALENCE_VERY_RARE   = 3;
    public static int PREVALENCE_UNIQUE      = 4;

    private static double[] prevalenceWeights = new double[]{1.0, 0.5, 0.25, 0.1, 0.0};
    private static double[] cummulative = MyArrays.prefixsum(prevalenceWeights);
    private static Map<Integer, Set<GameItem>> itemsByPrevalence = sortItemsByPrevalence(getItemsWhichAppearRandomly());


    public static GameItem getRandomSearchItem() {
        return getRandomItem(0, 4);
    }

    public static GameItem getRandomSpawnItem() {
        return getRandomItem(0, 4);
    }

    public static GameItem getRandomMerchantWares() {
        return getRandomItem(2, 4);
    }

    public static GameItem getRandomSantaGift() {
        return getRandomItem(1, 2);
    }


    private static Map<Integer, Set<GameItem>> sortItemsByPrevalence(Map<GameItem, Integer> prevalenceMap) {
        Map<Integer, Set<GameItem>> map = new HashMap<>();

        for (GameItem it : prevalenceMap.keySet()) {
            if (!map.containsKey(prevalenceMap.get(it))) {
                map.put(prevalenceMap.get(it), new HashSet<>());
            }
            map.get(prevalenceMap.get(it)).add(it);
        }

        return map;
    }

    private static GameItem getRandomItem(int prevMin, int prevMax) {
        double sum = MyArrays.sum(prevalenceWeights, prevMin, prevMax);
        double offset = MyArrays.sum(prevalenceWeights, 0, prevMin-1);
        double rand = MyRandom.nextDouble()*sum + offset;
        Logger.log("Spawning a random item... seed is " + rand);
        int rarity = 0;
        for (int i = 0; i < cummulative.length; ++i) {
            if (rand < cummulative[i]) {
                Logger.log(" which means prevalence " + i);
                rarity = i;
                break;
            }
        }
        GameItem it =  MyRandom.sample(itemsByPrevalence.get(rarity)).clone();
        return it;
    }

    private static Map<GameItem, Integer> getItemsWhichAppearRandomly() {
        Map<GameItem, Integer> list = new HashMap<>();

        // Chemicals
        list.put(new AcetoneChemicals(),            PREVALENCE_COMMON);
        list.put(new AmmoniaChemicals(),            PREVALENCE_COMMON);
        list.put(new BenzeneChemicals(),            PREVALENCE_COMMON);
        list.put(new CaseOfDrugs(),                 PREVALENCE_COMMON);
        list.put(new EthanolChemicals(),            PREVALENCE_COMMON);
        list.put(new EtherChemicals(),              PREVALENCE_COMMON);
        list.put(new GeneratorStartedFluid(),       PREVALENCE_COMMON);
        list.put(new HydroChloricAcidChemicals(),   PREVALENCE_COMMON);
        list.put(new HydrogenPeroxideChemicals(),   PREVALENCE_COMMON);
        list.put(new IodinePill(),                  PREVALENCE_COMMON);
        list.put(new SodiumChloride(),              PREVALENCE_COMMON);
        list.put(new SulfuricAcidChemicals(),       PREVALENCE_COMMON);

        // Foods
        list.put(new ApplePie(null),              PREVALENCE_COMMON);
        list.put(new Banana(null),                PREVALENCE_COMMON);
        list.put(new BananaPeelItem(),                  PREVALENCE_COMMON);
        list.put(new Beer(),                            PREVALENCE_COMMON);
        list.put(new DoubleFlambeSteakDiane(null), PREVALENCE_RARE);
        list.put(new Doughnut(null),            PREVALENCE_COMMON);
        list.put(new GrilledMonkeyDeluxe(null), PREVALENCE_VERY_RARE);
        list.put(new NukaCola(null),            PREVALENCE_VERY_COMMON);
        list.put(new Orange(null),              PREVALENCE_COMMON);
        list.put(new Pizza(null),               PREVALENCE_COMMON);
        list.put(new RawFoodContainer(),              PREVALENCE_COMMON);
        list.put(new SliceOfPizza(null),        PREVALENCE_COMMON);
        list.put(new SpaceBurger(null),         PREVALENCE_COMMON);
        list.put(new SpaceCheetos(null),        PREVALENCE_VERY_COMMON);
        list.put(new SpaceRum(),                      PREVALENCE_RARE);
        list.put(new SpinachSoup(null),         PREVALENCE_COMMON);
        list.put(new Tomato(null),              PREVALENCE_COMMON);
        list.put(new Vodka(),                         PREVALENCE_COMMON);
        list.put(new Wine(),                          PREVALENCE_COMMON);

        //General
        list.put(new BagOfSoil(),           PREVALENCE_COMMON);
        list.put(new BigFireExtinguisher(), PREVALENCE_COMMON);
        list.put(new BoobyTrapBomb(),       PREVALENCE_VERY_RARE);
        list.put(new Defibrilator(),        PREVALENCE_VERY_RARE);
        list.put(new DummyHivePlacer(),     PREVALENCE_VERY_RARE);
        list.put(new ElectronicParts(),     PREVALENCE_COMMON);
        list.put(new FireExtinguisher(),    PREVALENCE_VERY_COMMON);
        list.put(new FragGrenade(),         PREVALENCE_COMMON);
        list.put(new Laptop(),              PREVALENCE_RARE);
        list.put(new LarcenyGloves(),       PREVALENCE_VERY_RARE);
        list.put(new Locator(),             PREVALENCE_UNIQUE);
        list.put(new MedKit(),              PREVALENCE_VERY_COMMON);
        list.put(new MoneyStack(50), PREVALENCE_COMMON);
        list.put(new MoneyStack(25), PREVALENCE_COMMON);
        list.put(new MoneyStack(1), PREVALENCE_VERY_COMMON);
        list.put(new Mop(),                     PREVALENCE_COMMON);
        list.put(new MotionTracker(),           PREVALENCE_RARE);
        list.put(new Multimeter(),              PREVALENCE_COMMON);
        list.put(new PackOfSmokes(),            PREVALENCE_VERY_COMMON);
        list.put(new PaperItem(),               PREVALENCE_COMMON);
        list.put(new PirateNuclearDisc(),       PREVALENCE_UNIQUE);
        list.put(new PoisonSyringe(),           PREVALENCE_RARE);
        list.put(new PowerRadio(),              PREVALENCE_RARE);
        list.put(new RemoteBomb(),              PREVALENCE_VERY_RARE);
        list.put(new RobotParts(),              PREVALENCE_RARE);
        list.put(new RoomPartsStack(3), PREVALENCE_COMMON);
        list.put(new Saxophone(),               PREVALENCE_RARE);
        list.put(new SecurityRadio(),           PREVALENCE_RARE);
        list.put(new Syringe(),                 PREVALENCE_COMMON);
        list.put(new Teleporter(),              PREVALENCE_VERY_RARE);
        list.put(new TimeBomb(),                PREVALENCE_VERY_RARE);
        list.put(new Tools(),                   PREVALENCE_VERY_COMMON);
        list.put(new TornClothes(),             PREVALENCE_RARE);
        list.put(new UniversalKeyCard(),        PREVALENCE_VERY_RARE);
        list.put(new ZippoLighter(),            PREVALENCE_VERY_COMMON);

        // MINING
        list.put(new ElboniumShard(),       PREVALENCE_RARE);
        list.put(new MiningDrill(),         PREVALENCE_COMMON);
        list.put(new MiningExplosives(),    PREVALENCE_VERY_RARE);
        list.put(new MiningTeleporter(),    PREVALENCE_VERY_RARE);
        list.put(new OreShardBag(),         PREVALENCE_RARE);
        list.put(new RegolithShard(),       PREVALENCE_COMMON);
        list.put(new UnobtainiumShard(),    PREVALENCE_VERY_RARE);

        // SEEDS
        list.put(new MushroomSpores(),  PREVALENCE_COMMON);
        list.put(new OrangeSeeds(),     PREVALENCE_COMMON);
        list.put(new RedWeedSeeds(),    PREVALENCE_RARE);
        list.put(new TomatoSeeds(),     PREVALENCE_COMMON);

        // SPELLBOOKS
        list.put(new ClothesSwapSpellBook(),    PREVALENCE_VERY_RARE);
        list.put(new CluwnSpellBook(),          PREVALENCE_VERY_RARE);
        list.put(new DetonateSpellBook(),       PREVALENCE_VERY_RARE);
        list.put(new HealthPotion(),            PREVALENCE_VERY_RARE);
        list.put(new InvisibilitySpellBook(),   PREVALENCE_VERY_RARE);
        list.put(new InvulnerabilityPotion(),   PREVALENCE_VERY_RARE);
        list.put(new MagickaPotion(),           PREVALENCE_VERY_RARE);
        list.put(new OpenDimensionalPortalSpellBook(), PREVALENCE_VERY_RARE);
        list.put(new PolymorphSpellBook(),      PREVALENCE_VERY_RARE);
        list.put(new RaiseDeadSpellBook(),      PREVALENCE_VERY_RARE);
        list.put(new RegenerationSpellBook(),   PREVALENCE_VERY_RARE);
        list.put(new SectumsempraSpellBook(),   PREVALENCE_VERY_RARE);
        list.put(new SummonWallSpellBook(),     PREVALENCE_VERY_RARE);
        list.put(new TeleportSpellBook(),       PREVALENCE_VERY_RARE);
        list.put(new TimsSpellBook(),           PREVALENCE_VERY_RARE);

        // SUITS
        list.put(new AdventurersHat(),  PREVALENCE_RARE);
        list.put(new CaptainsHat(),     PREVALENCE_RARE);
        list.put(new CheapOxygenMask(), PREVALENCE_VERY_COMMON);
        list.put(new ChefsHat(),        PREVALENCE_RARE);
        list.put(new FancyClothes(),    PREVALENCE_COMMON);
        list.put(new FireSuit(),        PREVALENCE_COMMON);
        list.put(new InsulatedGloves(), PREVALENCE_COMMON);
        list.put(new JumpSuit(),        PREVALENCE_RARE);
        list.put(new MarshalOutfit(),   PREVALENCE_VERY_RARE);
        list.put(new MerchantSuit(),    PREVALENCE_VERY_RARE);
        list.put(new MinerSpaceSuit(),  PREVALENCE_RARE);
        list.put(new OperativeSpaceSuit(),      PREVALENCE_VERY_RARE);
        list.put(new OxygenMask(),              PREVALENCE_COMMON);
        list.put(new PirateCaptainOutfit(),     PREVALENCE_UNIQUE);
        list.put(new PirateOutfit(1337),   PREVALENCE_VERY_RARE);
        list.put(new PrisonerSuit(1337),      PREVALENCE_RARE);
        list.put(new PowerSuit(),               PREVALENCE_RARE);
        list.put(new RadiationSuit(),           PREVALENCE_COMMON);
        list.put(new Rapido(),                  PREVALENCE_RARE);
        list.put(new SantaSuit(),               PREVALENCE_RARE);
        list.put(new SecOffsHelmet(),           PREVALENCE_COMMON);
        list.put(new SecOffsVest(),             PREVALENCE_COMMON);
        list.put(new SpaceSuit(),               PREVALENCE_COMMON);
        list.put(new StillSuit(),               PREVALENCE_UNIQUE);
        list.put(new SunGlasses(),              PREVALENCE_COMMON);
        list.put(new SuperSuit(),               PREVALENCE_VERY_RARE);
        list.put(new Sweater(),                 PREVALENCE_COMMON);
        list.put(new UncoolSunglasses(),        PREVALENCE_VERY_RARE);
        list.put(new WizardsHat(),              PREVALENCE_RARE);
        list.put(new WizardsOutfit(),           PREVALENCE_RARE);
        list.put(new WizardsRobes(),            PREVALENCE_RARE);
        // TOOLS
        list.put(new DockWorkerRadio(), PREVALENCE_RARE);
        list.put(new Wrench(),          PREVALENCE_VERY_COMMON);


        // WEAPONS
        list.put(new AutoBrigger(),     PREVALENCE_RARE);
        list.put(new AutoCremator(),    PREVALENCE_RARE);
        list.put(new Baton(),           PREVALENCE_RARE);
        list.put(new BullWhip(),        PREVALENCE_VERY_RARE);
        list.put(new Crowbar(),         PREVALENCE_VERY_COMMON);
        list.put(new FireStaff(),       PREVALENCE_VERY_RARE);
        list.put(new Flamer(),          PREVALENCE_COMMON);
        list.put(new FlashBang(),       PREVALENCE_COMMON);
        list.put(new IceStaff(),        PREVALENCE_VERY_RARE);
        list.put(new Knife(),           PREVALENCE_VERY_COMMON);
        list.put(new LaserPistol(),     PREVALENCE_RARE);
        list.put(new LaserSword(),      PREVALENCE_VERY_RARE);
        list.put(new LightningWand(),   PREVALENCE_VERY_RARE);
        list.put(new PulseRifle(),      PREVALENCE_RARE);
        list.put(new QuickCastWand(),   PREVALENCE_VERY_RARE);
        list.put(new Revolver(),        PREVALENCE_COMMON);
        list.put(new Shotgun(),         PREVALENCE_COMMON);
        list.put(new ShotgunShells(),   PREVALENCE_COMMON);
        list.put(new StunBaton(),       PREVALENCE_COMMON);
        list.put(new SurgicalProceedureScalpel(), PREVALENCE_RARE);
       
        // OTHERS
        list.put(new DoorPartsStack(3),       PREVALENCE_COMMON);
        list.put(new EmergencyKit(),            PREVALENCE_VERY_COMMON);
        list.put(new EmptyContainer(),          PREVALENCE_COMMON);
        list.put(new FlashLight(),              PREVALENCE_COMMON);
        list.put(new HandCuffs(),               PREVALENCE_COMMON);
        list.put(new MedPatch(),                PREVALENCE_VERY_COMMON);
        list.put(new Scalpel(),                 PREVALENCE_COMMON);

        return list;
    }

}
