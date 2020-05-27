package model.modes;

import model.GameData;

public abstract class ScoredModeStats extends GameStats {
    private final ScoredGameMode scoredMode;

    public ScoredModeStats(GameData gameData, ScoredGameMode mode) {
        super(gameData, mode);
        this.scoredMode = mode;
    }


    protected abstract String getAntagonistTeamName();

    protected abstract String modeSpecificTableRows(String style);

    @Override
    public String getOutcome() {
        GameOver status = scoredMode.getGameResult(gameData);
        if (status == GameOver.TIME_IS_UP) {
            int score = scoredMode.getScore(gameData);
            if (score == 0) {
                return "It's a draw!";
            } else if (score > 0) {
                return "Crew Team Wins!";
            } else {
                return getAntagonistTeamName() + " Wins!";
            }
        }
        if (status == GameOver.PROTAGONISTS_DEAD) {
            return getAntagonistTeamName() + " Wins!";
        }
        if (status == GameOver.ALL_DEAD) {
            return "Everybody Lost!";
        }

        throw new IllegalStateException("Tried to get game outcome before game was over!");
    }


    @Override
    public String getEnding() {
        GameOver status = scoredMode.getGameResult(gameData);
        if (status == GameOver.TIME_IS_UP) {
            return "Time limit reached";
        }
        if (status == GameOver.PROTAGONISTS_DEAD) {
            return "Crew team eliminated";
        }
        if (status == GameOver.ALL_DEAD) {
            return "All players died";
        }

        throw new IllegalStateException("Tried to get game outcome before game was over!");
    }

    @Override
    public String getContent() {
        int score =  scoredMode.getScore(gameData);
        String color = "#000000";
        if (score < 0) {
            color ="#DD1111";
        }

        String style = "style='text-align:right'";

        StringBuffer buf = new StringBuffer("<table>");
        buf.append("<tr><td><b>Total Score</b></td><td style='text-align:right;color:"+color+"'><b>" + score              + "</b></td></tr>");
        buf.append(modeSpecificTableRows(style));
        buf.append("<tr><td>Station Fully Powered</td><td " + style +">" + scoredMode.pointsFromPower(gameData)        + "</td></tr>");
        buf.append("<tr><td>Station Cleanliness</td><td " + style +">" + scoredMode.pointsFromDirtyStation(gameData)      + "</td></tr>");
        buf.append("<tr><td>Equipment Destroyed</td><td " + style +">"+ scoredMode.pointsFromBrokenObjects(gameData)    + "</td></tr>");
        buf.append("<tr><td>Fires</td><td " + style +">"              + scoredMode.pointsFromFires(gameData)            + "</td></tr>");
        buf.append("<tr><td>Hull Breaches</td><td " + style +">"      + scoredMode.pointsFromBreaches(gameData)         + "</td></tr>");
        buf.append("<tr><td>Points from God</td><td " + style +">"     + scoredMode.pointsFromGod(gameData)         + "</td></tr>");
        buf.append("<tr><td>Parasites Killed</td><td " + style +">"   + scoredMode.pointsFromParasites(gameData)        + "</td></tr>");
        if (scoredMode.cosmicArtifactFound(gameData) > 0) {
            buf.append("<tr><td>Cosmic Artifact Acquired</td><td " + style + ">" + scoredMode.cosmicArtifactFound(gameData) + "</td></tr>");
        }
        buf.append("<tr><td>Defused Bombs</td><td " + style +">"   + scoredMode.pointsFromBombsDefused(gameData)        + "</td></tr>");
        buf.append("<tr><td>Bad Security</td><td " + style +">"   + scoredMode.pointsFromSecurity(gameData)        + "</td></tr>");
        buf.append("<tr><td>Asteroid Mining</td><td " + style +">"   + scoredMode.pointsFromMining(gameData)        + "</td></tr>");
        buf.append("<tr><td>Crates Sold</td><td " + style + ">" + scoredMode.pointsFromSelling(gameData) + "</td></tr>");
        buf.append("<tr><td>Planets Explored</td><td " + style +">"   + scoredMode.pointsFromExploredPlanets(gameData)        + "</td></tr>");


        int piratePoints = scoredMode.pointsFromPirates(gameData);
        if (piratePoints > 0) {
            buf.append("<tr><td>Pirates killed</td><td " + style + ">" + piratePoints + "</td></tr>");
        }
        int catPoints = scoredMode.pointsFromCat(gameData) ;
        buf.append("<tr><td>Cat " + ((catPoints<0)?"(dead)":"") + " </td><td " + style +">"               + catPoints      + "</td></tr>");
        int tarsPoints = scoredMode.pointsFromTARS(gameData);
        buf.append("<tr><td>TARS " + ((tarsPoints<0)?"(destroyed)":"") + "</td><td " + style +">"              + tarsPoints      + "</td></tr>");
        int chimpPoints = scoredMode.pointsFromChimp(gameData) ;
        buf.append("<tr><td>Chimp " + ((chimpPoints<0)?"(dead)":"") + " </td><td " + style +">"               + chimpPoints      + "</td></tr>");


        buf.append("</table>");
        return buf.toString();
    }

}
