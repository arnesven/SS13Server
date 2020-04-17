package clientview.components;

import clientlogic.ClientDoor;
import clientlogic.GameData;
import clientlogic.Room;
import clientview.OverlaySprite;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

public class MapPanelMouseListener extends MouseAdapter implements MouseMotionListener {
    private final MapPanel mp;
    private Point dragStartPint;

    public MapPanelMouseListener(MapPanel mapPanel) {
        this.mp = mapPanel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if (mp.getInventoryPanel().mouseClicked(e, mp)) {
            return;
        }

        MyPopupMenu mpm = null;
        for (OverlaySprite ps : GameData.getInstance().getOverlaySprites()) {
            if (ps.mouseHitsThis(e)) {
                if (mpm == null) {
                    mpm = ps.getPopupMenu(e);
                } else {
                    mpm.addAll(ps.getPopupMenu(e));
                }

            }
        }


        List<Room> l = new ArrayList<Room>();
        l.addAll(GameData.getInstance().getMiniMap());
        // Check if any door in any room was clicked
        for (Room r : l) {
            if (r.getDoors() != null) {
                for (ClientDoor d : r.getDoors()) {
                    if (d.actOnClick(e)) {
                       return;
                    }
                }
            }
        }


        // Check if any room was clicked
        for (Room r : l) {
            if (r.actOnClick(e)) {
                if (mpm == null) {
                    r.getPopupMenu().show(e.getComponent(), e.getX(), e.getY());
                } else {
                    mpm.addAll(r.getPopupMenu());
                }
            }
        }

        if (mpm != null) {
            mpm.showYourself();
            return;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
       // System.out.println("Dragging " + e.getPoint());
        if (dragStartPint != null) {
            MapPanel.addXTranslation((int) (e.getX() - dragStartPint.getX()));
            MapPanel.addYTranslation((int) (e.getY()- dragStartPint.getY()));
            dragStartPint = e.getPoint();
        }
        mp.repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.dragStartPint = e.getPoint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

        List<Room> l = new ArrayList<Room>();
        l.addAll(GameData.getInstance().getMiniMap());
        for (Room r : l) {
            r.isHoveredOver(e, mp);
        }
        for (OverlaySprite os : GameData.getInstance().getOverlaySprites()) {
            os.isHoveredOver(e, mp);
        }
        if (mp.getInventoryPanel() != null) {
            mp.getInventoryPanel().mouseHover(e, mp);
        }

    }

}
