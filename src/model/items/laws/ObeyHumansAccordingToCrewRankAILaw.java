package model.items.laws;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.itemactions.ShowExamineFancyFrameAction;
import model.fancyframe.FancyFrame;
import model.fancyframe.ItemDescriptionFancyFrame;
import model.items.NoSuchThingException;
import model.items.general.GameItem;
import model.npcs.NPC;
import util.HTMLText;

import java.util.ArrayList;
import java.util.List;

public class ObeyHumansAccordingToCrewRankAILaw extends AILaw {
    public ObeyHumansAccordingToCrewRankAILaw() {
        super(2, "Obey humans according to crew rank");
    }

    @Override
    public List<Action> getInventoryActions(GameData gameData, Actor forWhom) {
        List<Action> acts = new ArrayList<>();
        acts.add(new ShowExamineFancyFrameAction(gameData, (Player)forWhom, this) {
            @Override
            protected AILawDescriptionFancyFrame getFancyFrame(Actor performingClient) {
                return new AILawDescriptionFancyFrame((Player)performingClient, gameData);
            }
        });
        return acts;
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "Crew Rank:";
    }

    private class AILawDescriptionFancyFrame extends ItemDescriptionFancyFrame {

        public AILawDescriptionFancyFrame(Player performingClient, GameData gameData) {
            super(performingClient, gameData, ObeyHumansAccordingToCrewRankAILaw.this);

            makecontent(gameData, performingClient);
        }

        private void makecontent(GameData gameData, Player performingClient) {
            StringBuilder content = new StringBuilder();
            content.append("<b>" + getBaseName() + "</b><br/>");
            content.append("Crew Rank:<br/>");

            List<Actor> actors = new ArrayList<>();
            actors.addAll(gameData.getActors());
            actors.removeIf((Actor a) -> !a.isCrew());
            content.append("<table>");
            for (Actor a : actors) {
                content.append("<tr><td>");
                if (a instanceof NPC) {
                    if (gameData.getGameMode().getDecoys().values().contains(a)) {
                        content.append(getClidForDecoy(gameData, (NPC)a) + "</td><td>");
                    } else {
                        content.append("</td><td>");
                    }
                } else {
                    try {
                        content.append(gameData.getClidForPlayer((Player)a) + "</td><td>");
                    } catch (NoSuchThingException e) {
                        e.printStackTrace();
                    }
                }
                content.append(a.getBaseName() + "</td></tr>");
            }
            content.append("</table>");

            setData("AI Law " + getNumber(), false, HTMLText.makeColoredBackground("#86e0ff", content.toString()));
        }

        private String getClidForDecoy(GameData gameData, NPC a) {
            for (Player p : gameData.getGameMode().getDecoys().keySet()) {
                if (gameData.getGameMode().getDecoys().get(p) == a) {
                    try {
                        return gameData.getClidForPlayer(p);
                    } catch (NoSuchThingException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }
    }
}
