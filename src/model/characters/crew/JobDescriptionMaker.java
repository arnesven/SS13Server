package model.characters.crew;

import model.items.general.GameItem;
import model.items.general.MoneyStack;

import java.util.ArrayList;
import java.util.List;

public class JobDescriptionMaker {
    private final String description;
    private final String abilities;
    private final String type;
    private final String startingItems;

    public JobDescriptionMaker(String type, List<GameItem> startingItems, String description, String abilities) {
        this.type = type;
        this.startingItems = makeIntoStringList(startingItems);
        this.description = description;
        this.abilities = abilities;
    }

    private String makeIntoStringList(List<GameItem> startingItems) {
        StringBuilder result = new StringBuilder();
        List<GameItem> things = new ArrayList<>();
        things.addAll(startingItems);
        things.removeIf((GameItem it) -> it instanceof MoneyStack);
        for (int i = 0; i < things.size(); ++i) {
            result.append(startingItems.get(i).getBaseName());
            if (i + 1 < startingItems.size()) {
                result.append(", ");
            }
        }
        return result.toString();
    }

    public String makeString() {
        return "<font size=\"3\"><b>Type: " + type + "</b><br/><b>Gear:</b> " + startingItems +"<br/><i>" + description + "</i><br/>" + "<b>Abilities:</b> " + abilities + "</font>";
    }
}
