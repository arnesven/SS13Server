package model.items;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.FreeAction;
import model.actions.general.Action;
import model.characters.crew.ScienceOfficerCharacter;
import model.characters.general.GameCharacter;
import model.fancyframe.ExperimentNotesFancyFrame;
import model.items.general.GameItem;
import model.objects.general.GameObject;
import model.objects.monolith.StrangeMonolith;
import util.Logger;

import javax.naming.directory.Attribute;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ExperimentNotes extends GameItem {
    private StrangeMonolith monolith;
    private Set<String> crossedOut;

    public ExperimentNotes(GameData gameData) {
        super("Experiment Notes", 0.2, true, 0);
        crossedOut = new HashSet<>();
        try {
            for (GameObject obj : gameData.getRoom("Lab").getObjects()) {
                if (obj instanceof StrangeMonolith) {
                    this.monolith = (StrangeMonolith)obj;
                }
            }
            CosmicArtifact crossed;
            do {
                crossed = CosmicArtifact.getRandomArtifact();
            } while (crossed.getBaseName().equals(monolith.getBaseName()));
            Logger.log("Adding " + crossed.getBaseName() + " to crossed out");
            crossedOut.add(crossed.getBaseName());
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("experimentnotes", "items.png", 8, this);
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "A clipboard with the Science Officer's notes regarding the experiments on the strange monolith in the lab. The first page" +
                " is a list of items, where one has already been crossed out. The remaining pages seem to be notes on" +
                " related work. Needless to say, Nanotrasen must really want to know what the hulking monolith is, or the Sci Off" +
                " wouldn't be putting this much work into a single experiment.";
    }

    @Override
    public GameItem clone() {
        throw new IllegalStateException("Experiment Notes should not be cloned!");
    }

    @Override
    public void addYourActions(GameData gameData, ArrayList<Action> at, Actor cl) {
        super.addYourActions(gameData, at, cl);
        if (cl instanceof Player) {
            at.add(new InspectNotesAction(gameData, (Player)cl));
        }
    }

    public static void doSetup(GameData gameData) {
        boolean notesGotGiven = false;
        for (Player p : gameData.getPlayersAsList()) {
            if (p.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof ScienceOfficerCharacter)) {
                p.getCharacter().giveItem(new ExperimentNotes(gameData), null);
                notesGotGiven = true;
            }
        }
        if (!notesGotGiven) {
            try {
                gameData.getRoom("Lab").addItem(new ExperimentNotes(gameData));
            } catch (NoSuchThingException e) {
                e.printStackTrace();
            }
        }
    }

    public Set<String> getCrossedOut() {
        return crossedOut;
    }

    private class InspectNotesAction extends FreeAction {
        public InspectNotesAction(GameData gameData, Player p) {
            super("Read Notes", gameData, p);
        }

        @Override
        protected void doTheFreeAction(List<String> args, Player p, GameData gameData) {
            p.setFancyFrame(new ExperimentNotesFancyFrame(p, gameData, ExperimentNotes.this));
        }
    }
}
