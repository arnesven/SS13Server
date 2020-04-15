package comm.chat;

import model.*;

public class InCharacterSayChatHandler extends ChatCommandHandler {
    public InCharacterSayChatHandler() {
        super("insay");
    }

    @Override
    protected void internalHandle(GameData gameData, Player sender, String rest) {
        String whatWasSaid = rest.replace("/insay ", "");
        if (gameData.getGameState() != GameState.PRE_GAME) {
            if (sender.isDead()) {
                gameData.getChat().serverInSay("You try to talk, but you're dead, so you only manage to utter a faint whisper.", sender);
                for (Actor a : sender.getPosition().getActors()) {
                    if (a instanceof Player) {
                        if (!a.isDead()) {
                            gameData.getChat().serverInSay("You hear a faint whisper", (Player)a);
                        } else {
                            gameData.getChat().serverInSay(sender.getPublicName(a) + " whispered \"" + whatWasSaid + "\"", (Player)a);
                        }
                    }

                }
            } else {
                gameData.getChat().inCharacterSay(sender, whatWasSaid);
                Talking.decorateWithTalk(gameData, sender, whatWasSaid);
            }
        }
    }
}
