package model.characters.decorators;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.DoNothingAction;
import model.actions.general.SensoryLevel;
import model.characters.general.GameCharacter;
import model.fancyframe.SinglePageFancyFrame;
import model.modes.goals.PersonalGoal;
import util.HTMLText;

import java.util.ArrayList;
import java.util.List;

public class PersonalGoalDecorator extends CharacterDecorator {
    private final PersonalGoal goal;

    public PersonalGoalDecorator(GameCharacter character, PersonalGoal finalGoal) {
        super(character, "Personal Goal Displayer");
        this.goal = finalGoal;
    }

    @Override
    public void addCharacterSpecificActions(GameData gameData, ArrayList<Action> at) {
        super.addCharacterSpecificActions(gameData, at);
        at.add(new PersonalGoalFancyFrameDisplayerAction());
    }

    private class PersonalGoalFancyFrameDisplayerAction extends Action {
        public PersonalGoalFancyFrameDisplayerAction() {
            super("Show Personal Goal", SensoryLevel.NO_SENSE);
        }

        @Override
        protected String getVerb(Actor whosAsking) {
            return "";
        }

        @Override
        protected void execute(GameData gameData, Actor performingClient) {
           // This is just a free action and does not need execute.
        }

        @Override
        public void setArguments(List<String> args, Actor performingClient) {
            if (performingClient instanceof Player) {
                Player p = ((Player) performingClient);
                p.setFancyFrame(new SinglePageFancyFrame(p.getFancyFrame(), "Personal Goal",
                        HTMLText.makeColoredBackground("white", HTMLText.makeCentered("<br/>" +
                        HTMLText.makeCentered(goal.getText()) +
                        "<br/><br/><i>Personal goals can earn you a Hall of Fame point if you do not receive any other points."))));
                p.setNextAction(new DoNothingAction());
            }
        }

        @Override
        public boolean doesSetPlayerReady() {
            return false;
        }
    }
}
