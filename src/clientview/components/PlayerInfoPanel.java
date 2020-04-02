package clientview.components;

import clientlogic.GameData;
import clientlogic.Observer;
import clientview.HeartIcon;
import clientview.SmallHeartIcon;
import clientview.SpriteManager;

import javax.swing.*;
import java.awt.*;

public class PlayerInfoPanel extends JPanel implements Observer {

    private JLabel charNameLabel;
    //private Label healthLabel;
    private Box healthPanel;
    private JLabel suitLabel;
    private JPanel itemsPanel;
    private Label weightLabel;

    public PlayerInfoPanel() {
        //ScrollPanel sp = new ScrollPanel();
        this.setLayout(new BorderLayout());
        Box vp = new Box(BoxLayout.Y_AXIS);
        Box hp2 = new Box(BoxLayout.X_AXIS);
        charNameLabel = new JLabel("");
        charNameLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        hp2.add(charNameLabel);


        {
            Box hp = new Box(BoxLayout.X_AXIS);
            hp.setAlignmentX(-1.0f);
            hp.add(new JLabel(""));
            //healthLabel = new Label("???");
            //hp.add(healthLabel);

            healthPanel = new Box(BoxLayout.X_AXIS);
            hp.add(Box.createHorizontalGlue());
            hp.add(healthPanel);
            hp2.add(hp);
        }
        vp.add(hp2);

        {
            Box hp = new Box(BoxLayout.X_AXIS);
            suitLabel = new JLabel("???");
            hp.add(suitLabel);

            vp.add(hp);
        }

        itemsPanel = new JPanel();


        Box hp3 = new Box(BoxLayout.X_AXIS);
        hp3.add(itemsPanel);
        vp.add(hp3);

        //sp.add(vp);
        this.add(vp);
        makeInventoryPanel(itemsPanel);

        GameData.getInstance().subscribe(this);
    }

    @Override
    public void update() {
        charNameLabel.setText(GameData.getInstance().getCharacter());
        paintHealth(GameData.getInstance().getHealth());
        //healthLabel.setText(GameData.getInstance().getHealth()+"");
        suitLabel.setText(GameData.getInstance().getSuit());
//		weightLabel.setText(GameData.getInstance().getWeight()+" kg");
        makeInventoryPanel(itemsPanel);
    }

    private void makeInventoryPanel(JPanel itemsLabel) {
        itemsLabel.removeAll();

        String items = GameData.getInstance().getItems().toString();
        items = items.substring(1, items.length()-1);
        String[] splits= items.split(", ");
        //Label first = new Label("[");
        //first.getElement().getStyle().setProperty("display", "inline");
        //itemsLabel.add(first);
        for (int i = 0; i < splits.length; ++i) {
            String s = splits[i];

            String[] strs = s.split("<img>");
            if (strs.length > 1) {
                ImageIcon w = SpriteManager.getSprite(strs[0]);

                itemsLabel.add(new JLabel(w));
            }
            JLabel l = new JLabel(strs[strs.length-1]);

            itemsLabel.add(l);
            if (i != splits.length-1) {
                JLabel l2 = new JLabel(", ");
                itemsLabel.add(l2);
            }
        }
        JLabel last = new JLabel(" " + GameData.getInstance().getWeight());
        itemsLabel.add(last);
     //   System.out.println("Fixing itemspanel, parent: " + getParent().getWidth() + " x " + getParent().getWidth());
        if (getParent() == null) {
            itemsPanel.setPreferredSize(new Dimension(500, 0));
        } else {
            itemsPanel.setPreferredSize(new Dimension(getParent().getWidth(), 0));
        }
        this.revalidate();


    }

    private void paintHealth(double health) {
        healthPanel.removeAll();
        if (health > Math.floor(health)) {

            healthPanel.add(new SmallHeartIcon());
        }
        for (int i = 0; i < Math.floor(health); ++i) {

            healthPanel.add(new HeartIcon());
        }


    }
}
