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
        return "Alien Team";
    }

    @Override
    protected String modeSpecificTableRows(String style) {
        StringBuffer buf = new StringBuffer();
        if (huntMode.getPointsForInfestationExterminated(gameData) > 0) {
            buf.append("<tr><td><b>Infestation Exterminated!</b></td><td " + style + ">" + huntMode.getPointsForInfestationExterminated(gameData) + "</td></tr>");
        } else {
            buf.append("<tr><td>Alive aliens</td><td " + style + ">" + huntMode.getPointsForAliveAliens(gameData) + "</td></tr>");
            buf.append("<tr><td>Eggs remaining</td><td " + style + ">" + huntMode.getPointsForEggs(gameData) + "</td></tr>");
        }
        buf.append("<tr><td>Crew Survived</td><td " + style +">"         + huntMode.getPointsForAliveCrew(gameData)        + "</td></tr>");
        buf.append(getExtraScoringTableRowsHTML());

        return buf.toString();
    }

    private String getExtraScoringTableRowsHTML() {
        return "";
    }

    @Override
    protected String getModeSpecificStatus(Actor value) {
        if (huntMode.isAntagonist(value)) {
            return "<span style='background-color: #22FF22'>Alien</span>";
        }
        return "";
    }


    @Override
    protected String getExtraDeadInfo(Actor value) {
        String alien = "";
        if (huntMode.isAntagonist(value)) {
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
