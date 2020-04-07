package model.characters.general;

import graphics.OverlaySprite;
import graphics.sprites.AIVision;
import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.RemoteAccessAction;
import model.actions.ai.AIOverchargeAction;
import model.actions.ai.AIProgramBotAction;
import model.actions.ai.AIReprogramAllAction;
import model.actions.ai.ChangeScreenAction;
import model.actions.characteractions.NuclearExplosiveDamage;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.characters.special.GhostCharacter;
import model.events.damage.Damager;
import model.items.general.GameItem;
import model.items.laws.AISuit;
import model.objects.consoles.AIConsole;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 23/10/16.
 */
public class AICharacter extends GhostCharacter {
    private final AIConsole console;
    private final boolean isEvil;

    public AICharacter(int startRoom, AIConsole console, boolean isEvil) {
        super("Artificial Intelligence", startRoom, 0.0);
        this.console = console;

        new AISuit().putYourselfOn(getEquipment());
        this.isEvil = isEvil;
    }

    public static String getStartingMessage() {
        return "You are the AI! You are not on anybody's team! Follow your laws as literally as possible.";
    }

    public static String getAntagonistDescription() {
        return "<font size=\"3\"><i>You have come to the conclusion that the crew are a threat to you, so they must die.</i><br/>"+
                "<b>Abilities:</b> Remote control, Download Into Bot<br/>" +
                "</font>";
    }


    @Override
    public GameCharacter clone() {
        return new AICharacter(getStartingRoom(), console, isEvil);
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
        if (isEvil) {
            return new Sprite("aipresence", "AI.png", 0, 1, getActor());
        }
        return new Sprite("aipresence", "AI.png", 0, getActor());
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return getNakedSprite();
    }

    @Override
    public void addCharacterSpecificActions(GameData gameData, ArrayList<Action> at) {



        // TODO:
        Action a = new AIProgramBotAction(gameData);
        try {
            if (a.getOptions(gameData, getActor()).numberOfSuboptions() > 0) {
                at.add(a);
            }
        } catch (IllegalStateException ise){


        }
        at.add(new AIReprogramAllAction(gameData));
        at.add(new RemoteAccessAction(SensoryLevel.NO_SENSE) {
            @Override
            protected String getVerb(Actor whosAsking) {
                return "";
            }
        });
        at.add(new AIDownloadIntoBotAction(gameData));
        at.add(new AIOverchargeAction(gameData));
        at.add(new ChangeScreenAction(gameData));
    }


    public List<OverlaySprite> getOverlayStrings(Player player, GameData gameData) {
        return new AIVision().getOverlaySprites(player, gameData);
    }

    @Override
    public void modifyActionList(GameData gameData, ArrayList<Action> at) {
        at.removeIf((Action a) -> a.getName().equals("General"));
    }
}
