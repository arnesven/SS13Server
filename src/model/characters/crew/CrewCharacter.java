package model.characters.crew;

import model.characters.general.HumanCharacter;
import model.items.general.GameItem;
import model.items.general.MoneyStack;
import model.items.suits.OutFit;

import java.util.ArrayList;
import java.util.List;

public abstract class CrewCharacter extends HumanCharacter {

    public static String COMMAND_TYPE = "Command";
    public static String SCIENCE_TYPE = "Science";
    public static String SECURITY_TYPE = "Security";
    public static String TECHNICAL_TYPE = "Technical";
    public static String SUPPORT_TYPE = "Support";
    public static String CIVILIAN_TYPE = "Civilian";
    private final String type;

    public CrewCharacter(String name, String type, int startRoom, double speed) {
		super(name, startRoom, speed);
		this.type = type;
		putOnEquipment(new OutFit(this));
	}


    @Override
    public List<GameItem> getStartingItems() {
        List<GameItem> items = new ArrayList<>();
        items.add(new MoneyStack(getStartingMoney()));
        items.addAll(getCrewSpecificItems());
        return items;
    }

    public int getStartingMoney() {
        return 5;
    }

    public abstract List<GameItem> getCrewSpecificItems();

    @Override
    public boolean isCrew() {
        return true;
    }


    public abstract String getJobDescription();

    public String getType() {
        return type;
    }
}
