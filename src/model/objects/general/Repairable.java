package model.objects.general;

import model.GameData;

public interface Repairable {

	boolean isDamaged();

	boolean isBroken();

    void doWhenRepaired(GameData gameData);

}
