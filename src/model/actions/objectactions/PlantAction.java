package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.items.general.GameItem;
import model.items.seeds.SeedsItem;
import model.objects.general.SoilPatch;

import java.util.List;

/**
 * Created by erini02 on 26/11/16.
 */
public class PlantAction extends Action {

    private final SoilPatch soil;
    private SeedsItem selectedSeeds;

    public PlantAction(SoilPatch soil) {
        super("Plant Seeds", SensoryLevel.PHYSICAL_ACTIVITY);
        this.soil = soil;
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "planted some seeds";
    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        ActionOption opt =  super.getOptions(gameData, whosAsking);
        for (GameItem it : whosAsking.getItems()) {
            if (it instanceof SeedsItem) {
                opt.addOption(it.getPublicName(whosAsking));
            }
        }
        return opt;
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {

        if (!performingClient.getItems().contains(selectedSeeds)) {
            performingClient.addTolastTurnInfo("What, the seeds weren't there anymore? " + failed(gameData, performingClient));
            return;
        }

        if (soil.isPlanted()) {
            performingClient.addTolastTurnInfo("What, the soil was already planted? " + failed(gameData, performingClient));
        }

        soil.plant(selectedSeeds, gameData, performingClient);
        performingClient.addTolastTurnInfo("You planted the " + selectedSeeds.getPublicName(performingClient) + " in the soil.");
        performingClient.getItems().remove(selectedSeeds);
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {
        for (GameItem it : performingClient.getItems()) {
            if (it instanceof SeedsItem) {
                if (it.getPublicName(performingClient).contains(args.get(0))) {
                    selectedSeeds = (SeedsItem)it;
                }
            }
        }

    }
}
