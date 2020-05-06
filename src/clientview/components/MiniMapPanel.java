package clientview.components;

import clientlogic.GameData;
import clientlogic.Observer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MiniMapPanel extends JPanel implements Observer {

    private JLabel zed;

    public MiniMapPanel() {
        setLayout(new BorderLayout());
        this.add(makeMapDepthBar(), BorderLayout.NORTH);
        this.add(new MiniMapDrawingArea());
        GameData.getInstance().subscribe(this);
    }

    private JPanel makeMapDepthBar() {
        JPanel bar = new JPanel(new FlowLayout());
        bar.setAlignmentX(LEFT_ALIGNMENT);
        JButton minus = makeSmallButton("-");
        minus.addActionListener((ActionEvent e) -> MapPanel.addZTranslation(-1));
        bar.add(minus);
        JButton plus = makeSmallButton("+");
        plus.addActionListener((ActionEvent e) -> MapPanel.addZTranslation(+1));
        bar.add(plus);
        this.zed = new JLabel("Z = 0");
        bar.add(zed);
        return bar;
    }

    private JButton makeSmallButton(String s) {
        JButton result = new JButton(s);
        result.setMargin(new Insets(0, 0, 0, 0));
        result.setPreferredSize(new Dimension(20, 20));
        result.setFont(new Font("Arial", Font.PLAIN, 9));
        return result;
    }

    @Override
    public void update() {
        int currZ = GameData.getInstance().getCurrentZ() + MapPanel.getZTranslation();
        zed.setText("Z = " + currZ);
    }

    public void unregisterYourself() {
        GameData.getInstance().unsubscribe(this);
    }
}
