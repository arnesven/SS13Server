package model.map.rooms;

import graphics.ClientInfo;
import graphics.sprites.Sprite;

public class PrisonPlanet extends Room {
    public PrisonPlanet(int i) {
        super(i, "Prison Planet", "P R I S O N P L A N E T",
                0, 0, 10, 10,
                new int[]{30}, new double[]{-1.0, -1.0}, RoomType.outer);
    }

    @Override
    public boolean hasBackgroundSprite() {
        return true;
    }

    @Override
    public Sprite getBackgroundSprite(ClientInfo clientInfo) {
        return new Sprite("prisonplanetback", "prisonplanet.png",
                0, 0,
                240, 180, (clientInfo.getWidth()*60)/100,
                clientInfo.getHeight());
    }
}
