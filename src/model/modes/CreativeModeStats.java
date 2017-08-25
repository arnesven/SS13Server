package model.modes;

import model.Actor;
import model.GameData;

/**
 * Created by erini02 on 25/08/17.
 */
public class CreativeModeStats extends GameStats {
    public CreativeModeStats(GameData gameData, CreativeGameMode creativeGameMode) {
        super(gameData, creativeGameMode);
    }


    @Override
    protected String getModeSpecificStatus(Actor value) {
        return "";
    }

    @Override
    protected String getExtraDeadInfo(Actor value) {
        return "";
    }

    @Override
    protected String modeSpecificExtraInfo(Actor value) {
        return "";
    }

    @Override
    public String getMode() {
        return "Creative";
    }

    @Override
    public String getOutcome() {
        return "";
    }

    @Override
    public String getEnding() {
        return "";
    }

    @Override
    public String getContent() {
        return "";
    }
}
