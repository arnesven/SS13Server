package model.actions;

import model.GameData;
import model.Player;

import java.util.List;

public interface QuickAction {
    void performQuickAction(GameData gameData, Player performer);
    boolean isValidToExecute(GameData gameData, Player performer);
    List<Player> getPlayersWhoNeedToBeUpdated(GameData gameData, Player performer);
}
