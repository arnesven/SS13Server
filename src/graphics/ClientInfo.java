package graphics;

import java.awt.*;
import java.io.Serializable;

/**
 * Created by erini02 on 21/12/16.
 */
public class ClientInfo implements Serializable {
    private Dimension clientDimension = new Dimension(800, 600);

    public void setDimension(int width, int height) {
        clientDimension = new Dimension(width, height);
    }

    public int getWidth() {
        return clientDimension.width;
    }

    public int getHeight() {
        return clientDimension.height;
    }
}
