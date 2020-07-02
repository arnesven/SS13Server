package model.characters.decorators;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.characteractions.CommitSuicideAction;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.characters.general.GameCharacter;
import model.characters.visitors.VisitorCharacter;
import model.items.general.FragGrenade;
import model.items.general.GameItem;
import model.items.general.GodModeTeleporter;
import graphics.StationShakeExtraEffect;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 17/12/16.
 */
public class GodModeDecorator extends CharacterDecorator {
    private final GameData gameData;

    public GodModeDecorator(GameCharacter character, GameData gameData) {
        super(character, "godmode");

         for (GameCharacter gc : gameData.getGameMode().getAllCrew()) {
            if (gc.getClass() != getActor().getInnermostCharacter().getClass()) {
                for (GameItem gi : gc.getStartingItems()) {
                    getActor().addItem(gi.clone(), null);
                }
            }
        }
        getActor().addItem(new FragGrenade(), null);
         getActor().addItem(new GodModeTeleporter(), null);
        this.gameData = gameData;

    }

    @Override
    public boolean isEncumbered() {
        return false;
    }

    @Override
    public int getMovementSteps() {
        return 5;
    }

    @Override
    public void addCharacterSpecificActions(GameData gameData, ArrayList<Action> at) {
        super.addCharacterSpecificActions(gameData, at);

        for (GameCharacter gc : gameData.getGameMode().getAllCrew()) {
            if (gc.getClass() != getActor().getInnermostCharacter().getClass() && !(gc instanceof VisitorCharacter)) {
                GameCharacter gc2 = gc.clone();
                gc2.setActor(getActor());
                gc2.addCharacterSpecificActions(gameData, at);
            }
        }

        at.add(new CommitSuicideAction());
        at.add(new ShakeStationAction());
    }

    @Override
    public boolean checkInstance(InstanceChecker instanceChecker) {

        for (GameCharacter gc : gameData.getGameMode().getAllCrew()) {
            if (!(gc instanceof VisitorCharacter)) {
               if (instanceChecker.checkInstanceOf(gc)) {
                   return true;
               }
            }
        }

        return super.checkInstance(instanceChecker);
    }

    private class ShakeStationAction extends Action {

        public ShakeStationAction() {
            super("Shake Station", SensoryLevel.NO_SENSE);
        }

        @Override
        protected String getVerb(Actor whosAsking) {
            return "shake";
        }

        @Override
        protected void execute(GameData gameData, Actor performingClient) {
            if (performingClient instanceof Player) {
                ((Player) performingClient).addExtraEffect(new StationShakeExtraEffect());
            }
        }

        @Override
        protected void setArguments(List<String> args, Actor performingClient) {

        }
    }
}
