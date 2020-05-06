package model.events.ambient;

import java.util.*;

import model.Actor;
import model.GameData;
import model.actions.general.SensoryLevel;
import model.events.Event;
import model.map.rooms.Room;
import model.objects.general.BreakableObject;
import model.objects.general.ElectricalMachinery;
import model.objects.general.PowerConsumer;
import model.objects.power.PositronGenerator;
import model.objects.general.GameObject;
import model.objects.power.PowerSupply;
import util.Logger;
import util.MyStrings;

public abstract class SimulatePower extends Event {
	public static final double ONE_TURN_IN_HOURS = 0.1;  // 6 minutes

	private List<Double> history = new ArrayList<>();
	private List<Room> noLifeSupport = new ArrayList<>();
	private List<Room> noLight = new ArrayList<>();
	private List<PowerConsumer> noPower = new ArrayList<>();
	private List<String> prios;

	public SimulatePower() {
		setupPrio();
	}

	private void setupPrio() {
		this.prios = new ArrayList<>();
		prios.add("Life Support");
		prios.add("Lighting");
		prios.add("Equipment");
	}

	public abstract Collection<Room> getAffectedRooms(GameData gameData);


	@Override
	public void apply(GameData gameData) {
        List<PowerSupply> ps = this.findPowerSources(gameData);
        if (ps != null) {
        	updatePower(gameData, ps);
			handleLifeSupport(gameData);
			handleDarkness(gameData);
			handleOvercharge(gameData, ps);
			updateHistory(gameData);
		}
	}

	private void updateHistory(GameData gameData) {
		history.add(getAvailablePower(gameData) * 1000);
	}

	private void handleOvercharge(GameData gameData, List<PowerSupply> ps) {
		for (PowerSupply pss : ps) {
			if (pss instanceof PositronGenerator) {
				((PositronGenerator)pss).handleOvercharge(gameData);
			}
		}
	}

	private void updatePower(GameData gameData, List<PowerSupply> sources) {
		List<PowerConsumer> consumers = findConsumers(gameData);

		List<PowerConsumer> oldNoPower = noPower;
		this.noPower = new ArrayList<>();
		this.noPower.addAll(consumers);

		int supplyIndex = -1;
		double powerRemaining = 0;
		for (PowerConsumer con : consumers) {
			while (powerRemaining < con.getPowerConsumption()) {
				supplyIndex++;
				if (supplyIndex < sources.size()) {
					if (supplyIndex > 0) {
						sources.get(supplyIndex-1).drainEnergy(powerRemaining * ONE_TURN_IN_HOURS);
					}
					powerRemaining += sources.get(supplyIndex).getPower() + 0.00001; // 10 W extra for good luck
				} else {
					// no more power sources
					break;
				}
			}

			if (supplyIndex == sources.size()) {
				// run out of sources, no more power to consumers.
				break;
			} else if (powerRemaining >= con.getPowerConsumption() &&
					con.getPowerConsumption()*ONE_TURN_IN_HOURS <= sources.get(supplyIndex).getEnergy()) {
					powerRemaining -= con.getPowerConsumption();
					con.receiveEnergy(gameData, con.getPowerConsumption() * ONE_TURN_IN_HOURS);
					sources.get(supplyIndex).drainEnergy(con.getPowerConsumption() * ONE_TURN_IN_HOURS);
					noPower.remove(con);
				//Logger.log("Powering " + ((ElectricalMachinery)con).getName());
			}
		}


		Logger.log("POWER: objects without power are: "+ MyStrings.join(noPower, ", "));
		runPowerOnOrOffFunctions(gameData, oldNoPower);

		noLifeSupport = new ArrayList<>();
		noLight = new ArrayList<>();
		for (Room r : getAffectedRooms(gameData)) {
			if (noPower.contains(r.getLighting())) {
				noLight.add(r);
			}
			if (noPower.contains(r.getLifeSupport())) {
				noLifeSupport.add(r);
			}
		}
	}

	public List<PowerConsumer> findConsumers(GameData gameData) {
		List<PowerConsumer> consumers = new ArrayList<>();
		for (Room room : getAffectedRooms(gameData)) {
			for (BreakableObject obj : room.getBreakableObjects(gameData)) {
				if (obj instanceof PowerConsumer) {
					if (obj instanceof ElectricalMachinery) {
						((ElectricalMachinery) obj).setPowerSimulation(this);
						if (!obj.isBroken()) {
							consumers.add((PowerConsumer) obj);
						}
					} else {
						consumers.add((PowerConsumer) obj);
					}
				}
			}
			if (room.getLifeSupport() != null) {
				consumers.add(room.getLifeSupport());
				room.getLifeSupport().setPowerSimulation(this);
			}
			if (room.getLighting() != null) {
				consumers.add(room.getLighting());
				room.getLighting().setPowerSimulation(this);
			}
		}
		Collections.sort(consumers, new ConsumerComparator());
		return consumers;
	}


	public Double getAvailablePower(GameData gameData) {
		List<PowerSupply> ps = findPowerSources(gameData);
		Double res = 0.0;
		for (PowerSupply pss: ps) {
			if (pss.getEnergy() > 0.0) {
				res += pss.getPower();
			}
		}
		return res;
	}

