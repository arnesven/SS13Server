package model.modes;

import model.Actor;
import model.GameData;

/**
 * Created by erini02 on 03/05/16.
 */
class ArmageddonGameStats extends GameStats {
    private ArmageddonGameMode armageddonGameMode;

    public ArmageddonGameStats(ArmageddonGameMode armageddonGameMode, GameData gameData) {
        super(gameData, armageddonGameMode);
        this.armageddonGameMode = armageddonGameMode;
    }

    @Override
    protected String getModeSpecificStatus(Actor value) {
        return armageddonGameMode.getZombieEvent().isZombie(value) ? "<span style='background-color: #22FF22'>Zombie</span>" : "";
    }

    @Override
    protected String getExtraDeadInfo(Actor value) {
        return armageddonGameMode.getZombieEvent().isZombie(value) ? "(Zombie)" : "";
    }

    @Override
    protected String modeSpecificExtraInfo(Actor value) {
        return "";
    }

    @Override
    public String getMode() {
        return armageddonGameMode.getName();
    }

    @Override
    public String getOutcome() {
        GameOver status = armageddonGameMode.getGameResultType(gameData);
        if (status == GameOver.TIME_IS_UP) {
            return "Crew Team Wins!";
        }
        if (status == GameOver.ALL_DEAD) {
            return "Everybody Lost!";
        }

        throw new IllegalStateException("Tried to get game outcome before game was over!");
    }

    @Override
    public String getEnding() {
        GameOver status = armageddonGameMode.getGameResultType(gameData);
        if (status == GameOver.TIME_IS_UP) {
            return "Time limit reached";
        }

        return "All players died";
    }

    @Override
    public String getContent() {
        StringBuffer buf = new StringBuffer("<table>");

        buf.append("<tr><td>Zombie Killer:</td><td>" + armageddonGameMode.getZombieKiller() + "</td></tr>");
        buf.append("</table>");
        return buf.toString();

    }

    @Override
    protected String getTopContent() {
        return "<img src='" + armageddonGameMode.getImageURL() + "'>";
    }
}
