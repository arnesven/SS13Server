package model.objects.general;

import graphics.sprites.Sprite;
import model.Player;
import model.items.foods.CustomFoodItem;
import util.Logger;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomDishDesigner implements Serializable {

    private final List<CustomFoodItem> savedDishes;
    private Map<String, Sprite> baseSprites;

    public CustomDishDesigner() {
        this.baseSprites = makeBaseSprites();
        this.savedDishes = new ArrayList<>();
    }




    public List<Sprite> getBaseSprites() {
        List<Sprite> result = new ArrayList<>();
        result.addAll(baseSprites.values());
        return result;
    }

    public List<Sprite> getCondiments(Color color) {
        return makeCondiments(color);
    }

    private List<Sprite> makeCondiments(Color color) {
        List<Sprite> list = new ArrayList<>();

        for (int x = 0; x < 25; ++x) {
            String name = "condiment" + x + "x";
            Sprite sp = new Sprite(name, "custom_food.png", x, 6, null);
            sp.setColor(color);
            list.add(sp);
        }

        return list;
    }


    private static Map<String, Sprite> makeBaseSprites() {
        Map<String, Sprite> result = new HashMap<>();

        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 25; x++) {
                String name = "foodbasex"+x+"y"+y;
                result.put(name+"0", new Sprite(name, "custom_food.png", x, y, null));
            }
        }
        return result;
    }

    public Sprite getTotalSprite(String selectedBase, int condimentNumber, Color condColor, int selectedDeck, int decorationShift) {
        List<Sprite> sprs = new ArrayList<>();
        String name = selectedBase;
        sprs.add(baseSprites.get(selectedBase));
        Sprite condimentSprite = getCondiments(condColor).get(condimentNumber);
        name += condimentSprite.getName();
        sprs.add(condimentSprite);
        Sprite dec = getDecorations().get(selectedDeck).copy();
        dec.shiftUpPx(decorationShift);
        name += dec.getName() + decorationShift;
        sprs.add(dec);

        return new Sprite(name, "human.png", 0, sprs, null);
    }

    public void saveDish(String selectedBase, int condimentNumber, Color condColor, String dishName, int selectedDeck, int decorationShift, Player maker) {
        this.savedDishes.add(new CustomFoodItem(dishName, getTotalSprite(selectedBase, condimentNumber, condColor, selectedDeck, decorationShift), maker));
    }

    public List<CustomFoodItem> getSavedDishes() {
        return savedDishes;
    }

    public List<Sprite> getDecorations() {
        return makeDecorations();
    }

    private static List<Sprite> makeDecorations() {
        List<Sprite> sprs = new ArrayList<>();
        for (int x = 0; x < 25; ++x) {
            sprs.add(new Sprite("decoration" + x, "custom_food.png", x, 4, null));
        }
        return sprs;
    }
}
