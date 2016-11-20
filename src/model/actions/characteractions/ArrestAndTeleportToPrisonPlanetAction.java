package model.actions.characteractions;

import model.Actor;
import model.GameData;
import model.MostWantedCriminals;
import model.Player;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.characters.decorators.PassiveDecorator;
import model.items.NoSuchThingException;
import model.items.general.GameItem;
import model.items.general.Teleporter;
import util.HTMLText;
import util.Logger;

import java.util.List;

/**
 * Created by erini02 on 17/11/16.
 */
public class ArrestAndTeleportToPrisonPlanetAction extends Action {

    private Actor selected;

    public ArrestAndTeleportToPrisonPlanetAction() {
        super("Arrest and teleport", SensoryLevel.PHYSICAL_ACTIVITY);
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "arrested " + selected.getPublicName() + " and teleported " +
                (selected.getCharacter().getGender().equals("man")?"him":"her") + " to the Prison Planet.";
    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        ActionOption opts = super.getOptions(gameData, whosAsking);
        if (GameItem.hasAnItem(whosAsking, new Teleporter())) {
            Logger.log(Logger.INTERESTING, "   in ArrestAndTeleportToPrisonPlanetAction, whos asking has teleporter");
            for (Actor a : whosAsking.getPosition().getActors()) {
                if (a.getAsTarget().isTargetable()) {
                    opts.addOption(a.getPublicName());
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
    public void lateExecution(GameData gameData, Actor performingClient) {
        try {
            if (selected.getPosition() == performingClient.getPosition()) {
                performingClient.addTolastTurnInfo("You arrested " + performingClient.getPublicName() + " and teleported " +
                        (performingClient.getCharacter().getGender().equals("man") ? "him" : "her") + " to the Prison Planet.");

                selected.addTolastTurnInfo(HTMLText.makeText("red", performingClient.getPublicName() + "; Stand down! You are under arrest for the murder of a Nanotransen Merchant." +
                        " You have been auto-sentenced to a lifetime of imprisonment on the Prison Planet!"));
                selected.moveIntoRoom(gameData.getRoom("Prison Planet"));
                selected.setCharacter(new PassiveDecorator(selected.getCharacter()));
                if (selected instanceof Player) {
                    try {
                        MostWantedCriminals.remove(gameData.getClidForPlayer((Player) selected));
                    } catch (NoSuchThingException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                performingClient.addTolastTurnInfo("What? The " + selected.getPublicName() + " wasn't there anymore? " + Action.FAILED_STRING);
            }
        } catch (NoSuchThingException nste) {
            nste.printStackTrace();
        }
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {
            for (Actor a : performingClient.getPosition().getActors()) {
                if (a.getPublicName().equals(args.get(0))) {
                   selected = a;
                }
            }
    }
}
