package model.modes.goals;

import model.GameData;

public class CarryManyThings extends PersonalGoal {
    private final int target;

    public CarryManyThings(int i) {
        target = i;
    }

    @Override
    public String getText() {
        return "Collect " + target + " items.";
    }

    @Override
    public boolean isCompleted(GameData gameData) {
        return getBelongsTo().getItems().size() >= target;
    }
}
