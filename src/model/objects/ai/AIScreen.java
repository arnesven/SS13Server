package model.objects.ai;

import graphics.sprites.Sprite;
import model.Player;
import model.map.rooms.Room;
import model.objects.consoles.AIConsole;
import model.objects.general.GameObject;
import util.MyRandom;

import java.awt.*;
import java.util.ArrayList;

public class AIScreen extends GameObject {
    private final AIConsole console;
    private Point selectedScreen;

    public AIScreen(Room position, AIConsole console) {
        super("AI Screen", position);
        this.console = console;
    }

    @Override
    public void addYourselfToRoomInfo(ArrayList<String> info, Player whosAsking) {
        info.add(0, getScreenSprite(whosAsking).getName() + "<img>" + "AI Screen");
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        return getScreenSprite(whosAsking);
    }

    private Sprite getScreenSprite(Player whosAsking) {
        if (console.isCorrupt()) {
            return new Sprite("aiscreencorrupt", "AI.png", 0, 1);
        } else if (console.isShutDown()) {
            double rand = MyRandom.nextDouble();
            if (rand < 0.33) {
                return new Sprite("aiscreenshutdown", "AI.png", 3, 1);
            } else if (rand < 0.66) {
                return new Sprite("aiscreenshutdown", "AI.png", 4, 1);
            } else {
                return new Sprite("aiscreenshutdown", "AI.png", 25, 3);
            }
        }

        if (selectedScreen != null) {
            return new Sprite("aiselectedscreen" + selectedScreen.x+"x"+selectedScreen.y,
                    "AI.png", selectedScreen.x, selectedScreen.y);
        }

        return new Sprite("aiscreenspritenormal", "AI.png", 0);
    }

    public void setSelectedScreen(Point selectedScreen) {
        this.selectedScreen = selectedScreen;
    }
}
