package model.modes;

import model.Actor;
import model.GameData;
import model.Player;

/**
 * Created by erini02 on 04/12/16.
 */
public class MutinyGameStats extends GameStats {
    private final MutinyGameMode mode;

    public MutinyGameStats(GameData gameData, MutinyGameMode mode) {
        super(gameData, mode);
        this.mode = mode;
    }

    @Override
    protected String getModeSpecificStatus(Actor value) {
        if (value instanceof Player) {
            if (mode.isAntagonist((Player)value)) {
                return "<b>Mutineer</b>";
            }
        }
        return "";
    }

    @Override
    protected String getExtraDeadInfo(Actor value) {
        if (value instanceof Player) {
            if (mode.isAntagonist((Player)value)) {
                return " (Mutineer)";
            }
        }
        return "";
    }

    @Override
    protected String modeSpecificExtraInfo(Actor value) {
        return "";
    }

    @Override
    public String getMode() {
        return "Mutiny";
    }

    @Override
    public String getOutcome() {
        GameOver ov = mode.getGameResult(gameData);
        if (ov == GameOver.ANTAGONISTS_DEAD) {
            return "Mutiny was beaten down.";
        } else if (ov == GameOver.ALL_DEAD) {
            return "Everybody Lost!";
        } else if (ov == GameOver.PROTAGONISTS_DEAD) {
            return "The mutinners took over the station.";
        } else if (ov == GameOver.TIME_IS_UP) {
            return "Mutiny Unsuccessful";
        }


        throw new IllegalStateException("Tried to get game outcome before game was over!");
    }

    @Override
    public String getEnding() {
        GameOver ov = mode.getGameResult(gameData);
        if (ov == GameOver.ANTAGONISTS_DEAD) {
            return "Mutineers killed";
        } else if (ov == GameOver.ALL_DEAD) {
            return "Everybody died";
        } else if (ov == GameOver.PROTAGONISTS_DEAD) {
            return "Captain killed";
        } else if (ov == GameOver.TIME_IS_UP) {
            return "Time Limit Reached";
        }
        return "";
    }

    @Override
    public String getContent() {
        return "There was a mutiny!";
    }

    @Override
    protected String getTopContent() {
        return "<img src='https://horrorpediadotcom.files.wordpress.com/2016/07/mutiny-in-outer-space.jpg?w=441&h=331'>";
    }
}
