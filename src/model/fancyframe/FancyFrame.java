package model.fancyframe;

import graphics.sprites.SpriteManager;
import model.GameData;
import model.Player;
import model.items.NoSuchThingException;
import model.items.general.Tools;
import util.HTMLText;
import util.Logger;

import java.io.Serializable;

public class FancyFrame implements Serializable {
    private static final String BLANK_DATA = "(none)<part>NO INPUT<part>BLANK<part>300:250";
    private int state = 0;
    private String data;
    private int width = 300;
    private int height = 250;

    public FancyFrame(FancyFrame old) {
        if (old != null) {
            old.beingDisposed();
            this.state = old.state + 1;
            data = BLANK_DATA;
        }
    }

    public int getState() {
        return state;
    }

    public String getData() {
        return data;
    }

    protected void setData(String title, boolean hasInput, String html) {
        data = title + "<part>" + (hasInput ? "HAS INPUT" : "NO INPUT") + "<part>" + html + "<part>" + width + ":" + height;
        state++;
    }

    public void handleEvent(GameData gameData, Player player, String event) {
        Logger.log("Fancy frame handling event " + event);
        if (event.contains("DISMISS")) {
            state++;
            data = BLANK_DATA;
            beingDisposed();
        }
    }

    public void handleInput(GameData gameData, Player player, String data) {
        Logger.log("Fancy frame handling input \"" + data + "\"");
    }

    public void handleClick(GameData gameData, Player player, int x, int y) {
        Logger.log("Fancy frame handling click x=" + x + " y=" + y);
    }

    protected void setState(int state) {
        this.state = state;
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }


    protected void beingDisposed() {

    }

    protected void readyThePlayer(GameData gameData, Player player) {
        try {
            gameData.setPlayerReady(gameData.getClidForPlayer(player), true);
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
    }

    public void rebuildInterface(GameData gameData, Player player) { }

    public void doAtEndOfTurn(GameData gameData, Player actor) { }

    public void leaveFancyFrame(GameData gameData, Player pl) {
        pl.setFancyFrame(new FancyFrame(this));
    }

}