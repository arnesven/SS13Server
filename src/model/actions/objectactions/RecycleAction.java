package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.events.Event;
import model.items.NoSuchThingException;
import model.items.general.GameItem;
import model.objects.recycling.RecyclingContainer;
import model.objects.recycling.TrashBin;

import java.util.List;

public class RecycleAction extends Action {

    private final TrashBin trash;
    private GameItem selectedItem;

    public RecycleAction(TrashBin tb) {
        super("Recycle", SensoryLevel.PHYSICAL_ACTIVITY);
        this.trash = tb;
    }


    @Override
    protected String getVerb(Actor whosAsking) {
        if (selectedItem == null) {
            return "tried to recycle something.";
        }
        return "recycled " + selectedItem.getPublicName(whosAsking);
    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        ActionOption opts = super.getOptions(gameData, whosAsking);
        for (GameItem gi : whosAsking.getItems()) {
            opts.addOption(gi.getFullName(whosAsking));
        }
        return opts;
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        if (selectedItem == null) {
            performingClient.addTolastTurnInfo("What, the item was missing? " + failed(gameData, performingClient));
        } else {
            performingClient.getItems().remove(selectedItem);
            selectedItem.setHolder(null);
            try {
                RecyclingContainer cont = gameData.findObjectOfType(RecyclingContainer.class);
                if (!cont.isFull() || !cont.getPosition().getName().equals("Janitorial")) {
                    cont.recycle(selectedItem);
                    performingClient.addTolastTurnInfo("You recycled the " + selectedItem.getPublicName(performingClient) + ". Go you!");
                    trash.setAnimating(true);
                } else {
                    performingClient.addTolastTurnInfo("You tried to recycle the " + selectedItem.getPublicName(performingClient) +", but the trash bin rejected it!");
                    trash.setRejecting(true);
                }
            } catch (NoSuchThingException e) {
                performingClient.addTolastTurnInfo("You tried to recycle the " + selectedItem.getPublicName(performingClient) +", but the trash bin rejected it!");
                trash.setRejecting(true);
            }
            gameData.addEvent(new ResetTrashcanEvent(trash, gameData.getRound()));
            trash.setLastUsedInRound(gameData.getRound());
        }
    }

    @Override
    protected void setArguments(List<String> args, Actor performingClient) {
        for (GameItem gi : performingClient.getItems()) {
            if (gi.getFullName(performingClient).equals(args.get(0))) {
                selectedItem = gi;
                break;
            }
        }
    }

    private class ResetTrashcanEvent extends Event {
        private final TrashBin trash;
        private final int round;

        public ResetTrashcanEvent(TrashBin trash, int round) {
            this.trash = trash;
            this.round = round;
        }

        @Override
        public void apply(GameData gameData) {
            if (gameData.getRound() > round && trash.getLastUsedIn() != gameData.getRound()) {
                trash.reset();
            }
        }

        @Override
        public String howYouAppear(Actor performingClient) {
            return "";
        }

        @Override
        public SensoryLevel getSense() {
            return SensoryLevel.NO_SENSE;
        }

        @Override
        public boolean shouldBeRemoved(GameData gameData) {
            return gameData.getRound() > round;
        }
    }
}
