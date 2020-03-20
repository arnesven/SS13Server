package clientview;

import javax.swing.*;
import java.awt.*;

public class MyLabel extends JComponent {
    public MyLabel(String name) {
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.setAlignmentX(0.0f);
        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(new Font(nameLabel.getFont().getFontName(), Font.ITALIC | Font.BOLD, nameLabel.getFont().getSize()));
        this.add(Box.createHorizontalStrut(3));
        this.add(nameLabel);
        this.add(Box.createHorizontalStrut(3));
        this.setMinimumSize(new Dimension(75, 0));
    }
}
