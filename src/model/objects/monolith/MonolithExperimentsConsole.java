package model.objects.monolith;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.fancyframeactions.SitDownAtConsoleAction;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.characters.decorators.StunningSparksAnimationDecorator;
import model.events.FlashEvent;
import model.events.ambient.ElectricalFire;
import model.events.animation.AnimatedSprite;
import model.events.damage.ElectricalDamage;
import model.events.damage.RadiationDamage;
import model.events.damage.SonicDamage;
import model.fancyframe.ConsoleFancyFrame;
import model.fancyframe.MonolithExperimentsConsoleFancyFrame;
import model.map.rooms.Room;
import model.objects.consoles.Console;

import java.util.ArrayList;
import java.util.List;

public class MonolithExperimentsConsole extends Console {
    private static final double STANDBY_MODE_POWER_CONSUMP_MW = 0.000050;
    private final MonolithExperimentRig monolith;
    private StringBuilder testResults;
    private MonolithExperimentAction currentTest;

    private final MonolithExperimentAction[] experiments = new MonolithExperimentAction[] {
        null, new LightAndHeatTestAction(), new FireTestAction(), new SoundWaveTest(),
        new ElectricCurrentTest(), new PressureTest(), new XRayTest()
    };

    public MonolithExperimentsConsole(Room r, MonolithExperimentRig monolith) {
        super("Experiment Console", r);
        this.monolith = monolith;
        testResults = new StringBuilder();
        currentTest = null;
    }

    public List<String> getTestNames() {
        List<String> result = new ArrayList<>();
        for (MonolithExperimentAction act : experiments) {
            if (act != null) {
                result.add(act.getShortName());
            }
        }
        return result;
    }

    private void setCurrentTest(MonolithExperimentAction monolithExperimentAction) {
        currentTest = monolithExperimentAction;
    }


    @Override
    public double getPowerConsumption() {
        if (currentTest == null) {
            return STANDBY_MODE_POWER_CONSUMP_MW;
        }
        return currentTest.getPowerConsumption();
    }

    @Override
    public Sprite getNormalSprite(Player whosAsking) {
        return new Sprite("experimentconsole", "computer2.png", 19, 23, this);
    }

    @Override
    protected Sprite getUnpoweredSprite(Player whosAsking) {
        return new Sprite("experimentconsolenopower", "computer2.png", 20, 23, this);
    }

    @Override
    protected Sprite getBrokenSprite(Player whosAsking) {
        return new Sprite("experimentconsolenobroken", "computer2.png", 21, 23, this);
    }

    @Override
    protected void addConsoleActions(GameData gameData, Actor cl, ArrayList<Action> at) {
        if (cl instanceof Player) {
            at.add(new SitDownAtConsoleAction(gameData, this, (Player)cl) {
                @Override
                protected ConsoleFancyFrame getNewFancyFrame(Console console, GameData gameData, Player performingClient) {
                    return new MonolithExperimentsConsoleFancyFrame(performingClient, gameData, MonolithExperimentsConsole.this);
                }
            });
        }

    }

    public MonolithExperimentRig getMonolith() {
        return monolith;
    }

    public String getTestResults() {
        return testResults.toString();
    }

    public void addTestResult(String s) {
        testResults.append(s + "<br/");
    }

    public Action getTestForNumber(int i) {
        return experiments[i].copyYourself();
    }

    public void addTestSprite(Player whosAsking, List<Sprite> sprs, StringBuilder suffix) {
        if (currentTest != null) {
            sprs.add(currentTest.getAnimationSprite());
            suffix.append(currentTest.getShortName().toLowerCase().replaceAll(" ", "_"));
        }
    }

    public void cancelTest() {
        currentTest = null;
    }


    private abstract class MonolithExperimentAction extends Action {
        public MonolithExperimentAction(String name) {
            super(name, SensoryLevel.OPERATE_DEVICE);
        }

