package model.items.general;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.fancyframe.SecurityCameraFancyFrame;
import util.HTMLText;

import java.util.List;

public class PolaroidCamera extends GameItem {
    private int shotsLeft;

    public PolaroidCamera() {
        super("Polaroid Camera", 0.8, true, 230);
        this.shotsLeft = 10;
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("polaroidcamera", "custom_items.png", 20, 5, this);
    }

    @Override
    public List<Action> getInventoryActions(GameData gameData, Actor forWhom) {
        List<Action> acts = super.getInventoryActions(gameData, forWhom);
        if (forWhom instanceof Player) {
            acts.add(new TakePhotoAction());
        }
        return acts;
    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "A camera which instantly ejects a paper copy of the picture taken.";
    }

    @Override
    public GameItem clone() {
        return new PolaroidCamera();
    }


    private class TakePhotoAction extends Action {
        public TakePhotoAction() {
            super("Take Photo", SensoryLevel.OPERATE_DEVICE);
        }

        @Override
        protected String getVerb(Actor whosAsking) {
            return "Took a snapshot with a camera";
        }

        @Override
        protected void execute(GameData gameData, Actor performingClient) {
            if (PolaroidCamera.this.shotsLeft > 0) {
                Sprite spr = SecurityCameraFancyFrame.makeCombinedSprite(gameData, (Player)performingClient, performingClient.getPosition(), false);
                performingClient.getCharacter().giveItem(new PolaroidPhoto(spr, (Player)performingClient, performingClient.getPosition()),
                        performingClient.getAsTarget());
                PolaroidCamera.this.shotsLeft--;
                performingClient.addTolastTurnInfo("You took a photo of your current location.");
                gameData.getGameMode().getMiscHappenings().add("The " + performingClient.getBaseName() +
                        " took this picture of the " + performingClient.getPosition().getName() + ":<br/>" +
                        HTMLText.makeImage(spr));
            } else {
                performingClient.addTolastTurnInfo("The camera has no shots left :-(");
            }

        }

        @Override
        protected void setArguments(List<String> args, Actor performingClient) {

        }
    }
}
