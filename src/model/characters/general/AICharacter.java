package model.characters.general;

import graphics.sprites.OverlaySprites;
import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.RemoteAccessAction;
import model.actions.characteractions.AIProgramBotAction;
import model.actions.characteractions.AIReprogramAllAction;
import model.actions.characteractions.NuclearExplosiveDamage;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.actions.general.WatchAction;
import model.characters.special.GhostCharacter;
import model.events.damage.Damager;
import model.items.general.GameItem;
import model.items.laws.AISuit;
import model.items.weapons.Weapon;
import model.objects.consoles.AIConsole;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 23/10/16.
 */
public class AICharacter extends GhostCharacter {
    private final AIConsole console;

    public AICharacter(int startRoom, AIConsole console) {
        super("Strong AI", startRoom, 0.0);
        this.console = console;
        putOnSuit(new AISuit());
    }

    public static String getStartingMessage() {
        return "You are the AI! You are not on anybody's team! Follow your laws as literally as possible.";
    }



    @Override
    public GameCharacter clone() {
        return new AICharacter(getStartingRoom(), console);
    }



    @Override
    public List<GameItem> getItems() {
        List<GameItem> list = new ArrayList<>();
        list.addAll(console.getLaws());
        return list;
    }

    public void beExposedTo(Actor something, Damager damager) {
        if (damager instanceof NuclearExplosiveDamage) {
            super.beExposedTo(something, damager);
        }
    }



    @Override
    public int getMovementSteps() {
        if (isDead()) {
            return 0;
        }
        return 3;
    }



    @Override
    public Sprite getNakedSprite() {
        return new Sprite("aipresence", "genetics.png", 25);
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return getNakedSprite();
    }

    @Override
    public void addCharacterSpecificActions(GameData gameData, ArrayList<Action> at) {

        at.removeIf((Action a) -> (!(a instanceof WatchAction)));

        // TODO:
        at.add(new AIProgramBotAction(gameData));
        at.add(new AIReprogramAllAction(gameData));
        at.add(new RemoteAccessAction(SensoryLevel.NO_SENSE) {
            @Override
            protected String getVerb(Actor whosAsking) {
                return "";
            }
        });
        at.add(new AIDownloadIntoBotAction(gameData));
    }


    public List<String> getOverlayStrings(Player player, GameData gameData) {
        return OverlaySprites.seeAIVision(player, gameData);
    }
}
