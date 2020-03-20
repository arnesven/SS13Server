package clientview.overlays;


import clientview.MapPanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

public class TitledOverlayComponent extends CenteredOverlayComponent {
    private final String title;
    private final boolean disposebutton;
    private final ActionListener onDispose;
    private final ArrayList<OverlayContent> laterDraws;
    private Color titleBackgroundColor = Color.DARK_GRAY;
    private Color titleColor = Color.YELLOW;
    private Color textColor = Color.BLACK;
    private List<OverlayContent> widgets;
    private double TITLE_OFFSET = 30;
    private List<OverlayButton> buttons;
    private double BUTTON_SEP = 10;
    private Rectangle disposeBox;
    private Map<String, OverlayComponentInput> inputs;

    public TitledOverlayComponent(Dimension dimension, String s, boolean disposebutton, MapPanel parent, ActionListener onDispose) {
        super(dimension, parent);
        this.title = s;
        widgets = new LinkedList<>();
        buttons = new LinkedList<>();
        inputs = new HashMap<>();
        this.disposebutton = disposebutton;
        disposeBox = null;
        this.onDispose = onDispose;
        laterDraws = new ArrayList<OverlayContent>();
    }


    @Override
    protected void subDraw(Graphics2D g2d, Point position, MapPanel comp) {


        drawTitle(g2d, position);

        drawTexts(g2d, position, comp);

        drawButtons(g2d, position, comp);

        for (OverlayContent oc : laterDraws) {
            oc.drawYourself(g2d, 0, 0, comp);
        }

        laterDraws.clear();


    }

    private void drawButtons(Graphics2D g2d, Point position, MapPanel comp) {
        int i = 0;
        for (OverlayButton ob : buttons) {

            int xoff = (int)((getSize().width - (buttons.size()*ob.getSize().getWidth()+BUTTON_SEP)) / 2 +
                    i * (ob.getSize().getWidth() + BUTTON_SEP));

            ob.setPosition(new Point(position.x + xoff,
                    position.y + getSize().height - ob.getSize().height - 8));
            ob.drawYourself(g2d, comp);
            i++;
        }
    }


    private void drawTitle(Graphics2D g2d, Point position) {
        int textwidth = g2d.getFontMetrics().stringWidth(title);
        int textheight = g2d.getFontMetrics().getHeight();

        Stroke oldStroke = g2d.getStroke();
        g2d.setColor(titleBackgroundColor);
        g2d.fillRect(position.x, position.y,  (int)getSize().getWidth(), textheight+4);
        g2d.setColor(getBorderColor1());
        g2d.setStroke(getThickStroke());
        g2d.drawRect(position.x, position.y, (int)getSize().getWidth(), textheight+4);
        g2d.setColor(getBorderColor2());
        g2d.setStroke(getThinStroke());
        g2d.drawRect(position.x, position.y, (int)getSize().getWidth(), textheight+4);
        if (disposebutton) {
            disposeBox = new Rectangle(position.x + getSize().width - textheight-4, position.y,
                                                position.x + getSize().width, position.y + textheight+4);

            g2d.drawLine(position.x + getSize().width - textheight-4, position.y,
                    position.x + getSize().width - textheight-4, position.y + textheight+4);
            g2d.setStroke(new BasicStroke(1.0f));
            g2d.drawLine(position.x + getSize().width - textheight-4, position.y,
                    position.x + getSize().width, position.y + textheight+4);
            g2d.drawLine(position.x + getSize().width, position.y,
                    position.x + getSize().width - textheight-4, position.y + textheight+4);


        }
        g2d.setColor(titleColor);
        g2d.drawString(title, position.x + (int)((getSize().getWidth() - textwidth) / 2), position.y + textheight);
        g2d.setStroke(oldStroke);



    }


    public void drawTexts(Graphics2D g2d, Point position, MapPanel comp) {

        int y = (int)(position.getY() + TITLE_OFFSET);

        for (OverlayContent oc : widgets) {
            y +=  oc.drawYourself(g2d, position.x, y, comp);
        }
    }


    public void addText(String s) {
        for (String text : s.split("\n")) {
            widgets.add(new OverlayContent() {
                @Override
                public int drawYourself(Graphics2D g2d, int x, int y, MapPanel comp) {
                    int textheight = g2d.getFontMetrics().getHeight();
                    y += textheight;
                    g2d.setColor(textColor);
                    int textwidth = g2d.getFontMetrics().stringWidth(text);
                    int x2 = x +  (int)((getSize().getWidth() - textwidth) / 2);
                    g2d.drawString(text, x2, y);
                    return textheight + 4;
                }
            });
        }
    }

