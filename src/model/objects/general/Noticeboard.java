package model.objects.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.objectactions.WalkUpToObjectAction;
import model.fancyframe.FancyFrame;
import model.fancyframe.NoticeboardFancyFrame;
import model.map.rooms.Room;
import util.MyRandom;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;

public class Noticeboard extends GameObject {
    private final ArrayList<String> notes;

    public Noticeboard(Room position) {
        super("Noticeboard", position);
        this.notes = new ArrayList<>();
        String lastNote;
        try {
            notes.add(MyRandom.getRandomLineFromFile("resources/NOTICEBOARD.TXT"));
            for (int i = 4; i > 0 && MyRandom.nextDouble() < 0.66; --i) {
                do {
                    lastNote = MyRandom.getRandomLineFromFile("resources/NOTICEBOARD.TXT");
                } while (notes.contains(lastNote));
                notes.add(lastNote);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addSpecificActionsFor(GameData gameData, Actor cl, ArrayList<Action> at) {
        super.addSpecificActionsFor(gameData, cl, at);
        if (cl instanceof Player) {
            at.add(new WalkUpToObjectAction(gameData, cl, this, "Examine") {
                @Override
                protected FancyFrame getFancyFrame(Actor performingClient, GameData gameData, GameObject someObj) {
                    return new NoticeboardFancyFrame((Player)performingClient, gameData, Noticeboard.this);
                }
            });
        }
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        if (notes.size() < 5) {
            return new Sprite("noticeboard" + notes.size(), "stationobjs.png", 223 + notes.size(), this);
        }
        return new Sprite("noticeboardmany", "stationobjs.png", 228, this);
    }

    public Collection<? extends String> getNotes() {
        return notes;
    }

    public void addNote(String text) {
        notes.add(text);
    }
}
