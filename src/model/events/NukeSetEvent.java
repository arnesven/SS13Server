package model.events;

import model.Actor;
import model.GameData;
import model.actions.general.SensoryLevel;
import model.items.NoSuchThingException;
import model.objects.consoles.AIConsole;
import model.objects.general.NuclearBomb;

/**
 * Created by erini02 on 18/11/16.
 */
public class NukeSetEvent extends Event {

    private final NuclearBomb bomb;
    private int roundsLeft = 2;

        public NukeSetEvent(NuclearBomb bomb) {
            this.bomb = bomb;
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
        public void apply(GameData gameData) {
            String tellString = "Detonation in " + roundsLeft + " minutes.";

            if (roundsLeft == 2) {
                tellString = "Nuclear warhead detected. " + tellString;
                gameData.setNumberOfRounds(gameData.getNoOfRounds() + 2);
            }


            try {
                gameData.findObjectOfType(AIConsole.class).informOnStation(tellString, gameData);
            } catch (NoSuchThingException e) {

            }


            if (roundsLeft == 0) {
                bomb.detonate(gameData);
                doAfterNuked(gameData);
            }

            roundsLeft--;
        }

    protected void doAfterNuked(GameData gameData) {

    }

    @Override
    public boolean shouldBeRemoved(GameData gameData) {
            return roundsLeft == -1 || bomb.deactivated();
    }
}
