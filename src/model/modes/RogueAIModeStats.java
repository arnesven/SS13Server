package model.modes;

import model.Actor;
import model.GameData;

public class RogueAIModeStats extends GameStats {
    private final RogueAIMode mode;

    public RogueAIModeStats(RogueAIMode rogueAIMode, GameData gameData) {
        super(gameData, rogueAIMode);
        this.mode = rogueAIMode;
    }

    @Override
    protected String getModeSpecificStatus(Actor value) {
        return value == mode.getAIPlayer()? "<span style='background-color: #FF0000'>Rogue AI</span>" : "";
    }

    @Override
    protected String getExtraDeadInfo(Actor value) {
        if (mode.getAIPlayer() == value) {
            return "(Rogue AI)";
        }
        return "";
    }

    @Override
    protected String modeSpecificExtraInfo(Actor value) {
        if (mode.getAIPlayer() == value) {
            return "<i>(Decoy: " + mode.getDecoy().getBaseName() + ")</i>";
        }
        return "";
    }

    @Override
    public String getMode() {
        return "Rogue AI";
    }

    @Override
    public String getOutcome() {
        GameOver ov = mode.getGameResultType(gameData);
       if (ov == GameOver.ANTAGONISTS_DEAD) {
            return "Crew Team Wins!";
        }
        return "Rogue AI Wins!";
    }

    @Override
    public String getEnding() {
        GameOver ov = mode.getGameResultType(gameData);
        if (ov == GameOver.ANTAGONISTS_DEAD) {
            return "AI Disabled";
        } else if (ov == GameOver.TIME_IS_UP) {
            return "Time limit reached";
        } else if (ov == GameOver.PROTAGONISTS_DEAD) {
            return "Crew Dead";
        }
        throw new IllegalStateException("Tried to get game outcome before game was over!");
    }




    @Override
    public String getContent() {
        return "";
    }
}
