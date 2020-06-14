package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.fancyframe.BioScannerFancyFrame;
import model.objects.general.BioScanner;

import java.util.List;

public class ActivateBioScannerAction extends Action {

    private final BioScanner bioScanner;
    private final BioScannerFancyFrame fancyFrame;

    public ActivateBioScannerAction(BioScanner bioScanner, BioScannerFancyFrame bioScannerFancyFrame) {
        super("Activate BioScanner", SensoryLevel.OPERATE_DEVICE);
        this.bioScanner = bioScanner;
        this.fancyFrame = bioScannerFancyFrame;
    }

    public ActivateBioScannerAction(BioScanner bioScanner) {
        this(bioScanner, null);
    }

    @Override
    public void setArguments(List<String> args, Actor p) {
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "activated the BioScanner";
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        if (!bioScanner.isLoaded()) {
            performingClient.addTolastTurnInfo("You failed to activate the BioScanner.");

        } else {
            bioScanner.setLoadedChemicals(null);
            bioScanner.setInUse(true);
            bioScanner.giveBioScannerOutput(gameData, performingClient,fancyFrame);
            gameData.executeAtEndOfRound(performingClient, this);
        }
    }

    @Override
    public void lateExecution(GameData gameData,
                              Actor performingClient) {
        bioScanner.setInUse(false);
    }
}

