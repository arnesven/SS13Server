package model.events;

import model.Actor;
import model.GameData;
import model.actions.general.SensoryLevel;
import model.items.NoSuchThingException;
import model.map.GameMap;
import model.map.rooms.PirateShipRoom;
import model.objects.consoles.AIConsole;

public class AICheckingForPiratesComingEvent extends Event {
    private final PirateShipRoom pirateShip;
    private boolean alreadyHappened;

    public AICheckingForPiratesComingEvent(PirateShipRoom psr) {
        this.pirateShip = psr;
        alreadyHappened = false;
    }

    @Override
    public void apply(GameData gameData) {
        if (alreadyHappened) {
            return;
        }
        try {
            if (gameData.getMap().getLevelForRoom(pirateShip).getName().equals(GameMap.STATION_LEVEL_NAME)) {
                gameData.findObjectOfType(AIConsole.class).informOnStation("Warning! Pirate marauders are boarding " +
                        "the station through " + pirateShip.getDockingPointRoom().getName() + "!", gameData);
                alreadyHappened = true;
            }
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String howYouAppear(Actor performingClient) {
        return null;
    }

    @Override
    public SensoryLevel getSense() {
        return null;
    }
}
