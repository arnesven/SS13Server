package model.fancyframe;

import graphics.sprites.Sprite;
import model.GameData;
import model.Player;
import model.actions.objectactions.SetSolarPanelRotationAction;
import model.characters.crew.CrewCharacter;
import model.characters.general.GameCharacter;
import model.objects.consoles.Console;
import model.objects.consoles.SolarArrayControl;
import model.objects.decorations.SolarPanel;
import util.HTMLText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SolarArrayControlFancyFrame extends ConsoleFancyFrame {
    private final SolarArrayControl console;
    private boolean showingAdvanced;

    public SolarArrayControlFancyFrame(Player performingClient, SolarArrayControl console, GameData gameData) {
        super(performingClient.getFancyFrame(), console, gameData, "#02558c", "white");
        this.console = console;
        showingAdvanced = false;

        concreteRebuild(gameData, performingClient);
    }

    @Override
    protected void concreteRebuild(GameData gameData, Player player) {
        StringBuilder content = new StringBuilder();

        if (!showingAdvanced) {
            content.append("______________________" + HTMLText.makeFancyFrameLink("ADVANCED", "[advanced]") + "<br>");
            content.append("<b>Align Solar Panels:</b><br/>");

            StringBuilder innerContent = new StringBuilder();
            int[][] rotations = new int[][]{new int[]{3, 2, 1}, new int[]{4, -1, 0}, new int[]{5, 6, 7}};
            for (int[] rotrow : rotations) {
                for (int rot : rotrow) {
                    if (rot != -1) {
                        SolarPanel sp = new SolarPanel(null, rot);
                        innerContent.append(HTMLText.makeFancyFrameLink("SET " + rot, HTMLText.makeImage(sp.getSprite(player))));
                    } else {
                        innerContent.append(HTMLText.makeImage(Sprite.blankSprite()));
                    }
                }
                innerContent.append("<br/>");
                for (int rot : rotrow) {
                    if (rot == -1) {
                        innerContent.append(".......");
                    } else {
                        innerContent.append(" " + rot*45 + " ");
                    }
                }
                innerContent.append("<br/>");
            }


            content.append(HTMLText.makeCentered(innerContent.toString()));
        } else {
            content.append("________________________" +
                    HTMLText.makeFancyFrameLink("BACK", "[back]") + "<br/>");

            if (canOperateSolarPanels(player)) {
                content.append("Connected Solar Panels:<br/>");
                List<SolarPanel> panels = new ArrayList<>();
                panels.addAll(console.findSolarPanels());
                Collections.sort(panels, new Comparator<SolarPanel>() {
                    @Override
                    public int compare(SolarPanel solarPanel, SolarPanel t1) {
                        Double d1 = solarPanel.getPower(gameData);
                        Double d2 = t1.getPower(gameData);
                        return d2.compareTo(d1);
                    }
                });

                for (SolarPanel p : panels) {
                    content.append("Panel #" + p.getNumber() + " " + (int)(p.getPower(gameData) * 1000000.0) + "W <br/>");
                }
            } else {
                String text = "Panel #145 512W Panel #146 512W Panel #147 512W Panel #148 512W Panel #149 512W Panel #150 512W Panel #151 512W Panel #152 512W";
                greekify(content, text, "a technically skilled person");
            }

        }

        setData(console.getPublicName(player), false, content.toString());
    }

    private boolean canOperateSolarPanels(Player player) {
        return player.isAI() ||
                player.getCharacter().checkInstance((GameCharacter gc) -> gc
                        instanceof CrewCharacter && ((CrewCharacter) gc).getType().equals("Technical"));
    }

    @Override
    protected void consoleHandleEvent(GameData gameData, Player player, String event) {
        if (event.contains("BACK")) {
            showingAdvanced = false;
            concreteRebuild(gameData, player);
        } else if (event.contains("ADVANCED")) {
            showingAdvanced = true;
            concreteRebuild(gameData, player);
        } else if (event.contains("SET")) {
            List<String> args = new ArrayList<>();
            args.add(Integer.parseInt(event.replace("SET ", ""))*45 + " Degrees");
            SetSolarPanelRotationAction sspra = new SetSolarPanelRotationAction(console);
            sspra.setActionTreeArguments(args, player);
            player.setNextAction(sspra);
            readyThePlayer(gameData, player);
            concreteRebuild(gameData, player);
        }
    }
}
