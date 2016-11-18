package model.events;

import model.GameData;
import model.modes.OperativesGameMode;
import model.objects.general.NuclearBomb;

/**
 * Created by erini02 on 18/11/16.
 */
public class NukeSetByOperativesEvent extends NukeSetEvent {


    public NukeSetByOperativesEvent(NuclearBomb bomb) {
        super(bomb);
    }

    @Override
    protected void doAfterNuked(GameData gameData) {
        ((OperativesGameMode)gameData.getGameMode()).setNuked(true);
        gameData.setNumberOfRounds(gameData.getNoOfRounds() - 2);
    }
}
