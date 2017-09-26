package model.items.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.actions.general.ActionOption;
import model.items.NoSuchThingException;

/**
 * Created by erini02 on 14/11/16.
 */
public class MoneyStack extends ItemStack {

    private static final int[] limits = new int[]{10, 100, 200, 500, 1000, 2500, 5000, 10000};

    public MoneyStack(int startingMoney) {
        super("$$", 0.0, 0, startingMoney);
    }

    @Override
    public String getFullName(Actor whosAsking) {
        return  "$$ " + getAmount();
    }

    @Override
    public String getPublicName(Actor whosAsking) {
        return "Money Stack";
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        int i = 0;
        for (int lim : limits) {
            if (getAmount() < lim) {
                return new Sprite("monestack"+getAmount(), "items2.png", 4+i, 3);
            }
            i++;
        }
        i--;
        return new Sprite("monestack"+getAmount(), "items2.png", 4+i, 3);


    }

    @Override
    public GameItem clone() {
        return new MoneyStack(getAmount());
    }




    @Override
    public ActionOption getActionOptions(Actor whosAsking) {
        ActionOption opt = super.getActionOptions(whosAsking);
        opt.addOption("1");
        opt.addOption("10");
        opt.addOption("50");
        opt.addOption(getAmount()/2 + "");
        opt.addOption(getAmount() + "");
        return opt;
    }





    public static MoneyStack getActorsMoney(Actor whosAsking) throws NoSuchThingException {
        return (MoneyStack)GameItem.getItemFromActor(whosAsking, new MoneyStack(1));
    }

    @Override
    public void transferBetweenActors(Actor from, Actor to, String giveArgs) {
        super.transferBetweenActors(from, to, giveArgs);
        // both actors now got money increased by givers full money total
        int totalAmount = this.getAmount();
        giveArgs = giveArgs.replaceAll(" \\[.*\\]", "");
        int amountTransferred = Integer.parseInt(giveArgs);
        GameItem copy = this.clone();
        from.getCharacter().giveItem(copy, null);
        try {
            // givers stack reduced by giving amount
            ((MoneyStack) copy).subtractFrom(amountTransferred);
        } catch (ItemStackDepletedException e) {
            from.getCharacter().getItems().remove(copy);
        }

        for (GameItem it : to.getItems()) {
            if (it instanceof MoneyStack) {
                try {
                    ((MoneyStack) it).subtractFrom(totalAmount - amountTransferred);
                } catch (ItemStackDepletedException e) {
                    e.printStackTrace(); // should not happen!
                }
                break;
            }
        }
    }



}
