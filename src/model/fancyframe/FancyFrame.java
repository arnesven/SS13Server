package model.fancyframe;

import graphics.sprites.SpriteManager;
import model.items.general.Tools;
import util.HTMLText;
import util.Logger;

import java.io.Serializable;

public class FancyFrame implements Serializable {
    private int state = 0;
    private String data;

    public FancyFrame() {
       // setData("Test", true, HTMLText.makeImage(new Tools().getSprite(null)));
    }

    public int getState() {
        return state;
    }

    public String getData() {
        return data;
    }

    public void setData(String title, boolean hasInput, String html) {
        data = title + "<part>" + (hasInput?"HAS INPUT":"NO INPUT") + "<part>" + html;
        state++;
    }

    public void handleEvent(String event) {
        Logger.log("Fancy frame handling event " + event);
        if (event.contains("DISMISS")) {
            state++;
            data = "BLANK";
        }
    }

    public void handleInput(String data) {
        Logger.log("Fancy frame handling input \"" + data + "\"");
    }

    public void handleClick(int x, int y) {
        Logger.log("Fancy frame handling click x=" + x + " y=" + y);
    }
}
