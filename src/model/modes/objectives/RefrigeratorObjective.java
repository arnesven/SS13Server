package model.modes.objectives;

import model.GameData;
import model.items.NoSuchThingException;
import model.items.foods.Beer;
import model.items.foods.Vodka;
import model.items.foods.Wine;
import model.items.general.GameItem;
import model.items.general.Locatable;
import model.map.levels.NewAlgiersMapLevel;
import model.map.rooms.Room;
import model.objects.general.Refrigerator;

public class RefrigeratorObjective extends PirateCaptainTraitorObjective {
    private Locatable locatable;

    public RefrigeratorObjective(GameData gameData) {
        super(gameData);
        try {
            this.locatable = gameData.findObjectOfType(Refrigerator.class);
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isCompleted(GameData gameData) {
        return super.isCompleted(gameData) || boozeInNewAlgiers(gameData) >= 20;
    }

    private int boozeInNewAlgiers(GameData gameData) {
        int count = 0;
        for (Room r : gameData.getMap().getRoomsForLevel(NewAlgiersMapLevel.LEVEL_NAME)) {
            for (GameItem it : r.getItems()) {
                if (it instanceof Beer || it instanceof Vodka || it instanceof Wine) {
                    count++;
                }
            }

        }
        return count;
    }

    @Override
    public Locatable getLocatable() {
        return locatable;
    }

    @Override
    protected String thingToDetach() {
        return "Refrigerator";
    }

    @Override
    protected String thingToBringBack() {
        return "all the booze";
    }
}
