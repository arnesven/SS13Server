package model.events;

import model.Actor;
import model.GameData;
import model.actions.general.SensoryLevel;
import model.events.ambient.AmbientEvent;
import model.items.NoSuchThingException;
import model.map.Room;
import model.npcs.NPC;
import model.npcs.SantaNPC;
import model.objects.consoles.AIConsole;
import util.Logger;
import util.MyRandom;

/**
 * Created by erini02 on 22/11/16.
 */
public class SantaClauseEvent extends AmbientEvent {
    private boolean hasHappened = false;

    @Override
    protected double getStaticProbability() {
        return AmbientEvent.everyNGames(3);
    }

    @Override
    public void apply(GameData gameData) {
        if (!hasHappened && MyRandom.nextDouble() < getProbability()) {
            hasHappened = true;

            Room santaRoom = MyRandom.sample(gameData.getRooms());

            NPC npc = new SantaNPC(santaRoom);
            gameData.addNPC(npc);
            Logger.log(Logger.INTERESTING, "Santa arrived in " + santaRoom.getName());
            try {
                gameData.findObjectOfType(AIConsole.class).informOnStation("HO HO HO, santa brings gifts for SS13's little girls and boys. Come to " + santaRoom.getName(), gameData);
            } catch (NoSuchThingException e) {
                // dont inform..
            }
        }
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
