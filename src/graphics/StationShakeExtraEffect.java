package graphics;


import model.Actor;
import model.GameData;
import model.Player;

public class StationShakeExtraEffect extends ExtraEffect {
    public StationShakeExtraEffect() {
        super(null, null, null, 0, false);
    }

    @Override
    public String getStringRepresentation(Actor forWhom) {
        return "stationshake";
    }


    public static void shakeForAllPlayers(GameData gameData) {
            for (Player p : gameData.getPlayersAsList()) {
                p.addExtraEffect(new StationShakeExtraEffect());
            }
    }
}
