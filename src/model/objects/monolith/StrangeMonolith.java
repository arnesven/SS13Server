package model.objects.monolith;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.FreeAction;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.events.ambient.SimulatePower;
import model.events.animation.AnimatedSprite;
import model.events.damage.Damager;
import model.events.damage.FireDamage;
import model.items.CosmicArtifact;
import model.items.general.GameItem;
import model.items.general.Multimeter;
import model.items.weapons.Weapon;
import model.map.rooms.Room;
import model.objects.general.BreakableObject;
import model.objects.general.GameObject;
import model.objects.general.PowerConsumer;

import java.util.ArrayList;
import java.util.List;

public class StrangeMonolith extends BreakableObject implements PowerConsumer {
    private final CosmicArtifact artifact;
    private boolean isClosed;
    private final MonolithExperimentsConsole console;
    private boolean correctlyConcluded;

    public StrangeMonolith(Room labRoom) {
        super("Strange Monolith Experiment", 3.0, labRoom);
        this.artifact = CosmicArtifact.getRandomArtifact();
        this.isClosed = true;
        this.console = new MonolithExperimentsConsole(labRoom, this);
        correctlyConcluded = false;
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        List<Sprite> sprs = new ArrayList<>();
        sprs.add(artifact.getSprite(whosAsking));
        sprs.get(0).shiftUpPx(3);
        StringBuilder suffix = new StringBuilder();
        console.addTestSprite(whosAsking, sprs, suffix);
        if (isClosed) {
            if (isBroken()) {
                sprs.add(new Sprite("cagedbroken", "stationobjs.png", 285, this));
                suffix.append("broken");
            } else {
                sprs.add(new Sprite("cageclosed", "stationobjs.png", 283, this));
                suffix.append("closed");
            }
        } else {
            sprs.add(new Sprite("cageopen", "stationobjs.png", 284, this));
            suffix.append("open");
        }

        sprs.add(console.getSprite(whosAsking));
        return new Sprite("artifactcage"+suffix.toString()+console.getSprite(whosAsking).getName(),
                "human.png", 0, sprs, this);

    }

    @Override
    public void addSpecificActionsFor(GameData gameData, Actor cl, ArrayList<Action> at) {
        if (!isBroken()) {
            at.add(new OpenOrCloseGlassCover());
        }
        if (!isClosed && cl instanceof Player) {
            at.add(new TouchMonolithAction(gameData, (Player)cl));
            at.add(new LookForMarkingsAction(gameData, (Player)cl));
        }
        if (GameItem.hasAnItemOfClass(cl, Multimeter.class)) {
            at.add(new MeasureRadiationAction(gameData, (Player)cl));
        }
        super.addSpecificActionsFor(gameData, cl, at);
    }

    @Override
    protected void addActions(GameData gameData, Actor cl, ArrayList<Action> at) {
        console.addSpecificActionsFor(gameData, cl, at);
    }

    public CosmicArtifact getCosmicArtifact() {
        return artifact;
    }

    public boolean isCoverUp() {
        return isClosed;
    }

    public boolean wasCorrectlyConcluded() {
        return correctlyConcluded;
    }

    public void reportSent(Player player, GameData gameData, CosmicArtifact conclusionArtifact) {
        if (conclusionArtifact.getBaseName().equals(artifact.getBaseName())) {
            // Conclusion correct!
            gameData.getGameMode().getMiscHappenings().add("The " + player.getBaseName() + " correctly concluded the Strange Monolith's true nature by experimenting!<br/>(" + artifact.getBaseName() + ")");
            correctlyConcluded = true;
        } else {
            gameData.getGameMode().getMiscHappenings().add("The " + player.getBaseName() + " made an erroneous conclusion about the Strange Monolith's true nature.<br/>"+
                    "(Reported as a " + conclusionArtifact.getBaseName() + ", when really it was a " + getCosmicArtifact().getBaseName()+ ")");

        }
    }

    @Override
    public double getPowerConsumption() {
        return console.getPowerConsumption();
    }

    @Override
    public String getTypeName() {
        return "Equipment";
    }

    @Override
    public void onPowerOff(GameData gameData) {
        console.onPowerOff(gameData);
    }

    @Override
    public void onPowerOn(GameData gameData) {
        console.onPowerOn(gameData);
    }

    @Override
    public int getPowerPriority() {
        return console.getPowerPriority();
    }

    @Override
    public void setPowerPriority(int i) {
        console.setPowerPriority(i);
    }

    @Override
    public void receiveEnergy(GameData gameData, double energy) {
        console.receiveEnergy(gameData, energy);
    }

    @Override
    public void setPowerSimulation(SimulatePower simulatePower) {
        console.setPowerSimulation(simulatePower);
    }

    @Override
    public void setBreaker(Actor a) {
        super.setBreaker(a);
        console.setBreaker(a);
    }

    @Override
    public boolean beAttackedBy(Actor performingClient, Weapon item, GameData gameData) {
        super.beAttackedBy(performingClient, item, gameData);
        return console.beAttackedBy(performingClient, item, gameData);
    }

    @Override
    public void beExposedTo(Actor performingClient, Damager damage, GameData gameData) {
        if (!(damage instanceof FireDamage)) {
            super.beExposedTo(performingClient, damage, gameData);
        }
        console.beExposedTo(performingClient, damage, gameData);
    }

    @Override
    public void thisJustBroke(GameData gameData) {
        super.thisJustBroke(gameData);
        isClosed = false;
        console.thisJustBroke(gameData);
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
            console.cancelTest();
        }

        @Override
        protected void setArguments(List<String> args, Actor performingClient) {

        }
    }

    private class TouchMonolithAction extends FreeAction {
        public TouchMonolithAction(GameData gameData, Player performer) {
            super("Touch Monolith", gameData, performer);
        }

        @Override
        protected void doTheFreeAction(List<String> args, Player p, GameData gameData) {
            if (getCosmicArtifact().isTouchSmooth()) {
                gameData.getChat().serverInSay("The monolith is smooth to the touch.", p);
            } else {
                gameData.getChat().serverInSay("The monolith has a rough surface.", p);
            }
        }
    }

    private class LookForMarkingsAction extends FreeAction {
        public LookForMarkingsAction(GameData gameData, Player cl) {
            super("Look for Markings", gameData, cl);
        }

        @Override
        protected void doTheFreeAction(List<String> args, Player p, GameData gameData) {
            if (getCosmicArtifact().hasMarkings()) {
                gameData.getChat().serverInSay("There are small markings on the monolith, although you can't " +
                        "figure out what's written or if it's even a language.", p);
            } else {
                gameData.getChat().serverInSay("You scrutinize the monolith, but can't find any markings.", p);
            }
        }
    }

    private class MeasureRadiationAction extends FreeAction {
        public MeasureRadiationAction(GameData gameData, Player cl) {
            super("Measure Radiation", gameData, cl);
        }

        @Override
        protected void doTheFreeAction(List<String> args, Player p, GameData gameData) {
            if (getCosmicArtifact().doesEmitRadiation()) {
                gameData.getChat().serverInSay("The monolith is emitting a small mount of radiation.", p);
            } else {
                gameData.getChat().serverInSay("There's no radiation coming from the monolith.", p);
            }
        }
    }
}
