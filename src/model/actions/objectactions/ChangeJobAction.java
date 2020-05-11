package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.characters.crew.JanitorCharacter;
import model.characters.crew.StaffAssistantCharacter;
import model.characters.decorators.CharacterDecorator;
import model.characters.general.GameCharacter;
import model.characters.visitors.ClownCharacter;
import model.characters.visitors.LawyerCharacter;
import model.characters.visitors.VisitorCharacter;
import model.items.NoSuchThingException;
import model.items.suits.SuitItem;
import model.map.GameMap;
import model.modes.GameMode;
import model.objects.consoles.PersonnelConsole;
import model.objects.consoles.RequisitionsConsole;

import java.util.*;

/**
 * Created by erini02 on 05/12/16.
 */
public class ChangeJobAction extends ConsoleAction {
    private final PersonnelConsole adminConsole;
    private final GameData gameData;
    private Actor selectedActor = null;
    private GameCharacter selectedJob = null;

    public ChangeJobAction(PersonnelConsole administrationConsole, GameData gameData) {
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
            if ((a.getPosition() == whosAsking.getPosition() && adminConsole.getAcceptedActors().contains(a)) || adminConsole.getToBeDemoted().contains(a)) {
                if (a != whosAsking && a.getAsTarget().isTargetable() && a.getCharacter().isCrew()) {
                    ActionOption newOpt = new ActionOption(a.getBaseName());
                    for (GameCharacter newJob : adminConsole.getAvailableJobs(a.getCharacter(), adminConsole.getToBeDemoted().contains(a))) {
                        newOpt.addOption(newJob.getBaseName());
                    }
                    opts.addOption(newOpt);
                }
            }
        }

        return opts;
    }


    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        gameData.executeAtEndOfRound(performingClient, this);
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {
        for (Actor a : gameData.getActors()) {
            if (a.getPosition() == performingClient.getPosition() || adminConsole.getToBeDemoted().contains(a)) {
                if (args.get(0).equals(a.getBaseName())) {
                    selectedActor = a;
                }
            }
        }

        for (GameCharacter gc : adminConsole.getAvailableJobs(selectedActor.getCharacter(),
                adminConsole.getToBeDemoted().contains(selectedActor))) {
            if (args.get(1).equals(gc.getBaseName())) {
                selectedJob = gc;
            }
        }
    }

    @Override
    public void lateExecution(GameData gameData, Actor performingClient) {
        super.lateExecution(gameData, performingClient);

        if (selectedActor == null) {
            performingClient.addTolastTurnInfo("What, no crew member in the room to change jobs for? " + failed(gameData, performingClient));
            return;
        }

        try {
            if (gameData.getMap().getLevelForRoom(selectedActor.getPosition()).getName().equals(GameMap.STATION_LEVEL_NAME)) {

                boolean demotion = adminConsole.getToBeDemoted().contains(selectedActor);
                if (adminConsole.getAcceptedActors().contains(selectedActor) || demotion) {
                    List<SuitItem> wornStuff = undressToStack(selectedActor);
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
                performingClient.addTolastTurnInfo("What? That crew member isn't on the station! " + failed(gameData, performingClient));
            }
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }

    }

    private List<SuitItem> undressToStack(Actor selectedActor) {
        List<SuitItem> stack = selectedActor.getCharacter().getEquipment().getSuitsAsList();
        selectedActor.getCharacter().getEquipment().removeEverything();
        return stack;
    }

    private void makeSwitch(Actor selectedActor, GameCharacter selectedJob, GameData gameData, List<SuitItem> suits) {
        selectedActor.getCharacter().moveDecoratorsAndCopy(selectedJob);
        if (selectedActor.getCharacter() instanceof CharacterDecorator) {
            // nothing needs doing, inner most char is changed,
        } else {
            selectedActor.setCharacter(selectedJob);
        }

        for (SuitItem s : suits) {
            s.putYourselfOn(selectedActor.getCharacter().getEquipment());
        }

    }

}
