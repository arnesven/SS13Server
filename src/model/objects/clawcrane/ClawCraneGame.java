package model.objects.clawcrane;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.objectactions.PlayClawCraneGameAction;
import model.actions.objectactions.WalkUpToSinglePersonUseMachineAction;
import model.events.animation.AnimatedSprite;
import model.fancyframe.ClawCraneGameFancyFrame;
import model.fancyframe.FancyFrame;
import model.items.NoSuchThingException;
import model.items.RandomItemManager;
import model.items.general.GameItem;
import model.items.general.ItemStackDepletedException;
import model.items.general.MoneyStack;
import model.map.rooms.Room;
import model.objects.SinglePersonUseMachine;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ClawCraneGame extends SinglePersonUseMachine {
    public static final int CONTENTS_WIDTH = 200;
    public static final int CONTENTS_HEIGHT = 130;
    private final ClawCrane crane;
    private final OutShaft shaft;
    private List<ClawCranePrize> contents;
    public static final int HITBOX_SIZE = 16;

    public ClawCraneGame(Room room) {
        super("Claw Crane Game", room);
        this.crane = new ClawCrane();
        this.shaft = new OutShaft(this);
        generateContents();
    }

    private void generateContents() {
        this.contents = new ArrayList<>();
        for (int i = 10; i > 0; --i) {
            contents.add(new ClawCranePrize(this, RandomItemManager.getRandomSearchItem()));
        }
        for (int i = 15; i > 0; --i) {
            contents.add(new ClawCraneCapsulePrize(this));
        }
        Collections.sort(getContents(), new Comparator<ClawCranePrize>() {
            @Override
            public int compare(ClawCranePrize c1, ClawCranePrize c2) {
                return c1.hitbox.y - c2.hitbox.y;
            }
        });
    }


    @Override
    public double getPowerConsumption() {
        return super.getPowerConsumption()*0.5;
    }

    @Override
    public Sprite getSprite(Player whosAsking) {
        if (isPowered()) {
            return new AnimatedSprite("clawcrane", "arcade.png", 3, 2, 32, 32, this, 5, true);
        } else if (isBroken()) {
            return new Sprite("clawcranebroken", "arcade.png", 2, 2, this);
        }
        return new Sprite("clawcranenopower", "arcade.png", 5, 7, this);
    }

    @Override
    protected void addActions(GameData gameData, Actor cl, ArrayList<Action> at) {
        if (cl instanceof Player) {
            at.add(new WalkUpToSinglePersonUseMachineAction((Player)cl, gameData, this) {
                @Override
                protected FancyFrame getFancyFrame(GameData gameData, Actor performingClient) {
                    return  new ClawCraneGameFancyFrame((Player)cl, gameData, ClawCraneGame.this);
                }
            });

        }
    }

    public BufferedImage makeImageFromContents(boolean active) {
        BufferedImage img = new BufferedImage(CONTENTS_WIDTH, CONTENTS_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) img.getGraphics();
        g.setColor(new Color(204, 255, 255));
        g.fillRect(0, 0, CONTENTS_WIDTH, CONTENTS_HEIGHT);
        shaft.drawYousrelf(g);
        for (ClawCranePrize prize : contents) {
            prize.drawYourself(g);
        }
        crane.drawYourself(g);
        if (!active) {
            g.setColor(new Color(0f, 0f, 0f, 0.5f));
            g.fillRect(0, 0, CONTENTS_WIDTH, CONTENTS_HEIGHT);
        }
        g.setStroke(new BasicStroke(1));
        g.drawRect(0, 0, CONTENTS_WIDTH-1, CONTENTS_HEIGHT-1);
        return img;
    }

    public void moveLeft(int i) {
        crane.setXPos(Math.max(0,  crane.getXPos() - i));
    }

    public void moveRight(int i) {
        crane.setXPos(Math.min(crane.getMaximumPosition(), crane.getXPos() + i));
    }

    public void resetYourself() {
        ClawCranePrize prize = crane.getGrabbedPrize();
        crane.reset();
        if (prize != null) {
            moveDown(prize);
        }
    }

    private void moveDown(ClawCranePrize prize) {
        while (!prize.isColliding() && prize.hitbox.y < CONTENTS_HEIGHT - HITBOX_SIZE) {
            prize.hitbox.y += 3;
        }
    }

    public Action grabOrDrop(GameData gameData, Player p, ClawCraneGameFancyFrame ccgff) {
        if (crane.getGrabbedPrize() == null) {
            ClawCranePrize prize = crane.grab(this);
            if (prize == null) {
                gameData.getChat().serverInSay("You failed to grab anything with the crane...", p);
            } else {
                gameData.getChat().serverInSay("You grabbed something with the crane!", p);
                crane.setGrabbed(prize);
            }


        } else {
            return new PlayClawCraneGameAction(this, ccgff);
        }
        return null;
    }

    public GameItem endGame(GameData gameData, Player player) {
        GameItem it = null;
        if (crane.getGrabbedPrize() != null) {
            if (crane.getAimLine().intersects(shaft.getHitbox())) {
                gameData.getChat().serverInSay("Something fell down into the out shaft!", player);
                it = crane.getGrabbedPrize().item;
                player.getCharacter().giveItem(it, this);
                contents.remove(crane.getGrabbedPrize());
                crane.setGrabbed(null);
                player.refreshClientData();
            } else {
                gameData.getChat().serverInSay("You dropped it...", player);
            }
        }
        resetYourself();
        return it;
    }

    public List<ClawCranePrize> getContents() {
        return contents;
    }

    public ClawCrane getCrane() {
        return crane;
    }

    public boolean startGame(GameData gameData, Player player) {
        try {
            MoneyStack m = MoneyStack.getActorsMoney(player);
            if (m.getAmount() >= 5) {
                try {
                    m.subtractFrom(5);
                    gameData.getChat().serverInSay("You put a $$ 5 chip into the machine, *chink*", player);

                } catch (ItemStackDepletedException e) {
                    player.getItems().remove(m);
                }
            } else {
                gameData.getChat().serverInSay("You don't have enough money to play :-(", player);
                return false;
            }
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
        return true;
    }
}
