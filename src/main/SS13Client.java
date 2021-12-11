package main;

import clientcomm.ConnectData;
import clientcomm.MyCallback;
import clientcomm.ServerCommunicator;
import clientlogic.Cookies;
import clientlogic.GameData;
import clientlogic.Room;
import clientsound.ClientSoundManager;
import clientview.FancyFrame;
import clientview.SpriteManager;
import clientview.animation.AnimationHandler;
import clientview.components.GameUIPanel;
import clientview.components.MapPanel;
import clientview.components.ReturningPlayerPanel;
import clientview.dialogs.ConnectToServerDialog;
import clientview.dialogs.StartNewServerDialog;
import clientview.strategies.BackgroundDrawingStrategy;
import clientsound.SoundJLayer;
import tests.SimulationClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class SS13Client extends JFrame {

    public static final String CLIENT_VERSION_STRING = "1.3";
    private final ReturningPlayerPanel retPan;
    public static final Dimension originalSize = new Dimension(960, 960);
    public static final Dimension ingameSize = new Dimension(1200, 960);
    private static ConnectData connectData;
    private JMenu view;
    private boolean errorShowing;
    private GameUIPanel guiPanel = null;
    private int lastPortUsed;
    private int botNumber = 1;
    private FancyFrame fancyFrame;
    private SoundJLayer backgroundMusic;

    public SS13Client() {
        super("SS13 Client " + CLIENT_VERSION_STRING);
        errorShowing = false;
        this.setSize(originalSize);
        this.setLocation(new Point(250, 50));
        this.retPan = new ReturningPlayerPanel();
        this.add(retPan);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ServerCommunicator.setFrameReference(this);
        makeMenuBar();
        //setUpKeyListener();
        this.setVisible(true);
        makeFancyFrame();
        playBackgroundMusic(false);
    }


    private void makeFancyFrame() {
        this.fancyFrame = new FancyFrame(this);
    }

    public static void main(String[] args) {
        new SS13Client();
    }

    public void LoginToServer(boolean returning, Boolean spectator, String clid) {
        String message = "IDENT ME" + clid;
        if (spectator) {
            message += " SPECTATOR";
        }

        if (returning) {
            message = clid + " RETURNING";
        }

        connectData = new ConnectData(returning, spectator, clid, retPan);
        ServerCommunicator.send(message, new MyCallback() {

            @Override
            public void onSuccess(String result) {
                if (result.equals("ID ERROR")) {
                    retPan.setIDError();
                } else if (result.contains("ERROR") || result.contains("error")) {
                    JOptionPane.showMessageDialog(SS13Client.this,
                            result.replace("ERROR", ""));
                    new ConnectToServerDialog(SS13Client.this);
                } else if (result.contains("fail")) {
                    JOptionPane.showMessageDialog(SS13Client.this, "Could not connect to server.");

                } else {
                    String parts[] = result.split(":");
                    String clid = parts[0];
                    GameData.getInstance().setClid(clid);
                    GameData.getInstance().setLastSound(Integer.parseInt(parts[1]));
                    switchToGameUI(clid);
                    errorShowing = false;
                }
            }

            @Override
            public void onFail() {

            }
        });
    }

    public void switchBackToStart() {
        System.out.println("Switching views back");
        GameData.resetAllData();
        getContentPane().remove(guiPanel);
        guiPanel.unregisterYourself();
        getContentPane().add(retPan);
        disableView();
        this.setSize(originalSize);
        revalidate();
        repaint();
    }

    public void showConnectionError() {
        if (!errorShowing) {
            errorShowing = true;
            JOptionPane.showMessageDialog(this, "Connection to server lost.");
        }
    }

    protected void switchToGameUI(String username) {
        System.out.println("Switching views");
        getContentPane().remove(retPan);
        guiPanel = new GameUIPanel(username, this);
        getContentPane().add(guiPanel);
        enableView();
        guiPanel.setUpPollingTimer(username);
        this.setSize(ingameSize);
        this.revalidate();
        this.repaint();
        if (GameData.getInstance().getState() == 0) {
            playBackgroundMusic(true);
        }
    }

    private void makeMenuBar() {
        JMenuBar menubar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenuItem item = new JMenuItem("Connect");
        view = new JMenu("View");
        makeCenterOnMe(view);
        makeSmallWindowMenu(view);
        makeScaleMenu(view);
        makeZoomMenu(view);
        makeDepthMenu(view);
        makeBackgroundMenu(view);
        makeHeightMenu(view);
        makeAnimationMenu(view);
        view.setEnabled(false);
        JMenu server = new JMenu("Server");

        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ConnectToServerDialog ctsd = new ConnectToServerDialog(SS13Client.this);
            }
        });
        file.add(item);
        menubar.add(file);
        menubar.add(view);

        menubar.add(makeAudioMenu());

        JMenuItem startServer = new JMenuItem("Host Game");
        JMenuItem bot = new JMenuItem("Add Bot");
        JMenuItem tenBots = new JMenuItem("Add 10 Bots");
        startServer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                StartNewServerDialog snsd = new StartNewServerDialog(SS13Client.this);
                lastPortUsed = snsd.getPort();

                if (snsd.didStart()) {
                    startServer.setEnabled(false);
                    bot.setEnabled(true);
                    tenBots.setEnabled(true);
                    if (snsd.alsoConnectMe()) {
                        if (Cookies.getCookie("last_user_name") != null) {
                            GameData.getInstance().setHost("localhost");
                            GameData.getInstance().setPort(snsd.getPort());
                            System.out.println("Waiting five seconds for server to start.");
                            try {
                                Thread.sleep(5000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            LoginToServer(false, false, Cookies.getCookie("last_user_name"));
                        } else {
                            new ConnectToServerDialog(SS13Client.this);
                        }
                    }

                }
            }
        });
        server.add(startServer);
        bot.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                SimulationClient sc = new SimulationClient(lastPortUsed, "Botty" + botNumber++);
            }
        });
        bot.setEnabled(false);
        server.add(bot);

        tenBots.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                for (int i = 0; i < 10; ++i) {
                    SimulationClient sc = new SimulationClient(lastPortUsed, "Botty" + botNumber++);
                }
            }
        });
        tenBots.setEnabled(false);
        server.add(tenBots);
        menubar.add(server);



        this.setJMenuBar(menubar);
    }

    private JComponent makeAudioMenu() {
        JMenu sound = new JMenu("Audio");

        JRadioButtonMenuItem soundOn = new JRadioButtonMenuItem("Sound On");
        soundOn.addActionListener((ActionEvent ev) -> SoundJLayer.setSoundEnabled(true));
        JRadioButtonMenuItem soundOff = new JRadioButtonMenuItem("Sound Off");
        soundOff.addActionListener((ActionEvent ev) -> {SoundJLayer.setSoundEnabled(false);
        stopPlayingBackgroundMusic();
            ClientSoundManager.stopPlayingBackgroundSound();
        } );
        ButtonGroup bg = new ButtonGroup();
        bg.add(soundOn);
        bg.add(soundOff);
        soundOn.setSelected(true);

        sound.add(soundOn);
        sound.add(soundOff);

        return sound;
    }

    private void makeZoomMenu(JMenu view) {
        JMenu menu = new JMenu("Zoom");
        JMenuItem pix32 = new JMenuItem("32x32 (normal)");
        pix32.addActionListener((ActionEvent e) -> setZoom(32));
        menu.add(pix32);

        JMenuItem pix64 = new JMenuItem("64x64 (double)");
        pix64.addActionListener((ActionEvent e) -> setZoom(64));
        menu.add(pix64);
        pix32.setSelected(true);

        view.add(menu);
    }

    private void setZoom(int i) {
        MapPanel.setZoom(i);
        SpriteManager.rescaleImages(i);
        Room.setXScale(MapPanel.getZoom()*3);
        Room.setYScale(MapPanel.getZoom()*3);
        Cookies.setCookie("preferredzoom", i+"");
    }

    private void makeDepthMenu(JMenu view) {
        JMenu mapDepth = new JMenu("Map Depth");
        JMenuItem stepDown = new JMenuItem("Go Down");
        stepDown.addActionListener((ActionEvent e) -> {MapPanel.addZTranslation(-1); repaint();});
        mapDepth.add(stepDown);
        JMenuItem stepUp = new JMenuItem("Go Up");
        stepUp.addActionListener((ActionEvent e) -> {MapPanel.addZTranslation(1); repaint(); });
        mapDepth.add(stepUp);
        view.add(mapDepth);
    }

    private void makeSmallWindowMenu(JMenu view) {
        JMenu smallWindowMenu = new JMenu("Small Window");
        ButtonGroup bg = new ButtonGroup();

        JMenuItem popup = new JRadioButtonMenuItem("Pop-Up");
        popup.addActionListener((ActionEvent e) -> {
            guiPanel.getInGameView().removeDockedSmallWindow();
            fancyFrame.setForceShow(false);
        });
        popup.setSelected(true);
        bg.add(popup);
        smallWindowMenu.add(popup);

        JMenuItem forceShow = new JRadioButtonMenuItem("Show Always");
        forceShow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                guiPanel.getInGameView().removeDockedSmallWindow();
                fancyFrame.setVisible(true);
                fancyFrame.setForceShow(true);
                fancyFrame.setDontShow(false);

            }
        });
        smallWindowMenu.add(forceShow);
        bg.add(forceShow);

        JMenuItem docked = new JRadioButtonMenuItem("Docked");
        docked.addActionListener((ActionEvent e) -> {
                    if (docked.isSelected()) {
                        guiPanel.getInGameView().addDockedSmallWindow();
                        fancyFrame.setVisible(false);
                        fancyFrame.setForceShow(false);
                        fancyFrame.setDontShow(true);
                    }
                });
        bg.add(docked);
        smallWindowMenu.add(docked);

        view.add(smallWindowMenu);
    }

    private void makeCenterOnMe(JMenu view) {
        JMenuItem center = new JMenuItem("Center Camera");
        center.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                MapPanel.setXTranslation(GameData.getInstance().getCurrentRoom().getXPos() / (int)(Room.getXScale() / MapPanel.getZoom()));
                MapPanel.setYTranslation(GameData.getInstance().getCurrentRoom().getYPos() / (int)(Room.getYScale() / MapPanel.getZoom()));
                MapPanel.setZTranslation(GameData.getInstance().getCurrentRoom().getZPos());
                repaint();
            }
        });

        view.add(center);
    }

    private void makeAnimationMenu(JMenu view) {
        JMenu ani = new JMenu("Animations");

        final Timer t = new Timer(100, ((ActionEvent e) -> {AnimationHandler.step(); repaint();}));
        t.start();

        JMenuItem slow = new JRadioButtonMenuItem("Slow and laggy");
        slow.addActionListener((ActionEvent e) -> t.stop());
        JMenuItem fast = new JRadioButtonMenuItem("Fast and not so laggy");
        fast.addActionListener((ActionEvent e) -> t.start());
        ButtonGroup bg = new ButtonGroup();
        bg.add(slow);
        bg.add(fast);
        fast.setSelected(true);
        ani.add(slow);
        ani.add(fast);

        view.add(ani);
    }

    private void makeHeightMenu(JMenu view) {
        JMenu height = new JMenu("View Depth");

        JMenuItem stepDown = new JMenuItem("Step Down", KeyEvent.VK_PAGE_DOWN);
        stepDown.addActionListener((ActionEvent e) -> MapPanel.addZTranslation(-1));

        JMenuItem stepUp = new JMenuItem("Step Up", KeyEvent.VK_PAGE_UP);
        stepUp.setMnemonic(KeyEvent.VK_PAGE_UP);
        stepUp.addActionListener((ActionEvent e) -> MapPanel.addZTranslation(1));
        height.add(stepUp);
        height.add(stepDown);

        view.add(height);
    }

    private void makeBackgroundMenu(JMenu view) {
        JMenu menu = new JMenu("Background");
        ButtonGroup bg = new ButtonGroup();
        JMenuItem auto = new JRadioButtonMenuItem("Auto");
        auto.addActionListener((ActionEvent e) -> MapPanel.setAutomaticBackground(true));
        bg.add(auto);
        auto.setSelected(true);
        menu.add(auto);

        for (String backgroundType : BackgroundDrawingStrategy.getAllTypesAsStrings()) {
            JMenuItem it = new JRadioButtonMenuItem(backgroundType);
            it.addActionListener((ActionEvent e) -> {
                guiPanel.getInGameView().getMapPanel().getDrawingStrategy().
                        setBackgroundDrawingStrategy(BackgroundDrawingStrategy.makeStrategy(backgroundType));
                MapPanel.setAutomaticBackground(false);
                    }
            );
            bg.add(it);
            menu.add(it);
        }
        view.add(menu);
    }


    private void makeScaleMenu(JMenu view) {
        JMenu jmenu = new JMenu("Map Scale");
        JMenuItem auto = new JMenuItem("Auto");
        JMenuItem twobytwo = new JMenuItem("2x2");
      //  JMenuItem threebytwo = new JMenuItem("3x2");
        JMenuItem threebythree = new JMenuItem("3x3");
        JMenuItem fourbyfour = new JMenuItem("4x4");
        JMenuItem fivebyfive = new JMenuItem("5x5");
        threebythree.setSelected(true);
        auto.addActionListener((ActionEvent e) -> Room.setAutomaticScaling(true));
        twobytwo.addActionListener((ActionEvent e) -> setXYScale(2, 2));
       // threebytwo.addActionListener((ActionEvent e) -> setXYScale(3, 2));
        threebythree.addActionListener((ActionEvent e) -> setXYScale(3, 3));
        fourbyfour.addActionListener((ActionEvent e) -> setXYScale(4, 4));
        fivebyfive.addActionListener((ActionEvent e) -> setXYScale(5,5));

        jmenu.add(twobytwo);
        //jmenu.add(threebytwo);
        jmenu.add(threebythree);
        jmenu.add(fourbyfour);
        jmenu.add(fivebyfive);
        jmenu.add(auto);
        view.add(jmenu);
    }

    private void setXYScale(int x, int y) {
        Room.setAutomaticScaling(false);
        Room.setXScale(x * MapPanel.getZoom());
        Room.setYScale(y * MapPanel.getZoom());
        Cookies.setCookie("preferredxscale", x+"");
        Cookies.setCookie("preferredyscale", y+"");
        for (Room r : GameData.getInstance().getMiniMap()) {
            r.clearSlotTable();
        }
    }


    private void enableView() {
        this.view.setEnabled(true);
        if (Cookies.getCookie("preferredzoom") != null) {
            setZoom(Integer.parseInt(Cookies.getCookie("preferredzoom")));
        }
        if (Cookies.getCookie("preferredxscale") != null) {
            setXYScale(Integer.parseInt(Cookies.getCookie("preferredxscale")),
                    Integer.parseInt(Cookies.getCookie("preferredyscale")));
        }
    }

    private void disableView() { this.view.setEnabled(false);}



    public void playBackgroundMusic(boolean lobby) {
        if (backgroundMusic != null) {
            backgroundMusic.stop();
        }
        if (lobby) {
            backgroundMusic = new SoundJLayer("clientresources/sound/lobbymusic.mp3", true);
        } else {
            backgroundMusic = new SoundJLayer("clientresources/sound/splashmusic.mp3", true);
        }
        backgroundMusic.play();
    }

    public void stopPlayingBackgroundMusic() {
        System.out.println("Gonna stop music...");
        if (backgroundMusic != null && backgroundMusic.isPlaying()) {
            backgroundMusic.stop();
        } else {
            System.out.println("   either bgm is null, or bgm is not playing...");
        }
    }
}
