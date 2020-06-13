package model.actions;

import model.GameData;
import model.Player;

import java.util.List;

public interface QuickAction {

    void performQuickAction(GameData gameData, Player performer);
    boolean isValidToExecute(GameData gameData, Player performer);
    List<Player> getPlayersWhoNeedToBeUpdated(GameData gameData, Player performer);


    static void saveActionPoint(GameData gameData, Player player) {
        player.setActionPoints(player.getActionPoints() + 1);
        gameData.getChat().serverInSay("You saved one Action Point (AP).", player);
        if (player.getActionPoints() > 1) {
            gameData.getChat().serverInSay(" You have a total of " + player.getActionPoints() + " AP.", player);
        }
    }

}
