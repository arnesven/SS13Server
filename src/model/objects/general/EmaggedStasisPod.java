package model.objects.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.map.rooms.Room;

/**
 * Created by erini02 on 14/12/16.
 */
public class EmaggedStasisPod extends StasisPod {
    private final Actor emaggedBy;

    public EmaggedStasisPod(Room r, Actor emaggedBy) {
        super(r);
        this.emaggedBy = emaggedBy;
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        if (isVacant()) {
            return super.getSprite(whosAsking);
        } else {
            return new Sprite("emaggedstasispodoccupied", "Cryogenic2.png", 1, 17, this);
        }
    }

    @Override
    public void putIntoPod(GameData gameData, Actor performingClient, String timeChosen) {
        super.putIntoPod(gameData, performingClient, "Forever");
        performingClient.addTolastTurnInfo("This Stasis Pod feels strange...");
    }
}
