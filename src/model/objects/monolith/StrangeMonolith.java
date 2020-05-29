package model.objects.monolith;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.items.CosmicArtifact;
import model.map.rooms.Room;
import model.objects.general.GameObject;

import java.util.ArrayList;
import java.util.List;

public class StrangeMonolith extends GameObject {
    private final CosmicArtifact artifact;
    private boolean isClosed;
    private final MonolithExperimentsConsole console;
    private boolean correctlyConcluded;

    public StrangeMonolith(Room labRoom) {
        super("Strange Monolith Experiment", labRoom);
        this.artifact = CosmicArtifact.getRandomArtifact();
        this.isClosed = true;
        this.console = new MonolithExperimentsConsole(labRoom);
        correctlyConcluded = false;
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        List<Sprite> sprs = new ArrayList<>();
        sprs.add(artifact.getSprite(whosAsking));
        sprs.get(0).shiftUpPx(3);
        String suffix;
        if (isClosed) {
            sprs.add(new Sprite("cageclosed", "stationobjs.png", 283, this));
            suffix = "open";
        } else {
            sprs.add(new Sprite("cageopen", "stationobjs.png", 284, this));
            suffix = "closed";
        }
        sprs.add(console.getSprite(whosAsking));
        return new Sprite("artifactcage"+suffix+console.getSprite(whosAsking).getName(),
                "human.png", 0, sprs, this);

    }

    @Override
    public void addSpecificActionsFor(GameData gameData, Actor cl, ArrayList<Action> at) {
        super.addSpecificActionsFor(gameData, cl, at);
        at.add(new OpenOrCloseGlassCover());
        console.addSpecificActionsFor(gameData, cl, at);
    }

    public CosmicArtifact getCosmicArtifact() {
        return artifact;
    }


    public boolean wasCorrectlyConcluded() {
        return correctlyConcluded;
    }

    public void reportSent(Player player, GameData gameData, CosmicArtifact conclusionArtifact) {
        if (conclusionArtifact.getBaseName().equals(artifact.getBaseName())) {
            // Conclusion correct!
            gameData.getGameMode().getMiscHappenings().add("The " + player.getBaseName() + " correctly concluded the Strange Monolith's true nature by experimenting!");
            correctlyConcluded = true;
        } else {
            gameData.getGameMode().getMiscHappenings().add("The " + player.getBaseName() + " made an erroneous conclusion about the Strange Monolith's true nature.");

        }
    }

    private class OpenOrCloseGlassCover extends Action {
        public OpenOrCloseGlassCover() {
            super((isClosed?"Open":"Close") + " Glass Cover", SensoryLevel.OPERATE_DEVICE);
        }

        @Override
        protected String getVerb(Actor whosAsking) {
            return (isClosed?"opened":"closed") + " the glass cover";
        }

        @Override
        protected void execute(GameData gameData, Actor performingClient) {
            performingClient.addTolastTurnInfo("You " + (isClosed?"opened":"closed") + " the glass cover.");
            isClosed = !isClosed;
        }

        @Override
        protected void setArguments(List<String> args, Actor performingClient) {

        }
    }
}
