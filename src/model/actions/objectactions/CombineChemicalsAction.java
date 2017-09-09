package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.items.general.Chemicals;
import model.items.general.GameItem;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by erini02 on 09/09/17.
 */
public class CombineChemicalsAction extends Action {
    private Chemicals selectedOne = null;
    private Chemicals selectedTwo = null;

    public CombineChemicalsAction(GameData gameData, Actor cl) {
        super("Combine Chemicals", SensoryLevel.OPERATE_DEVICE);

    }


    @Override
    protected String getVerb(Actor whosAsking) {
        return "Fiddled with Chemical Apparatus";
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {

        if (performingClient.getItems().contains(selectedOne) &&
                performingClient.getItems().contains(selectedTwo)) {

            GameItem result = selectedOne.combineWith(selectedTwo);
            if (result == null) {
                result = selectedTwo.combineWith(selectedOne);
            }
            performingClient.addTolastTurnInfo("Reacting " + selectedOne.getFormula() + " + " + selectedTwo.getFormula());

            if (result == null) {
                performingClient.addTolastTurnInfo("The reaction did not yield a useful result.");
            } else {
                performingClient.addTolastTurnInfo("You combined the chemicals into a " + result.getPublicName(performingClient) + "!");
                performingClient.addItem(result, null);
            }
            performingClient.getItems().remove(selectedOne);
            performingClient.getItems().remove(selectedTwo);


        } else {
            performingClient.addTolastTurnInfo("What, chemicals gone? " + Action.FAILED_STRING);
        }

    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {
        for (GameItem it : performingClient.getItems()) {
            if (it.getFullName(performingClient).equals(args.get(0))) {
                selectedOne = (Chemicals)it;
            } else if (it.getFullName(performingClient).equals(args.get(1))) {
                selectedTwo = (Chemicals)it;
            }
            if (selectedOne != null && selectedTwo != null) {
                break;
            }
        }
    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        ActionOption opts = super.getOptions(gameData, whosAsking);

        Set<String> difChems = new HashSet<>();
        for (GameItem it : whosAsking.getItems()) {
            if (it instanceof Chemicals) {
                difChems.add(it.getFullName(whosAsking));
            }
        }

        if (difChems.size() > 1) {

            for (String s : difChems) {
                ActionOption opt2 = new ActionOption(s);
                Set<String> lesserSet = new HashSet<>();
                lesserSet.addAll(difChems);
                lesserSet.remove(s);
                for (String s2 : lesserSet) {
                    opt2.addOption(s2);
                }
                opts.addOption(opt2);
            }
        }

        return opts;
    }
}
