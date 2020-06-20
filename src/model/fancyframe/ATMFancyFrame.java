package model.fancyframe;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.objectactions.AccessAccountAction;
import model.characters.decorators.CharacterDecorator;
import model.characters.general.GameCharacter;
import model.items.NoSuchThingException;
import model.items.general.MoneyStack;
import model.items.keycard.IdentCardItem;
import model.objects.general.ATM;
import sounds.Sound;
import sounds.TerminalButtonSound;
import util.HTMLText;
import util.Logger;

import java.util.ArrayList;
import java.util.List;

public class ATMFancyFrame extends SinglePersonUseMachineFancyFrame {
    private final ATM atm;
    private boolean depositScreenShowing;
    private boolean withdrawScreenShowing;
    private int[] withdrawValues;

    public ATMFancyFrame(Player performingClient, GameData gameData, ATM atm) {
        super(performingClient, atm);
        this.atm = atm;
        withdrawScreenShowing = false;
        depositScreenShowing = false;

        buildContent(gameData, performingClient);
    }

    @Override
    public void rebuildInterface(GameData gameData, Player player) {
        super.rebuildInterface(gameData, player);
        buildContent(gameData, player);
    }

    private void buildContent(GameData gameData, Player player) {
        StringBuilder content = new StringBuilder();
        if (atm.getInsertedCard() == null) {
            content.append(HTMLText.makeFancyFrameLink("INSERT", "[card]") + HTMLText.makeImage(Sprite.blankSprite()) + "<br/>");
            makeInnerBox(content, "Welcome", "Please insert", "your ident card", new ArrayList<>());

        } else if (atm.isCardEjected() || atm.getMoneyShowing() != null) {
            content.append(HTMLText.makeFancyFrameLink("TAKECARD", HTMLText.makeImage(atm.getInsertedCard().getSprite(player))) + "<br/>");
            makeInnerBox(content, "Take your", "card", (atm.getMoneyShowing()!=null?"and money":HTMLText.makeText("black", ".")),
                    new ArrayList<>());

        } else {
            content.append(HTMLText.makeFancyFrameLink("EJECT", "[eject]") + HTMLText.makeImage(Sprite.blankSprite()) + "<br/>");
            if (withdrawScreenShowing) {
                int maxValue = atm.getBalanceForInsertedCardPerson();
               withdrawDepositScreen(content, maxValue, "Withdraw");
            } else if (depositScreenShowing) {
                int maxValue = 0;
                try {
                    maxValue = MoneyStack.getActorsMoney(player).getAmount();
                } catch (NoSuchThingException e) {
                    e.printStackTrace();
                }
                withdrawDepositScreen(content, maxValue, "Deposit");
            } else {
                if (atm.hasAnAccount(atm.getInsertedCard().getBelongsTo())) {
                    makeInnerBox(content, "Account \"" + atm.getInsertedCard().getBelongsTo().getBaseName() + "\"",
                            "Balance: $$ " + atm.getBalanceForInsertedCardPerson(), "What do you wish to do?", "Withdraw" +
                                    HTMLText.makeText("black", ". . . . . . ") + "Deposit", List.of(7, 8));
                } else {
                    makeInnerBox(content, "",
                            "No Account Found", "", new ArrayList<>());
                }
            }
        }

        content.append("<br/>" + HTMLText.makeCentered("<i>Service by Nanotrasen Banking Inc.</i>") + "<br/>");
        content.append(HTMLText.makeCentered(HTMLText.makeBox("white", "black", 40, false, "")));
        if (atm.getMoneyShowing() != null) {
            content.append(HTMLText.makeCentered(HTMLText.makeBox("white", "black", 40, false,
                    HTMLText.makeCentered(HTMLText.makeFancyFrameLink("TAKEMONEY", HTMLText.makeImage(atm.getMoneyShowing().getSprite(player)))))));
        } else {
            content.append(HTMLText.makeCentered(HTMLText.makeBox("white", "black", 40, false, HTMLText.makeImage(Sprite.blankSprite()))));
        }
        content.append(HTMLText.makeCentered(HTMLText.makeBox("white", "black", 40, false, "")));

        setData(atm.getPublicName(player), false, HTMLText.makeColoredBackground("#31c9fe", content.toString()));
    }

    private void withdrawDepositScreen(StringBuilder content, int maxValue, String text) {
        withdrawValues = new int[]{maxValue / 10, maxValue / 5, 0, 0, 0, 0, maxValue / 2, maxValue};
        String upper = String.format("%d%24d", maxValue / 10, maxValue / 5).replace("  ", HTMLText.makeText("black", " ."));
        String lower = String.format("%d%24d", maxValue / 2, maxValue).replace("  ", HTMLText.makeText("black", " ."));
        makeInnerBox(content, upper, text, "how much?", lower, List.of(1, 2, 7, 8));
    }

    private String whiteCourierCentered(String text) {
        return HTMLText.makeCentered(HTMLText.makeText("white", "courier", 3, text));
    }

