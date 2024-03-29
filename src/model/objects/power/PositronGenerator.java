package model.objects.power;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.DragAction;
import model.actions.general.Action;
import model.actions.itemactions.BoostGeneratorAction;
import model.events.SpontaneousExplosionEvent;
import model.items.chemicals.GeneratorStartedFluid;
import model.items.general.GameItem;
import model.items.general.Locatable;
import model.map.doors.Door;
import model.map.rooms.Room;
import model.objects.DetachableObject;
import model.objects.DraggableObject;
import model.objects.consoles.GeneratorConsole;
import model.objects.general.PowerConsumer;
import model.objects.general.BreakableObject;
import model.objects.general.ElectricalMachinery;
import model.objects.general.GameObject;
import model.objects.general.Repairable;
import util.Logger;
import util.MyRandom;
import util.MyStrings;

import java.util.*;

/**
 * Created by erini02 on 26/11/16.
 */
public class PositronGenerator extends BreakableObject
        implements Repairable, PowerSupply, DetachableObject, DraggableObject, Locatable {



    public double startingPower;
    private static final double FIXED_INCREASE = 0.035;
    private static final double ONGOING_INCREASE = 0.025;


    private double level;
    private double dlevel = 0.0;
    private boolean isDetached;

    public double getStartingPower() {
        return startingPower;
    }

    public PositronGenerator(double normal_output, Room r, GameData gameData) {
        super("Positron Generator", 5, r);
        this.startingPower = normal_output;
        level = startingPower;
        isDetached = false;

    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        if (getPowerOutput() < 0.01) {
            return new Sprite("powersourcenothing", "stationobjs.png", 150, this);
        } else if (getPowerOutput() < 0.25) {
            return new Sprite("powersourcelowest", "stationobjs.png", 156, this);
        } else if (getPowerOutput() < 0.5) {
            return new Sprite("powersourcelower", "stationobjs.png", 157, this);
        } else if (getPowerOutput() < 0.75) {
            return new Sprite("powersourcelow", "stationobjs.png", 159, this);
        } else if (getPowerOutput() < 1.1) {
            return new Sprite("powersourcenormal", "stationobjs.png", 161, this);
        } else if (getPowerOutput() < 1.5) {
            return new Sprite("powersourcehigh", "stationobjs.png", 167, this);
        } else if (getPowerOutput() < 1.5) {
            return new Sprite("powersourcehigher", "stationobjs.png", 178, this);
        }
        return new Sprite("powersource", "stationobjs.png", 160, this);
    }



    public double getLevel() {
        if (isBroken()) {
            return 0.0;
        }
        return level;
    }

    public double getPower() {
        return getLevel();
    }

    public double getEnergy() {
        return Double.MAX_VALUE;
    }

    @Override
    public void drainEnergy(double d) {
        // Nothing to drain, infinite energy.
    }



    public void setLevel(boolean fixed, boolean increase) {
        if (fixed) {
            level = Math.max(0.0, getLevel() + FIXED_INCREASE * (increase?+1.0:-1.0));
            dlevel = 0.0;
        } else {
            dlevel = ONGOING_INCREASE * (increase?+1.0:-1.0);
        }
        Logger.log("Powerlevel is now " + getLevel());
    }

    public double getOngoing() {
        return dlevel;
    }


    public double getPowerLevel() {
        return level;
    }

    @Override
    public void updateYourself(GameData gameData) {
        level += dlevel;
    }



    public void addToLevel(double d) {
        level = Math.max(0.0, getLevel() + d);
    }


    @Override
    public void doWhenRepaired(GameData gameData) {

    }

    public Double getLevelChange() {
        return dlevel;
    }

    public double getPowerOutput() {
        return (getLevel()/ startingPower);
    }


    public void handleOvercharge(GameData gameData) {
        double outputPct = getPowerOutput();

        if (MyRandom.nextDouble() < outputPct - 1.0) {
            Logger.log(Logger.INTERESTING,
                    "Increased power output (>100%) caused fire in generator room");
            gameData.getGameMode().addFire(getPosition());
        }

        if (MyRandom.nextDouble() < outputPct - 1.5) {
            Logger.log(Logger.INTERESTING,
                    "High power output (>150%) caused fire in room adjacent to generator room");
            if (!getPosition().getNeighborList().isEmpty()) {
                Room r = MyRandom.sample(getPosition().getNeighborList());
                gameData.getGameMode().addFire(r);
            }
        }

        if (MyRandom.nextDouble() < outputPct - 1.5) {
            Logger.log(Logger.INTERESTING,
                    "High power output (>150%) caused explosion in generator room");
            SpontaneousExplosionEvent exp = new SpontaneousExplosionEvent();
            exp.explode(getPosition(), gameData);
        }
    }

    public void boostGeneratorOutput(GameData gameData, Actor performingClient) {
        if (!isBroken()) {
            level += FIXED_INCREASE * (MyRandom.nextDouble() + 0.5);
        }
    }

    public static PositronGenerator roomHasGenerator(Room position) {
        for (GameObject obj : position.getObjects()) {
            if (obj instanceof PositronGenerator) {
                return (PositronGenerator)obj;
            }
        }
        return null;
    }

    @Override
    protected void addActions(GameData gameData, Actor cl, ArrayList<Action> at) {
        if (GameItem.hasAnItemOfClass(cl, GeneratorStartedFluid.class)) {
            at.add(new BoostGeneratorAction());
        }
    }


    @Override
    public void detachYourself(GameData gameData, Actor performer) {
        isDetached = true;
        setPreferredRelativePosition(null);
    }

    @Override
    public String getDetachingDescription() {
        return "The Positron Generator came loose from the floor!";
    }

    @Override
    public int getDetachTimeRounds() {
        return 1;
    }

    @Override
    public boolean canBeDragged() {
        return isDetached;
    }

    @Override
    public void addSpecificActionsFor(GameData gameData, Actor cl, ArrayList<Action> at) {
        super.addSpecificActionsFor(gameData, cl, at);
        if (canBeDragged()) {
            DragAction drag = new DragAction(cl);
            at.add(drag);
        }
    }
}
