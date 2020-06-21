package model.fancyframe;

import graphics.sprites.Sprite;
import model.GameData;
import model.Player;
import model.combat.DefaultUnarmedAttack;
import model.combat.MartialArtsAttack;
import model.items.weapons.UnarmedAttack;
import util.HTMLText;

public class UnarmedCombatFancyFrame extends FancyFrame {

    private static Sprite harmSprite   = new Sprite("harmsprite", "interface.png", 16, 10, null);
    private static Sprite grabSprite   = new Sprite("grabsprite", "interface.png", 2, 11, null);
    private static Sprite disarmSprite = new Sprite("disarmsprite", "interface.png", 1, 11, null);
    private final UnarmedAttack unarmedAttack;
    private MartialArtsAttack selected = new DefaultUnarmedAttack();
    private boolean pushed;

    public UnarmedCombatFancyFrame(Player performingClient, UnarmedAttack unarmedAttack) {
        super(performingClient.getFancyFrame());
        this.unarmedAttack = unarmedAttack;
        pushed = false;

        buildContent(performingClient);
    }

    private void buildContent(Player performingClient) {
        StringBuilder content = new StringBuilder();
        content.append("<br/>");
        content.append(HTMLText.makeText("Yellow", HTMLText.makeCentered("Select type of unarmed attack:")));
        content.append("<center><table><tr>");
        content.append("<td bgcolor=\"" + isSelected("Harm") + "\">" + HTMLText.makeFancyFrameLink("HARM",   HTMLText.makeImage(harmSprite)) + "</td><td>x</td>");
        content.append("<td bgcolor=\"" + isSelected("Grab") + "\">" + HTMLText.makeFancyFrameLink("GRAB",   HTMLText.makeImage(grabSprite)) + "</td><td>x</td>");
        content.append("<td bgcolor=\"" + isSelected("Disarm") + "\">" + HTMLText.makeFancyFrameLink("DISARM", HTMLText.makeImage(disarmSprite)) + "</td>");
        content.append("</table></tr></center>");
        content.append("<br/>");
        content.append(HTMLText.makeCentered(HTMLText.makeText("yellow", "serif", 3,
                selected.getHitAndDamage() + "<br/><i>" + getDescriptionForSelected()) + "</i>"));

        content.append(HTMLText.makeCentered(HTMLText.makeGrayButton(pushed, "DONE", "READY")));

        setData("Unarmed Combat", false, content.toString());
    }

    private String getDescriptionForSelected() {
        return selected.getDescription();
    }

    private String isSelected(String s) {
        if (selected.getName().equals(s)) {
            return "yellow";
        }
        return "blue";
    }

    @Override
    public void handleEvent(GameData gameData, Player player, String event) {
        super.handleEvent(gameData, player, event);
        if (event.contains("HARM") || event.contains("GRAB") || event.contains("DISARM")) {
            selected = MartialArtsAttack.factory(event.toLowerCase());
            buildContent(player);
        } else if (event.contains("DONE")) {
            unarmedAttack.setSelectedAttack(selected);
            readyThePlayer(gameData, player);
            pushed = true;
            dispose(player);
        }


    }
}
