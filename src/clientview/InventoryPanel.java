package clientview;

import clientcomm.MyCallback;
import clientcomm.ServerCommunicator;
import clientlogic.GameData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class InventoryPanel {
    //public static final int INVENTORY_PANEL_HEIGHT = 64;
    private Rectangle youBox;
    private Rectangle healthBox;
    private Rectangle backpackBox;
    private ArrayList<Rectangle> itemBoxes = new ArrayList<>();
    private ArrayList<String> itemNames = new ArrayList<>();
    private ArrayList<String> itemActions = new ArrayList<>();
    private int finalHeightRows;

    public void drawYourself(Graphics g, int yOffset, int width) {

            finalHeightRows = 1+(int)(Math.ceil((double)((GameData.getInstance().getItems().size()+1) * 32)/(double)width));
            g.setColor(Color.BLACK);
            g.fillRect(0, yOffset, width, getHeight());
            ImageIcon frame = SpriteManager.getSprite("uiframe0");
            g.drawImage(frame.getImage(), 0, yOffset, null);

            for (String s : GameData.getInstance().getRoomInfo()) {
                String strs[] = s.split("<img>");
                if (strs[1].contains("You")) {
                    g.drawImage(SpriteManager.getSprite(strs[0]).getImage(), 0, yOffset, null);
                    break;
                }
            }
            youBox = new Rectangle(0, yOffset, frame.getIconWidth(), frame.getIconHeight());

            ImageIcon img;
            if (GameData.getInstance().getHealth() > 3.0) {
                img = SpriteManager.getSprite("healthover0");
            } else if (GameData.getInstance().getHealth() == 0) {
                img = SpriteManager.getSprite("healthdead0");
            } else {
                img = SpriteManager.getSprite("health" + (int)(GameData.getInstance().getHealth()*10.0) + "0");
            }

            g.drawImage(img.getImage(), frame.getIconWidth(), yOffset, null);
            healthBox = new Rectangle(frame.getIconWidth(), yOffset, img.getIconWidth(), img.getIconHeight());

            g.setFont(new Font("Arial", Font.ITALIC, 14));
            g.setColor(Color.YELLOW);
            g.drawString(GameData.getInstance().getCharacter(), img.getIconWidth()*2, yOffset+g.getFontMetrics().getHeight()-5);
            g.drawString(GameData.getInstance().getSuit(), img.getIconWidth()*2, yOffset+2*g.getFontMetrics().getHeight()-5);

            int newY = yOffset + img.getIconHeight();
            ImageIcon backpack = SpriteManager.getSprite("backpack0");
            g.drawImage(backpack.getImage(), 0, newY, null);
            backpackBox = new Rectangle(0, newY,
                    backpack.getIconWidth(), backpack.getIconHeight());

            int startX = backpack.getIconWidth();
            itemBoxes = new ArrayList<Rectangle>();
            itemNames = new ArrayList<String>();
            itemActions = new ArrayList<>();
            for (String item : GameData.getInstance().getItems()) {
                if (item.contains("<img>")) {
                    String[] parts = item.split("<img>");
                    ImageIcon itemPic = SpriteManager.getSprite(parts[0]);
                    g.drawImage(frame.getImage(), startX, newY, null);
                    g.drawImage(itemPic.getImage(), startX, newY, null);
                    itemBoxes.add(new Rectangle(startX, newY, itemPic.getIconWidth(), itemPic.getIconHeight()));
                    itemNames.add(parts[1]);
                    itemActions.add(parts[2]);
                    startX += itemPic.getIconWidth();
                    if (startX > width - itemPic.getIconWidth()) {
                        startX = 0;
                        newY += itemPic.getIconHeight();
                    }
                } else {
                    System.err.println("Weird item: " + item);
                }
            }
        }

    public void mouseHover(MouseEvent e, MapPanel mapPanel) {
        if (boxContains(youBox, e)) {
            mapPanel.setToolTipText("You");
        } else if (boxContains(healthBox, e)) {
            mapPanel.setToolTipText("Life: " + String.format("%1.1f", GameData.getInstance().getHealth()));
        } else if (boxContains(backpackBox, e)) {
            mapPanel.setToolTipText("Inventory - " + GameData.getInstance().getWeight());
        } else {
            int i = 0;
            for (Rectangle r : itemBoxes) {
                if (boxContains(r, e)) {
                    mapPanel.setToolTipText(itemNames.get(i));
                }
                i++;
            }
        }

    }

    private boolean boxContains(Rectangle box, MouseEvent e) {
        if (box != null) {
            if (box.contains(e.getPoint())) {
                return true;
            }
        }
        return false;
    }

    public boolean mouseClicked(MouseEvent e, MapPanel mapPanel) {
        if (youBox.contains(e.getPoint())) {
            if (e.getButton() == MouseEvent.BUTTON3) {
                MyPopupMenu mpm = new MyPopupMenu("All Actions", GameData.getInstance().getActionTree(), e) {
                    @Override
                    public ActionListener getActionListener(String newActionString) {
                        return new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                ServerCommunicator.send(GameData.getInstance().getClid() + " NEXTACTION " +
                                        newActionString, new MyCallback<String>() {

                                    @Override
                                    public void onSuccess(String result) {
                                        GameData.getInstance().setNextAction(newActionString.replace("root,", ""));

                                    }
                                });
                            }
                        };
                    }
                };
                mpm.showYourself();
                return true;

            }
        }
        int i = 0;
        for (Rectangle r : itemBoxes) {
            if (boxContains(r, e)) {
                makeInventoryActionMenu(e, itemNames.get(i), itemActions.get(i));
                return true;
            }
            i++;
        }

        return false;
    }

    private void makeInventoryActionMenu(MouseEvent e, String itemName, String actionData) {
         MyPopupMenu mpm = new MyPopupMenu(itemName, actionData, e)  {

             @Override
             public ActionListener getActionListener(String newActionString) {
                 return new ActionListener() {
                     @Override
                     public void actionPerformed(ActionEvent e) {
                         ServerCommunicator.send(GameData.getInstance().getClid() + " INVENTORYACTION " +
                                 newActionString + "," + itemName, new MyCallback<String>() {

                             @Override
                             public void onSuccess(String result) {
                                 GameData.getInstance().setNextAction(newActionString.replace("root,", "")+","+itemName);

                             }
                         });
                     }
                 };
             }
         };
         mpm.showYourself();
    }


    public int getHeight() {
        return (finalHeightRows)*32;
    }
}
