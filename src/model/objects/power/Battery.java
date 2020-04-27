package model.objects.power;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.events.ambient.SimulatePower;
import model.map.rooms.Room;
import model.objects.general.ElectricalMachinery;
import util.Logger;

import java.util.ArrayList;
import java.util.List;

public class Battery extends ElectricalMachinery implements PowerSupply {

    private static final double BATTERY_POWER_OUTPUT = 0.02; // 20 kW
    public static final double MAX_ENERGY = BATTERY_POWER_OUTPUT * SimulatePower.ONE_TURN_IN_HOURS * 35;
    boolean isCharging;
    private double energy;

    public Battery(Room room, double initialCharge, boolean startsCharging) {
        super("Battery", room);
        isCharging = startsCharging;
        energy = initialCharge;
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        List<Sprite> sprs = new ArrayList<>();
        sprs.add(new Sprite("batterybase", "power.png", 9, 7, this));
        StringBuilder bldr = new StringBuilder();
        if (isCharging) {
            bldr.append("charging");
            sprs.add(new Sprite("charging", "power.png", 3, 8, this));
        } else {
            bldr.append("notcharging");
            sprs.add(new Sprite("notcharging", "power.png", 2, 8, this));
        }
        int frame = (int)(Math.floor((energy * 5.0) / MAX_ENERGY)) + 10;
        bldr.append("charge"+frame);
        int extraRow = 0;
        if (frame > 12) {
            frame -= 13;
            extraRow = 1;
        }
        sprs.add(new Sprite("cbatterychargelevel", "power.png", frame, 7+extraRow, this));
        return new Sprite("batteryfullgetup"+bldr.toString(), "human.png", 0, sprs, this);
    }

    @Override
    protected void addActions(GameData gameData, Actor cl, ArrayList<Action> at) {
        if (isCharging) {
            at.add(new StopChargingAction());
        } else {
            at.add(new StartChargingAction());
        }
    }

    @Override
    public double getPowerConsumption() {
        if (isCharging) {
            return BATTERY_POWER_OUTPUT;
        }
        return 0.0;
    }

    @Override
    public double getPower() {
        if (isCharging) {
            return 0;
        }
        return BATTERY_POWER_OUTPUT;
    }

    @Override
    public double getEnergy() {
        return this.energy;
    }

    @Override
    public void drainEnergy(double d) {
        this.energy -= d;
        if (this.energy < 0.0) {
            this.energy = 0.0;
        }
    }

    @Override
    public void receiveEnergy(GameData gameData, double energy) {
        if (isCharging) {
            this.energy += energy;
            if (this.energy > MAX_ENERGY) {
                this.energy = MAX_ENERGY;
            }
        }
    }

    private class StartChargingAction extends Action {
        private int selectedPrio;

        public StartChargingAction() {
            super("Start Charging", SensoryLevel.OPERATE_DEVICE);
        }

        @Override
        protected String getVerb(Actor whosAsking) {
            return "fiddled with battery";
        }

        @Override
        public ActionOption getOptions(GameData gameData, Actor whosAsking) {
            ActionOption opts = super.getOptions(gameData, whosAsking);
            opts.addOption("Priority 0 - Super High");
            opts.addOption("Priority 1 - High");
            opts.addOption("Priority 2 - Medium High");
            opts.addOption("Priority 3 - Medium");
            opts.addOption("Priority 4 - Medium Low");
            opts.addOption("Priority 5 - Low");
            opts.addOption("Priority 6 - Super Low");
            return opts;
        }

        @Override
        protected void execute(GameData gameData, Actor performingClient) {
            isCharging = true;
            setPowerPriority(selectedPrio);
        }


        @Override
        protected void setArguments(List<String> args, Actor performingClient) {
            String[] rest = args.get(0).replace("Priority ", "").split(" - ");
            selectedPrio = Integer.parseInt(rest[0]);
        }
    }

    private class StopChargingAction extends Action {
        public StopChargingAction() {
            super("Stop Charging", SensoryLevel.OPERATE_DEVICE);
        }

        @Override
        protected String getVerb(Actor whosAsking) {
            return "fiddled with battery";
        }

        @Override
        protected void execute(GameData gameData, Actor performingClient) {
            isCharging = false;
            setPowerPriority(5);
        }

        @Override
        protected void setArguments(List<String> args, Actor performingClient) {

        }
    }
}
