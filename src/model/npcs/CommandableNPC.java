package model.npcs;

import model.GameData;
import model.actions.general.Action;

import java.util.List;

public interface CommandableNPC {
    int getCommandPointCost();
    List<Action> getExtraActionsFor(GameData gameData, NPC npc);
}
