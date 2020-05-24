package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.characters.crew.ChemistCharacter;
import model.characters.general.GameCharacter;
import model.items.chemicals.Chemicals;
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
            performingClient.addTolastTurnInfo("What, chemicals gone? " + failed(gameData, performingClient));
        }

    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {
        for (GameItem it : performingClient.getItems()) {
            if (it.getFullName(performingClient).equals(args.get(0))) {
                selectedOne = (Chemicals)it;
            } else if (args.get(1).contains(it.getFullName(performingClient))) {
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

        Set<Chemicals> difChems = new HashSet<>();
        for (GameItem it : whosAsking.getItems()) {
            if (it instanceof Chemicals) {
                difChems.add((Chemicals)it);
            }
        }

        if (difChems.size() > 1) {

            for (Chemicals s : difChems) {
                ActionOption opt2 = new ActionOption(s.getPublicName(whosAsking));
                Set<Chemicals> lesserSet = new HashSet<>();
                lesserSet.addAll(difChems);
                lesserSet.remove(s);
                for (Chemicals s2 : lesserSet) {
                    String label = s2.getPublicName(whosAsking);
                    if (whosAsking.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof ChemistCharacter)) {
                        GameItem it = s.combineWith(s2);
                        if (it == null) {
                            it = s2.combineWith(s);
                        }
                        if (it == null) {
                            label += " (nothing)";
                        } else {
                            label += " (" + it.getPublicName(whosAsking) + ")";
                        }
                    }
                    opt2.addOption(label);
                }
                opts.addOption(opt2);
            }
        }

        return opts;
    }
}
