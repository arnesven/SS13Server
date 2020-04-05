package model.items.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.actions.general.SensoryLevel;
import model.events.Event;
import model.items.NoSuchThingException;
import model.map.GameMap;
import util.Logger;

public class NuclearDisc extends GameItem {

    private final GameData gameData;

    public NuclearDisc(GameData gameData, boolean shouldBeTracked) {
		super("Nuclear Disc", 0.1, 1000);
        if (shouldBeTracked) {
            Event e = new RespawnIfTakenFromStation(this);
            gameData.addEvent(e);
            gameData.addMovementEvent(e);
        }
        this.gameData = gameData;
	}

    public NuclearDisc(GameData gameData) {
        this(gameData, false);
    }

    @Deprecated
    public NuclearDisc() {
        this(null, false);
    }

	@Override
	public NuclearDisc clone() {
		return new NuclearDisc(gameData);
	}

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("nucleardisc","cloning.png", 9, this);
    }


    private class RespawnIfTakenFromStation extends Event {
        private final NuclearDisc disc;

        public RespawnIfTakenFromStation(NuclearDisc nuclearDisc) {
            this.disc = nuclearDisc;
        }

        @Override
        public void apply(GameData gameData) {
            Logger.log("Checking that disc is still on station...");
            if (getHolder() != null) {
                try {
                    if (!gameData.getMap().getLevelForRoom(getHolder().getPosition()).getName().equals(GameMap.STATION_LEVEL_NAME)) {
                        Logger.log(Logger.INTERESTING, "disc is held by soneone, not on ss13 any more! Respawning disc in CQ");
                        getHolder().getItems().remove(disc);
                        gameData.getRoom("Captain's Quarters").addItem(disc);
                        return;
                    }
                } catch (NoSuchThingException e) {
                    e.printStackTrace();
                }
            }
            Logger.log(Logger.INTERESTING, "   was ok.");
        }

        @Override
        public String howYouAppear(Actor performingClient) {
            return "";
        }

        @Override
        public SensoryLevel getSense() {
            return SensoryLevel.NO_SENSE;
        }
    }
}
