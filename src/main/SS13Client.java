package main;

import clientcomm.MyCallback;
import clientcomm.ServerCommunicator;
import clientlogic.Cookies;
import clientlogic.GameData;
import clientview.ConnectData;
import clientview.GameUIPanel;
import clientview.ReturningPlayerPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SS13Client extends JFrame {

    private final ReturningPlayerPanel retPan;
    private static final Dimension originalSize = new Dimension(960, 960);
    private static final Dimension ingameSize = new Dimension(1200, 960);
    private static ConnectData connectData;
    private boolean errorShowing;
    private GameUIPanel guiPanel = null;

    public SS13Client() {
        super("SS13 Client 0.01a");
        errorShowing = false;
        this.setSize(originalSize);
        //this.setLayout(new BorderLayout());
        this.retPan = new ReturningPlayerPanel();
        this.add(retPan);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        ServerCommunicator.setFrameReference(this);

        JMenuBar menubar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenuItem item = new JMenuItem("Connect");
        JMenu server = new JMenu("Server");

        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int res = JOptionPane.showConfirmDialog(SS13Client.this, "Are you returning to a server?", "Returning", JOptionPane.YES_NO_OPTION);

                boolean returning;
                returning = res == JOptionPane.YES_OPTION;

                String name = JOptionPane.showInputDialog(SS13Client.this, "Enter name", Cookies.getCookie("last_user_name"));
                if (!name.matches("[a-zA-Z]*?") || name.equals("")) {
                    JOptionPane.showMessageDialog(SS13Client.this, "Illegal user name");
                    return;
                }
                Cookies.setCookie("last_user_name", name);

                String result =  JOptionPane.showInputDialog(SS13Client.this, "Enter server (name:port)", Cookies.getCookie("default_server"));
                System.out.println(result);
                if (result.contains(":")) {
                    String[] parts = result.split(":");
                    int port = Integer.parseInt(parts[1]);
                    GameData.getInstance().setHost(parts[0]);
                    GameData.getInstance().setPort(port);
                    Cookies.setCookie("default_server", result);
                    cont(returning, false, name, retPan);
                } else {
                    JOptionPane.showMessageDialog(SS13Client.this, "Illegal server name");
                }
            }
        });
        file.add(item);
        menubar.add(file);

        JMenuItem startServer = new JMenuItem("Host Game");
        startServer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                new SS13ServerMain(55444, "Local Game", true).start();
                startServer.setEnabled(false);
            }
        });
        server.add(startServer);
        menubar.add(server);

        this.setJMenuBar(menubar);
        this.setVisible(true);


        this.setFocusable(true);

        this.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println("pressed" +    e.getKeyCode());
                if (e.getKeyCode() == KeyEvent.VK_R && e.isControlDown()) {
                    System.out.println("Pressed R");
                    if (guiPanel != null) {
                        guiPanel.toggleReady();
                    }

                }
            }
        });


       // MyDialog d = new MyDialog(this, "Test", new Dimension(200, 200), Color.GRAY, true);
      //  ReconnectDialog rd = new ReconnectDialog(this);

    }

    public static void main(String[] args) {
        new SS13Client();

    }

    protected void switchToGameUI(String username) {
        System.out.println("Switching views");
		getContentPane().remove(retPan);
		guiPanel = new GameUIPanel(username);
		getContentPane().add(guiPanel);
		this.setSize(ingameSize);
		this.revalidate();
		this.repaint();
	}

	public void cont(boolean returning, Boolean spectator, String clid, final ReturningPlayerPanel returnPanel) {
        String message = "IDENT ME" + clid;
        if (spectator) {
            message += " SPECTATOR";
        }

        if (returning) {
            message = clid + " RETURNING";
        }

        connectData = new ConnectData(returning, spectator, clid, returnPanel);
        ServerCommunicator.send(message, new MyCallback() {

            @Override
            public void onSuccess(String result) {
                if (result.equals("ID ERROR")) {
                    returnPanel.setIDError();
                } else if (result.contains("ERROR") || result.contains("error")) {
                    JOptionPane.showMessageDialog(SS13Client.this,
                            result.replace("ERROR", ""));
                } else if (result.contains("fail")) {
                    JOptionPane.showMessageDialog(SS13Client.this, "Could not connect to server.");

                } else {
                    GameData.getInstance().setClid(result);
                    switchToGameUI(result);
                    errorShowing = false;

                }
            }

            @Override
            public void onFail() {
               // tryReconnect();
            }
        });
    }

    public void switchBackToStart() {
        System.out.println("Switching views back");
        GameData.resetAllData();
        getContentPane().remove(0);
        getContentPane().add(retPan);
        this.setSize(originalSize);
        revalidate();
        repaint();
    }

    public void showConnectionError() {
        if (!errorShowing) {
            errorShowing = true;
            JOptionPane.showMessageDialog(this, "Connection to server lost.");
          // tryReconnect();
//            if (res == JOptionPane.OK_OPTION) {
//                tryReconnect();
//            }
        }
    }

//    private void tryReconnect() {
//        ReconnectDialog dialog = new ReconnectDialog(SS13Client.this);
//        while (dialog.isActive()) {
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        reconnect();
//    }

    public void reconnect() {
        cont(connectData.returning, connectData.spectator, connectData.clid, connectData.returnPanel);
    }
}
