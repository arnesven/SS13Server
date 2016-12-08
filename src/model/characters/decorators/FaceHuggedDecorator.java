package model.characters.decorators;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.characters.general.GameCharacter;
import model.events.damage.InternalBleeding;
import model.events.damage.PoisonDamage;
import model.items.foods.FoodItem;
import model.items.general.Chemicals;
import model.items.general.GameItem;
import model.items.weapons.Knife;
import model.npcs.ParasiteNPC;
import util.HTMLText;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 30/11/16.
 */
public class FaceHuggedDecorator extends CharacterDecorator {
    private final int huggedInRound;
    private final Actor targetActor;

    public FaceHuggedDecorator(GameCharacter target, GameData gameData) {
        super(target, "Facehugged");
        this.huggedInRound = gameData.getRound();
        this.targetActor = target.getActor();
    }

    @Override
    public void doAtEndOfTurn(GameData gameData) {
        super.doAtEndOfTurn(gameData);
        if (gameData.getRound() - huggedInRound < 3) {
            getActor().addTolastTurnInfo("You don't feel so good. You need a doctor...");
        } else if (gameData.getRound() - huggedInRound < 5) {
            getActor().addTolastTurnInfo("You feel like something's trying to claw itself out of you!");
        } else if (gameData.getRound() - huggedInRound == 5 &&
                targetActor.getCharacter().checkInstance((GameCharacter gc) -> gc == this)) {
            getActor().addTolastTurnInfo(HTMLText.makeText("red", "Parasites burst out of your abdomen!"));
            getActor().getAsTarget().beExposedTo(null, new InternalBleeding(2.5));
            for (int i = 0; i < 3; i++) {
                gameData.addNPC(new ParasiteNPC(getPosition()));
            }
        } else {
            getActor().removeInstance((GameCharacter gc) -> gc instanceof FaceHuggedDecorator);
        }

    }

    @Override
    public void addActionsForActorsInRoom(GameData gameData, Actor anyActorInRoom, ArrayList<Action> at) {
        if (getActor().getCharacter().checkInstance((GameCharacter gc) -> gc instanceof OnSurgeryTableDecorator)) {
            if (GameItem.hasAnItem(anyActorInRoom, new Knife())) {
                at.add(new PerformSurgeryOnAction(getActor(), anyActorInRoom));
            }
        }
    }

    @Override
    public void doWhenConsumeItem(FoodItem foodItem, GameData gameData) {
        super.doWhenConsumeItem(foodItem, gameData);
        if (foodItem instanceof Chemicals) {
            if (getActor().getCharacter().checkInstance((GameCharacter gc) -> gc instanceof FaceHuggedDecorator)) {
                getActor().removeInstance((GameCharacter gc) -> gc instanceof FaceHuggedDecorator);
                getActor().addTolastTurnInfo("Your parasites have been euthanized.");
            }
        }
    }

    private class PerformSurgeryOnAction extends Action {
        private final Actor doctor;
        private final Actor patient;

        public PerformSurgeryOnAction(Actor actor, Actor anyActorInRoom) {
            super("Perform Surgery", SensoryLevel.PHYSICAL_ACTIVITY);
            this.doctor = anyActorInRoom;
            this.patient = actor;
        }

        @Override
        protected String getVerb(Actor whosAsking) {
            return "performed surgery on " + patient.getPublicName();
        }

        @Override
        protected void execute(GameData gameData, Actor performingClient) {
            while (performingClient.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof  FaceHuggedDecorator)) {
                performingClient.removeInstance((GameCharacter gc) -> gc instanceof FaceHuggedDecorator);
                performingClient.addTolastTurnInfo("You surgically removed eggs from " + patient.getPublicName() + ".");
                patient.addTolastTurnInfo(performingClient.getPublicName() + " surgically removed eggs from you");
            }
        }

        @Override
        public void setArguments(List<String> args, Actor performingClient) {

        }
    }
}
