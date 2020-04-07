package model.objects.consoles;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.objectactions.ScanBrainAction;
import model.items.Brain;
import model.items.NoSuchThingException;
import model.map.rooms.Room;
import model.npcs.behaviors.*;
import model.programs.BotProgram;
import model.programs.BrainBotProgram;
import util.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erini02 on 14/04/16.
 */
public class BotConsole extends Console {

    private List<BotProgram> programs;

    public BotConsole(Room r) {
        super("Robot Console", r);
    }

    public List<BotProgram> getPrograms(GameData gameData, Actor whosAsking) {
        if (programs == null) {
            programs = loadDefaultPrograms(gameData, whosAsking);
        }
        return programs;
    }

    @Override
    public Sprite getNormalSprite(Player whosAsking) {
        return new Sprite("botconsole", "computer2.png", 17, 14, this);
    }

    private static List<BotProgram> loadDefaultPrograms(GameData gameData, Actor whosAsking) {
        ArrayList<BotProgram> bp = new ArrayList<>();

        bp.add(new BotProgram("Anti-Fire",
                new GoTowardsFireMovement(gameData),
                new PutOutFireBehavior(gameData),
                new Sprite("firefighterbot", "robots2.png", 9, 2, null)));
        bp.add(new BotProgram("Friendly", new MeanderingMovement(0.5),
                new RandomSpeechBehavior("resources/CHITCHAT.TXT")));
        bp.add(BotProgram.createHostileProgram(gameData));
        bp.add(new BotProgram("Nurse",
                new MeanderingMovement(1.0),
                new HealOtherBehavior(),
                new Sprite("nursebot", "robots2.png", 10, 4, null)));
        bp.add(new BotProgram("Bodyguard",
                new FollowMeBehavior(whosAsking, gameData),
                new AttackBaddiesBehavior(),
                new Sprite("bodyguardbot", "robots2.png", 14, 4, null)));
        bp.add(new BotProgram("Repair",
                new GoTowardsBrokenMovement(gameData),
                new RepairStuffBehavior(gameData),
                new Sprite("repairbot", "robots2.png", 13, 2, null)));
        try {
            bp.add(new BotProgram("Security",
                    new FollowCriminalBehavior(gameData.findObjectOfType(CrimeRecordsConsole.class)),
                    new ArrestCriminalBehavior(gameData.findObjectOfType(CrimeRecordsConsole.class)),
                    new Sprite("securitybot", "robots2.png", 21, 2, null)));
        } catch (NoSuchThingException e) {
            Logger.log(Logger.CRITICAL, "No crime console on station. Not adding security bot program.");
        }

        return bp;
    }

    @Override
    protected void addConsoleActions(GameData gameData, Actor cl, ArrayList<Action> at) {
        ScanBrainAction sba = new ScanBrainAction(this);

        if (sba.getOptions(gameData, cl).numberOfSuboptions() > 0) {
            at.add(sba);
        }



    }

    public void addProgramFromBrain(GameData gameData, Brain selectedBrain, Actor whosAsking) {
        getPrograms(gameData, whosAsking).add(new BrainBotProgram(selectedBrain.getProgramName(), this, gameData, selectedBrain));
    }

    public void removeProgram(BotProgram program, GameData gameData, Actor whosAsking) {
        getPrograms(gameData, whosAsking).remove(program);
    }
}
