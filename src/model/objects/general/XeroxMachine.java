package model.objects.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.events.BackgroundEvent;
import model.events.Event;
import model.items.general.GameItem;
import model.items.general.ItemStack;
import model.map.rooms.Room;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 22/12/16.
 */
public class XeroxMachine extends GameObject {
    public XeroxMachine(Room positiono) {
        super("Xerox Machine", positiono);
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        return new Sprite("xeroxmachine", "stationobjs.png", 64);
    }


    @Override
    public void addSpecificActionsFor(GameData gameData, Actor cl, ArrayList<Action> at) {
        super.addSpecificActionsFor(gameData, cl, at);
        Action a = new CopyItemAction();
        if (a.getOptions(gameData, cl).numberOfSuboptions() > 0) {
            at.add(a);
        }
    }

    private class CopyItemAction extends Action {
        private GameItem selected;
        private int quant;

        public CopyItemAction() {
            super("Xerox", SensoryLevel.OPERATE_DEVICE);
        }

        @Override
        protected String getVerb(Actor whosAsking) {
            return "xeroxed";
        }

        @Override
        public ActionOption getOptions(GameData gameData, Actor whosAsking) {
            ActionOption opt = super.getOptions(gameData, whosAsking);
            for (GameItem it : whosAsking.getItems()) {
                if (!(it instanceof ItemStack)) {
                    ActionOption opt2 = new ActionOption(it.getFullName(whosAsking));
                    addQuantities(opt2);
                    opt.addOption(opt2);
                }


            }
            return opt;
        }

        private void addQuantities(ActionOption opt2) {
            opt2.addOption("1 copy");
            for (int c = 1; c <= 4; c++) {
                opt2.addOption(c*2 + " copies");
            }
        }

        @Override
        protected void execute(GameData gameData, Actor performingClient) {
            if (selected == null) {
                performingClient.addTolastTurnInfo("What, no item to xerox?");
                return;
            }

            if (!performingClient.getItems().contains(selected)) {
                performingClient.addTolastTurnInfo("What? The " + selected.getFullName(performingClient) + " isn't there anymore! " + Action.FAILED_STRING);
                return;
            }

            Event e = new BackgroundEvent() {
                @Override
                public void apply(GameData gameData) {

                    if (quant > 0) {
                        XeroxMachine.this.getPosition().addItem(selected.clone());
                        for (Actor a : XeroxMachine.this.getPosition().getActors()) {
                            a.addTolastTurnInfo("The " + XeroxMachine.this.getPublicName(a) +
                                    " spit out another " + selected.getPublicName(a));
                        }
                    }

                    quant--;
                }

                @Override
                public boolean shouldBeRemoved(GameData gameData) {
                    return quant <= 0;
                }
            };

            gameData.addEvent(e);
            gameData.addMovementEvent(e);

        }

        @Override
        public void setArguments(List<String> args, Actor performingClient) {
            for (GameItem it : performingClient.getItems()) {
               if (args.get(0).equals(it.getFullName(performingClient))) {
                   selected = it;
               }
            }
            quant = Integer.parseInt(args.get(1).substring(0, 1));
        }
    }
}
