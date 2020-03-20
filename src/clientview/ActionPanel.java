//package clientview;
//
//import clientcomm.MyCallback;
//import clientcomm.ServerCommunicator;
//import clientlogic.GameData;
//import clientlogic.MyTreeNode;
//import clientlogic.Observer;
//
//import javax.swing.*;
//import javax.swing.event.TreeSelectionEvent;
//import javax.swing.event.TreeSelectionListener;
//import javax.swing.tree.DefaultMutableTreeNode;
//import javax.swing.tree.TreePath;
//import javax.swing.tree.TreeSelectionModel;
//import java.awt.*;
//import java.util.ArrayList;
//
//public class ActionPanel extends JPanel  implements Observer {
//
//    private JTree treeWidget = new JTree(new DefaultMutableTreeNode("Actions"));
//    private static int FONT_SIZE = 18;
//    private MyTreeNode oldTree;
//
//
//    public ActionPanel() {
//        this.setLayout(new BorderLayout());
//
//        this.add(treeWidget, BorderLayout.WEST);
//        treeWidget.getSelectionModel().setSelectionMode
//                (TreeSelectionModel.SINGLE_TREE_SELECTION);
//
//        treeWidget.addTreeSelectionListener(new TreeSelectionListener() {
//            @Override
//            public void valueChanged(TreeSelectionEvent e) {
//                if (GameData.getInstance().getState() == 2) {
//                    DefaultMutableTreeNode node = (DefaultMutableTreeNode)
//                            treeWidget.getLastSelectedPathComponent();
//                    if (node == null) {
//                        return;
//                    } else if (node.isLeaf()) {
//                        System.out.println("Selected " + node.toString());
//                        sendActionStringToSever(node);
//                    }
//                }
//            }
//        });
//
//        GameData.getInstance().subscribe(this);
//    }
//
//
//    @Override
//    public void update() {
//        DefaultMutableTreeNode root = ((DefaultMutableTreeNode)treeWidget.getModel().getRoot());
//        if (oldTree != GameData.getInstance().getActionTree()) {
//            System.out.println("Removing all nodes");
//
//            ((DefaultMutableTreeNode) treeWidget.getModel().getRoot()).removeAllChildren();
//
//            //treeWidget.setAnimationEnabled(true);
//
//
//            for (MyTreeNode node : GameData.getInstance().getActionTree().children) {
//                if (node.children.isEmpty()) {
//                    addLeaf(node.name, root);
//                } else {
//                    DefaultMutableTreeNode it = addNode(node.name, root);
//                    addItems(node.children, it);
//                }
//            }
////			if (treeWidget.getItemCount() > 0) {
////				treeWidget.getItem(0).setSelected(true);
////			}
//
//            oldTree = GameData.getInstance().getActionTree();
//            treeWidget.updateUI();
//        }
//
//        treeWidget.expandPath(new TreePath(root));
//
//        revalidate();
//        repaint();
//
//
//    }
//
//    private void addItems(ArrayList<MyTreeNode> children, DefaultMutableTreeNode it) {
//        for (MyTreeNode node : children) {
//            if (node.children.isEmpty()) {
//                addLeaf(node.name, it);
//            } else {
//                DefaultMutableTreeNode it2 = addNode(node.name, it);
//                addItems(node.children, it2);
//            }
//        }
//
//    }
//
//
//    private void addLeaf(String label, DefaultMutableTreeNode node) {
//
//        DefaultMutableTreeNode butt = new DefaultMutableTreeNode(label);
//        //	butt.getElement().getStyle().setPaddingLeft(3, Unit.PX);
//        if (label.equals("Do Nothing")) {
//            //butt.setValue(true);
//        }
//        node.add(butt);
//       // System.out.println("Adding leaf " + label);
////        final TreeItem it = tree.addItem(butt);
////        butt.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
////
////            @Override
////            public void onValueChange(ValueChangeEvent<Boolean> event) {
////                if (event.getValue()) {
////                    sendActionStringToSever(it);
////                }
////            }
////        });
////        //	GWT.log(message)
////        //	GWT.log(butt.getElement().getStyle().getPaddingLeft() + " left padding 2");
////        butt.getElement().getParentElement().getStyle().setMargin(-20, Unit.PX);
////        butt.getElement().getParentElement().getStyle().setPaddingRight(20, Unit.PX);
//
//    }
//
//    protected void sendActionStringToSever(DefaultMutableTreeNode node) {
//        ServerCommunicator.send(GameData.getInstance().getClid() + " NEXTACTION " +
//                recursiveParentGetter(node).replace("Actions,", ""), new MyCallback<String>() {
//
//            @Override
//            public void onSuccess(String result) {
//                    GameData.getInstance().setNextAction(recursiveParentGetter(node).replace("root,Actions,", ""));
//
//            }
//        });
//
//    }
//
//    protected String recursiveParentGetter(DefaultMutableTreeNode item) {
//        if (item == null) {
//            return "root";
//        }
//        return recursiveParentGetter((DefaultMutableTreeNode)item.getParent()) + "," + item.toString();
//    }
//
//    private DefaultMutableTreeNode addNode(String string, DefaultMutableTreeNode node) {
//       DefaultMutableTreeNode node2 = new DefaultMutableTreeNode(string);
//       node.add(node2);
//        return node2;
//    }
////
////
//
//
//}
