package model.actions.itemactions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.items.EmptyContainer;
import model.items.MakeShiftBomb;
import model.items.NoSuchThingException;
import model.items.general.*;

import java.util.List;

public class CraftBombAction extends Action {

    private GameItem remoteItem;
    private boolean makeRiggable = false;
    private GameItem selectedChem;

    public CraftBombAction() {
        super("Craft Bomb", SensoryLevel.OPERATE_DEVICE);
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "crafted a bomb";
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        if (selectedChem != null) {
            if (performingClient.getItems().contains(selectedChem) || !GameItem.hasAnItemOfClass(performingClient, EmptyContainer.class)) {
                GameItem container = null;
                try {
                    container = GameItem.getItemFromActor(performingClient, new EmptyContainer());
                } catch (NoSuchThingException e) {
                    e.printStackTrace();
                }
                performingClient.getItems().remove(selectedChem);
                performingClient.getItems().remove(container);
                BombItem bomb = new MakeShiftBomb(gameData, performingClient);
                if (remoteItem != null) {
                    if (performingClient.getItems().contains(remoteItem)) {
                        performingClient.getItems().remove(hasUplinkItem(performingClient));
                        bomb = new RemoteBomb();
                    } else {
                        performingClient.addTolastTurnInfo("What, no " + remoteItem.getPublicName(performingClient)+ " to use? " + Action.FAILED_STRING);
                    }
                } else if (makeRiggable) {
                    try {
                        performingClient.getItems().remove(GameItem.getItemFromActor(performingClient, new Tools()));
                        bomb = new BoobyTrapBomb();
                    } catch (NoSuchThingException e) {
                        performingClient.addTolastTurnInfo("What, no tools to use? " + Action.FAILED_STRING);
                    }
                }
                performingClient.addItem(bomb, performingClient.getAsTarget());
                performingClient.addTolastTurnInfo("You crafted a " + bomb.getBaseName() + "!");
            } else {
                performingClient.addTolastTurnInfo("What, the raw materials weren't there? " + Action.FAILED_STRING);
            }
        } else {
            performingClient.addTolastTurnInfo("Could not find raw materials for bomb. " + Action.FAILED_STRING);
        }
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {
        selectedChem = null;
        for (GameItem it : performingClient.getItems()) {
            if (args.get(0).contains(it.getPublicName(performingClient))) {
                selectedChem = it;
            }
        }
        if (args.size() > 1) {
            if (args.get(1).contains("Remote Bomb")) {
                remoteItem = hasUplinkItem(performingClient);
            } else if (args.get(1).contains("Riggable Bomb")) {
                makeRiggable = true;
            }
        }
    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        ActionOption opts = super.getOptions(gameData, whosAsking);
        for (GameItem it : whosAsking.getItems()) {
            if (it instanceof Chemicals) {
                if (((Chemicals) it).isFlammable()) {
                    ActionOption innerOpt = new ActionOption("With " + it.getPublicName(whosAsking));
                    boolean either = false;
                    if (hasUplinkItem(whosAsking) != null) {
                        UplinkItem uit = hasUplinkItem(whosAsking);
                        innerOpt.addOption("Remote Bomb (destroys " + uit.getBaseName() + ")");
                        either = true;
                    }
                    if (GameItem.hasAnItem(whosAsking, new Tools())) {
                        innerOpt.addOption("Riggable Bomb (destroys Tools)");
                        either = true;
                    }
                    if (either) {
                        innerOpt.addOption("Makeshift Bomb");
                    }
                    opts.addOption(innerOpt);

                }
            }
        }
        return opts;
    }



    private static UplinkItem hasUplinkItem(Actor whosAsking) {
        for (GameItem it : whosAsking.getItems()) {
            if (it instanceof  UplinkItem) {
                return (UplinkItem) it;
            }
        }
        return null;
    }
}
