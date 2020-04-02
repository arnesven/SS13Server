package clientview.animation;

import clientview.components.MapPanel;

public interface MyAnimation {

    public void update(MapPanel comp);

    boolean isDone(MapPanel comp);
}
