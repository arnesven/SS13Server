package clientview.components;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.IOException;

public class ReturningPlayerPanel extends JPanel {

    public ReturningPlayerPanel() {

        try {
            //System.out.println(getClass().getClassLoader().getResource("clientresources/splash.jpg"));
            this.add(new JLabel(new ImageIcon(ImageIO.read(getClass().getClassLoader().getResourceAsStream("clientresources/splash.jpg")))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	public void setIDError() {
        JOptionPane.showMessageDialog(this, "Not an existing player!");
	}

}
