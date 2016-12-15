package model.characters.decorators;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.characters.general.GameCharacter;
import model.items.NoSuchThingException;
import model.items.general.FireExtinguisher;
import model.items.general.GameItem;
import model.map.GameMap;
import model.map.rooms.RoomType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 27/11/16.
 */
public class TumblingThroughSpaceDecorator extends CharacterDecorator {
    public TumblingThroughSpaceDecorator(GameCharacter character) {
        super(character, "tumbling");
    }

    @Override
    public void addCharacterSpecificActions(GameData gameData, ArrayList<Action> at) {
        at.clear();
        Action act = new ThrowItemAction();
        if (act.getOptions(gameData, getActor()).numberOfSuboptions() > 0) {
            at.add(act);
        }

        try {
            FireExtinguisher ext = GameItem.getItemFromActor(getActor(), new FireExtinguisher());
            if (ext.getUsesRemaining() > 0) {
                at.add(new PropelWithFireExtinguisherAction(ext));
            }
        } catch (NoSuchThingException e) {
            // did not have one...
        }

    }

    private class ThrowItemAction extends Action {

        private GameItem selected;
        private String direction;

        public ThrowItemAction() {
            super("Throw Item", new SensoryLevel(SensoryLevel.VisualLevel.CLEARLY_VISIBLE,
                    SensoryLevel.AudioLevel.INAUDIBLE,
                    SensoryLevel.OlfactoryLevel.UNSMELLABLE));
        }

        @Override
        protected String getVerb(Actor whosAsking) {
            return "Threw something";
        }

        @Override
        public ActionOption getOptions(GameData gameData, Actor whosAsking) {
            ActionOption opt = super.getOptions(gameData, whosAsking);
            for (GameItem it : whosAsking.getItems()) {
                ActionOption oneOpt = new ActionOption(it.getFullName(whosAsking));
                addDirections(oneOpt);
                opt.addOption(oneOpt);
            }
            return opt;
        }



        @Override
        protected void execute(GameData gameData, Actor performingClient) {
            performingClient.addTolastTurnInfo("You threw away the " + selected.getBaseName() + " " + direction.toLowerCase());
            performingClient.getItems().remove(selected);
            gameData.getMap().tumbleIntoLevel(gameData, performingClient, GameMap.getOppositeDirection(direction));
            performingClient.addTolastTurnInfo("You tumbled through space.");
            if (performingClient.getPosition().getType() != RoomType.space) {
                performingClient.removeInstance((GameCharacter gc) -> gc instanceof TumblingThroughSpaceDecorator);
            }
        }

        @Override
        public void setArguments(List<String> args, Actor performingClient) {
            for (GameItem it : performingClient.getItems()) {
                if (it.getFullName(performingClient).equals(args.get(0))) {
                    selected = it;
                }
            }
            direction = args.get(1);
        }
    }

    private class PropelWithFireExtinguisherAction extends Action {
        private FireExtinguisher ext;
        private String direction;

        public PropelWithFireExtinguisherAction(FireExtinguisher ext) {
            super("Propel Yourself", new SensoryLevel(SensoryLevel.VisualLevel.CLEARLY_VISIBLE,
                    SensoryLevel.AudioLevel.INAUDIBLE,
                    SensoryLevel.OlfactoryLevel.UNSMELLABLE));
            this.ext = ext;
        }

        @Override
        protected String getVerb(Actor whosAsking) {
            return "propelled";
        }

        @Override
        public ActionOption getOptions(GameData gameData, Actor whosAsking) {
            ActionOption opts = super.getOptions(gameData, whosAsking);
            addDirections(opts);
            return opts;
        }

        @Override
        protected void execute(GameData gameData, Actor performingClient) {
            performingClient.addTolastTurnInfo("You propelled yourself with the fire extinguisher.");
            ext.decrementLevel();
            gameData.getMap().tumbleIntoLevel(gameData, performingClient, GameMap.getOppositeDirection(direction));
            performingClient.addTolastTurnInfo("You tumbled through space.");
            if (performingClient.getPosition().getType() != RoomType.space) {
                performingClient.removeInstance((GameCharacter gc) -> gc instanceof TumblingThroughSpaceDecorator);
            }
        }

        @Override
        public void setArguments(List<String> args, Actor performingClient) {
            direction = args.get(0);
        }
    }


    private void addDirections(ActionOption oneOpt) {

        for (String s : GameMap.getDirectionStrings()) {
            oneOpt.addOption(s);
        }

    }
}
