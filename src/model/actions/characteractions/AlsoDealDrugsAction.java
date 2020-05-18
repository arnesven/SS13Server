package model.actions.characteractions;

import model.Actor;
import model.GameData;
import model.Target;
import model.actions.DealDrugsAction;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.actions.general.TargetingAction;
import model.characters.decorators.CharacterDecorator;
import model.characters.general.GameCharacter;
import model.items.general.GameItem;

import java.util.ArrayList;
import java.util.List;

public class AlsoDealDrugsAction extends TargetingAction {
    private final Actor dealer;

    public AlsoDealDrugsAction(Actor ap) {
        super("Deal Drugs (free action)", SensoryLevel.PHYSICAL_ACTIVITY, ap);
        this.dealer = ap;
    }

    @Override
    protected void applyTargetingAction(GameData gameData, Actor performingClient, Target target, GameItem item) {

    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "did something shady";
    }

    @Override
    public boolean isViableForThisAction(Target target2) {
        return !target2.isDead() && target2 instanceof Actor && ((Actor) target2).isHuman() && target2 != dealer;
    }

    @Override
    public boolean doesSetPlayerReady() {
        return false;
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {
        super.setArguments(args, performingClient);
        performingClient.setCharacter(new DealingDrugsToCharacterDecorator(performingClient.getCharacter(), (Actor)target));

    }

    private class DealingDrugsToCharacterDecorator extends CharacterDecorator {
        private final Actor victim;

        public DealingDrugsToCharacterDecorator(GameCharacter character, Actor target) {
            super(character, "Dealing drugs to " + target.getBaseName());
            this.victim = target;
        }

        @Override
        public void doAfterActions(GameData gameData) {
            super.doAtEndOfTurn(gameData);
            DealDrugsAction dda = new DealDrugsAction(dealer);
            List<String> args = new ArrayList<>();
            args.add(victim.getPublicName(dealer));
            dda.setActionTreeArguments(args, dealer);
            dda.doTheAction(gameData, dealer);
            getActor().removeInstance((GameCharacter gc) -> gc == this);
        }
    }
}
