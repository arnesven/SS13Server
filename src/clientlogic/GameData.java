package clientlogic;

import clientcomm.MyCallback;
import clientcomm.ServerCommunicator;
import clientview.*;
import clientview.components.*;
import shared.ClientExtraEffect;
import sounds.Sound;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameData {

    private static GameData instance = null;
	private ArrayList<Observer> observers = new ArrayList<>();
	private ArrayList<String> clients = new ArrayList<>();
	private ArrayList<Boolean> ready = new ArrayList<>();
	private ArrayList<Boolean> spectators = new ArrayList<>();
	private ArrayList<Room> rooms = new ArrayList<>();
	private int state = -1;
	private String clid = Cookies.getCookie("clid");
	private ArrayList<Integer> selectableRooms = new ArrayList<>();
	private String character;
	private double health;
	private int currentPos;
	private String host = Cookies.getCookie("host");
	private ArrayList<String> equipment = new ArrayList<>();
	private ArrayList<String> items = new ArrayList<>();
	private ArrayList<String> roomInfo = new ArrayList<>();
	private ArrayList<String> lastTurnInfo = new ArrayList<>();
	private MyTreeNode tree  = new MyTreeNode("-1<actpart>root");
	private int round = 0;
	private String summaryString = "<p>Game in progress...</p>";
	private ArrayList<String> jobNames = new ArrayList<String>();
	private ArrayList<Boolean> jobVals = new ArrayList<>();
	private int noOfRounds;
	private String selectedMode = "Unknown";
	private ArrayList<String> modeAlternatives = new ArrayList<>();
	private int port;
	private int lastMessage = -1;
	private int lastSound = -1;
	private String weight;
	private ArrayList<OverlaySprite> overlays = new ArrayList<>();
	private ArrayList<String> settingNames = new ArrayList<>();
	private ArrayList<Boolean> settingBools = new ArrayList<>();
	private int selectedRoom = 0;
    private String nextAction = "Do Nothing";
    private String nextMove = "";
    private List<Room> miniMap = new ArrayList<>();
	private Room currentRoom;
	private int zShift = 0;
	private String serversSuggestedClientVersion = "Unknown Version";
	private CharacterStyle characterStyle = new CharacterStyle();
	private int fancyFrameState = 0;
	private int playerDataState = 0;
	private String fancyFrameTitle = "Default";
	private boolean fancyFrameHasInput = false;
	private String fancyFrameContent = "BLANK";
	private Dimension fancyFrameDimension = new Dimension(0, 0);
    private double[] oldSpacePos;
    private ArrayList<ClientExtraEffect> extraEffects = new ArrayList<>();

    private GameData() {
		modeAlternatives.add("Default");
	}

	public static GameData getInstance() {
		if (instance == null) {
			instance = new GameData();
		}
		return instance;
	}

	public static void resetAllData() {
		ArrayList<Observer> observers = instance.observers;
        instance = null;
        instance = new GameData();
        instance.observers = observers;
    }

	public ArrayList<String> getClientList() {
		return clients;
	}

	public ArrayList<Boolean> getReadyList() {
		return ready;
	}

	public void deconstructReadyListAndStateAndRoundAndSettings(String result) {
		String[] parts = result.split("<player-data-part>");
		instance.setState(Integer.parseInt(parts[1]));
		instance.setRound(Integer.parseInt(parts[2]));
		result = parts[0];

		String withoutBraces = result.substring(1, result.length()-1);
		String[] clients = withoutBraces.split(",");

		instance.clients.clear();
		instance.ready.clear();
		instance.spectators.clear();
		//Window.alert(result);
		for (int i = 0; i < clients.length ; ++i) {
			String[] cl = clients[i].split("=");
			if (i == 0) {
				instance.clients.add(cl[0]);
			} else {
				instance.clients.add(cl[0].substring(1));
			}
			instance.ready.add(Boolean.parseBoolean(cl[1]));
			if (cl[1].toUpperCase().equals(cl[1])) {
				instance.spectators.add(true);
			} else {
				instance.spectators.add(false);
			}
		}

		noOfRounds = Integer.parseInt(parts[3]);

		int serverLastMess = Integer.parseInt(parts[4]);

		if (serverLastMess > getLastMessage()) {
			getChatMessagesFromServer(serverLastMess);
		}

		int index = 4;

		try {
			int fancyFrameState = Integer.parseInt(parts[index+1]);
			if (fancyFrameState != this.fancyFrameState) {
				System.out.println("Fancy frame needs update, getting from server.");
			    getFancyFrame();
			    this.fancyFrameState = fancyFrameState;
			}
			index++;
		} catch (NumberFormatException nfe) {
            System.out.println("Problem parsing sound-index");
		}

		selectedMode = parts[6];
		serversSuggestedClientVersion = parts[7];
		modeAlternatives.clear();
		int i = 8;
		for (; i < parts.length - 2; ++i) {
			instance.modeAlternatives.add(parts[i]);
		}
		setNextAction(parts[i++]);
		setPlayerDataState(Integer.parseInt(parts[i]));

		notifyObservers();
	}

	private void setPlayerDataState(int i) {
		if (i > this.playerDataState) {
			GameUIPanel.pollServerActions();
			this.playerDataState = i;
		}
	}

	private void getFancyFrame() {
		ServerCommunicator.send(GameData.getInstance().getClid() + " FANCYFRAME GET", new MyCallback() {
			@Override
			public void onSuccess(String result) {
				System.out.println("Got fancyframe result: " + result);
				GameData.getInstance().deconstructFancyFrameData(result);
			}

			@Override
			public void onFail() {
				System.out.println("Failed while doing FANCYFRAME");
			}
		});
	}

	private void getChatMessagesFromServer(final int newLastMessage) {
		ServerCommunicator.send(GameData.getInstance().getClid() + " CHATGET " +
				getLastMessage(), new MyCallback<String>() {

					@Override
					public void onSuccess(String result) {
					    String[] messes =result.substring(1, result.length()-1).split("<list-part>");
					    ChatPanel.addToChatMessages(messes);
                        LastTurnPanel.addToChatMessages(messes);
						GameData.this.setLastMessage(newLastMessage);
					}

			@Override
			public void onFail() {
				System.out.println("Failed to send CHATGET message to server");
			}

		});
	}

//	private void getSoundsFromServer(final int serverLastSound) {
//		ServerCommunicator.send(getClid() + " SOUNDGET " + getLastSound(), new MyCallback<String>() {
//
//			@Override
//			public void onSuccess(String result) {
//				MusicPlayer.addSounds(result.substring(1,  result.length()-1).split("<list-part>"));
//				GameData.this.setLastSound(serverLastSound);
//			}
//
//		});
//
//	}

	protected void setLastSound(int serverLastSound) {
		this.lastSound = serverLastSound;
	}

	private void setRound(int round) {
		this.round = round;
	}

	public int getState() {
		return state;
	}

	private void setState(int state) {
		if (this.state != state) {
			this.state  = state;
			notifyObservers();
		}
	}

	private void notifyObservers() {

		for (Observer o : instance.observers) {
			o.update();
		}
	}

	public void subscribe(Observer os) {
		instance.observers.add(os);
	}

	public void unsubscribe(Observer os) {
		observers.remove(os);
	}

	public ArrayList<Room> getRooms() {
		return rooms;
	}

	public static void deconstructRoomList(String result, List<Room> rooms) {

		String sub = result.substring(1, result.length()-1);

		String[] roomParts = sub.split("<list-part>");

		rooms.clear();

		for (String roomStr : roomParts) {
			String[] parts = roomStr.split(":");

			ClientDoor[] doors = null;
			if (parts[9].contains(",")) {
				String[] dArr = parts[9].substring(1, parts[9].length()).split(", ");
				doors = new ClientDoor[dArr.length/6];
				for (int i = 0; i < dArr.length; i+=6) {
					doors[i/6] = new ClientDoor(Double.parseDouble(dArr[i].replaceAll("]", "")),
							Double.parseDouble(dArr[i+1].replaceAll("]", "")),
							Double.parseDouble(dArr[i+2].replaceAll("]", "")),
							dArr[i+3].replaceAll("]", ""),
							dArr[i+4], dArr[i+5]);

				}
			}

			Room r = new Room(Integer.parseInt(parts[0]), parts[1], parts[2],
					Integer.parseInt(parts[3]), Integer.parseInt(parts[4]),
					Integer.parseInt(parts[5]), Integer.parseInt(parts[6]),
					Integer.parseInt(parts[7]), doors,
					parts[10], parts[11], parts[12]);
			rooms.add(r);
			if (r.getID() == getInstance().getCurrentPos()) {
				GameData.getInstance().setCurrentRoom(r);
			}
		}

	}

	private void setCurrentRoom(Room r) {
		if (currentRoom != null) {
			if (r.getZPos() != currentRoom.getZPos()) {
				MapPanel.setZTranslation(0);
			}
		}
		this.currentRoom = r;
	}

	public int getMapWidth() {
        return getMapWidthHelper(getScalingRooms());
    }

	private List<Room> getScalingRooms() {
		List<Room> roomsToCheck = new ArrayList<>();
		roomsToCheck.addAll(rooms);
		roomsToCheck.removeIf((Room r) -> r.getZPos() != getCurrentZ());
		return roomsToCheck;
	}

	public int getMiniMapWidth() {
        return getMapWidthHelper(miniMap);
    }

	private static int getMapWidthHelper(List<Room> rooms) {
		int maxX = 0;
		Room maxRoom = null;
		for (Room r : rooms) {
			if (r.getXPos() + r.getWidth() > maxX) {
				maxX = r.getXPos() + r.getWidth();
				maxRoom = r;
			}
		}
		return maxX-getMinXHelper(rooms);
	}

	public int getMapHeight() {
		return getMapHeightHelper(getScalingRooms());
    }

    public int getMiniMapHeight() {
        return getMapHeightHelper(miniMap);
    }

	private static int getMapHeightHelper(List<Room> rooms) {
		int maxY = 0;
		for (Room r : rooms) {
			if (r.getYPos() + r.getHeight() > maxY) {
				maxY = r.getYPos() + r.getHeight();
			}
		}
		return maxY-getMinYHelper(rooms);
	}

	public void setClid(String result) {
		this.clid = result;
		Cookies.setCookie("clid", this.clid);
		notifyObservers();
	}

	public String getClid() {
		if (clid == null) {
			this.setClid("");
		}
		return clid;
	}


	public boolean amIReady() {
		for (int i = 0; i < clients.size(); ++i) {
			if (clients.get(i).equals(getClid())) {
				return ready.get(i);
			}
		}
		return false;
//		throw new IllegalStateException("Did not find our own clid!");

	}

	public void deconstructMovementData(String result) {
		String[] roomParts = result.substring(1, result.length()-1).split(", ");

		ArrayList<Integer> selectableRooms = new ArrayList<>();
		for (String r : roomParts) {
			selectableRooms.add(Integer.parseInt(r));
		}

		this.setSelectableRooms(selectableRooms);
		nextMove = "";
	}


	public void deconstructActionData(String result) {
		//System.out.println("Action data result is: " + result);
		String[] parts = result.split("<player-data-part>");

		tree = new MyTreeNode("-1<actpart>root");
		makeStringIntoTree(parts[0].substring(1,  parts[0].length()), tree);
		deconstructPlayerData(result);
		this.notifyObservers();
	}

	public static int makeStringIntoTree(String treeString, MyTreeNode node) {
		int start = 0;
		if (treeString.charAt(0) != '}') {
			while (treeString.charAt(start) != '}') {
				int ptr = start;
				while (treeString.charAt(ptr) != '{') {
					ptr++;
				}
				MyTreeNode innerNode = new MyTreeNode(treeString.substring(start, ptr));
				node.children.add(innerNode);
				start = ptr + 2 + makeStringIntoTree(treeString.substring(ptr+1), innerNode);
			}

		}
		return start;
	}

	private void setSelectableRooms(ArrayList<Integer> selectableRooms) {
		this.selectableRooms.clear();
		this.selectableRooms.addAll(selectableRooms);
		//this.notifyObservers();
	}

	private void deconstructPlayerData(String indata) {
       String[] parts = indata.split("<player-data-part>");
		GameData.getInstance().setCharacter(parts[1]);
		GameData.getInstance().setCurrentPos(Integer.parseInt(parts[2]));
		GameData.getInstance().setHealth(Double.parseDouble(parts[3]));
		GameData.getInstance().setWeight(parts[4]);
		deconstructEquipment(parts[5]);
		deconstructItems(parts[6]);
		deconstructRoomInfo(parts[7]);
		deconstructLastTurnInfo(parts[8]);
		deconstructOverlaySprites(parts[9]);
		if (!parts[10].equals("nothing")) {
            deconstructExtraEffects(parts[10]);
        } else {
			extraEffects.clear();

		}
	}

	private void deconstructExtraEffects(String part) {
		extraEffects.clear();
		String[] ees = part.split("<ee>");
		for (String ee : ees) {
			String[] data = ee.split("<eepart>");
			extraEffects.add(new ClientExtraEffect(data[0], data[1], data[2],
					Integer.parseInt(data[3]),
					Integer.parseInt(data[4]),
					Integer.parseInt(data[5]),
					Boolean.parseBoolean(data[6])));
		}
	}


	private void deconstructLastTurnInfo(String string) {
		this.lastTurnInfo .clear();
		this.lastTurnInfo.addAll(MyUtils.deconstructList(string));

	}

	private void deconstructRoomInfo(String string) {
		this.roomInfo.clear();
		this.roomInfo.addAll(MyUtils.deconstructList(string));
	}

	private void deconstructItems(String string) {
		this.items.clear();
		this.items.addAll(MyUtils.deconstructList(string));
	}

	private void deconstructEquipment(String string) {
		this.equipment.clear();
		this.equipment.addAll(MyUtils.deconstructList(string));
	}

	private void deconstructOverlaySprites(String string) {
		this.overlays.clear();
		for (String str : MyUtils.deconstructList(string)) {
			String[] strs = str.split("<o-p>");
			overlays.add(new OverlaySprite(strs[0],
								Double.parseDouble(strs[1]),
								Double.parseDouble(strs[2]),
                                Double.parseDouble(strs[3]),
                                Integer.parseInt(strs[4]),
                                Integer.parseInt(strs[5]),
                                strs[6], strs[7],
								Integer.parseInt(strs[8]),
								Integer.parseInt(strs[9]),
								Boolean.parseBoolean(strs[10])));
		}

	}

	private void setCurrentPos(int pos) {
	    if (playerIsInSpace()) {
            if (oldSpacePos != getSpaceCoordinates()) {
                MapPanel.setXTranslation(0);
                MapPanel.setYTranslation(0);
                MapPanel.setZTranslation(0);
            }
            oldSpacePos = getSpaceCoordinates();
        } else {
            if (pos != currentPos) {
                MapPanel.setXTranslation(0);
                MapPanel.setYTranslation(0);
                MapPanel.setZTranslation(0);
            }
        }
		this.currentPos = pos;
	}

	private void setHealth(double d) {
		this.health = d;
	}

	private void setCharacter(String string) {
		this.character = string;
	}

	public ArrayList<Integer> getSelectableRooms() {
		return this.selectableRooms;
	}

	public String getCharacter() {
		return this.character;
	}

	public double getHealth() {
		return this.health;
	}

	public Room getCurrentRoom() {
		for (Room r : rooms) {
			if (r.getID() == this.currentPos) {
				return r;
			}
		}
		return null;
	}

	public ArrayList<String> getItems() {
		return items;
	}

	public ArrayList<String> getRoomInfo() {
		return roomInfo;
	}

	public ArrayList<String> getLastTurnInfo() {
		return lastTurnInfo;
	}


	public MyTreeNode getActionTree() {
		return tree;
	}

	public int getCurrentPos() {
		return currentPos;
	}

	public String getHost() {
		return this.host;
	}

	public void setHost(String value) {
		this.host = value;
		Cookies.setCookie("host", value);
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getPort() {
		return port;
	}

	public int getRound() {
		return round;
	}

	public void setSummaryString(String result) {
		this.summaryString  = decodeBase64Images(result);
	}

	public String getServersSuggestedClientVersion() {
		return serversSuggestedClientVersion;
	}

	public String getSummaryString() {
		return this.summaryString;
	}

	public void putJob(String substring, boolean val) {
		for (int i = 0; i < jobNames.size(); ++i) {
			if (jobNames.get(i).equals(substring)) {
				jobVals.set(i, val);
				return;
			}
		}

		this.jobNames.add(substring);
		this.jobVals.add(val);
	}

	public void sendJobs() {
		String res = "{";
		for (int i = 0; i < jobNames.size(); ++i) {
			res += jobNames.get(i) + "=" + jobVals.get(i);
			if (i < jobNames.size()-1) {
				res += ",";
			}
		}
		res += "}";
		Cookies.setCookie("jobselections", res);

		ServerCommunicator.send(getClid() + " JOBS " + res, new MyCallback<String>() {

			@Override
			public void onSuccess(String result) {

			}

			@Override
			public void onFail() {
				System.out.println("Failed to send JOBS message to server");
			}
		});


	}

	public void setAllJobs(boolean b) {
		for (int i = 0; i < jobVals.size(); ++i) {
			jobVals.set(i, b);
		}

	}

	public int getNoOfRounds() {
		return noOfRounds;
	}

	public void setNoOfRounds(int num) {
		this.noOfRounds = num;
		sendSettings();

	}

	public void sendSettings() {
		ServerCommunicator.send(GameData.getInstance().getClid() + " SETTINGS " +
								getNoOfRounds() + "<player-data-part>" + getSelectedMode() + "<player-data-part>" +
								getLastMessage() + "<player-data-part>" + getPlayerSettings(),
				new MyCallback<String>() {

			@Override
			public void onSuccess(String result) {
				ArrayList<String> strs = MyUtils.deconstructList(result);
				settingNames.clear();
				settingBools.clear();
				//GWT.log(strs.toString());
				//Window.alert(result);
				for (String str : strs) {
					String[] parts = str.split(",");
					settingNames.add(parts[0]);
					settingBools.add(Boolean.parseBoolean(parts[1]));
				}
			}

					@Override
					public void onFail() {
						System.out.println("Failed to send SETTINGS message to server.");
					}
				});
	}

	private String getPlayerSettings() {
		String res = "[";

		for (int i = 0; i < getSettingNames().size() ; ++i) {
			res += getSettingNames().get(i) + "=" + getSettingBools().get(i);
			if (i < getSettingNames().size()-1) {
				res += ",";
			}
		}

		return res + "]";
	}

	public ArrayList<String> getSettingNames() {
		return settingNames;
	}

	public ArrayList<Boolean> getSettingBools() {
		return settingBools;
	}

	public String getSelectedMode() {
		return selectedMode;
	}

	public ArrayList<String> getModeAlternatives() {
		return modeAlternatives ;
	}

	public void setSelectedMode(String selectedItemText) {
		this.selectedMode = selectedItemText;
		sendSettings();
	}

	public void setLastMessage(int lastMess) {
		this.lastMessage = lastMess;
	}

	public int getLastMessage() {
		return this.lastMessage;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String d) {
		this.weight = d;
	}

	public ArrayList<OverlaySprite> getOverlaySprites() {
		return overlays;
	}

	public void setPlayerSetting(String setName, Boolean value) {
		//Window.alert("Found setting to set" + value);
		for (int i = 0; i < settingNames.size(); ++i) {
			if (settingNames.get(i).equals(setName)) {
				settingBools.set(i, value);

				break;
			}
		}
		sendSettings();

	}

	public void clearActionList() {
		tree = new MyTreeNode("-1<actpart>root");
		nextAction = "Do Nothing";
	}

	public void clearRoomColors() {
		selectableRooms.clear();
		for (Room room : rooms) {
			//room.setGrayBackground();
		}
		//notifyObservers();
	}

	public void setSelectedRoom(int id) {
		this.selectedRoom  = id;
	}

	public int getSelectedRoom() {
		return selectedRoom;
	}

	public int getMinY() {
        return getMinYHelper(getScalingRooms());
    }

    public int getMiniMapMinY() {
        return getMinYHelper(miniMap);
    }

	private static int getMinYHelper(List<Room> rooms) {
		int minY = 1284789249;
		for (Room r : rooms) {
			if (r.getYPos() < minY) {
				minY = r.getYPos();
			}
		}
		return minY;
	}

	public int getMinX() {
        return getMinXHelper(getScalingRooms());
    }

    public int getMiniMapMinX() {
        return getMinXHelper(miniMap);
    }

	private static int getMinXHelper(List<Room> rooms) {
		int minX = 1248203412;
		for (Room r : rooms) {
			if (r.getXPos() < minX) {
				minX = r.getXPos();
			}
		}
		return minX;
	}

	public ArrayList<Boolean> getSpectatorList() {
		return spectators;
	}

	public boolean amISpectator() {
		for (int i = 0; i < getClientList().size(); ++i) {
			if (getClientList().get(i).equals(getClid())) {
				return getSpectatorList().get(i);
			}
		}

	//	Window.alert("Did not find own clid: " + getClid() + ", clients: " + getClientList().toString() + ", spectators " + getSpectatorList().toString());
		return false;

	}


    public String getNextAction() {
        return nextAction;
    }

    private void setNextAction(String replace) {
        nextAction = replace;
    }


    public String getNextMove() {
        return nextMove;
    }

    public List<Room> getMiniMap() {
        return miniMap;
    }

    public boolean isASelectableRoom(int id) {
            for (Room r2 : GameData.getInstance().getRooms()) {
                if (id == r2.getID()) {
                    return true;
                }
            }
            return false;
    }

	public int getCurrentZ() {
		if (character == null) {
			return 0;
		}
		if (playerIsInSpace()) {
			return (int)getSpaceCoordinates()[2];
		}
		if (currentRoom != null) {
			return currentRoom.getZPos();
		}
		return 0;
	}

	public List<String> getEquipment() {
		return equipment;
	}

	public CharacterStyle getStyle() {
		return characterStyle;
	}


	public void deconstructFancyFrameData(String result) {
		String[] parts = result.split("<part>");
		fancyFrameState = Integer.parseInt(parts[0]);
		fancyFrameTitle = parts[1];
		fancyFrameHasInput = parts[2].equals("HAS INPUT");
		fancyFrameContent = decodeBase64Images(parts[3]);
		Scanner scan = new Scanner(parts[4]);
		scan.useDelimiter(":");
		fancyFrameDimension = new Dimension(scan.nextInt(), scan.nextInt());
		notifyObservers();
	}

	public String decodeBase64Images(String t) {
		if (t.contains("<img src=\"data:image/png;base64")) {
			Pattern pattern = Pattern.compile("<img src=\"data:image/png;base64,[\\w\\+/=]*\"></img>");
			Matcher matcher = pattern.matcher(t);
			int uid = 0;
			while (matcher.find()) {
				String datapart = matcher.group().replace("<img src=\"data:image/png;base64,", "");
				datapart = datapart.replace("\"></img>", "");
				BufferedImage buf = SpriteManager.setBase64(datapart);
				try {
					File file = File.createTempFile("ss13img_" + (uid++) + "_", ".png");
					ImageIO.write(buf, "png", file);
					t = t.replace("data:image/png;base64," + datapart, "file:///" + file.getAbsolutePath());
					//System.out.println("Path to image is: " + "file://" + file.getAbsolutePath());
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
		return t;
	}

	public String getFancyFrameContent() {
		return fancyFrameContent;
	}

	public String getFancyFrameTitle() {
		return fancyFrameTitle;
	}

	public boolean getFancyFrameInputField() {
		return fancyFrameHasInput;
	}

	public Dimension getFancyFrameDimensions() {
		return fancyFrameDimension;
	}

	public boolean playerIsInSpace() {
		return character.contains("(In Space at ");
	}

	public double[] getSpaceCoordinates() {
		String[] rest = character.split("\\(In Space at ");
		rest = rest[1].split("\\)");
		String[] strs = rest[0].substring(0, rest[0].length()-1).split(",");
		return new double[]{Double.parseDouble(strs[0]), Double.parseDouble(strs[1]), Double.parseDouble(strs[2])};
	}

	public List<Room> getRoomsToDraw() {
		List<Room> roomList = new ArrayList<>();
		roomList.addAll(GameData.getInstance().getMiniMap());
		for (Room r : GameData.getInstance().getRooms()) {
			if (!existsIn(r.getID(), roomList)) {
				roomList.add(r);
			}
		}
		Collections.sort(roomList);
		return roomList;
	}

	private boolean existsIn(int id, List<Room> roomList) {
		for (Room r : roomList) {
			if (id == r.getID()) {
				return true;
			}
		}
		return false;
	}

	public int getDataState() {
		return playerDataState;
	}


    public List<ClientExtraEffect> getExtraEffects() {
    	return extraEffects;
    }
}
