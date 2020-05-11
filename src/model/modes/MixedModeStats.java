package model.modes;

import model.Actor;
import model.GameData;

import javax.swing.table.TableStringConverter;

public class MixedModeStats extends OperativesModeStats {
    private final MixedGameMode mode;
    private final TraitorModeStats traitorStats;
    private final HostModeStats hostStats;
    private final ChangelingModeStats lingStats;

    public MixedModeStats(GameData gameData, MixedGameMode mixedGameMode) {
        super(gameData, mixedGameMode);
        this.mode = mixedGameMode;
        this.traitorStats = new TraitorModeStats(gameData, mode.getTraitorMode());
        this.hostStats = new HostModeStats(gameData, mode.getHostMode());
        this.lingStats = new ChangelingModeStats(gameData, mode.getChangelingMode());
    }

    @Override
    protected String getModeSpecificStatus(Actor value) {
        if (mode.isOperative(value)) {
            return super.getModeSpecificStatus(value);
        } else if (mode.getChangelingMode().getChangeling() == value) {
            return lingStats.getModeSpecificStatus(value);
        } else if (mode.getTraitorMode().isAntagonist(value)) {
            return traitorStats.getModeSpecificStatus(value);
        } else if (mode.getHostMode().getHostPlayer() == value) {
            return hostStats.getModeSpecificStatus(value);
        }
        return super.getModeSpecificStatus(value);
    }

    @Override
    protected String getExtraDeadInfo(Actor value) {
        if (mode.getChangelingMode().getChangeling() == value) {
            return lingStats.getExtraDeadInfo(value);
        } else if (mode.getTraitorMode().isAntagonist(value)) {
            return traitorStats.getExtraDeadInfo(value);
        } else if (mode.getHostMode().getHostPlayer() == value) {
            return hostStats.getExtraDeadInfo(value);
        }

        return super.getExtraDeadInfo(value);
    }

    @Override
    public String getMode() {
        return "Mixed";
    }

    @Override
    public String getOutcome() {
        GameOver ov = mode.getGameResult(gameData);
        if (ov == GameOver.SHIP_NUKED) {
            return "Operative Team Wins!";
        }
        return "Undecided.";
    }

    @Override
    public String getContent() {
        return traitorStats.getContent();
    }

    @Override
    public String getEnding() {
        GameOver ov = mode.getGameResult(gameData);
        if (ov == GameOver.SHIP_NUKED) {
            return "SS13 nuked by operatives";
        } else if (ov == GameOver.TIME_IS_UP) {
            return "Time limit reached";
        } else {
            return "Unknown.";
        }
        //throw new IllegalStateException("Tried to get game outcome before game was over!");
    }


}
