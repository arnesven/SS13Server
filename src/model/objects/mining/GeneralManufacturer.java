package model.objects.mining;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.actions.objectactions.GeneralManufacturerAction;
import model.events.animation.AnimatedSprite;
import model.items.DoorPartsStack;
import model.items.FlashLight;
import model.items.HandCuffs;
import model.items.chemicals.*;
import model.items.general.*;
import model.items.mining.MiningDrill;
import model.items.mining.OreShard;
import model.items.mining.OreShardBag;
import model.items.suits.*;
import model.items.weapons.*;
import model.map.rooms.Room;
import model.objects.general.ElectricalMachinery;

import java.util.*;

/**
 * Created by erini02 on 18/09/17.
 */
public class GeneralManufacturer extends ElectricalMachinery {
    private int charge;
    private Map<String, Set<GameItem>> itemsMap = new HashMap<>();
    private int totalcharged = 0;
    private boolean isManufacturing = false;

    public GeneralManufacturer(Room r) {
        super("General Manufacturer", r);
        this.charge = 5;
        itemsMap = getAvailableItems();
    }

    @Override
    public double getPowerConsumption() {
        if (isManufacturing()) {
            return super.getPowerConsumption();
        }
        return 0.000100; // 100 W
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        if (isBroken()) {
            return new Sprite("generalmanufacturerbroken", "robotics.png", 6, this);
        } else if (!isPowered()) {
            return new Sprite("generalmanufacturernopwer", "robotics.png", 5, this);
        } else if (isManufacturing()) {
            return new AnimatedSprite("generalmanufacturerani", "robotics.png",
                    0, 1, 32, 32, this, 4, true);
        }
        return new Sprite("generalmanufacturer", "robotics.png", 4, this);
    }

    private boolean isManufacturing() {
        return isManufacturing;
    }

    @Override
    protected void addActions(GameData gameData, Actor cl, ArrayList<Action> at) {
        Action a = new GeneralManufacturerAction(this);
        if (a.getOptions(gameData,cl).numberOfSuboptions() > 0) {
            at.add(a);
        }
        a = new AddShardsAction();
        if (GameItem.hasAnItemOfClass(cl, OreShard.class) || GameItem.hasAnItemOfClass(cl, OreShardBag.class)) {
        //if (a.getOptions(gameData,cl).numberOfSuboptions() > 0) {
            at.add(a);
        }
    }

    public int getLimit() {
        return charge * 15;
    }

    public int getCharge() {
        return charge;
    }

    public void addCharge(int add){
        charge += add;
    }

    private void addToTotalLoaded(int totalcharge) {
        this.totalcharged += totalcharge;
    }

    public int getTotalCharged() {
        return totalcharged;
    }

    public void reduceCharge(int cost) {
        double d = (double) getCharge() - Math.ceil((double)cost / 15.0);
        charge = (int) Math.max(0.0, d);
    }

    public static Map<String,Set<GameItem>> getAvailableItems() {
        Map<String, Set<GameItem>> availableItems = new HashMap<>();

        Set<GameItem> construction = new HashSet<>();
        construction.add(new RoomPartsStack(4));
        construction.add(new ElectronicParts());
        construction.add(new DoorPartsStack(4));
        construction.add(new RobotParts());
        construction.add(new MiningDrill());
        availableItems.put("Construction", construction);

        Set<GameItem> weapons = new HashSet<>();
        weapons.add(new LaserPistol());
        weapons.add(new LaserSword());
        weapons.add(new Revolver());
        weapons.add(new StunBaton());
        weapons.add(new Baton());
        weapons.add(new Shotgun());
        weapons.add(new PulseRifle());
        weapons.add(new BullWhip());
        weapons.add(new Knife());
        weapons.add(new AutoCremator());
        weapons.add(new Flamer());
        availableItems.put("Weapons", weapons);

        Set<GameItem> tools = new HashSet<>();
        tools.add(new Crowbar());
        tools.add(new Tools());
        tools.add(new Multimeter());
        tools.add(new Teleporter());
        tools.add(new SecurityRadio());
        tools.add(new PowerRadio());
        tools.add(new ZippoLighter());
        tools.add(new MotionTracker());
        tools.add(new FireExtinguisher());
        tools.add(new FlashLight());
        tools.add(new HandCuffs());
        availableItems.put("Tools", tools);

        Set<GameItem> medical = new HashSet<>();
        medical.add(new MedKit());
        medical.add(new Syringe());
        medical.add(new Defibrilator());
        medical.add(new BenzeneChemicals());
        medical.add(new AcetoneChemicals());
        medical.add(new AmmoniaChemicals());
        medical.add(new EthanolChemicals());
        medical.add(new EtherChemicals());
        medical.add(new HydroChloricAcidChemicals());
        medical.add(new HydrogenPeroxideChemicals());
        medical.add(new SodiumChloride());
        medical.add(new SulfuricAcidChemicals());
        availableItems.put("Medical", medical);

        Set<GameItem> clothing = new HashSet<>();
        clothing.add(new AdventurersHat());
        clothing.add(new ChefsHat());
        clothing.add(new FancyClothes());
        clothing.add(new JumpSuit());
        clothing.add(new OxygenMask());
        clothing.add(new PowerSuit());
        clothing.add(new Rapido());
        clothing.add(new SecOffsVest());
        clothing.add(new SecOffsHelmet());
        clothing.add(new SunGlasses());
        clothing.add(new Sweater());
        availableItems.put("Clothing", clothing);

        return availableItems;
    }

    public Map<String, Set<GameItem>> getItemsMap() {
        return itemsMap;
    }

    public void setIsManufacturing(boolean b) {
        isManufacturing = b;
    }

    private class AddShardsAction extends Action {
        private boolean all = false;

        public AddShardsAction() {
            super("Load Shards into GM", SensoryLevel.OPERATE_DEVICE);
        }

        @Override
        protected String getVerb(Actor whosAsking) {
            return "Added shards to the General Manufacturer";
        }

        @Override
        protected void execute(GameData gameData, Actor performingClient) {
            List<GameItem> toBeRemovedFromPerformer = new ArrayList<>();
            int totalcharge = 0;
            for (GameItem it : performingClient.getItems()) {
                if (it instanceof OreShard) {
                    totalcharge += ((OreShard)it).getCharge();
                    GeneralManufacturer.this.addCharge(((OreShard)it).getCharge());
                    toBeRemovedFromPerformer.add(it);
                }
                if (it instanceof OreShardBag) {
                    OreShardBag osb = (OreShardBag) it;
                    Iterator<OreShard> iter = ((OreShardBag) it).getShards().iterator();
                    while (iter.hasNext()) {
                        OreShard os = iter.next();
                        totalcharge += os.getCharge();
                        GeneralManufacturer.this.addCharge(os.getCharge());
                        iter.remove();
                    }
                }
            }
            for (GameItem it : toBeRemovedFromPerformer) {
                performingClient.getItems().remove(it);
            }
            performingClient.addTolastTurnInfo("You loaded the General Manufacturer with " + totalcharge + " charge!");
            GeneralManufacturer.this.addToTotalLoaded(totalcharge);
        }

        @Override
        public void setArguments(List<String> args, Actor performingClient) {


        }
    }


}
