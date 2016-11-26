package model.events.ambient;

import model.Actor;
import model.GameData;
import model.actions.general.SensoryLevel;
import model.items.NoSuchThingException;
import model.objects.consoles.AIConsole;
import util.MyRandom;

/**
 * Created by erini02 on 20/10/16.
 */
public class CorruptAIEvent extends AmbientEvent {
    private boolean hasHappened = false;
    private static double occurranceChance = AmbientEvent.everyNGames(5);

    @Override
    public void apply(GameData gameData) {
        if (!hasHappened && MyRandom.nextDouble() < getProbability()) {
            hasHappened = true;
            gameData.getGameMode().getMiscHappenings().add("AI was corrupted.");
            try {
                gameData.findObjectOfType(AIConsole.class).corrupt(gameData);
            } catch (NoSuchThingException e) {
                e.printStackTrace();
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


    @Override
    protected double getStaticProbability() {
        return occurranceChance;
    }
}
