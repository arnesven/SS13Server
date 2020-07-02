package shared;

import clientview.strategies.CameraPanEffect;
import clientview.strategies.DrawingStrategy;
import util.MyRandom;

import java.awt.*;

public class StationShakeEffect extends ClientExtraEffect implements CameraPanEffect {

    private final double angle;
    private double freq;
    private int state;
    private double amp;

    public StationShakeEffect() {
        super("stationshake", "", "",
                0, 0, 0, false);
        this.state = 0;
        this.freq = 180.0;
        this.amp = 30.0;
        this.angle = MyRandom.nextDouble()*Math.PI;

    }

    @Override
    protected void findBuddy() {

    }

    @Override
    public void drawYourself(Graphics g) {

    }

    @Override
    public void applyYourself(DrawingStrategy drawingStrategy) {
        if (amp > 0.0 && freq > 0.0) {
            double distance = (Math.cos(Math.toRadians(state * freq)) * amp);
            int xoff = (int)(Math.cos(angle)*distance);
            int yoff = (int)(Math.sin(angle)*distance);

            drawingStrategy.setExtraXOffset(xoff);
            drawingStrategy.setExtraYOffset(yoff);

            state++;
            //freq -= 10.0;
            amp -= 2.0;
        } else {
            drawingStrategy.setExtraXOffset(0);
            drawingStrategy.setExtraYOffset(0);
        }
    }
}