    public void addComponent(OverlayContent oc) {
        widgets.add(oc);
    }

//    public OverlayContent addIcon(MapPanel playerIcon) {
//        OverlayContent c = new OverlayContent() {
//            @Override
//            public int drawYourself(Graphics2D g2d, int i, int y, MapPanel comp) {
//                int x = i + (int)(getSize().getWidth() - playerIcon.getSprite().getWidth()*playerIcon.getZoom()) / 2;
//                playerIcon.getSprite().drawYourself(g2d, playerIcon.getZoom(), x, y+2);
//                return playerIcon.getSprite().getHeight()*playerIcon.getZoom()+2;
//            }
//        };
//        widgets.add(c);
//        return c;
//    }

    @Override
    protected void onClick(MouseEvent e) {
        for (OverlayButton ob : buttons) {
            ob.handleMousePressed(e);

            ob.handleMouseClick(e);
        }
        for (OverlayComponentInput oci : inputs.values()) {
            oci.setSelected(false);
        }
        for (OverlayComponentInput oci : inputs.values()) {
            oci.handleMouseClick(e);
        }
        if (disposeBox != null) {
            if (disposeBox.contains(e.getPoint())) {
                this.dispose();
            }
        }
    }

    public void dispose() {
        this.onDispose.actionPerformed(new ActionEvent(this, 0, "kajsd"));
    }

    @Override
    public void returnWasHit() {
        if (getButtons().size() == 1) {
            for (OverlayButton b : buttons) {
                b.returnWasHit();
            }
        }
    }

    @Override
    protected void onPressed(MouseEvent e) {
        for (OverlayButton ob : buttons) {
            ob.handleMousePressed(e);
        }
        for (OverlayContent oc : widgets) {
            oc.handleMousePressed(e);
        }
    }

    @Override
    protected void onReleased(MouseEvent e) {
        for (OverlayButton ob : buttons) {
            ob.handleMouseReleased(e);
        }
        for (OverlayContent oc : widgets) {
            oc.handleMouseReleased(e);
        }

    }

    @Override
    protected void onMouseMove(MouseEvent e) {
        for (OverlayContent oc : widgets) {
            oc.handleMouseMoved(e);
        }
    }

    @Override
    protected void onMouseDragged(MouseEvent e) {
        for (OverlayContent oc : widgets) {
            oc.handleMouseDragged(e);
        }
    }

    public void addButton(OverlayButton cancel) {
        this.buttons.add(cancel);
    }


    public void addInput(String name, boolean showLabel, int width) {
        OverlayComponentInput oci = new OverlayComponentInput(this, name, width, showLabel);
        inputs.put(name, oci);
        widgets.add(oci);
    }


    @Override
    public boolean handleKeyboard(KeyEvent e) {
        for (OverlayComponentInput oci : inputs.values()) {
            if (oci.isSelected()) {
                oci.handleKeyboard(e);
                return true;
            }
        }

        return super.handleKeyboard(e);
    }

    public String getInput(String key) {
        OverlayComponentInput oci = inputs.get(key);
        if (oci != null) {
            return oci.getInput();
        }
        return null;
    }

    public void setInput(String key, String value) {
        OverlayComponentInput oci = inputs.get(key);
        if (oci != null) {
            oci.setInput(value);
        }
    }

//    public ColorPicker addColorPicker(String label) {
//        ColorPicker cp = new ColorPicker(this, label);
//        widgets.add(cp);
//        return cp;
//    }
//
//    public StringPicker addStringPicker(String label, String[] strs) {
//        StringPicker sp = new StringPicker(this, label, strs);
//        widgets.add(sp);
//        return sp;
//    }
//
//    public AvatarPicker addAvatarPicker() {
//        AvatarPicker ap = new AvatarPicker(this);
//        widgets.add(ap);
//        return ap;
//    }

    public void drawLater(OverlayContent oc) {
        this.laterDraws.add(oc);

    }



    public List<OverlayButton> getButtons() {
        return buttons;
    }

    public void removeContent(OverlayContent cont) {
        widgets.remove(cont);
    }

    public void addWidget(OverlayContent cont) {
        if (cont instanceof OverlayComponentInput) {
            OverlayComponentInput input = (OverlayComponentInput)cont;
            inputs.put(input.getName(), input);
        }
        widgets.add(cont);
    }
}
