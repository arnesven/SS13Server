package graphics;

import graphics.sprites.Sprite;

import java.util.ArrayList;
import java.util.List;

public class BackgroundSprites {

    private List<Sprite> space = generateSpaceSprites();
    private List<Sprite> planet = generatePlanetSprites();

    private List<Sprite> generatePlanetSprites() {
        List<Sprite> lst = new ArrayList<>();
        int counter = 0;
        int row = 23;
        int col = 24;
        while (counter < 15) {
            lst.add(new Sprite("planetbackground" + counter++, "floors.png", col, row, null));
            col++;
            if (col == 30) {
                col = 0;
                row++;
            }
        }
        return lst;
    }

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
