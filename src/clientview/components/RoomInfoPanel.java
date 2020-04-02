package clientview.components;

import clientlogic.GameData;
import clientlogic.Observer;
import clientview.SpriteManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RoomInfoPanel extends JPanel implements Observer {

    	private Box table = new Box(BoxLayout.Y_AXIS);
	private JLabel roomTitle;
//	private static final String header = "<p style='font-weight:bold'>";
//	private static final String footer = "</p>";

	public RoomInfoPanel() {
	    this.setLayout(new BorderLayout());
		roomTitle = new JLabel( "???" );
		roomTitle.setFont(new Font("Arial", Font.BOLD, 16));
		this.add(roomTitle, BorderLayout.NORTH);

		//JScrollPane sp = new JScrollPane();
		//sp.add(table);
		table.add(Box.createVerticalGlue());
		this.add(table);

		GameData.getInstance().subscribe(this);
	}

	private void fillTable(ArrayList<String> list) {

	    table.removeAll();

		for (int i = 0; i < list.size(); i++) {
			String[] strs = list.get(i).split("<img>");
			Box b = new Box(BoxLayout.X_AXIS);
			if (strs.length > 1) {
				b.add(new JLabel(getImageForString(strs[0])));
			}
            b.add(new JLabel(strs[strs.length-1]));
			b.add(Box.createHorizontalGlue());
			table.add(b);

		}
	}


	public static ImageIcon getImageForString(String string) {
		return SpriteManager.getSprite(string);

	}

	@Override
	public void update() {
		roomTitle.setText(GameData.getInstance().getCurrentRoom());
		fillTable(GameData.getInstance().getRoomInfo());

	}


}
