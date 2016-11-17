package model.events;

import model.Actor;
import model.GameData;
import model.MostWantedCriminals;
import model.Player;
import model.actions.general.SensoryLevel;
import model.events.ambient.AmbientEvent;
import model.items.NoSuchThingException;
import model.npcs.GalacticFederalMarshalNPC;
import model.objects.consoles.AIConsole;
import util.Logger;
import util.MyRandom;

import java.util.Map;

/**
 * Created by erini02 on 17/11/16.
 */
public class GalacticFederalMarshalsEvent extends AmbientEvent {
    private boolean hasHappened = false;
    private boolean hasLeft = false;
    private Player mostWantedCriminal;
    private GalacticFederalMarshalNPC gfm1;
    private GalacticFederalMarshalNPC gfm2;
    private boolean removed = false;

    @Override
    protected double getStaticProbability() {
        return 0.1;
    }

    @Override
    public void apply(GameData gameData) {
        if (!hasHappened && MyRandom.nextDouble() < getProbability()) {
            mostWantedCriminal = wantedCriminalOnStation(gameData);
            if (mostWantedCriminal != null) {
                hasHappened = true;
                gfm1 = new GalacticFederalMarshalNPC(1, mostWantedCriminal, gameData.getRoom("Shuttle Gate"));
                gameData.addNPC(gfm1);
                gfm2 = new GalacticFederalMarshalNPC(2, mostWantedCriminal, gameData.getRoom("Shuttle Gate"));
                gameData.addNPC(gfm2);
                try {
                    gameData.findObjectOfType(AIConsole.class).informOnStation("Galactic Federal Marshals have arrived on the station. Keep clear and let them do their jobs.",
                            gameData);
                } catch (NoSuchThingException e) {
                    e.printStackTrace();
                }
            }

        } else if (hasHappened) {
            mostWantedCriminal = wantedCriminalOnStation(gameData);
            if (mostWantedCriminal == null && !removed) {
                removeIfNotDead(gfm1, gameData);
                removeIfNotDead(gfm2, gameData);
                removed = true;
                try {
                    gameData.findObjectOfType(AIConsole.class).informOnStation("The marshals have left the station.",
                            gameData);
                } catch (NoSuchThingException e) {
                    e.printStackTrace();
                }
            } else {
                gfm1.setMostWanted(mostWantedCriminal);
                gfm2.setMostWanted(mostWantedCriminal);
            }
        }
    }

    private void removeIfNotDead(GalacticFederalMarshalNPC gfm, GameData gameData) {
        if (!gfm.isDead()) {
            try {
                gfm.getPosition().removeNPC(gfm);
                gameData.getNPCs().remove(gfm);
            } catch (NoSuchThingException e) {
                e.printStackTrace();
            }

        }
    }

    private Player wantedCriminalOnStation(GameData gameData) {
        Player aCriminal = null;
        Logger.log(Logger.INTERESTING, "Searching for a most wanted criminal");
        for (String wantedCriminal : MostWantedCriminals.getCriminals()) {
            for (Map.Entry<String, Player> entry : gameData.getPlayersAsEntrySet()) {
                Logger.log(Logger.INTERESTING, wantedCriminal + " - " + entry.getKey() );
                if (entry.getKey().toLowerCase().equals(wantedCriminal.toLowerCase())) {
                    aCriminal = entry.getValue();
                    Logger.log(Logger.INTERESTING, "   ---> Found a most wanted criminal!");
                }
            }
        }
        return aCriminal;
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
