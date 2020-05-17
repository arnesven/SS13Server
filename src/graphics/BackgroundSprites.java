package graphics;

import graphics.sprites.Sprite;

import java.util.ArrayList;
import java.util.List;

public class BackgroundSprites {

    private List<Sprite> space = generateSpaceSprites();
    private List<Sprite> planet = generatePlanetSprites();
    private List<Sprite> orbit = generateOrbitSprites();
    private List<Sprite> swooshUp = genereratSwooshUpSprites();
    private List<Sprite> swooshSide = genereratSwooshSideSprites();


    private List<Sprite> generateOrbitSprites() {
        // THIS METHOD ONLY USED FOR TESTING IN CLIENT
        // EXOTIC PLANETS DEFINE THEIR OWN PLANET BACKGROUNDS.
        List<Sprite> lst = new ArrayList<>();
        for (int row = 0; row < 1; row++) {
            for (int col = 0; col < 1; col++) {
                lst.add(new Sprite("orbitplanet-" + col + "-" + row + "-" + 96, "planets.png", col, row, 96, 96, null));
            }
        }

        return lst;
    }

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
        return lst;
    }


    private List<Sprite> genereratSwooshUpSprites() {
        List<Sprite> lst = new ArrayList<>();
        int counter = 0;
        for (int row = 4; row < 13; ++row) {
            for (int col = 0; col <= 25; col++) {
                if (!(col < 4 && row == 4) && !(col > 20 && row == 12)) {
                    lst.add(new Sprite("swooshvertical" + counter++, "space.png", col, row, null));
                }
            }
        }
        System.err.println("vertical: " + counter);
        return lst;
    }

    private List<Sprite> genereratSwooshSideSprites() {
        List<Sprite> lst = new ArrayList<>();
        int counter = 0;
        for (int row = 12; row < 22; ++row) {
            for (int col = 0; col <= 25; col++) {
                if (!(col < 21 & row == 12) && !(col > 11 && row == 25)) {
                    lst.add(new Sprite("swooshhorizontal" + counter++, "space.png", col, row, null));
                }
            }
        }
        System.err.println("horizontal: " + counter);
        return lst;
    }
}
