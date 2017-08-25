package model.actions.general;

import model.Actor;
import model.GameData;
import model.items.general.GameItem;
import model.items.general.HidableItem;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by erini02 on 25/08/17.
 */
public class PickupAndUseAction extends Action {
    private final GameData gameData;
    private GameItem item;
    private Action finalAction;

    public PickupAndUseAction(Actor it, GameData gameData) {
        super("Pick Up and Use", SensoryLevel.PHYSICAL_ACTIVITY);
        this.gameData = gameData;


    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return finalAction.getVerb(whosAsking);
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        PickUpAction pickup = new PickUpAction(performingClient);
        pickup.setItem(item);
        pickup.doTheAction(gameData, performingClient);
        if (performingClient.getCharacter().getItems().contains(item)) {
            finalAction.doTheAction(gameData, performingClient);
        }
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {
        for (GameItem it : performingClient.getPosition().getItems()) {
            if (it instanceof HidableItem && ((HidableItem) it).isHidden()) {
                continue;
            }

            if (args.get(0).equals(it.getPublicName(performingClient))) {
                this.item = it;
                ArrayList<Action> tmp = new ArrayList<>();
                it.addYourActions(gameData, tmp, performingClient);

                for (Action a : tmp) {
                    if (args.get(1).equals(a.getName())) {
                        finalAction = a;
                        finalAction.setArguments(args.subList(2, args.size()), performingClient);
                    }
                }
            }
        }


    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        ActionOption opts = super.getOptions(gameData, whosAsking);

        Set<String> set = new HashSet<>();
        ArrayList<Action> itActions = new ArrayList<>();
        for (GameItem it : whosAsking.getPosition().getItems()) {
            if (it instanceof HidableItem && ((HidableItem) it).isHidden()) {
                continue;
            }
            if (!set.contains(it.getFullName(whosAsking))) {
                ActionOption thisItemOptions = new ActionOption(it.getPublicName(whosAsking));
                ArrayList<Action> tmp = new ArrayList<>();
                it.addYourActions(gameData, tmp, whosAsking);

                for (Action a : tmp) {
                    thisItemOptions.addOption(a.getOptions(gameData, whosAsking));
                }
                if (thisItemOptions.numberOfSuboptions() > 0) {
                    opts.addOption(thisItemOptions);
                }

                set.add(it.getFullName(whosAsking));
            }
        }

        return opts;
    }
}