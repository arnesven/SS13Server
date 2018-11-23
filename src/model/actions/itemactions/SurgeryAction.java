package model.actions.itemactions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.characters.decorators.InstanceChecker;
import model.characters.decorators.OnSurgeryTableDecorator;
import model.characters.general.GameCharacter;
import model.characters.general.HumanCharacter;
import model.items.BodyPart;
import model.items.BodyPartFactory;
import model.items.general.GameItem;

import java.util.List;

public class SurgeryAction extends Action {

    private Actor target;
    private String operationType;
    private String bodyPart;
    private GameItem reattPart;

    public SurgeryAction() {
        super("Perform Surgery", SensoryLevel.PHYSICAL_ACTIVITY);
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        if (target == null) {
            return "Performed surgery";
        }
        return operationType + "d " + target.getPublicName() + "'s " + bodyPart + "!";
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        if (target != null) {
            if (operationType.equals("Amputate")) {
                performingClient.addItem(BodyPartFactory.makeBodyPart(bodyPart, target), target.getAsTarget());
                performingClient.addTolastTurnInfo("You amputated " + target.getPublicName() + "'s " + bodyPart + "!");
                target.getCharacter().getPhysicalBody().removeBodyPart(bodyPart);
            } else { // reattached
                if (reattPart != null) {
                    performingClient.addTolastTurnInfo("You reattached " + target.getPublicName() + "'s " + bodyPart + "!");
                    performingClient.getItems().remove(reattPart);
                    target.getCharacter().getPhysicalBody().addBodyPart(bodyPart);
                } else {
                    performingClient.addTolastTurnInfo("What, the body part is missing? " + Action.FAILED_STRING);
                }
            }
        }
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {
        if (target != null && target.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof OnSurgeryTableDecorator)) {
            this.operationType = args.get(0);
            this.bodyPart = args.get(1);
            if (operationType.equals("Reattach")) {
                for (GameItem it : performingClient.getItems()) {
                    if (it.getPublicName(performingClient).equals(bodyPart)) {
                        reattPart = it;
                    }
                }
            }
        }
    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        ActionOption opts = super.getOptions(gameData, whosAsking);

        for (Actor a : whosAsking.getPosition().getActors()) {
            if (isEligableForSurgery(a)) {
                target = a;
                ActionOption amp = new ActionOption("Amputate");
                for (String part : BodyPart.getAllParts()) {
                    if (a.getCharacter().getPhysicalBody().hasBodyPart(part)) {
                        amp.addOption(part);
                    }
                }
                opts.addOption(amp);

                ActionOption reatt = new ActionOption("Reattach");
                for (GameItem i : whosAsking.getItems()) {
                    if (i instanceof BodyPart) {
                        reatt.addOption(i.getPublicName(whosAsking));
                    }
                }
                if (reatt.numberOfSuboptions() > 0) {
                    opts.addOption(reatt);
                }

            }
        }
        return opts;
    }

    private static boolean isEligableForSurgery(Actor a) {
        return isHuman(a) && (a.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof OnSurgeryTableDecorator) || a.isDead());
    }

    private static boolean isHuman(Actor t) {
        return t.getCharacter().checkInstance(new InstanceChecker() {
            @Override
            public boolean checkInstanceOf(GameCharacter ch) {
                return ch instanceof HumanCharacter;
            }
        });
    }
}
