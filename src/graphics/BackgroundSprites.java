package graphics;

import graphics.sprites.Sprite;

import java.util.ArrayList;
import java.util.List;

public class BackgroundSprites {

    private List<Sprite> sprites = generateSpaceSprites();

    private List<Sprite> generateSpaceSprites() {
        List<Sprite> lst = new ArrayList<>();
        int counter = 0;
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 25; col++) {
                if (!(col == 0 & row == 0) && !(col == 24 && row == 3)) {
                    lst.add(new Sprite("spacebackground" + counter++, "space.png", col, row, null));
                }
            }
        }
        System.err.println(counter);
        return lst;
    }
}
