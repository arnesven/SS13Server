package clientlogic;

import clientcomm.MyCallback;
import clientcomm.ServerCommunicator;
import clientview.components.MapPanel;
import clientview.SpriteManager;
import clientview.components.MyPopupMenu;
import util.MyStrings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

public class ClientDoor extends MouseInteractable {

    private final String actionData;
    private final String sprite;
    private double x;
    private double y;
    private double z;
    private String name;


    public ClientDoor(double x, double y, double z, String name, String sprite, String actionData) {
       this.x = x;
       this.y = y;
       this.z = z;
       this.name = name;
       this.actionData = actionData;
       this.sprite = sprite;
    }


    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    protected void setHitBox(int x, int y, int finalW, int finalH) {
        super.setHitBox(x, y, (int)z, finalW, finalH);
    }

    @Override
    protected void doOnClick(MouseEvent e) {
        System.out.println("clicked a door " + getName());
    }

    @Override
    protected void doOnHover(MouseEvent e, MapPanel mapPanel) {

    }

    public void drawYourself(Graphics g, Room room, int xOffset, int yOffset, int xOffPx, int yOffPx) {
        ImageIcon ic = SpriteManager.getSprite(sprite);
        int xpos = (int)((getX()-xOffset) * room.getXScale()) + xOffPx;
        int ypos = (int)((getY()-yOffset) * room.getYScale()) + yOffPx;

        g.drawImage(SpriteManager.getSprite(room.getFloorSpriteBaseName()).getImage(), xpos, ypos, null);
        g.drawImage(ic.getImage(), xpos, ypos, null);

        setHitBox(xpos, ypos, MapPanel.getZoom(), MapPanel.getZoom());
        g.setColor(Color.BLACK);
    }

    public MyPopupMenu getPopupMenu(MouseEvent e) {
        MyPopupMenu pm = new MyPopupMenu(MyStrings.capitalize(getName()), actionData, e) {
            @Override
            public ActionListener getActionListener(String newActionString) {
                return new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        ServerCommunicator.send(GameData.getInstance().getClid() + " NEXTACTION " +
                                "root,Room,Doors,"+newActionString.replace("root,", ""), new MyCallback<String>() {

                            @Override
                            public void onSuccess(String result) {
                                //GameData.getInstance().setNextAction("Searching in " + getName());
                                System.out.println("Success in sending door action!");
                            }

                            @Override
                            public void onFail() {
                                System.out.println("Failed to send NEXTACTION (doors) message to server.");
                            }
                        });

                    }
                };
            }
        };
        return pm;
    }
}
