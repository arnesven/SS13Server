package model.objects.mining;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.items.DoorPartsStack;
import model.items.FlashLight;
import model.items.HandCuffs;
import model.items.chemicals.*;
import model.items.general.*;
import model.items.mining.MiningDrill;
import model.items.suits.*;
import model.items.weapons.*;
import model.map.rooms.MiningStationRoom;
import model.map.rooms.Room;
import model.objects.consoles.GeneratorConsole;
import model.objects.general.ElectricalMachinery;
import model.objects.general.GameObject;

import java.util.*;

/**
 * Created by erini02 on 18/09/17.
 */
public class GeneralManufacturer extends ElectricalMachinery {
    private int charge;
    private Map<String, Set<GameItem>> itemsMap = new HashMap<>();

    public GeneralManufacturer(Room r) {
        super("General Manufacturer", r);
        this.charge = 5;
        itemsMap = getAvailableItems();
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("generalmanufacturer", "robotics.png", 4);
    }

    @Override
    protected void addActions(GameData gameData, Actor cl, ArrayList<Action> at) {
        at.add(new GeneralManufacturerAction());
    }

    public int getLimit() {
        return charge * 15;
    }

    public int getCharge() {
        return charge;
    }


    private class GeneralManufacturerAction extends Action {

        private GameItem selected = null;

        public GeneralManufacturerAction() {
            super("Manufacture", SensoryLevel.OPERATE_DEVICE);
        }

        @Override
        protected String getVerb(Actor whosAsking) {
            return "fiddled with general manufacturer";
        }

        @Override
        protected void execute(GameData gameData, Actor performingClient) {
            if (selected != null) {
                if (selected.getCost() <= getLimit()) {
                    performingClient.addItem(selected.clone(), GeneralManufacturer.this);
                    GeneralManufacturer.this.reduceCharge(selected.getCost());
                    performingClient.addTolastTurnInfo("The general manufacturer produced a " + selected.getBaseName());
                } else {
                    performingClient.addTolastTurnInfo("The general manufacturer doesn't have enough charge. Feed it more shards. " + Action.FAILED_STRING);
                }
            } else {
                performingClient.addTolastTurnInfo("Item not found. " + Action.FAILED_STRING);
            }
        }

        @Override
        public void setArguments(List<String> args, Actor performingClient) {
            for (String s : itemsMap.keySet()) {
                if (args.get(0).equals(s)) {
                    for (GameItem it : itemsMap.get(s))  {
                        if (it.getBaseName().equals(args.get(1))) {
                            selected = it;
                        }
                    }
                }
            }

        }

        @Override
        public ActionOption getOptions(GameData gameData, Actor whosAsking) {
            ActionOption opt = super.getOptions(gameData, whosAsking);

            for (String s : itemsMap.keySet()) {
                ActionOption sub = new ActionOption(s);
                for (GameItem it : itemsMap.get(s)) {
                    if (it.getCost() <= getLimit())
                    sub.addOption(it.getBaseName());
                }
                if (sub.numberOfSuboptions() > 0) {
                    opt.addOption(sub);
                }
            }

            return opt;
        }
    }

    private void reduceCharge(int cost) {
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
        tools.add(new GeigerMeter());
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
}
