package clientview;

import clientcomm.MyCallback;
import clientcomm.ServerCommunicator;
import clientlogic.GameData;
import clientlogic.MouseInteractable;
import clientlogic.Room;
import clientview.animation.AnimationHandler;
import util.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

public class OverlaySprite extends MouseInteractable {


    private final String name;
    private final String actionData;
    private final int frames;
    private final int roomid;
    private String sprite;
    private double x;
    private double y;


    public OverlaySprite(String sprite, double x, double y, String name, String actionData, int frames, int roomid) {
        this.sprite = sprite;
        this.x = x;
        this.y = y;
        this.name = name;
        this.actionData = actionData;
        this.frames = frames;
        this.roomid = roomid;
    }

    public void drawYourself(Graphics g, int xOffset, int yOffset, int xOffPx, int yOffPx) {
         int finalX = (int)((x - xOffset) * Room.getXScale()) + xOffPx;
         int finalY = (int)((y - yOffset) * Room.getYScale()) + yOffPx;
      //  System.out.println("Frames for " + sprite + ": " + frames + ", size= " + image.getIconWidth() + "x" + image.getIconHeight());
        drawAt(g, finalX, finalY);

    }

    private void drawAt(Graphics g, int finalX, int finalY) {
        ImageIcon image = SpriteManager.getSprite(sprite);
        if (this.frames == 1) {
            g.drawImage(image.getImage(), finalX, finalY, null);
            setHitBox(finalX, finalY, image.getIconWidth(), image.getIconHeight());
        } else {
            int state = AnimationHandler.getState() % (frames);
            if (state < frames) {
                g.drawImage(image.getImage(), finalX, finalY, finalX + 32, finalY + 32,
                        state * 32, 0, (state + 1) * 32, 32, null);
            }
        }
    }


    public void drawYourselfInRoom(Graphics g, Room r, Pair<Double, Double> slotPos, int xOffset, int yOffset, int xOffPx, int yOffPx) {
        int finalX = (int)((slotPos.first + r.getXPos() - xOffset) * Room.getXScale()) + xOffPx;
        int finalY = (int)((slotPos.second + r.getYPos() - yOffset) * Room.getYScale()) + yOffPx;
        drawAt(g, finalX, finalY);
    }

    @Override
    protected void doOnClick(MouseEvent e) {
        if (!name.equals("Unknown")) {
           MyPopupMenu mpm = getPopupMenu(e);
           mpm.showYourself();
        }
    }

    public MyPopupMenu getPopupMenu(MouseEvent e) {
        MyPopupMenu mpm = new MyPopupMenu(name, actionData, e){

            @Override
            public ActionListener getActionListener(String newActionString) {
                return new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ServerCommunicator.send(GameData.getInstance().getClid() + " OVERLAYACTION " +
                                newActionString + "," + OverlaySprite.this.name, new MyCallback<String>() {

                            @Override
                            public void onSuccess(String result) {
                                GameData.getInstance().setNextAction(newActionString.replace("root,", "") +
                                        "," + OverlaySprite.this.name);
                            }
                        });
                    }
                };
            }
        };
        return mpm;
    }

    @Override
    protected void doOnHover(MouseEvent e, MapPanel mapPanel) {
        mapPanel.setToolTipText(name);
    }

    public int getRoomid() {
        return roomid;
    }

    public int getHash() {
        int sum = 0;
        for (int i = 0; i < name.length(); ++i) {
            sum += name.charAt(i);
        }
        return sum;
    }
}
