package model.objects.monolith;

import model.GameData;
import model.items.CosmicMonolith;
import model.items.general.GameItem;
import model.npcs.DoomsdayMachineNPC;
import util.MyRandom;

public class DoomsdayMachine extends CosmicMonolith {
    private final boolean hollow;
    private final GameData gameData;
    private boolean activated;

    public DoomsdayMachine(GameData gameData) {
        super("Doomsday Machine");
        hollow = MyRandom.nextBoolean();
        activated = false;
        this.gameData = gameData;
    }

    @Override
    public boolean reactsToLightHeat() {
        return false;
    }

    @Override
    public boolean reactsToFlames() {
        return true;
    }

    @Override
    public boolean reactsToPressure() {
        return false;
    }

    @Override
    public boolean isTouchSmooth() {
        return true;
    }

    @Override
    public boolean doesEmitRadiation() {
        return false;
    }

    @Override
    public boolean isHollow() {
        return hollow;
    }

    @Override
    public boolean doesReactToSound() {
        return true;
    }

    @Override
    public boolean doesConductCurrent() {
        return true;
    }

    @Override
    public boolean hasMarkings() {
        return true;
    }

    @Override
    public boolean smellsWhenCorrosiveApplied() {
        DoomsdayMachineNPC npc = new DoomsdayMachineNPC(getPosition(), this);
        gameData.addNPC(npc);
        activated = true;
        return false;
    }

    @Override
    public String getNotesText() {
        return "In 2231 a strange metallic object was intercepted by a cargo vessel in the outer pleiades. First thought " +
                " to be a navigational or communication device but the strange symbols inscribed along its smooth edges and " +
                " surfaces intrigued the crew to investigate the object further." +
                " The crew then some how activated the device which  proceeded to" +
                " kill four and seriously injure two of them before they were able to subdue it. According to the surviving crew," +
                " the metallic machine was not man made," +
                " but whether it was made by an alien race, or if it itself was some sort of sentient mechanical alien " +
                " was not clear.<br/>A similar incident happened in 2246 when an archaeological site reported a mysterious find." +
                " This time a crew of scientists were able to research it object more carefully and found it unresponsive to pressure" +
                " and light waves and emitting no radiation. However, when the team applied flames the object seemed to vibrate and" +
                " the application of corrosive chemicals seem to evoke a similar reaction. It was when the team exposed the object" +
                " to low frequency sonic waves that it suddenly came to life and instantly incenerated one scientist, while another was" +
                " stabbed by a previously hidden pointy prod.<br/>A third incident was reported in 2248, when a remote penal facility" +
                " spotted a small metallic object approaching their station. The object was taken aboard (contrary to quarantine procedures)" +
                " and then placed in an incinerator. The flames must have activated the doomsday machine because only two prisoners and" +
                " a ward were found, locked in a bathroom on board the station. The strange object had killed over 253 inmates and 42 guards" +
                " before it seemingly shut down on its own.";
    }

    @Override
    public String getEnding() {
        return "it may be a highly dangerous object and most of its properties are still unknown.";
    }

    @Override
    public GameItem clone() {
        return new DoomsdayMachine(gameData);
    }
}
