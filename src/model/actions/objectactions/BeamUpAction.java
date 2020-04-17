package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.ActionOption;
import model.actions.general.SensoryLevel;
import model.actions.itemactions.MakeMolotovAndThrowAction;
import model.actions.itemactions.TeleportAction;
import model.characters.general.GameCharacter;
import model.characters.general.HumanCharacter;
import model.characters.general.RobotCharacter;
import model.items.general.Teleporter;
import model.map.GameMap;
import model.map.rooms.Room;
import model.objects.consoles.TeleportConsole;
import util.Logger;
import util.MyMaps;
import util.MyRandom;

import java.util.*;

/**
 * Created by erini02 on 16/09/17.
 */
public class BeamUpAction extends ConsoleAction {
    //private static final int OFFSET = MyRandom.nextInt(100)+1;
    private final TeleportConsole tpconsole;
    private final Map<String, Set<Actor>> actorMap;
    private static final Map<Character, Character> lookup = makeRandomCharTable();
    private static final Map<Character, Character> lookup2 = MyMaps.reverseMap(lookup);



    private Actor selected;

    public BeamUpAction(TeleportConsole teleportConsole, GameData gameData) {
        super("Beam Someone Up", SensoryLevel.OPERATE_DEVICE);
        this.tpconsole = teleportConsole;
        this.actorMap = new HashMap<>();
        for (String level : gameData.getMap().getLevels()) {
            if (!level.equals(GameMap.STATION_LEVEL_NAME)) {
                Set<Actor> actors = new HashSet<>();
                for (Room r : gameData.getMap().getRoomsForLevel(level)) {
                    for (Actor a : r.getActors()) {
                        if (!a.isDead()) {
                            actors.add(a);
                        }
                    }
                }
                if (actors.size() > 0) {
                    Integer[] ints = gameData.getMap().getPositionForLevel(level);
                    actorMap.put(ints[0] + "**-" + ints[1] + "**-" + ints[2] + "**", actors);
                }
            }
        }
    }

    @Override
    protected String getVerb(Actor whosAsking) {
        return "Fiddled with Teletronics";
    }

    @Override
    protected void execute(GameData gameData, Actor performingClient) {
        if (selected != null) {
            Teleporter tele = new Teleporter();
            tele.setMarked(tpconsole.getPosition());
            Action a = new TeleportAction(tele);
            ((TeleportAction) a).setTarget(selected);
            a.doTheAction(gameData, performingClient);
        } else {
            performingClient.addTolastTurnInfo("No such target found...");
        }
    }

    @Override
    public void setArguments(List<String> args, Actor performingClient) {
        String decoded = decode(args.get(1));
        Logger.log("Coded name: " + args.get(1));
        Logger.log("Decoded name: "  + decoded);

        for (Map.Entry<String, Set<Actor>> entry : actorMap.entrySet()) {
            if (entry.getKey().equals(args.get(0))) {
                for (Actor a : entry.getValue()) {
                    if (a.getPublicName().equals(decoded)) {
                        selected = a;
                        return;
                    }
                }
            }
        }


    }

    private String decode(String s) {
        String decoded = new String(s);

        decoded = decoded.replace("Human", "");
        decoded = decoded.replace("Robot", "");
        decoded = decoded.replace("Creature", "");

        StringBuffer result = new StringBuffer();
        for (int i = 0; i < decoded.length(); ++i) {
            char c = decode(decoded.charAt(i));
            result.append(c);
        }


        return result.toString();
    }


    @Override
    public ActionOption getOptions(GameData gameData, Actor whosAsking) {
        ActionOption opts = super.getOptions(gameData, whosAsking);

        for (Map.Entry<String, Set<Actor>> entry : actorMap.entrySet()) {
            ActionOption newOpt = new ActionOption(entry.getKey());

            for (Actor a : entry.getValue()) {
                newOpt.addOption(encode(a));
            }

            opts.addOption(newOpt);
        }

        return opts;
    }

    private String encode(Actor a) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < a.getPublicName().length(); ++i) {
            char c = encode(a.getPublicName().charAt(i));
            buf.append(c);
        }
        if (a.getCharacter().checkInstance((GameCharacter gc) ->  gc instanceof HumanCharacter)) {
            return "Human"+buf.toString();
        } else if (a.getCharacter().checkInstance((GameCharacter gc) ->  gc instanceof RobotCharacter)) {
            return "Robot"+buf.toString();
        }
        return "Creature"+buf.toString();

    }


    private static Map<Character, Character> makeRandomCharTable() {
        String alpha = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz ";
        List<Character> list = new ArrayList<>();
        for (int i = 0; i < alpha.length(); ++i) {
            list.add(alpha.charAt(i));
        }
        Collections.shuffle(list);

        Map<Character, Character> res = new HashMap<>();
        for (int i = 0; i < alpha.length(); ++i) {
            res.put(alpha.charAt(i), list.get(i));
        }
        Logger.log(res.toString());

        return res;
    }

    private char decode(Character c) {

        Character chother = lookup2.get(c);
        if (chother == null) {
            return c;
        }
        return chother;
    }


    private static char encode(Character c) {
        Character chother = lookup.get(c);
        if (chother == null) {
            return c;
        }
        return chother;
    }
}
