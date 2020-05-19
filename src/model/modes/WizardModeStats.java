package model.modes;

import model.Actor;
import model.GameData;

public class WizardModeStats extends TraitorModeStats {
    private final WizardGameMode wizardGameMode;

    public WizardModeStats(GameData gameData, WizardGameMode wizardGameMode) {
        super(gameData, wizardGameMode);
        this.wizardGameMode = wizardGameMode;
    }

    @Override
    protected String getModeSpecificStatus(Actor value) {
        if (getTraitorMode().getTraitors().contains(value)) {
            return "<span style='background-color: blue; color: white'>Wizard</span>";
        }
        return "";
    }

    @Override
    protected String getExtraDeadInfo(Actor value) {
        String traitor = "";
        if (getTraitorMode().getTraitors().contains(value)) {
            traitor = " (Wizard)";
        }
        return traitor;
    }

    @Override
    protected String getExtraScoringTableRowsHTML() {
        if (wizardGameMode.wizardIsCaptured()) {
            return "<tr><td>Wizard Captured</td><><td style=\"text-align:right\">" + wizardGameMode.pointsFromCapturedWizard() + "</td></tr>";
        }
        return "";
    }

    @Override
    protected String getTraitorTeamName() {
        return "Wizard";
    }
}
