package clientview.animation;

import clientview.MapPanel;

public interface MyAnimation {

    public void update(MapPanel comp);

    boolean isDone(MapPanel comp);
}
