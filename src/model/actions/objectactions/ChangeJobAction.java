package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.characters.crew.JanitorCharacter;
import model.characters.decorators.CharacterDecorator;
import model.characters.general.GameCharacter;
import model.characters.visitors.ClownCharacter;
import model.characters.visitors.LawyerCharacter;
import model.characters.visitors.VisitorCharacter;
import model.items.NoSuchThingException;
import model.items.suits.OutFit;
import model.items.suits.SuitItem;
import model.map.GameMap;
import model.map.MapLevel;
import model.modes.GameMode;
import model.objects.consoles.AdministrationConsole;

import java.util.*;

/**
 * Created by erini02 on 05/12/16.
 */
public class ChangeJobAction extends ConsoleAction {
    private final AdministrationConsole adminConsole;
    private final GameData gameData;
    private Actor selectedActor = null;
    private GameCharacter selectedJob = null;

    public ChangeJobAction(AdministrationConsole administrationConsole, GameData gameData) {
        super("Change Job", SensoryLevel.OPERATE_DEVICE);
        this.adminConsole = administrationConsole;
        this.gameData = gameData;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "fiddled with Admin Console";
    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        ActionOption opts = super.getOptions(gameData, whosAsking);

        for (Actor a : gameData.getActors()) {
            if (a.getPosition() == whosAsking.getPosition() || adminConsole.getToBeDemoted().contains(a)) {
                if (a != whosAsking && a.getAsTarget().isTargetable() && a.getCharacter().isCrew()) {
                    ActionOption newOpt = new ActionOption(a.getPublicName());
                    addJobs(newOpt, a.getCharacter(), adminConsole.getToBeDemoted().contains(a));
                    opts.addOption(newOpt);
                }
            }
        }

        return opts;
    }

    private void addJobs(ActionOption newOpt, GameCharacter character, boolean demotion) {
        for (GameCharacter gc : getAvailableJobs()) {
            if (character.getSpeed() > gc.getSpeed() || !demotion) {
                newOpt.addOption(gc.getBaseName());
            }
        }
        if (newOpt.numberOfSuboptions() == 0) {
            newOpt.addOption(new JanitorCharacter().getBaseName());
        }
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        gameData.executeAtEndOfRound(performingClient, this);
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {
        for (Actor a : gameData.getActors()) {
            if (a.getPosition() == performingClient.getPosition() || adminConsole.getToBeDemoted().contains(a)) {
                if (args.get(0).equals(a.getPublicName())) {
                    selectedActor = a;
                }
            }
        }

        for (GameCharacter gc : getAvailableJobs()) {
            if (args.get(1).equals(gc.getBaseName())) {
                selectedJob = gc;
            }
        }
    }

    @Override
    public void lateExecution(GameData gameData, Actor performingClient) {
        super.lateExecution(gameData, performingClient);

        if (selectedActor == null) {
            performingClient.addTolastTurnInfo("What, no crew member in the room to change jobs for? " + Action.FAILED_STRING);
            return;
        }

        try {
            if (gameData.getMap().getLevelForRoom(selectedActor.getPosition()).getName().equals(GameMap.STATION_LEVEL_NAME)) {

                boolean demotion = adminConsole.getToBeDemoted().contains(selectedActor);
                if (adminConsole.getAcceptedActors().contains(selectedActor) || demotion) {
                    Stack<SuitItem> wornStuff = new Stack<>();
                    undressToStack(selectedActor, wornStuff);
                    performingClient.addTolastTurnInfo("You changed " + selectedActor.getBaseName() + "'s job to " + selectedJob.getBaseName() + ".");


                    if (demotion) {
                        selectedActor.addTolastTurnInfo("You have been demoted to " + selectedJob.getBaseName() + ".");
                        gameData.getGameMode().getMiscHappenings().add("The " + selectedActor.getBaseName() + " was demoted to " + selectedJob.getBaseName() + ".");
                        adminConsole.getToBeDemoted().remove(selectedActor);
                    } else {
                        selectedActor.addTolastTurnInfo("You have changed jobs. You are now a " + selectedJob.getBaseName() + ".");
                        gameData.getGameMode().getMiscHappenings().add("The " + selectedActor.getBaseName() + " changed jobs to " + selectedJob.getBaseName() + ".");
                        adminConsole.getAcceptedActors().remove(selectedActor);
                    }

                    makeSwitch(selectedActor, selectedJob, gameData, wornStuff);
                } else {
                    performingClient.addTolastTurnInfo(selectedActor.getPublicName() + " is not eligible for a job change (needs to voluntarily sign up or be demoted).");
                }
            } else {
                performingClient.addTolastTurnInfo("What? That crew member isn't on the station! " + Action.FAILED_STRING);
            }
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }

    }

    private void undressToStack(Actor selectedActor, Stack<SuitItem> stack) {
        while (selectedActor.getCharacter().getSuit() != null) {
            if (! (selectedActor.getCharacter().getSuit() instanceof OutFit)) {
                stack.push(selectedActor.getCharacter().getSuit());
            }
            selectedActor.takeOffSuit();
        }
    }

    private void makeSwitch(Actor selectedActor, GameCharacter selectedJob, GameData gameData, Stack<SuitItem> suits) {
        selectedActor.getCharacter().moveDecoratorsAndCopy(selectedJob);
        if (selectedActor.getCharacter() instanceof CharacterDecorator) {
            // nothing needs doing, inner most char is changed,
        } else {
            selectedActor.setCharacter(selectedJob);
        }

        while (!suits.empty()) {
            selectedActor.putOnSuit(suits.pop());
        }

    }

    public Set<GameCharacter> getAvailableJobs() {
        Set<GameCharacter> jobSet = new HashSet<>();
        jobSet.addAll(GameMode.getAllCrew());
        jobSet.removeIf((GameCharacter gc) -> gc instanceof VisitorCharacter);
        jobSet.add(new ClownCharacter());
        jobSet.add(new LawyerCharacter());
        return jobSet;
    }
}
