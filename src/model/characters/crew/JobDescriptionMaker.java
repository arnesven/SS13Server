package model.characters.crew;

import model.characters.visitors.VisitorCharacter;
import model.items.general.GameItem;
import model.items.general.MoneyStack;
import model.objects.consoles.PersonnelConsole;
import model.objects.consoles.RequisitionsConsole;
import util.HTMLText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JobDescriptionMaker {
    private final String description;
    private final String abilities;
    private final String type;
    private final String startingItems;
    private final String salary;
    private final String initiative;

    public JobDescriptionMaker(CrewCharacter chara, String description, String abilities) {
        this.type = chara.getType();
        this.startingItems = makeIntoStringList(chara.getStartingItems());
        this.description = description;
        this.abilities = abilities;
        this.salary = getSalaryStringFromWage(chara);
        this.initiative = (chara instanceof VisitorCharacter)?"Varies":String.format("%1.1f", chara.getSpeed());

    }



    private String getSalaryStringFromWage(CrewCharacter chara) {
        int wage = PersonnelConsole.getWageForCharacter(chara);
        String[] words = new String[]{"None", "Poor", "Poor", "Poor", "Poor", "Poor",
                "Meager", "Meager", "Meager", "Meager", "Fair", "Fair", "Fair", "Good", "Good", "Good", "Good", "Good",
                "Good", "Good", "Very Good", "Very Good", "Very Good", "Very Good", "Very Good", "Very Good", "Very Good", "Very Good"};
        if (wage >= words.length) {
            return "excellent";
        }
        return words[wage];
    }

    private String makeIntoStringList(List<GameItem> startingItems) {
        StringBuilder result = new StringBuilder();
        List<GameItem> things = new ArrayList<>();
        things.addAll(startingItems);
        things.removeIf((GameItem it) -> it instanceof MoneyStack);
        for (int i = 0; i < things.size(); ++i) {
            result.append(things.get(i).getBaseName());
            if (i + 1 < startingItems.size()) {
                result.append(", ");
            }
        }
        return result.toString();
    }

    public String makeString() {
        return "<font size=\"3\"><b>Type: " + HTMLText.makeText(getColorForType(type), type) + "</b><br/>" +
                (startingItems.equals("")?"":"<b>Gear:</b> " + startingItems +"<br/>") +
                "<i>" + description + "</i><br/>" +
                (abilities.equals("")?"":"<b>Abilities:</b> " + abilities + "<br/>") +
                "<b>Salary:</b> " + salary + "<br/>" +
                "<b>Initiative:</b> " + initiative + "</font>";
    }

    private String getColorForType(String type) {
        Map<String, String> map = new HashMap<>();
        map.put(CrewCharacter.CIVILIAN_TYPE, "black");
        map.put(CrewCharacter.COMMAND_TYPE, "blue");
        map.put(CrewCharacter.SCIENCE_TYPE, "green");
        map.put(CrewCharacter.SECURITY_TYPE, "red");
        map.put(CrewCharacter.SUPPORT_TYPE, "purple");
        map.put(CrewCharacter.TECHNICAL_TYPE, "orange");
        return map.get(type);
    }

    public static String getTraitorDescription() {
        return "<font size=\"3\"><i>You are a disgruntled Nanotrasen employee who has contacted the Syndicate. " +
                "You have received a mission to sabotage, assassinate, steal equipment " +
                "or otherwise wreak havoc on the station. You have a Syndicate "+
                "PDA which can supply you with nasty equipment.</i></font>";
    }
}
