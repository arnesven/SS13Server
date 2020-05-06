package model.actions.itemactions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.items.general.GameItem;
import model.items.general.UnpackableItem;

import java.util.List;

public class UnpackItemAction extends Action {

        private final UnpackableItem unpackableItem;

        public UnpackItemAction(String actionName, UnpackableItem uit) {
            super(actionName, SensoryLevel.PHYSICAL_ACTIVITY);
            this.unpackableItem = uit;
        }

        @Override
        protected String getVerb(Actor whosAsking) {
            return "unwrapped a gift";
        }

        @Override
        protected void execute(GameData gameData, Actor performingClient) {
            performingClient.getItems().remove(unpackableItem);
            for (GameItem it : unpackableItem.getInners()) {
                performingClient.addItem(it, null);
            }
        }

        @Override
        public void setArguments(List<String> args, Actor performingClient) {

        }

}
