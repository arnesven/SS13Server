package model.items.suits;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.characters.general.GameCharacter;
import util.Logger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Equipment implements Serializable {

    public static final int HEAD_SLOT = 0;
    public static final int HANDS_SLOT = 1;
    public static final int TORSO_SLOT = 2;
    public static final int FEET_SLOT = 3;

    private final GameCharacter character;
    private SuitItem[] slots;
    private static final String[] slotNames = new String[]{"Head", "Hands", "Torso", "Feet"};

    public Equipment(GameCharacter gameCharacter) {
        this.character = gameCharacter;
        slots = new SuitItem[4];
    }

    public List<String> getGUIData(GameData gameData, Player player) {
        List<String> result = new ArrayList<>();
        for (int slotIndex = 0; slotIndex < slots.length; slotIndex++) {
            result.add(getGUIStringForSlot(slotIndex, gameData, player));
        }

        return result;
    }

    private String getGUIStringForSlot(int slotIndex, GameData gameData, Player player) {
        SuitItem w = slots[slotIndex];
        if (w == null && !otherSlotCovers(slotIndex, gameData, player)) {
            Logger.log("Slot was null and no other slot covers it, " + slotIndex);
            return Sprite.blankSprite().getName() + "<img>" + slotNames[slotIndex] + "<img>" +
                    Action.makeActionListStringSpecOptions(gameData, new ArrayList<>(), player);
        } else {
            String name = slotNames[slotIndex];
            String actionData = Action.makeActionListStringSpecOptions(gameData, new ArrayList<>(), player);
            if (slots[slotIndex] != null) {
                name = slots[slotIndex].getFullName(player);
                actionData =  slots[slotIndex].getEquipmentActionsData(player, gameData);
            }

            return getCombinedSpritesForSlot(slotIndex, gameData, player).getName() + "<img>" + name + "<img>" +
                   actionData;
        }

    }

    private boolean otherSlotCovers(int slotIndex, GameData gameData, Player player) {
        for (int index = 0; index < slots.length; index++) {
            if (index != slotIndex) {
                if (slots[index] != null) {
                    if (slots[index].hasAdditionalSprites() && slots[index].getAdditionalSprites().get(slotIndex) != null) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private Sprite getCombinedSpritesForSlot(int slotIndex, GameData gameData, Player player) {
        List<Sprite> sprites = new ArrayList<>();
        String finalName = "";
        for (SuitItem others : slots) {
            if (others != null) {
                if (others.hasAdditionalSprites() && others.getAdditionalSprites().get(slotIndex) != null) {
                    if (others.permitsOver()) {
                        Sprite spriteToAdd = others.getAdditionalSprites().get(slotIndex);
                        sprites.add(spriteToAdd);
                        finalName += spriteToAdd.getName();
                    }
                }
            }
        }
        if (slots[slotIndex] != null) {
            sprites.add(slots[slotIndex].getSprite(player));
            finalName += slots[slotIndex].getSprite(player).getName();
        }
        for (SuitItem others : slots) {
            if (others != null) {
                if (others.hasAdditionalSprites() && others.getAdditionalSprites().get(slotIndex) != null) {
                    if (!others.permitsOver()) {
                        Sprite spriteToAdd = others.getAdditionalSprites().get(slotIndex);
                        sprites.add(spriteToAdd);
                        finalName += spriteToAdd.getName();
                    }
                }
            }
        } // TODO: we may need to change the order on this.

        Sprite combination = new Sprite(finalName, sprites);
        return combination;
    }

    public SuitItem getEquipmentForSlot(int slot) {
        return slots[slot];
    }

    public void putOnEquipmentInSlot(SuitItem newSuit, int slot) {
        newSuit.setUnder(getEquipmentForSlot(slot));
        slots[slot] = newSuit;
    }

    public void removeEquipmentForSlot(int slot) {
        if (slots[slot] != null) {
            SuitItem underSuit = slots[slot].getUnder();
            slots[slot].setUnder(null);
            if (character.getActor() != null) {
                slots[slot].beingTakenOff(character.getActor());
            }
            slots[slot] = underSuit;
        }
    }

    public Sprite getGetup(Actor whosAsking) {
        List<Sprite> sprites = new ArrayList<>();
        sprites.add(0, new Sprite("getupbase", "human.png", 0, character.getActor()));
        StringBuilder finalName = new StringBuilder();
        Logger.log("Getting getup for " + whosAsking.getPublicName());
        int index = 0;
        for (SuitItem it : slots) {
            if (it != null) {
                if (index == TORSO_SLOT) {
                    Logger.log("...Finding " + it.getPublicName(whosAsking));
                    Sprite spriteToAdd = it.getGetup(character.getActor(), whosAsking);
                    sprites.add(spriteToAdd);
                    finalName.append(spriteToAdd.getName());
                } else {
                    Sprite spriteToAdd = it.getWornSprite(whosAsking);
                    sprites.add(spriteToAdd);
                    finalName.append(spriteToAdd.getName());
                }
            } else if (index == TORSO_SLOT) {
                Logger.log("... Finding nakedness");
                Sprite nakedSprite = character.getActor().getCharacter().getNakedSprite();
                sprites.add(nakedSprite);
                finalName.append(nakedSprite.getName());
            }
            index++;
        }
        return new Sprite(finalName.toString(), sprites);
    }

    public double getTotalWeight() {
        double sum = 0.0;
        for (SuitItem w : slots) {
            if (w != null) {
                sum += w.getWeight();
            }
        }
        return sum;
    }

    public void removeEverything() {
        for (int i = 0; i < slots.length; ++i) {
            while (slots[i] != null) {
                removeEquipmentForSlot(i);
            }
        }
    }

    public List<SuitItem> getSuitsAsList() {
        List<SuitItem> result = new ArrayList<>();
        for (SuitItem s : slots) {
            if (s != null) {
                SuitItem s2 = s;
                while (s2.getUnder() != null) {
                    result.add(s2.getUnder());
                }
                result.add(s);
            }
        }
        return result;
    }

    public boolean canWear(SuitItem suitItem) {
        int targetSlot = suitItem.getEquipmentSlot();

        if (slots[targetSlot] == null) {
            return true;
        }

        if (!slots[targetSlot].permitsOver()) {
            return false;
        }

        for (int slotNum = 0; slotNum < slots.length; ++slotNum) {
            if (slotNum != targetSlot) {
                if (slots[slotNum] != null) {
                    if (slots[slotNum].blocksSlot(targetSlot)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public Equipment copyAll(GameCharacter forChara) {
        Equipment eq = new Equipment(forChara);
        for (int i = 0; i < slots.length; ++i) {
            if (this.slots[i] != null) {
                eq.slots[i] = this.slots[i].clone();
            }
        }
        return eq;
    }

    public boolean hasAnyEquipment() {
        for (SuitItem s : slots) {
            if (s != null) {
                return true;
            }
        }
        return false;
    }

    public List<SuitItem> getTopEquipmentAsList() {
        List<SuitItem> result = new ArrayList<>();
        for (SuitItem it : slots) {
            if (it != null) {
                result.add(it);
            }
        }
        return result;
    }

    public boolean isNaked() {
        return getEquipmentForSlot(TORSO_SLOT) == null;
    }
}