	public List<PowerSupply> findPowerSources(GameData gameData) {
		List<PowerSupply> result = new ArrayList<>();
        for (Room r : getAffectedRooms(gameData)) {
            for (GameObject obj : r.getObjects()) {
                if (obj instanceof PowerSupply) {
                    result.add((PowerSupply) obj);
                }
            }
        }
        Collections.sort(result, new Comparator<PowerSupply>() {
			@Override
			public int compare(PowerSupply powerSupply, PowerSupply t1) {
				if (t1.getEnergy() > powerSupply.getEnergy()) {
					return 1;
				} else if (t1.getEnergy() < powerSupply.getEnergy()) {
					return -1;
				}
				return new Double(t1.getPower()).compareTo(new Double(powerSupply.getPower()));
			}
		});
       	return result;
    }


	private void handleLifeSupport(GameData gameData) {
		for (Room r : getAffectedRooms(gameData)) {
			if (r.getLifeSupport() == null) {
				continue;
			}
			if (!r.getLifeSupport().isPowered() && gameData.getRound() > r.getLifeSupport().getLifeSupportLastOnIn() + 2) {
				r.getLifeSupport().addColdEvent(gameData);
			} else if (r.getLifeSupport().isPowered() && gameData.getRound() > r.getLifeSupport().getLifeSupportLastOnIn() + 2) {
				r.getLifeSupport().removeColdEvent(gameData);
			}
		}
	}


	private void handleDarkness(GameData gameData) {
		for (Room r : getAffectedRooms(gameData)) {
			if (r.getLighting() == null) {
				continue;
			}
			if (!r.getLighting().isPowered()) {
				r.getLighting().addDarkness(gameData);
			} else if (r.getLighting().isPowered()) {
				r.getLighting().removeDarkness(gameData);
			}
		}
	}



	@Override
	public String howYouAppear(Actor performingClient) {
		return "";
	}

	@Override
	public SensoryLevel getSense() {
		return SensoryLevel.NO_SENSE;
	}

	public List<String> getPrios() {
		return prios;
	}

	public void setHighestPrio(String selected, GameData gameData) {
		Iterator<String> it = prios.iterator();
		while (it.hasNext()) {
			if (it.next().equals(selected)) {
				it.remove();
			}
		}
		prios.add(0, selected);
		Logger.log("Prios are now: " + prios);

		for (PowerConsumer c : findConsumers(gameData)) {
			c.setPowerPriority(prios.indexOf(c.getTypeName())*2+1);
		}
	}

	private String getLSString(GameData gameData) {
		Collection<Room> all = getAffectedRooms(gameData);
		return "Life support units (" + (all.size() - noLifeSupport.size()) + "/" + all.size() + ")";
	}

	private String getLightString(GameData gameData) {
		Collection<Room> all = getAffectedRooms(gameData);
		return "Lights (" + (all.size() - noLight.size()) + "/" + all.size() + ")";
	}

	private String getEquipmentString(GameData gameData) {
		List<PowerConsumer> all = findConsumers(gameData);
		Collection<Room> rooms = getAffectedRooms(gameData);
		return "Electrical equipment (" +
				(all.size()-2*rooms.size() - (noPower.size()-noLifeSupport.size()-noLight.size())) + "/" +
				(all.size()-2*rooms.size()) + ")";
	}

	public List<Double> getHistory() {
		return history;
	}


	public List<PowerConsumer> getNoPowerObjects() {
		return noPower;
	}

	public List<Room> getNoLightRooms() {
		return noLight;
	}


	private void runPowerOnOrOffFunctions(GameData gameData,
										  List<PowerConsumer> oldNoPower) {
		for (PowerConsumer pc : noPower) {
			if (!oldNoPower.contains(pc)) {
				pc.onPowerOff(gameData);
			}
		}
		for (PowerConsumer pc : oldNoPower) {
			if (!noPower.contains(pc)) {
				pc.onPowerOn(gameData);
			}
		}
	}


	public List<String> getStatusMessages(GameData gameData) {
		List<String> strs = new ArrayList<>();
		double avail = getAvailablePower(gameData);
		double demand = getPowerDemand(gameData);

		strs.add("STATION POWER; " + String.format("%1.3f", avail) +
				"MW available, " +
				String.format("%1.3f", demand) + "MW demand, " +
				 (int)((avail*100.0)/demand) + "% of demand.");
		strs.add("-> " + getLSString(gameData));
		strs.add("-> " + getLightString(gameData));
		strs.add("-> " + getEquipmentString(gameData));
		return strs;
	}

	public Double getPowerDemand(GameData gameData) {
		double res = 0.0;
		for (Room r : getAffectedRooms(gameData)) {
			if (r.getLifeSupport() != null) {
				res += r.getLifeSupport().getPowerConsumption();
			}
			if (r.getLighting() != null) {
				res += r.getLighting().getPowerConsumption();
			}
			for (GameObject obj : r.getBreakableObjects(gameData)) {
				if (obj instanceof ElectricalMachinery) {
					res += ((ElectricalMachinery) obj).getPowerConsumption();
				}
			}
		}
		return res;
	}


	private class ConsumerComparator implements Comparator<PowerConsumer> {
		@Override
		public int compare(PowerConsumer powerConsumer, PowerConsumer t1) {
			if (powerConsumer.getPowerPriority() == t1.getPowerConsumption()) {
				return (int) (powerConsumer.getPowerConsumption() - t1.getPowerConsumption() * 100000);
			}
			return powerConsumer.getPowerPriority() - t1.getPowerPriority();
		}
	}
}
