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
        System.out.println("Clicked!");
        if (mp.getInventoryPanel().mouseClicked(e, mp)) {
            System.out.println("Hit inventorypanel, returning");
            return;
        }

        MyPopupMenu mpm = null;
        for (OverlaySprite ps : GameData.getInstance().getOverlaySprites()) {
            if (ps.mouseHitsThis(e)) {
       //         System.out.println("Mouse hit " + ps.getName());
                if (mpm == null) {
       //             System.out.println("  mpm null, getting new.");
                    mpm = ps.getPopupMenu(e);
                } else {
       //             System.out.println("  adding all.");
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
                    if (d.mouseHitsThis(e)) {
         //               System.out.println("Mouse hit door" + d.getName());
                        if (mpm != null) {
           //                 System.out.println("  adding all.");
                            mpm.addAll(d.getPopupMenu(e));
                        } else {
             //               System.out.println("  mpm null, getting new.");
                            mpm = d.getPopupMenu(e);
                        }
                    }
                }
            }
        }


        // Check if any room was clicked
        for (Room r : l) {
            if (r.actOnClick(e)) {
       //         System.out.println("Mouse hit room" + r.getName());
                if (mpm == null) {
         //           System.out.println("  mpm null, getting new.");
                    mpm = r.getPopupMenu();
                } else {
           //         System.out.println("  adding all.");
                    mpm.addAll(r.getPopupMenu());
                }
            }
        }

        if (mpm != null) {
  //          System.out.println("  mpm wasn't null, showing it!");
            mpm.showYourself();
            return;
        }
        System.out.println("mpm was still null, showing nothing.");
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
        System.out.println("Mouse pressed!");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println("Mouse released");
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
