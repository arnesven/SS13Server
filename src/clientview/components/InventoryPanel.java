package clientview.components;

import clientcomm.MyCallback;
import clientcomm.ServerCommunicator;
import clientlogic.GameData;
import clientview.SpriteManager;

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
    private Rectangle roomIcons;
    private ArrayList<Rectangle> itemBoxes = new ArrayList<>();
    private ArrayList<Rectangle> roomBoxes = new ArrayList<>();
    private ArrayList<String> roomNames = new ArrayList<>();
    private ArrayList<String> itemNames = new ArrayList<>();
    private ArrayList<String> itemActions = new ArrayList<>();
    private int finalHeightRows;
    private boolean itemsShowing = true;

    public int getHeight() {
        return (finalHeightRows)*MapPanel.getZoom();
    }

    public void drawYourself(Graphics g, int yOffset, int width) {

        drawYou(g, yOffset, width);
        drawHealth(g, yOffset, width);
        drawName(g, yOffset);
        itemBoxes = new ArrayList<Rectangle>();
        itemNames = new ArrayList<String>();
        itemActions = new ArrayList<>();
        drawRoomEffectsAndAbilityIcons(g, yOffset, width);
        int xOffset = drawEquipment(g, yOffset, width);
        drawInventory(g, xOffset, yOffset, width);

    }


    public void mouseHover(MouseEvent e, MapPanel mapPanel) {
        if (boxContains(youBox, e)) {
            mapPanel.setToolTipText("You");
        } else if (boxContains(healthBox, e)) {
            mapPanel.setToolTipText("Life: " + String.format("%1.1f", GameData.getInstance().getHealth()));
        } else if (boxContains(backpackBox, e)) {
            mapPanel.setToolTipText("Inventory - " + GameData.getInstance().getWeight());
        } else if (boxContains(roomIcons, e)) {
            int i = 0;
            for (Rectangle r : roomBoxes) {
                if (boxContains(r, e)) {
                    mapPanel.setToolTipText(roomNames.get(i));
                }
                i++;
            }
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
                                        //GameData.getInstance().setNextAction(newActionString.replace("root,", ""));

                                    }

                                    @Override
                                    public void onFail() {
                                        System.out.println("Failed to send NEXTACTION message to server");
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
        if (backpackBox.contains(e.getPoint())) {
            itemsShowing = !itemsShowing;
            return true;
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

    private void drawYou(Graphics g, int yOffset, int width) {
        finalHeightRows = 1+(int)(Math.ceil((double)((GameData.getInstance().getItems().size()+1) * MapPanel.getZoom())/(double)width));
        g.setColor(Color.BLACK);
        g.fillRect(0, yOffset, width, MapPanel.getZoom());
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
    }

    private void drawHealth(Graphics g, int yOffset, int width) {
        ImageIcon img;
        if (GameData.getInstance().getHealth() > 3.0) {
            img = SpriteManager.getSprite("healthover0");
        } else if (GameData.getInstance().getHealth() == 0) {
            img = SpriteManager.getSprite("healthdead0");
        } else {
            img = SpriteManager.getSprite("health" + (int)(GameData.getInstance().getHealth()*10.0) + "0");
        }

        g.drawImage(img.getImage(), img.getIconWidth(), yOffset, null);
        healthBox = new Rectangle(img.getIconWidth(), yOffset, img.getIconWidth(), img.getIconHeight());
    }

    private void drawName(Graphics g, int yOffset) {
        g.setFont(new Font("Arial", Font.ITALIC, 14*(MapPanel.getZoom()/32)));
        g.setColor(Color.YELLOW);
        int paren = GameData.getInstance().getCharacter().indexOf("(");
        if (paren == -1) {
            g.drawString(GameData.getInstance().getCharacter(), MapPanel.getZoom() * 2, yOffset + g.getFontMetrics().getHeight() - 5);
        } else {
            g.drawString(GameData.getInstance().getCharacter().substring(0, paren), MapPanel.getZoom() * 2, yOffset + g.getFontMetrics().getHeight() - 5);
            g.drawString(GameData.getInstance().getCharacter().substring(paren), MapPanel.getZoom()*2, yOffset+2*g.getFontMetrics().getHeight()-5);
        }

    }

    private void drawRoomEffectsAndAbilityIcons(Graphics g, int yOffset, int width) {
        int index = 0;
        for (String s : GameData.getInstance().getRoomInfo()) {
            String strs[] = s.split("<img>");
            if (!strs[1].contains("You")) {
                int iconWidth = MapPanel.getZoom();
                SpriteManager.drawSprite(strs[0], g, width - MapPanel.getZoom()*++index, yOffset);
                Rectangle hitbox = new Rectangle(width - index*iconWidth, yOffset, iconWidth, iconWidth);
                roomBoxes.add(hitbox);
                itemBoxes.add(hitbox);
                roomNames.add(strs[1]);
                itemNames.add(strs[1]);
                itemActions.add(strs[2]);
            }
        }
        roomIcons = new Rectangle(width-MapPanel.getZoom()*index, yOffset,
                MapPanel.getZoom()*index,
                MapPanel.getZoom());

    }


    private int drawEquipment(Graphics g, int yOffset, int width) {
        int newY = yOffset + MapPanel.getZoom();
        ImageIcon helmet = SpriteManager.getSprite("helmeteqslot0");
        g.drawImage(helmet.getImage(), 0, newY, null);

        ImageIcon gloves = SpriteManager.getSprite("gloveseqslot0");
        g.drawImage(gloves.getImage(), helmet.getIconWidth(), newY, null);

        ImageIcon suit = SpriteManager.getSprite("suiteqslot0");
        g.drawImage(suit.getImage(), 2*gloves.getIconWidth(), newY, null);

        ImageIcon boots = SpriteManager.getSprite("bootseqslot0");
        g.drawImage(boots.getImage(), 3*gloves.getIconWidth(), newY, null);



        int startX = 0;
        for (String eqItem : GameData.getInstance().getEquipment()) {
            String[] parts = eqItem.split("<img>");
            ImageIcon pic = SpriteManager.getSprite(parts[0]);
            g.drawImage(pic.getImage(), startX, newY, null);
            itemBoxes.add(new Rectangle(startX, newY, pic.getIconWidth(), pic.getIconHeight()));
            itemNames.add(parts[1]);
            itemActions.add(parts[2]);
            startX += pic.getIconWidth();
        }

        return 4*gloves.getIconWidth();
    }


    private void drawInventory(Graphics g, int xOffset, int yOffset, int width) {
        ImageIcon frame = SpriteManager.getSprite("uiframe0");
        int newY = yOffset + MapPanel.getZoom();
        ImageIcon backpack = SpriteManager.getSprite("backpack0");
        g.drawImage(backpack.getImage(), xOffset, newY, null);
        backpackBox = new Rectangle(xOffset, newY,
                backpack.getIconWidth(), backpack.getIconHeight());


        if (itemsShowing) {
            int startX = backpack.getIconWidth() + xOffset;
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
                                 //GameData.getInstance().setNextAction(newActionString.replace("root,", "")+","+itemName);
                             }

                             @Override
                             public void onFail() {
                                 System.out.println("Failed to send INVENTORYACTION to server");
                             }
                         });
                     }
                 };
             }
         };
         mpm.showYourself();
    }


}