    private void makeInnerBox(StringBuilder content, String string1, String string2, String string3, String string4, List<Integer> bs) {
        content.append("<table width=\"100%\"bgcolor=\"#31c9fe\">");
        content.append("<colgroup><col width=\"10%\"><col width=\"80%\"><col width=\"10%\"></colgroup>");
        content.append("<tr><td>" + makeButton(1, bs) + "</td><td width=\"80%\" rowspan=\"4\" bgcolor=\"black\" fgcolor=\"white\">" +
                innerInnerBox(string1, string2, string3, string4) +
                "</td><td>" + makeButton(2, bs) + "</td></tr>");
        content.append("<tr><td>" + makeButton(3, bs) + "</td><td>" + makeButton(4, bs) + "</td></tr>");
        content.append("<tr><td>" + makeButton(5, bs) + "</td><td>" + makeButton(6, bs) + "</td></tr>");
        content.append("<tr><td>" + makeButton(7, bs) + "</td><td>" + makeButton(8, bs) + "</td></tr>");
        content.append("</table>");

   }

    private String makeButton(int i, List<Integer> buttonsShowing) {
        if (buttonsShowing.contains(i)) {
            return HTMLText.makeFancyFrameLink("BUTTON " + i, (i%2==1)?"-&gt;":"&lt;-");
        }
        return "";
    }

    private String innerInnerBox(String string1, String string2, String string3, String string4) {
        int boxwidth = 100;
        StringBuilder content = new StringBuilder();
        content.append(HTMLText.makeCentered(HTMLText.makeBox("White", "Black", boxwidth, false, whiteCourierCentered(string1))));
        content.append(HTMLText.makeCentered(HTMLText.makeBox("White", "Black", boxwidth, false, whiteCourierCentered(string2))));
        content.append(HTMLText.makeCentered(HTMLText.makeBox("White", "Black", boxwidth, false, whiteCourierCentered(string3))));
        content.append(HTMLText.makeCentered(HTMLText.makeBox("White", "Black", boxwidth, false, whiteCourierCentered(string4))));
        return content.toString();
    }

    private void makeInnerBox(StringBuilder content, String string1, String string2, String string3, List<Integer> buttonsShowing) {
        makeInnerBox(content, string1, string2, string3, HTMLText.makeText("black", "."), buttonsShowing);
    }


    @Override
    public void handleEvent(GameData gameData, Player player, String event) {
        super.handleEvent(gameData, player, event);
        if (event.contains("DISMISS")) {
            leaveFancyFrame(gameData, player);
        } else if (event.contains("INSERT")) {
            IdentCardItem card = IdentCardItem.findIdentCard(player);
            atm.insertCard(card);
            card.setHolder(null);
            player.getItems().remove(card);
            buildContent(gameData, player);
            player.getSoundQueue().add(new Sound("terminal_prompt"));
            player.refreshClientData();
        } else if (event.contains("EJECT")) {
            atm.setEjectedCard(true);
            this.depositScreenShowing = false;
            this.withdrawScreenShowing = false;
            buildContent(gameData, player);
        } else if (event.contains("TAKECARD")) {
            player.getCharacter().giveItem(atm.removeCard(), atm);
            buildContent(gameData, player);
            player.getSoundQueue().add(new Sound("card_slide"));
            player.refreshClientData();
        } else if (event.contains("TAKEMONEY")) {
            try {
                MoneyStack playersMoney = MoneyStack.getActorsMoney(player);
                playersMoney.addTo(atm.getMoneyShowing().getAmount());
            } catch (NoSuchThingException e) {
                player.getCharacter().giveItem(atm.getMoneyShowing(), atm);
            }
            atm.setMoneyShowing(null);
            player.getSoundQueue().add(new Sound("paper_pickup"));
            buildContent(gameData, player);
            player.refreshClientData();
        } else if (event.contains("BUTTON")) {
            String rest = event.replace("BUTTON ", "");
            Logger.log("Got button " + rest);
            player.getSoundQueue().add(new TerminalButtonSound());
            if (atm.getInsertedCard() != null) {
                if (withdrawScreenShowing) {
                    List<String> args = new ArrayList<>();
                    args.add("Withdraw");
                    args.add(""+withdrawValues[Integer.parseInt(rest)-1]);
                    AccessAccountAction aaa = new AccessAccountAction(atm, this);
                    aaa.setActionTreeArguments(args, player);
                    player.setNextAction(aaa);
                    readyThePlayer(gameData, player);
                } else if (depositScreenShowing) {
                    List<String> args = new ArrayList<>();
                    args.add("Deposit");
                    args.add(""+withdrawValues[Integer.parseInt(rest)-1]);
                    AccessAccountAction aaa = new AccessAccountAction(atm, this);
                    aaa.setActionTreeArguments(args, player);
                    player.setNextAction(aaa);
                    readyThePlayer(gameData, player);
                } else {
                    if (rest.equals("7")) {
                        withdrawScreenShowing = true;
                        buildContent(gameData, player);
                    } else {
                        depositScreenShowing = true;
                        buildContent(gameData, player);
                    }
                }
            }
        }
    }

    public void finalizeTransaction(GameData gameData, Actor performingClient) {
        atm.setEjectedCard(true);
        withdrawScreenShowing = false;
        depositScreenShowing = false;
        buildContent(gameData, (Player)performingClient);
    }
}
