package graphics;

import graphics.sprites.Sprite;
import graphics.sprites.SpriteObject;
import model.Actor;

public class UserInterface {

    private final Sprite uiFrameSprite;

    public UserInterface() {
        uiFrameSprite = new Sprite("uiframe", "interface.png", 7, 2, null);
        uiFrameSprite.setRotation(uiFrameSprite.getRotation());

        int col = 0;
        for (int i = 30; i >= 5; i -= 5) {
            Sprite health = new Sprite("health" + i, "interface.png", col++, 1, null);
        }
        Sprite deadHealth = new Sprite("healthdead", "interface.png", 7, 1, null);
        Sprite overhealth = new Sprite("healthover", "interface.png", 16, 0, null);

        Sprite backpack = new Sprite("backpack", "interface.png", 2, 13, null);
    }

}
