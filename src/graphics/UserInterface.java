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

        Sprite door1 = new Sprite("skeweddoorleft", "newdoor.png", 0, null);
        Sprite door2 = new Sprite("skeweddoorright", "newdoor.png", 1, null);

        Sprite door3 = new Sprite("skeweddoortop", "newdoor.png", 2, null);
        Sprite door4 = new Sprite("skeweddoorbottom", "newdoor.png", 2, 1, null);

        Sprite window1 = new Sprite("skewedwindowtop", "newdoor.png", 0, 1, null);
        Sprite window2 = new Sprite("skewedwindowbottom", "newdoor.png", 0, 2, null);
        Sprite window3 = new Sprite("skewedwindowleft", "newdoor.png", 1, 2, null);
        Sprite window4 = new Sprite("skewedwindowright", "newdoor.png", 2, 2, null);

    }

}
