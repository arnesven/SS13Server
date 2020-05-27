package model.actions.characteractions;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.characters.special.AlienCharacter;

import java.util.List;

public class HatchAction extends Action {
    public HatchAction() {
        super("Hatch from Egg", new SensoryLevel(SensoryLevel.VisualLevel.VISIBLE_IF_CLOSE, SensoryLevel.AudioLevel.INAUDIBLE, SensoryLevel.OlfactoryLevel.UNSMELLABLE));
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "hatched";
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        if (performingClient.getInnermostCharacter() instanceof AlienCharacter) {
            AlienCharacter ac = (AlienCharacter)performingClient.getInnermostCharacter();
            ac.setStage(AlienCharacter.STAGE_PARASITE);
            performingClient.addTolastTurnInfo("You hatched out of the egg.");
        }
    }

    @Override
    protected void setArguments(List<String> args, Actor performingClient) {

    }

    @Override
    public Sprite getAbilitySprite() {
        return new Sprite("hatchabisprite", "alien2.png", 16, 8, null);
    }
}
