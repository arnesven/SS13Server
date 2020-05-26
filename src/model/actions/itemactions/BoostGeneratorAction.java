package model.actions.itemactions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.items.NoSuchThingException;
import model.items.chemicals.GeneratorStartedFluid;
import model.items.general.GameItem;
import model.objects.power.PositronGenerator;

import java.util.List;

public class BoostGeneratorAction extends Action {

    public BoostGeneratorAction() {
        super("Boost Generator", SensoryLevel.PHYSICAL_ACTIVITY);
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "boosted the generator";
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        if (GameItem.hasAnItemOfClass(performingClient, GeneratorStartedFluid.class)) {
            try {
                PositronGenerator psg = PositronGenerator.roomHasGenerator(performingClient.getPosition());
                if (psg != null) {
                    GeneratorStartedFluid gsf = GameItem.getItemFromActor(performingClient, new GeneratorStartedFluid());
                    performingClient.getCharacter().getItems().remove(gsf);
                    psg.boostGeneratorOutput(gameData, performingClient);
                    performingClient.addTolastTurnInfo("You boosted the output of the generator!");
                } else {
                    performingClient.addTolastTurnInfo("What, no generator to boost? " + failed(gameData, performingClient));
                }


            } catch (NoSuchThingException e) {
                e.printStackTrace();
            }
        } else {
            performingClient.addTolastTurnInfo("What, no starter fluid available? " + failed(gameData, performingClient));
        }
    }

    @Override
    protected void setArguments(List<String> args, Actor performingClient) {

    }
}