        @Override
        protected String getVerb(Actor whosAsking) {
            return "fiddled with " + getBaseName();
        }

        @Override
        protected void setArguments(List<String> args, Actor performingClient) {

        }

        @Override
        protected void execute(GameData gameData, Actor performingClient) {
            MonolithExperimentsConsole.this.setCurrentTest(this);
            executeTest(gameData, performingClient);
        }

        protected abstract void executeTest(GameData gameData, Actor performingClient);

        public abstract MonolithExperimentAction copyYourself();

        public abstract String getShortName();

        public abstract Sprite getAnimationSprite();

        public abstract double getPowerConsumption();
    }


    private class LightAndHeatTestAction extends MonolithExperimentAction {

        public LightAndHeatTestAction() {
            super("Conduct Light and Heat Test");
        }

        @Override
        protected void executeTest(GameData gameData, Actor performingClient) {
            if (!getMonolith().isCoverUp()) {
                FlashEvent flash = new FlashEvent(gameData, getPosition());
                getPosition().addEvent(flash);
                gameData.addEvent(flash);
                ((Player)performingClient).getFancyFrame().dispose((Player)performingClient);
            }
            if (getMonolith().getCosmicMonolith().reactsToLightHeat()) {
                addTestResult("Object responded to light waves in the spectrum 500-1000 nm.");
            } else {
                addTestResult("Object unresponsive to light/heat waves.");
            }
        }

        @Override
        public MonolithExperimentAction copyYourself() {
            return new LightAndHeatTestAction();
        }

        @Override
        public String getShortName() {
            return "Light/Heat";
        }

        @Override
        public Sprite getAnimationSprite() {
            return new AnimatedSprite("lighttestfalsh", "effects2.png",
                    4, 0, 32, 32, MonolithExperimentsConsole.this, 10, true);
        }

        @Override
        public double getPowerConsumption() {
            return STANDBY_MODE_POWER_CONSUMP_MW * 10;
        }
    }

    private class FireTestAction extends MonolithExperimentAction {

        public FireTestAction() {
            super("Conduct Fire Test");
        }

        @Override
        protected void executeTest(GameData gameData, Actor performingClient) {
            if (!getMonolith().isCoverUp()) {
                ((ElectricalFire)gameData.getGameMode().getEvents().get("fires")).startNewEvent(getPosition());
                performingClient.addTolastTurnInfo("Ahhh! A fire broke out!");
                ((Player)performingClient).getFancyFrame().dispose((Player)performingClient);
            }
            if (getMonolith().getCosmicMonolith().reactsToFlames()) {
                addTestResult("Object responded to fire.");
            } else {
                addTestResult("Object unresponsive to fire.");
            }
        }

        @Override
        public MonolithExperimentAction copyYourself() {
            return new FireTestAction();
        }

        @Override
        public String getShortName() {
            return "Fire";
        }

        @Override
        public Sprite getAnimationSprite() {
            return new AnimatedSprite("firetestburn", "newfire.png",
                    0, 0, 32, 32, MonolithExperimentsConsole.this, 18, true);
        }

        @Override
        public double getPowerConsumption() {
            return STANDBY_MODE_POWER_CONSUMP_MW * 5;
        }
    }

    private class SoundWaveTest extends MonolithExperimentAction {
        public SoundWaveTest() {
            super("Conduct Sound Wave Test");
        }

        @Override
        protected void executeTest(GameData gameData, Actor performingClient) {
            if (!getMonolith().isCoverUp()) {
                for (Actor a : getPosition().getActors()) {
                    a.getCharacter().beExposedTo(performingClient, new SonicDamage(0.25));
                }
                ((Player)performingClient).getFancyFrame().dispose((Player)performingClient);
            }
            if (getMonolith().getCosmicMonolith().doesReactToSound()) {
                addTestResult("Object responded to sound waves of wave lengths 100-20000 Hz.");
            } else {
                addTestResult("Object unresponsive to sound waves.");
            }
        }

