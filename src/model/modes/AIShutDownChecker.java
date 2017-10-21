package model.modes;

import model.Actor;
import model.GameData;
import model.actions.general.SensoryLevel;
import model.items.NoSuchThingException;
import model.objects.consoles.AIConsole;
import model.objects.general.GameObject;

public class AIShutDownChecker extends model.events.Event {
    @Override
    public void apply(GameData gameData) {
        try {
            AIConsole console = gameData.findObjectOfType(AIConsole.class);
            if (console.isShutDown()) {
                RogueAIMode mode = (RogueAIMode)gameData.getGameMode();
                mode.getAIPlayer().getCharacter().setHealth(0.0);
                mode.getAIPlayer().getCharacter().setKiller(console.getShutdowner());
            }
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String howYouAppear(Actor performingClient) {
        return "";
    }

    @Override
    public SensoryLevel getSense() {
        return SensoryLevel.NO_SENSE;
    }
}
