package model.items.tools;

import model.GameData;
import model.actions.characteractions.ProgramDockWorkerBotAction;
import model.actions.general.Action;
import model.actions.general.DoNothingAction;
import model.characters.general.GameCharacter;
import model.items.NoSuchThingException;
import model.items.general.GameItem;
import model.items.general.Radio;
import model.npcs.NPC;
import model.npcs.behaviors.ReadyForCommandsBehavior;
import model.npcs.robots.DockWorkerBot;
import model.npcs.robots.DockWorkerBotCharacter;
import model.objects.consoles.BotConsole;
import model.objects.consoles.Console;

import java.util.ArrayList;
import java.util.List;

public class DockWorkerRadio extends Radio {
    public DockWorkerRadio() {
        super("DockWorker Radio", 150);
    }

    @Override
    protected Console getSpecificConsole(GameData gameData) throws NoSuchThingException {
        return gameData.findObjectOfType(BotConsole.class);
    }

    @Override
    protected List<Action> getSpecificActions(GameData gameData) {
        List<Action> res = new ArrayList<>();
        for (NPC npc : gameData.getNPCs()) {
            if (npc.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof DockWorkerBotCharacter)) {
                if (npc.getActionBehavior() instanceof ReadyForCommandsBehavior) {
                    res.add(new ProgramDockWorkerBotAction(npc, gameData));
                } else {
                    res.add(new DoNothingAction(){
                        @Override
                        public String getName() {
                            return npc.getName() + " (busy)";
                        }
                    });
                }
            }
        }

        return res;
    }

    @Override
    public GameItem clone() {
        return new DockWorkerRadio();
    }
}