        @Override
        public MonolithExperimentAction copyYourself() {
            return new SoundWaveTest();
        }

        @Override
        public String getShortName() {
            return "Sound Wave";
        }

        @Override
        public Sprite getAnimationSprite() {
            return new AnimatedSprite("soundtest", "effects2.png",
                    1, 16, 32, 32, MonolithExperimentsConsole.this, 23, true);
        }

        @Override
        public double getPowerConsumption() {
            return STANDBY_MODE_POWER_CONSUMP_MW * 15;
        }
    }

    private class ElectricCurrentTest extends MonolithExperimentAction {
        public ElectricCurrentTest() {
            super("Conduct Electric Current Test");
        }

        @Override
        protected void executeTest(GameData gameData, Actor performingClient) {
            if (!getMonolith().isCoverUp()) {
                performingClient.getCharacter().beExposedTo(performingClient, new ElectricalDamage(0.5));
                performingClient.setCharacter(new StunningSparksAnimationDecorator(performingClient.getCharacter(), gameData));
                ((Player)performingClient).getFancyFrame().dispose((Player)performingClient);
            }
            if (getMonolith().getCosmicMonolith().doesConductCurrent()) {
                addTestResult("Object conducts electric current well.");
            } else {
                addTestResult("Object does not conduct electric current.");
            }
        }

        @Override
        public MonolithExperimentAction copyYourself() {
            return new ElectricCurrentTest();
        }

        @Override
        public String getShortName() {
            return "Electric Current";
        }

        @Override
        public Sprite getAnimationSprite() {
            return new AnimatedSprite("stunsparks", "effects3.png",
                    14, 20, 32, 32, MonolithExperimentsConsole.this, 12, true);
        }

        @Override
        public double getPowerConsumption() {
            return STANDBY_MODE_POWER_CONSUMP_MW * 20;
        }
    }

    private class PressureTest extends MonolithExperimentAction {
        public PressureTest() {
            super("Conduct Pressure Test");
        }

        @Override
        protected void executeTest(GameData gameData, Actor performingClient) {
            if (getMonolith().getCosmicMonolith().reactsToPressure()) {
                addTestResult("Object is reacting to pressure.");
            } else {
                addTestResult("Object is rigid and unyielding to pressure.");
            }
        }

        @Override
        public MonolithExperimentAction copyYourself() {
            return new PressureTest();
        }

        @Override
        public String getShortName() {
            return "Pressure";
        }

        @Override
        public Sprite getAnimationSprite() {
            return new AnimatedSprite("pressuretest", "effects2.png",
                    0, 20, 32, 32, MonolithExperimentsConsole.this, 14, true);
        }

        @Override
        public double getPowerConsumption() {
            return STANDBY_MODE_POWER_CONSUMP_MW * 10;
        }
    }

    private class XRayTest extends MonolithExperimentAction {
        public XRayTest() {
            super("Conduct X-Ray Test");
        }

        @Override
        protected void executeTest(GameData gameData, Actor performingClient) {
            if (!monolith.isCoverUp()) {
                performingClient.getCharacter().beExposedTo(performingClient, new RadiationDamage(0.25, gameData));
            }
            if (getMonolith().getCosmicMonolith().isHollow()) {
                addTestResult("X-Rayed object and it appears to be hollow.");
            } else {
                addTestResult("X-Rayed object, it's solid material.");
            }
        }

        @Override
        public MonolithExperimentAction copyYourself() {
            return new XRayTest();
        }

        @Override
        public String getShortName() {
            return "X-Ray";
        }

        @Override
        public Sprite getAnimationSprite() {
            return new AnimatedSprite("xraytest", "effects2.png",
                    8, 6, 32, 32, MonolithExperimentsConsole.this, 8, true);
        }

        @Override
        public double getPowerConsumption() {
            return STANDBY_MODE_POWER_CONSUMP_MW * 20;
        }
    }
}


