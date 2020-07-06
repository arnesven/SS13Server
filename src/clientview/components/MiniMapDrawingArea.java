package clientview.components;

import clientlogic.GameData;
import clientlogic.MouseInteractable;
import clientlogic.Room;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class MiniMapDrawingArea extends JComponent {

    private ArrayList<MiniMapRoom> hitBoxes = new ArrayList<>();

    public MiniMapDrawingArea() {

        this.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {

            }

            @Override
            public void mouseMoved(MouseEvent e) {
                for (MiniMapRoom mr : hitBoxes) {

                    mr.isHoveredOver(e, null);
                }
            }
        });


    }


    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        double mapWidth = GameData.getInstance().getMiniMapWidth()+2;
        double xscale = (getWidth() / (mapWidth));
        double mapHeight = GameData.getInstance().getMiniMapHeight()+2;
        double yscale = (getHeight() / (mapHeight));

        int xOffset = GameData.getInstance().getMiniMapMinX()-1;
        int yOffset = GameData.getInstance().getMiniMapMinY()-1;

        this.hitBoxes = new ArrayList<>();
        int currZ = GameData.getInstance().getCurrentZ() + MapPanel.getZTranslation();
        for (Room r : GameData.getInstance().getMiniMap()) {
            if (r.getZPos() == currZ) {
                int finalX = (int) ((r.getXPos() - xOffset) * xscale);
                int finalY = (int) ((r.getYPos() - yOffset) * yscale);
                if (r.getID() == GameData.getInstance().getCurrentPos()) {
                    g.setColor(Color.YELLOW);
                } else if (GameData.getInstance().isASelectableRoom(r.getID())) {
                    g.setColor(Color.BLUE);
                } else {
                    g.setColor(Color.LIGHT_GRAY);
                }
                g.fillRect(finalX, finalY,
                        (int) (r.getWidth() * xscale), (int) (r.getHeight() * yscale));
                g.setColor(Color.DARK_GRAY);
                g.drawRect(finalX, finalY, (int) (r.getWidth() * xscale), (int) (r.getHeight() * yscale));
                hitBoxes.add(new MiniMapRoom(r, finalX, finalY, (int) (r.getWidth() * xscale), (int) (r.getHeight() * yscale)));
            }
        }
       // g.setColor(Color.YELLOW);
       // g.drawString("Z = " + currZ, 3, getHeight()-6);
        g.setColor(Color.BLACK);
    }


    private class MiniMapRoom extends MouseInteractable {

        private final Room room;

        public MiniMapRoom(Room r, int x, int y, int w, int h) {
            room = r;
            this.setHitBox(x, y, r.getZPos(), w, h);
        }

        @Override
        protected void doOnClick(MouseEvent e) {
            //System.out.println("Clicked on " + room.getName());
        }

        @Override
        protected void doOnHover(MouseEvent e, MapPanel mapPanel) {
            MiniMapDrawingArea.this.setToolTipText(room.getName());
        }
    }
}
