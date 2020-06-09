package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.items.BodyPart;
import model.items.foods.BodyPartFood;
import model.items.general.GameItem;
import model.objects.general.CookOMatic;
import sounds.Sound;

import java.util.List;

public class CookBodyPartIntoFoodAction extends Action {
    private final CookFoodAction innerAction;
    private final CookOMatic cookOMatic;

    public CookBodyPartIntoFoodAction(CookOMatic cookOMatic, CookFoodAction cookFoodAction) {
        super("Cook Body Part Into Food", SensoryLevel.PHYSICAL_ACTIVITY);
        this.innerAction = cookFoodAction;
        this.cookOMatic = cookOMatic;
    }

    @Override
    public boolean hasRealSound() {
        return innerAction.hasRealSound();
    }

    @Override
    public Sound getRealSound() {
        return innerAction.getRealSound();
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return innerAction.getVerb(whosAsking);
    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        ActionOption inner = innerAction.getOptions(gameData, whosAsking);
        inner.setName("Cook Body Part Into Food");
        return inner;
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        BodyPart bp = null;
        for (GameItem it : performingClient.getItems()) {
            if (it instanceof BodyPart) {
                bp = (BodyPart)it;
            }
        }

        if (bp == null) {
            performingClient.addTolastTurnInfo("What, no body part to cook with? " + failed(gameData, performingClient));
        } else {
            performingClient.getItems().remove(bp);

            BodyPartFood bpFood = new BodyPartFood(bp, innerAction.getSelectedItem());
            innerAction.cookAndMaybeSendWithDumbwaiter(gameData, performingClient, bpFood);
            performingClient.addTolastTurnInfo("You cooked a " +
                    bp.getPublicName(performingClient) + " into the " + innerAction.getSelectedItem().getPublicName(performingClient));

        }
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {
        innerAction.setArguments(args, performingClient);
    }
}
