package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.items.chemicals.Chemicals;
import model.items.general.GameItem;
import model.objects.general.BioScanner;

import java.util.List;

public class TurnOnBioScannerAction extends Action {

    private BioScanner bioScanner;

    public TurnOnBioScannerAction(BioScanner bioScanner) {
        super("Turn On BioScanner", SensoryLevel.OPERATE_DEVICE);
        this.bioScanner = bioScanner;
    }

    @Override
    public void setArguments(List<String> args, Actor p) {
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "turned the bioscanner on";
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        Chemicals chems = getChemicals(performingClient);
        bioScanner.setLoadedChemicals(chems);
        performingClient.getItems().remove(chems);
        performingClient.addTolastTurnInfo("The BioScanner is warming up...");
    }

    private Chemicals getChemicals(Actor cl) {
        for (GameItem gi : cl.getItems()) {
            if (gi instanceof Chemicals) {
                return (Chemicals)gi;
            }
        }

        return null;
    }

}
