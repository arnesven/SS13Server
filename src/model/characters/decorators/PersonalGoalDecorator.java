package model.characters.decorators;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.FreeAction;
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
        if (getActor() instanceof  Player) {
            at.add(new PersonalGoalFancyFrameDisplayerAction(gameData, (Player) getActor()));
        }
    }

    private class PersonalGoalFancyFrameDisplayerAction extends FreeAction {
        public PersonalGoalFancyFrameDisplayerAction(GameData gameData, Player p) {
            super("Show Personal Goal", gameData, p);
        }


        @Override
        protected void doTheFreeAction(List<String> args, Player p, GameData gameData) {
            p.setFancyFrame(new SinglePageFancyFrame(p.getFancyFrame(), "Personal Goal",
                    HTMLText.makeColoredBackground("white", HTMLText.makeCentered("<br/>" +
                            HTMLText.makeCentered(goal.getText()) +
                            "<br/><br/><i>Personal goals can earn you a Hall of Fame point if you do not receive any other points."))));
        }

        @Override
        public Sprite getAbilitySprite() {
            return new Sprite("showpersonalgoal", "interface_retro.png", 2, 1, null);
        }
    }
}
