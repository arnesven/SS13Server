package clientview.components;

import clientlogic.GameData;
import clientlogic.MyTreeNode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

public abstract class MyPopupMenu extends JPopupMenu  {
    private final MouseEvent mouseEvent;
    private final String name;



    public MyPopupMenu(String name, String actionData, MouseEvent e) {
        this.mouseEvent = e;
        this.name = name;
        MyTreeNode node = new MyTreeNode("root");
        GameData.makeStringIntoTree(actionData.substring(1, actionData.length()), node);
        makeActionMenu(e, node);

    }

    public MyPopupMenu(String name, MyTreeNode tree, MouseEvent e) {
        this.mouseEvent = e;
        this.name = name;
        makeActionMenu(e, tree);
    }

    public void showYourself() {
        this.show(mouseEvent.getComponent(), mouseEvent.getX(), mouseEvent.getY());
    }

    private void makeActionMenu(MouseEvent e, MyTreeNode rootNode) {
//        JLabel nameLabel = new JLabel(name);
//        nameLabel.setFont(new Font(nameLabel.getFont().getFontName(), Font.ITALIC | Font.BOLD, nameLabel.getFont().getSize()));
//        this.add(nameLabel);
        this.add(new MyLabel(name));
        this.addSeparator();
        for (MyTreeNode node : rootNode.children) {
            if (node.children.isEmpty()) {
                addLeaf(this, node.name, "root");
            } else {
                JMenu menu2 = new JMenu(node.name);
                addItemsToMenu(menu2, node, "root");
                this.add(menu2);
            }
        }
        this.addSeparator();
    }

    private void addLeaf(JComponent menu, String name, String actionString) {

        JMenuItem item = new JMenuItem(name);
        final String newActionString = actionString + "," + name;
        item.addActionListener(getActionListener(newActionString));
        menu.add(item);
    }

    public abstract ActionListener getActionListener(String newActionString);

    private void addItemsToMenu(JMenu menu, MyTreeNode node, String actionString) {
        for (MyTreeNode subnode : node.children) {
            if (subnode.children.isEmpty()) {
                addLeaf(menu, subnode.name, actionString + "," + node.name);
            } else {
                JMenu menu2 = new JMenu(subnode.name);
                addItemsToMenu(menu2, subnode, actionString + "," + node.name);
                menu.add(menu2);
            }
        }
    }

    public void addAll(MyPopupMenu popupMenu) {
        for (Component c : popupMenu.getComponents()) {
            this.add(c);
        }

    }
}
