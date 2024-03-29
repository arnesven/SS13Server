package model.map.builders;

import model.GameData;
import model.characters.general.AbandonedBotCharacter;
import model.items.general.Tools;
import model.items.suits.SpaceSuit;
import model.items.tools.RepairTools;
import model.map.GameMap;
import model.map.doors.Door;
import model.map.doors.NormalDoor;
import model.map.levels.EmptySpaceLevel;
import model.map.levels.SpecialLevel;
import model.map.rooms.DerelictAirlockRoom;
import model.map.rooms.DerelictRoom;
import model.map.rooms.Room;
import model.npcs.NPC;
import model.npcs.behaviors.MeanderingMovement;
import model.npcs.behaviors.RandomSpeechBehavior;
import model.npcs.robots.RobotNPC;
import model.objects.consoles.ShipsLogsConsole;
import model.objects.consoles.TeleportConsole;
import model.objects.general.AirlockPanel;
import model.objects.general.DerelictPowerSource;

/**
 * Created by erini02 on 15/12/16.
 */
public class DerelictBuilder extends MapBuilder {
    @Override
    protected void buildPart(GameData gameData, GameMap gm) {
        Room derelictBridge = new DerelictRoom(34, "Derelict Bridge", "", 40, 40, 3, 1, new int[]{35}, new Door[]{});
        ShipsLogsConsole capsLog = new ShipsLogsConsole(derelictBridge);
        derelictBridge.addItem(new SpaceSuit());
        derelictBridge.addObject(capsLog);
        gm.createLevel(new SpecialLevel("derelict", "Space"));
        gm.addRoom(derelictBridge, "derelict", "derelict");
        Room derelictHall =  new DerelictRoom(35, "Derelict Hall", "", 41, 41, 1, 5, new int[]{34, 36, 37, 38},
                new Door[]{new NormalDoor(41.5, 41.0, 35, 34)});
        gm.addRoom(derelictHall, "derelict", "derelict");
        Room derelictLab =  new DerelictRoom(36, "Derelict Lab", "", 39, 43, 2, 2, new int[]{35},
                new Door[]{new NormalDoor(41.0, 43.5, 36, 35)});
        derelictLab.addObject(new TeleportConsole(derelictLab));
        gm.addRoom(derelictLab, "derelict", "derelict");
        Room derelictGen =  new DerelictRoom(37, "Derelict Generator", "", 40, 46, 3, 3, new int[]{35},
                new Door[]{new NormalDoor(41.5, 46.0, 37, 35)});

        NPC abandoned = new RobotNPC(new AbandonedBotCharacter(derelictGen), new MeanderingMovement(0),
                new RandomSpeechBehavior("resources/ABANDON.TXT"), derelictGen);
        gameData.addNPC(abandoned);
        derelictGen.addObject(new DerelictPowerSource(derelictGen, gameData));
        derelictGen.addItem(new RepairTools());

        gm.addRoom(derelictGen, "derelict", "derelict");
        DerelictAirlockRoom derelictAirLock =  new DerelictAirlockRoom(38, "Derelict Airlock", "", 42, 44, 1, 1, new int[]{35},
                new Door[]{new NormalDoor(42.0, 44.5, 38, 35)});
        derelictAirLock.addObject(new AirlockPanel(derelictAirLock));
        gm.addRoom(derelictAirLock, "derelict", "derelict");

        Room derelictUnreachableRoom =  new DerelictRoom(39, "Escape Pod", "", 52, 52, 2, 1, new int[]{}, new Door[]{});
        //derelictUnreachableRoom.addObject(new AirlockPanel(derelictUnreachableRoom));
        derelictUnreachableRoom.addItem(new SpaceSuit());
        gm.addRoom(derelictUnreachableRoom, "derelict", "derelict");
        Room derelictUnreachableRoom2 =  new DerelictRoom(40, "Broken Room", "", 35, 35, 1, 2, new int[]{}, new Door[]{});
        gm.addRoom(derelictUnreachableRoom2, "derelict", "derelict");
    }
}
