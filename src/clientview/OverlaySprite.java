package clientview;

import clientcomm.MyCallback;
import clientcomm.ServerCommunicator;
import clientlogic.GameData;
import clientlogic.MouseInteractable;
import clientlogic.Room;
import clientview.animation.AnimationHandler;
import clientview.components.MapPanel;
import clientview.components.MyPopupMenu;
import util.Pair;

import javax.naming.directory.Attribute;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

public class OverlaySprite extends MouseInteractable {

    private final String name;
    private final String actionData;
    private int frames;
    private int currentFrame = 0;
    private boolean looping;
    private final int roomid;
    private String sprite;
    private double x;
    private double y;
    private double z;
    private int width;
    private int height;
    private int frameShift = 0;


    public OverlaySprite(String sprite, double x, double y, double z, int width, int height,
                         String name, String actionData, int frames, int roomid, boolean looping) {
        this.sprite = sprite;
        this.x = x;
        this.y = y;
        this.z = z;
        this.width = width;
        this.height = height;
        this.name = name;
        this.actionData = actionData;
        this.frames = frames;
        this.looping = looping;
        this.roomid = roomid;
    }

    public void drawYourself(Graphics g, int xOffset, int yOffset, int xOffPx, int yOffPx, int currentZ) {
         int finalX = (int)((x - xOffset) * Room.getXScale()) + xOffPx;
         int finalY = (int)((y - yOffset) * Room.getYScale()) + yOffPx;
      //  System.out.println("Frames for " + sprite + ": " + frames + ", size= " + image.getIconWidth() + "x" + image.getIconHeight());
        drawAt(g, finalX, finalY, currentZ + MapPanel.getZTranslation());

    }

    private void drawAt(Graphics g, int finalX, int finalY, int currZ) {
    //    if (currZ != (int)this.z) {
    //        return;
    //    }
        ImageIcon image = SpriteManager.getSprite(sprite);
        int finalWidth = (int)(width * (MapPanel.getZoom() / 32.0));
        int finalHeight = (int)(height * (MapPanel.getZoom() / 32.0));
        if (this.frames == 1) {
            g.drawImage(image.getImage(), finalX, finalY, null);
        } else {
            int frameNo = 0;
            if (this.looping) {
                frameNo = (AnimationHandler.getState() + frameShift) % (frames);
            } else {
                frameNo = currentFrame;
                if (currentFrame < frames-1) {
                    currentFrame++;
                }
            }
            if (frameNo < frames) {
                g.drawImage(image.getImage(), finalX, finalY, finalX + finalWidth, finalY + finalHeight,
                        frameNo * finalWidth, 0, (frameNo + 1) * finalWidth, finalHeight, null);
            }
        }
        setHitBox(finalX, finalY, currZ, finalWidth, finalHeight);
    }


    public void drawYourselfInRoom(Graphics g, Room r, Pair<Double, Double> slotPos, int xOffset, int yOffset, int xOffPx, int yOffPx, int currZ) {
        int finalX = (int)((slotPos.first + r.getXPos() - xOffset) * Room.getXScale()) + xOffPx;
        int finalY = (int)((slotPos.second + r.getYPos() - yOffset) * Room.getYScale()) + yOffPx;
        drawAt(g, finalX, finalY, currZ);
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
                                //GameData.getInstance().setNextAction(newActionString.replace("root,", "") +
                                //        "," + OverlaySprite.this.name);
                            }

                            @Override
                            public void onFail() {
                                System.out.println("Failed to send OVERLAYACTION to server");
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

    public String getName() {
        return name;
    }

    public String getSprite() {
        return sprite;
    }

    public void setFrameShift(int frameShift) {
        this.frameShift = frameShift;
    }

    public int getFrames() {
        return frames;
    }

    public OverlaySprite copyYourself() {
        return new OverlaySprite(this.sprite, this.x, this.y, this.z, this.width, this.height,
                this.name, this.actionData, this.frames, this.roomid, this.looping);
    }

    public int getZ() {
        return (int)z;
    }
}
