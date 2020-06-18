package model.npcs.behaviors;

import model.Actor;
import model.GameData;
import model.actions.itemactions.PutOutActorAction;
import model.characters.decorators.OnFireCharacterDecorator;
import model.characters.decorators.PoisonedDecorator;
import model.characters.general.GameCharacter;
import model.events.ambient.RadiationStorm;
import model.items.chemicals.IodinePill;
import model.npcs.robots.DRBOTCharacter;
import model.objects.general.Antidote;
import util.MyRandom;

import java.util.ArrayList;
import java.util.List;

public class RunDiagnosticsBehavior implements ActionBehavior {

    private final HealOtherBehavior healBehavior;

    public RunDiagnosticsBehavior() {
        this.healBehavior = new HealOtherBehavior(1.5);
    }


    @Override
    public void act(Actor npc, GameData gameData) {
        List<Actor> healbles = new ArrayList<>();
        for (Actor a : npc.getPosition().getActors()) {
            if (a.getCharacter().isHealable()) {
                healbles.add(a);
            }
        }

        if (healbles.size() > 0) {
            for (Actor a : healbles) {
                if (a.getMaxHealth() > a.getCharacter().getHealth()) {
                    healBehavior.act(npc, gameData);
                    sayToAll(npc, gameData, "There there now...");
                    setHealingLook(npc);
                    break;
                }
            }

            for (Actor a : healbles) {
                if (a.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof PoisonedDecorator)) {
                    sayToAll(npc, gameData, "Diagnosis: poison detected!");
                    a.addTolastTurnInfo(npc.getPublicName(a) + " injected you with a syringe!");
                    Antidote ant = new Antidote();
                    ant.beEaten(a, gameData);
                    setAntidoteLook(npc);
                    break;
                }
            }

            for (Actor a : healbles) {
                if (a.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof OnFireCharacterDecorator)) {
                    sayToAll(npc, gameData, "Diagnosis: you are on fire!");
                    PutOutActorAction paa = new PutOutActorAction(npc);
                    List<String> args = new ArrayList<>();
                    args.add(a.getPublicName(npc));
                    paa.setActionTreeArguments(args, npc);
                    paa.doTheAction(gameData, npc);
                    setAntiFireLook(npc);
                    break;
                }
            }

            if (RadiationStorm.hasEvent(npc.getPosition())) {
                Actor a = MyRandom.sample(healbles);
                sayToAll(npc, gameData, "An apple a day keeps the doctor away!");
                a.addTolastTurnInfo(npc.getPublicName(a) + " injected you with a syringe!");
                IodinePill ip = new IodinePill();
                ip.beEaten(a, gameData);
                setAntiRadLook(npc);
            }



        }

    }

    private void setAntiFireLook(Actor npc) {
        if (npc.getInnermostCharacter() instanceof DRBOTCharacter) {
            DRBOTCharacter chara = (DRBOTCharacter)npc.getInnermostCharacter();
            chara.setAntiFireLook();
        }
    }

    private void setAntiRadLook(Actor npc) {
        if (npc.getInnermostCharacter() instanceof DRBOTCharacter) {
            DRBOTCharacter chara = (DRBOTCharacter)npc.getInnermostCharacter();
            chara.setAntiRadLook();
        }
    }

    private void setHealingLook(Actor npc) {
        if (npc.getInnermostCharacter() instanceof DRBOTCharacter) {
            DRBOTCharacter chara = (DRBOTCharacter)npc.getInnermostCharacter();
            chara.setHealingLook();
        }
    }

    private void setAntidoteLook(Actor npc) {
        if (npc.getInnermostCharacter() instanceof DRBOTCharacter) {
            DRBOTCharacter chara = (DRBOTCharacter)npc.getInnermostCharacter();
            chara.setAntidoteLook();
        }
    }

    private void sayToAll(Actor npc, GameData gameData, String s) {
        for (Actor a : npc.getPosition().getActors()) {
            a.addTolastTurnInfo(npc.getBaseName() + " said \"" + s + "\"");
        }
    }
}
