package model.objects.power;

import model.objects.general.PowerConsumer;

import java.io.Serializable;
import java.util.List;

@Deprecated
public interface PowerUpdater extends Serializable {
    double update(double currentPower, double lsPer, double liPer,
                  List<? extends PowerConsumer> ls,
                  List<? extends PowerConsumer> lights,
                  List<? extends PowerConsumer> eq);
}
