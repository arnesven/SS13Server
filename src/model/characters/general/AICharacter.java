package model.characters.general;

import graphics.OverlaySprite;
import graphics.sprites.AIVision;
import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.ai.AIOverchargeAction;
import model.actions.ai.AIProgramBotAction;
import model.actions.ai.AIReprogramAllAction;
import model.actions.ai.ChangeScreenAction;
import model.events.damage.NuclearExplosiveDamage;
import model.actions.general.Action;
import model.actions.objectactions.AIRemoteAccessAction;
import model.characters.special.GhostCharacter;
import model.events.damage.Damager;
import model.fancyframe.FancyFrame;
import model.fancyframe.SinglePageFancyFrame;
import model.items.general.GameItem;
import model.items.laws.AISuit;
import model.items.weapons.Weapon;
import model.map.rooms.Room;
import model.objects.consoles.AIConsole;
import model.objects.consoles.SecurityCameraConsole;
import util.HTMLText;

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


    public static String getAntagonistDescription() {
        return "<font size=\"3\"><i>You have come to the conclusion that the crew are a threat to you, so they must die.</i><br/>"+
                "<b>Abilities:</b> Remote control, Omnipresence, Download Into Bot<br/>" +
                "</font>";
    }

    public static String getJobDescription() {
       return "<font size=\"3\"><i>You are the station's trusted AI and have complete control over it's functions. You must try to follow Asimovs laws as best you can. But beware, those laws may be modifies!</i><br/>"+
               "<b>Abilities:</b> Remote control, Omnipresence, Download Into Bot<br/>" +
                "</font>";
    }

    public FancyFrame getStartingFancyFrame() {
        return new SinglePageFancyFrame(null, "Important!", HTMLText.makeColoredBackground("#86e0ff",
                HTMLText.makeCentered(getStartingMessage())));
    }

    private String getStartingMessage() {
        return "<br/>You are the AI!<br/><br/>" + HTMLText.makeImage(getNakedSprite()) + "<br/><br/>" +
                "You are not on anybody's team! Follow your laws as literally as possible.<br/><br/>" +
                HTMLText.makeFancyFrameLink("DISMISS", "[OK]");
    }



    @Override
    public GameCharacter clone() {
        return new AICharacter(getStartingRoom(), console, isEvil);
    }



    @Override
    public List<GameItem> getItems() {
        List<GameItem> list = new ArrayList<>();
        list.addAll(console.getLaws());
        list.addAll(console.getAIAbilities());
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

        Action a = new AIProgramBotAction(gameData);
        try {
            if (a.getOptions(gameData, getActor()).numberOfSuboptions() > 0) {
                at.add(a);
            }
        } catch (IllegalStateException ise){


        }
        at.add(new AIReprogramAllAction(gameData));
        at.add(new AIRemoteAccessAction());
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

    @Override
    public Sprite getMugshotSprite(GameData gameData, Actor player) {
        if (isEvil) {
            return new Sprite("aiscreencorrupt", "AI.png", 0, 1, null);
        }
        return new Sprite("aiscreenspritenormal", "AI.png", 0, null);
    }

    @Override
    public String getMugshotName() {
        if (isEvil) {
            return "Rogue AI";
        }
        return super.getMugshotName();
    }

    @Override
    public List<Room> getVisibleMap(GameData gameData) {
        return SecurityCameraConsole.getConnectedCameraRooms(gameData);
    }

    @Override
    public boolean beAttackedBy(Actor performingClient, Weapon weapon, GameData gameData) {
        return false;
    }


}
