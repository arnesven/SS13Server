package model.modes;

import model.Actor;
import model.GameData;
import model.Player;
import model.characters.decorators.HostCharacter;
import model.characters.general.GameCharacter;
import model.modes.objectives.TraitorObjective;
import util.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MixedGameMode extends OperativesGameMode {

    private TraitorGameMode traitors = new TraitorGameMode();
    private HostGameMode host = new HostGameMode();
    private ChangelingGameMode ling = new ChangelingGameMode();
    private List<GameMode> innerModes = new ArrayList<>();

    public MixedGameMode() {
        innerModes.add(ling);
        innerModes.add(traitors);
        innerModes.add(host);
    }

    protected int getNoOfOperatives(GameData gameData) {
        return (int)Math.floor(gameData.getTargetablePlayers().size() * (1.0/3.0));
    }

    @Override
    protected void assignOtherRoles(ArrayList<GameCharacter> listOfCharacters, GameData gameData) {
        super.assignOtherRoles(listOfCharacters, gameData);
        Logger.log("No of operatives: " + super.getNoOfOperatives(gameData));
        traitors.setUpOtherStuff(gameData);

        for (Player p :  gameData.getPlayersAsList()) {
            if (traitors.isAntagonist(p) && isOperative(p)) {
                // player can't be both traitor and operative
                Logger.log(p.getName() + " can't be both operative and traitor... removing traitor.");
                traitors.removeTraitor(p);
            }
        }
        Logger.log("No of traitors: " + traitors.getTraitors().size());

        host.assignOtherRoles(null, gameData);
        for (Player p : gameData.getPlayersAsList()) {
            if (host.isAntagonist(p) && isOperative(p)) {
                Logger.log(p.getName() + " can't be both operative and host... removing host.");
                p.removeInstance((GameCharacter gc) -> gc instanceof HostCharacter);
            }
        }
        Logger.log("Host is " + host.getHostPlayer().getName());


        try {
            ling.assignOtherRoles(null, gameData);
        } catch (GameCouldNotBeStartedException gcnbse) {
            Logger.log(gcnbse.getMessage());
            throw new GameCouldNotBeStartedException("Can't play mixed mode!");
        }
        // the changeling's decoy could be an operative, a traitor, or the host!
        Logger.log("Ling is " + ling.getChangeling().getBaseName());


        if (traitors.isAntagonist(ling.getChangeling())) {
            traitors.removeTraitor((Player)ling.getChangeling());
            Logger.log("Can't be both changeling and traitor, removing traitor.");
        }

    }

    @Override
    protected void setUpOtherStuff(GameData gameData) {
        super.setUpOtherStuff(gameData);

        host.setUpOtherStuff(gameData);
        // there can be a hive without a host now!
        ling.setUpOtherStuff(gameData);
    }


    private boolean isOperative(Player p) {
        return super.isAntagonist(p);
    }

    @Override
    public boolean isAntagonist(Actor c) {
        return super.isAntagonist(c) || traitors.isAntagonist(c) || host.isAntagonist(c) || ling.isAntagonist(c);
    }

    @Override
    protected void addAntagonistStartingMessage(Player c) {
        for (GameMode m : innerModes) {
            if (m.isAntagonist(c)) {
                m.addAntagonistStartingMessage(c);
                return;
            }
        }
        super.addAntagonistStartingMessage(c);
    }

    public void setAntagonistFancyFrame(Player c) {
        addAntagonistStartingMessage(c);
    }

    protected GameOver getGameResult(GameData gameData) {
		if (gameData.isAllDead()) {
			return GameOver.ALL_DEAD;
		} else if (gameData.getRound() == gameData.getNoOfRounds()) {
			return GameOver.TIME_IS_UP;
		} else if (super.isNuked()) {
			return GameOver.SHIP_NUKED;
		}
		return null;
	}


    @Override
    protected void addProtagonistStartingMessage(Player c) {
        SecretGameMode.protMessage(c);
    }

    @Override
    public String getSummary(GameData gameData) {
        return (new MixedModeStats(gameData, this)).toString();
    }

    public TraitorGameMode getTraitorMode() {
        return traitors;
    }

    public HostGameMode getHostMode() {
        return host;
    }

    public ChangelingGameMode getChangelingMode() {
        return ling;
    }

    @Override
    protected String getImageURL() {
        return "https://www.netonnet.se/GetFile/ProductImagePrimary/hem-hushall/koksmaskiner-koksredskap/blender/hamilton-beach-multiblend-blender(1003351)_317639_3_Normal_Extra.jpg";
    }

    @Override
    public String getModeDescription() {
        return "A mixture between several other game modes. Needless to say, it won't be a great day for the crew...";
    }

    public List<Player> getTraitors() {
        return traitors.getTraitors();
    }

    public Map<Player, TraitorObjective> getTraitorObjectives() {
        return traitors.getObjectives();
    }
}
