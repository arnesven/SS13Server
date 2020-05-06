package clientview;

import clientcomm.MyCallback;
import clientcomm.ServerCommunicator;
import clientlogic.GameData;
import clientlogic.Observer;
import clientview.components.GameUIPanel;
import main.SS13Client;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PlayersPanel extends JPanel implements Observer {


    private final JTable ft;
    private final SS13Client parentMain;
    private int selectedIndex = -1;

    public PlayersPanel(final String username, SS13Client parent) {
        this.parentMain = parent;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        ft = new JTable(new String[][]{new String[]{"Pelle", "Kalle"}, new String[]{"kjdaf", "soa"}}, new String[]{"kjdf", "gurgle"});

        ft.setFillsViewportHeight(true);
        ft.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                selectedIndex = e.getFirstIndex();
            }
        });
        this.add(ft);


        JButton kickButton = new JButton("Kick Player");
        kickButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
                ServerCommunicator.send(username + " KICK " + ft.getModel().getValueAt(ft.getSelectedRow(), 0),
                        new MyCallback<String>() {

                            @Override
                            public void onSuccess(String result) {
                                if (result.equals("KICKED")) {
                                    ((DefaultTableModel)ft.getModel()).removeRow(selectedIndex);
                                }
                                selectedIndex = -1;
                            }

                            @Override
                            public void onFail() {
                                System.out.println("Failed to send KICK message to server");
                            }
                        });
            }
        });

        JPanel buttBox = new JPanel(new FlowLayout());
        buttBox.add(kickButton);
        this.add(buttBox);
        this.add(Box.createVerticalGlue());
        GameData.getInstance().subscribe(this);

    }

    private void fillTable() {

        ArrayList<String> clientArr = GameData.getInstance().getClientList();
        ArrayList<Boolean> boolArr = GameData.getInstance().getReadyList();
        ArrayList<Boolean> specArr = GameData.getInstance().getSpectatorList();

//        while (tableModel.getRowCount() > 0) {
//            tableModel.removeRow(0);
//        }

        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{"Name", "Status"});
        ft.setModel(tableModel);
        ft.getColumnModel().getColumn(1).setCellRenderer(new DefaultTableCellRenderer(){
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
            {
                Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if(table.getValueAt(row, column).equals("READY")){
                    cellComponent.setBackground(Color.GREEN);
                } else if(table.getValueAt(row, column).equals("NOT READY")){
                    cellComponent.setBackground(Color.LIGHT_GRAY);
                }
                return cellComponent;
            }
        });
        for (int i = 0; i < clientArr.size() ; ++i) {
            //tableModel.setValueAt(clientArr.get(i), i, 0);
            //ft.setText(i, 0, clientArr.get(i));
            //System.out.println("Adding " + clientArr.get(i));
            String readyLabel = "";
            Color color;
            if (specArr.get(i)) {
                readyLabel = "SPECTATOR";
                color = new Color(0x5588FF);
            } else {
                if (boolArr.get(i)) {
                    readyLabel = "READY";
                    color = new Color(0x00FF00);
                } else {
                    readyLabel = "NOT READY";
                    color = new Color(0xDDDDDD);
                }
            }
            tableModel.addRow(new String[]{clientArr.get(i), readyLabel});
            if (selectedIndex == i) {
                ft.getSelectionModel().setSelectionInterval(selectedIndex, selectedIndex);
            }
        }


    }


    @Override
    public void update() {
        fillTable();
    }

    @Override
    public void unregisterYourself() {
        GameData.getInstance().unsubscribe(this);
    }


}
