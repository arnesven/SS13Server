package model.actions.itemactions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.characters.decorators.OnSurgeryTableDecorator;
import model.characters.general.GameCharacter;
import model.items.BodyPart;
import model.items.BodyPartFactory;
import model.items.general.GameItem;

import java.util.List;

public class SurgeryAction extends Action {

    private Actor target;
    private String operationType;
    private String bodyPart;

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
                performingClient.addTolastTurnInfo("You reattached " + target.getPublicName() + "'s " + bodyPart + "!");
            }
        }
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {
        if (target != null && target.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof OnSurgeryTableDecorator)) {
            this.operationType = args.get(0);
            this.bodyPart = args.get(1);
        }
    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        ActionOption opts = super.getOptions(gameData, whosAsking);

        for (Actor a : whosAsking.getPosition().getActors()) {
            if (a.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof OnSurgeryTableDecorator)) {
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
}
