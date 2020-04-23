package model.fancyframe;

import graphics.sprites.Sprite;
import model.GameData;
import model.Player;
import model.actions.objectactions.FillWithKnownMutationAction;
import model.actions.objectactions.MutateSyringeAction;
import model.items.NoSuchThingException;
import model.items.general.GameItem;
import model.items.general.Syringe;
import model.mutations.Mutation;
import model.objects.consoles.Console;
import model.objects.consoles.GeneticsConsole;
import util.HTMLText;
import util.MyRandom;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GeneticsConsoleFancyFrame extends ConsoleFancyFrame {
    private final GeneticsConsole console;
    private boolean mutateClicked = false;
    private static final Sprite dnaSprite = new Sprite("dnasprite", "dna.png", 0, 0, 35, 112, null);
    private static Sprite coloredDnaSprite = new Sprite("coloreddnasprite", "dna.png", 0, 0, 35, 112, null);
    private static Sprite dnaSpriteOver = new Sprite("dnasprite", "dna.png", 1, 0, 35, 112, null);
    private boolean inAdvanced;
    private Syringe playersSyringe;

    public GeneticsConsoleFancyFrame(Player performingClient, Console console, GameData gameData) {
        super(performingClient.getFancyFrame(), console, gameData, "#008fe7", "white");
        this.console = (GeneticsConsole)console;
        inAdvanced = false;
        coloredDnaSprite.setColor(MyRandom.randomFunColor());
        coloredDnaSprite = new Sprite("coloreddnasprite2", "dna.png",
                0, 0, 35, 112, List.of(coloredDnaSprite, dnaSpriteOver), null);
        concreteRebuild(gameData, performingClient);
    }

    @Override
    protected void concreteRebuild(GameData gameData, Player player) {
        boolean playerHasSyringe = GameItem.hasAnItemOfClass(player, Syringe.class);
        StringBuilder content = new StringBuilder();
        String buttonName = "advanced";
        Sprite spriteToUse = dnaSprite;
        if (inAdvanced) {
            buttonName = "basic";
            if (this.console.isGeneticist(player)) {
                spriteToUse = coloredDnaSprite;
            }
        }
        content.append("______________________" + HTMLText.makeFancyFrameLink("CHANGEPAGE", "[" + buttonName + "]"));
        content.append("<table border=\"1\"><tr valign=\"top\"><td>" + HTMLText.makeImage(spriteToUse) + "</td><td width=\"100%\">");
        if (inAdvanced) {
            makeAdvancedMode(gameData, player, content, playerHasSyringe);
        } else {
            makeBasicMode(gameData, player, content, playerHasSyringe);
        }
        content.append("</td></tr></table>");
        setData(console.getPublicName(player), false, content.toString());
    }

    private void makeAdvancedMode(GameData gameData, Player player, StringBuilder content, boolean playerHasSyringe) {

        if (console.isGeneticist(player)) {
            if (playerHasSyringe) {
                try {
                    playersSyringe = GameItem.getItemFromActor(player, new Syringe());
                    if (playersSyringe.isFilled()) {
                        content.append(HTMLText.makeText("White", HTMLText.makeCentered("<i>You currently have a syringe filled with blood from " +
                                playersSyringe.getOriginalActor().getBaseName() + ". " +
                                "Do you want to mutate it by blasting it with radiation?</i>")));
                        content.append("<br/>");
                        String bgColor = "gray";
                        String fgColor = "black";
                        if (this.mutateClicked) {
                            bgColor = "black";
                            fgColor = "white";
                        }

                        for (Mutation m : console.getRandomMutationsForRound(gameData)) {
                            content.append(HTMLText.makeFancyFrameLink("MUTATE " + m.getName(),
                                    HTMLText.makeText(fgColor, bgColor, "Courier", 4, "[MUTATE]")) + m.getName() + "<br/>");
                        }
                    } else {
                        content.append(HTMLText.makeCentered("<i>Please insert a filled syringe filled with blood into the receptacle.</i>"));
                    }
                } catch (NoSuchThingException e) {
                    e.printStackTrace();
                }
            } else {
                content.append(HTMLText.makeCentered("<i>Please insert a filled syringe filled with blood into the receptacle.</i>"));
            }
        } else {
            makeGreek(gameData, player, content);
        }

    }

    private void makeGreek(GameData gameData, Player player, StringBuilder content) {
        String text = "You currently have a syringe filled with blood from Captain. " +
                "Do you want to mutate it by blasting it with radiation?";
        greekify(content, text, "a geneticist");
    }

    private void makeBasicMode(GameData gameData, Player player, StringBuilder content, boolean playerHasSyringe) {
        content.append(HTMLText.makeText("white", "Currently known mutations:</br><table color=\"white\">"));
        boolean foundSome = false;
        for (Iterator<Mutation> mit = console.getKnownMutationsIterator(); mit.hasNext(); ) {
            Mutation m = mit.next();
            content.append("<tr><td>" + m.getName() + "</td><td>");
            if (playerHasSyringe) {
                content.append(HTMLText.makeFancyFrameLink("FILL " + m.getName(), "[FILL SYRINGE]"));
            }
            content.append("</td></tr>");
            foundSome = true;
        }
        content.append("</table>");
        if (!foundSome) {
            content.append(HTMLText.makeText("white", "*None*"));
        }
        content.append("<br/>");
        if (playerHasSyringe) {
            try {
                playersSyringe = GameItem.getItemFromActor(player, new Syringe());
                if (playersSyringe.isFilled()) {
                    content.append(HTMLText.makeText("White", HTMLText.makeCentered("You currently have a syringe filled with blood from " +
                            playersSyringe.getOriginalActor().getBaseName() + ". " +
                            "Do you want to mutate it by blasting it with radiation?")));
                    content.append("<br/>");
                    String bgColor = "gray";
                    String fgColor = "black";
                    if (this.mutateClicked) {
                        bgColor = "black";
                        fgColor = "white";
                    }

                    content.append(HTMLText.makeCentered(HTMLText.makeFancyFrameLink("MUTATE",
                            HTMLText.makeText(fgColor, bgColor, "Courier", 4, "[MUTATE]"))));
                }
            } catch (NoSuchThingException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    protected void consoleHandleEvent(GameData gameData, Player player, String event) {
        if (event.contains("CHANGEPAGE")) {
            inAdvanced = !inAdvanced;
            concreteRebuild(gameData, player);
        } else if (event.contains("FILL")) {
            FillWithKnownMutationAction fwkma = new FillWithKnownMutationAction(console);
            List<String> args = new ArrayList<>();
            args.add(event.replace("FILL ", ""));
            fwkma.setActionTreeArguments(args, player);
            player.setNextAction(fwkma);
            concreteRebuild(gameData, player);
            readyThePlayer(gameData, player);
        } else if (event.contains("MUTATE")) {
            List<String> args = new ArrayList<>();
            args.add(MutateSyringeAction.getNameFor(playersSyringe));
            if (!event.equals("MUTATE")) {
                args.add(event.replace("MUTATE ", ""));
            }
            MutateSyringeAction msa = new MutateSyringeAction(console);
            msa.setActionTreeArguments(args, player);
            player.setNextAction(msa);
            concreteRebuild(gameData, player);
            readyThePlayer(gameData, player);
        }
        player.refreshClientData();
    }
}
