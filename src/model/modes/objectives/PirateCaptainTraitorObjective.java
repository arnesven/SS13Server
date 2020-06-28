package model.modes.objectives;

import model.GameData;
import model.items.NoSuchThingException;
import model.map.levels.NewAlgiersMapLevel;
import util.MyRandom;

public abstract class PirateCaptainTraitorObjective implements TraitorObjective {
    private final GameData gameData;
    protected String DETACH_INFO = "(Detaching objects requires Blowtorch or Laser Sword and Mechanic skill).";

    public PirateCaptainTraitorObjective(GameData gameData) {
        this.gameData = gameData;
    }

    @Override
    public boolean wasCompleted() {
        return isCompleted(gameData);
    }

    @Override
    public int getPoints() {
        return 500;
    }


    public static PirateCaptainTraitorObjective makeRandomObjective(GameData gameData) {
        if (MyRandom.nextDouble() < 0.1) {
            return new MonolithObjective(gameData);
        } else if (MyRandom.nextDouble() < 0.95) {
            return new RefrigeratorObjective(gameData);
        }
        return new BioscannerObjective(gameData);
    }

    @Override
    public boolean isCompleted(GameData gameData) {
        try {
            return gameData.getMap().getLevelForRoom(getLocatable().getPosition()).getName().equals(NewAlgiersMapLevel.LEVEL_NAME);
        } catch (NoSuchThingException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String getText() {
        return "Detach the " + thingToDetach() + " in the " + getLocatable().getPosition().getName() +
                ", then bring " + thingToBringBack() + " back to your pirate stronghold (New Algiers). " +
                DETACH_INFO;
    }

    protected String thingToBringBack() {
        return "it";
    }

    protected abstract String thingToDetach();
}
