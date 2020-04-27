package model.objects.power;

public interface PowerSupply {

    String getName();

    double getPower(); // How many MW of power this supply supplies

    double getEnergy(); // How much energy (in MWh) which remains for this power supply

    void drainEnergy(double d); // Called when an amount of energy is drained from this power supply

}
