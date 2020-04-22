package model.actions.characteractions;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.characters.decorators.CharacterDecorator;
import model.characters.general.GameCharacter;
import model.items.NoSuchThingException;
import model.objects.consoles.AIConsole;
import util.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MarriageAction extends Action {

    private Map<String, Pair<Actor, Actor>> pairmap;
    private Actor personA;
    private Actor personB;

    public MarriageAction() {
        super("Perform Marriage", SensoryLevel.SPEECH);
        pairmap = new HashMap<>();
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "married " + personA.getPublicName() + " and " + personB.getPublicName();
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        String first = "husband";
        String second = "husband";
        if (!personA.getCharacter().getGender().equals("man")) {
            first = "wife";
        }
        if (!personB.getCharacter().getGender().equals("man")){
            second = "wife";
        }

        for (Actor a : performingClient.getPosition().getActors()) {
            a.addTolastTurnInfo(performingClient.getPublicName() + " said \"I now pronounce you " + first + " and " + second + ". You may kiss each other!\"");
        }

        personA.setCharacter(new MarriedDecorator(personA.getCharacter(), personB));
        personB.setCharacter(new MarriedDecorator(personB.getCharacter(), personA));
        try {
            gameData.findObjectOfType(AIConsole.class).informOnStation(personA.getPublicName() + " is now married to " +
                    personB.getPublicName() + " - congratulations!", gameData);
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {
        personA = pairmap.get(args.get(0)).first;
        personB = pairmap.get(args.get(0)).second;
    }

    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        ActionOption opts = super.getOptions(gameData, whosAsking);
        for (Actor a : whosAsking.getPosition().getActors()) {
            for (Actor b : whosAsking.getPosition().getActors()) {
                if (!a.equals(b) && !a.equals(whosAsking) && !b.equals(whosAsking) && !a.isDead() && !b.isDead()) {
                    String title = a.getPublicName() + " and " + b.getPublicName();
                    pairmap.put(title, new Pair<>(a, b));
                    opts.addOption(title);
                }
            }
        }

        return opts;
    }

    public class MarriedDecorator extends CharacterDecorator {
        private final Actor marriedTo;

        public MarriedDecorator(GameCharacter character, Actor personA) {
            super(character, "Married to " + personA.getPublicName());
            this.marriedTo = personA;
        }

        public Actor getMarriedTo() {
            return marriedTo;
        }
    }

    @Override
    public Sprite getAbilitySprite() {
        return new Sprite("performmarriageabi", "interface.png", 12, 14, null);
    }
}
