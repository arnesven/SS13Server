package comm.chat;

import model.GameData;
import model.GameState;
import model.Player;
import model.Talking;

public class InCharacterSayChatHandler extends ChatCommandHandler {
    public InCharacterSayChatHandler() {
        super("insay");
    }

    @Override
    protected void internalHandle(GameData gameData, Player sender, String rest) {
        String whatWasSaid = rest.replace("/insay ", "");
        if (gameData.getGameState() != GameState.PRE_GAME) {
            gameData.getChat().inCharacterSay(sender, whatWasSaid);
            Talking.decorateWithTalk(gameData, sender, whatWasSaid);
        }
    }
}
