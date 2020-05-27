package model.modes;

import model.Actor;
import model.GameData;

public class HuntModeStats extends ScoredModeStats {
    private final HuntGameMode huntMode;

    public HuntModeStats(GameData gameData, HuntGameMode huntGameMode) {
        super(gameData, huntGameMode);
        this.huntMode = huntGameMode;
    }

    @Override
    protected String getAntagonistTeamName() {
        return "Alien";
    }

    @Override
    protected String modeSpecificTableRows(String style) {
        // TODO
        return null;
    }

    @Override
    protected String getModeSpecificStatus(Actor value) {
        if (huntMode.getAliens().contains(value)) {
            return "<span style='background-color: #22FF22'>Alien</span>";
        }
        return "";
    }


    @Override
    protected String getExtraDeadInfo(Actor value) {
        String alien = "";
        if (huntMode.getAliens().contains(value)) {
            alien = " (Alien)";
        }
        return alien;
    }

    @Override
    protected String modeSpecificExtraInfo(Actor value) {
        // TODO: spawned in which round or original alien...
        return "";
    }

    @Override
    public String getMode() {
        return huntMode.getName();
    }

}
