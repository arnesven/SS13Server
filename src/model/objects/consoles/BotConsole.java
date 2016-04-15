package model.objects.consoles;

import model.GameData;
import model.Player;
import model.actions.Action;
import model.map.Room;
import model.npcs.behaviors.*;
import model.objects.general.GameObject;
import model.programs.BotProgram;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by erini02 on 14/04/16.
 */
public class BotConsole extends Console {

    private List<BotProgram> programs;

    public BotConsole(Room r) {
        super("Bot Console", r);
    }

    public List<BotProgram> getPrograms(GameData gameData) {
        if (programs == null) {
            programs = loadDefaultPrograms(gameData);
        }
        return programs;
    }

    private static List<BotProgram> loadDefaultPrograms(GameData gameData) {
        ArrayList<BotProgram> bp = new ArrayList<>();
        bp.add(new BotProgram("Security",
                                new FollowCriminalBehavior(gameData),
                                new ArrestCriminalBehavior(gameData)));

        bp.add(new BotProgram("Nurse",
                new MeanderingMovement(1.0),
                new HealOtherBehavior()));

//        bp.add(new BotProgram("Hostile",
//                new MoveTowardsClosestActorMovement(),
//                new AttackIfPossibleBehavior());
//
        bp.add(new BotProgram("Anti-Fire",
                new GoTowardsFireMovement(gameData),
                new PutOutFireBehavior(gameData)));
//
//        bp.add(new BotProgram("Repair",
//                new GoTowardsBrokenMovement(gameData),
//                new RepairStuffBehavior(gameData)));

        bp.add(new BotProgram("Friendly", new MeanderingMovement(0.5),
                new RandomSpeechBehavior("resources/CHITCHAT.TXT")));

        return bp;
    }

    @Override
    protected void addActions(GameData gameData, Player cl, ArrayList<Action> at) {

    }

    public static BotConsole find(GameData gameData) {
        for (GameObject o : gameData.getObjects()) {
            if (o instanceof BotConsole) {
                return (BotConsole) o;
            }
        }
        throw new NoSuchElementException("Could not find a bot console on the station");
    }
}
