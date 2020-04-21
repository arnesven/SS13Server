package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.events.Event;
import model.items.general.GameItem;
import model.objects.mining.GeneralManufacturer;

import java.util.List;

public class GeneralManufacturerAction extends Action {

    private GeneralManufacturer generalManufacturer;
    private GameItem selected = null;

    public GeneralManufacturerAction(GeneralManufacturer generalManufacturer) {
        super("Manufacture", SensoryLevel.OPERATE_DEVICE);
        this.generalManufacturer = generalManufacturer;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "fiddled with general manufacturer";
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        if (selected != null) {
            if (selected.getCost() <= generalManufacturer.getLimit()) {
                generalManufacturer.reduceCharge(selected.getCost());
                generalManufacturer.setIsManufacturing(true);
                Event e = new ManufactureItemEvent(gameData, performingClient, selected.clone(), generalManufacturer);
                generalManufacturer.getPosition().addEvent(e);
                gameData.addEvent(e);

            } else {
                performingClient.addTolastTurnInfo("The general manufacturer doesn't have enough charge. Feed it more shards. " + Action.FAILED_STRING);
            }
        } else {
            performingClient.addTolastTurnInfo("Item not found. " + Action.FAILED_STRING);
        }
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {
        for (String s : generalManufacturer.getItemsMap().keySet()) {
            if (args.get(0).equals(s)) {
                for (GameItem it : generalManufacturer.getItemsMap().get(s))  {
                    if (it.getBaseName().equals(args.get(1))) {
                        selected = it;
                    }
                }
            }
        }

    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        ActionOption opt = super.getOptions(gameData, whosAsking);

        for (String s : generalManufacturer.getItemsMap().keySet()) {
            ActionOption sub = new ActionOption(s);
            for (GameItem it : generalManufacturer.getItemsMap().get(s)) {
                if (it.getCost() <= generalManufacturer.getLimit())
                sub.addOption(it.getBaseName());
            }
            if (sub.numberOfSuboptions() > 0) {
                opt.addOption(sub);
            }
        }

        return opt;
    }

    private class ManufactureItemEvent extends Event {
        private final int roundSet;
        private final Actor maker;
        private final GameItem item;
        private final GeneralManufacturer gm;

        public ManufactureItemEvent(GameData gameData, Actor performingClient, GameItem clone, GeneralManufacturer generalManufacturer) {
            super();
            this.roundSet = gameData.getRound();
            this.maker = performingClient;
            this.item = clone;
            this.gm = generalManufacturer;
        }

        @Override
        public void apply(GameData gameData) {
            if (gameData.getRound() == roundSet+1) {
                if (maker.getPosition() == gm.getPosition()) {
                    maker.addTolastTurnInfo("The general manufacturer produced a " + item.getPublicName(maker) + "! You put it in your inventory.");
                    maker.addItem(item, generalManufacturer);
                } else {
                    maker.getPosition().addItem(item);
                }
                generalManufacturer.setIsManufacturing(false);
                generalManufacturer.getPosition().removeEvent(this);
            }
        }

        @Override
        public String howYouAppear(Actor performingClient) {
            return "The general manufacturer whirred. It's making something...";
        }

        @Override
        public SensoryLevel getSense() {
            return new SensoryLevel(SensoryLevel.VisualLevel.INVISIBLE, SensoryLevel.AudioLevel.SAME_ROOM, SensoryLevel.OlfactoryLevel.UNSMELLABLE);
        }

        @Override
        public boolean shouldBeRemoved(GameData gameData) {
            return gameData.getRound() > roundSet+1;
        }
    }
}
