package graphics;


import model.Actor;

public class StationShakeExtraEffect extends ExtraEffect {
    public StationShakeExtraEffect() {
        super(null, null, null, 0, false);
    }

    @Override
    public String getStringRepresentation(Actor forWhom) {
        return "stationshake";
    }
}
