package model.objects.general;

import model.GameData;
import model.events.ambient.SimulatePower;

/**
 * Created by erini02 on 08/12/16.
 */
public interface PowerConsumer {
    double getPowerConsumption();
    String getTypeName();
    void onPowerOff(GameData gameData);
    void onPowerOn(GameData gameData);
    int getPowerPriority();
    void setPowerPriority(int i);
    void receiveEnergy(GameData gameData, double energy);
    void setPowerSimulation(SimulatePower simulatePower);
}
