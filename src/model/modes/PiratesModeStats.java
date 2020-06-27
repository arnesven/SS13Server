package model.modes;

import model.Actor;
import model.GameData;


public class PiratesModeStats extends TraitorModeStats {
    private final PiratesGameMode piratesGameMode;

    public PiratesModeStats(GameData gameData, PiratesGameMode piratesGameMode) {
        super(gameData, piratesGameMode);
        this.piratesGameMode = piratesGameMode;
    }

    @Override
    protected String getModeSpecificStatus(Actor value) {
        if (piratesGameMode.getPirateCaptain() == value) {
            return "<span style='background-color: orange; color: white'>Pirate</span>";
        } else if (getTraitorMode().getTraitors().contains(value)) {
            return "<span style='background-color: #FF2222'>Traitor</span>";
        }
        return "";
    }

    @Override
    protected String getExtraDeadInfo(Actor value) {
        String traitor = "";
        if (piratesGameMode.getPirateCaptain() == value) {
            traitor = " (Pirate)";
        } else if (getTraitorMode().getTraitors().contains(value)) {
            traitor = " (Traitor)";
        }
        return traitor;
    }

    @Override
    protected String getExtraScoringTableRowsHTML() {
        //if (wizardGameMode.wizardIsCaptured()) {
        //    return "<tr><td>Wizard Captured</td><><td style=\"text-align:right\">" + wizardGameMode.pointsFromCapturedWizard() + "</td></tr>";
        //}
        return "";
    }

    @Override
    protected String getTraitorTeamName() {
        return "Pirate Team";
    }
}
