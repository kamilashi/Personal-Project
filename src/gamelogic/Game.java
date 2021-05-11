package gamelogic;

import java.io.IOException;

/**
 * New Game
 */

import java.util.HashMap;
import java.util.LinkedHashMap;

import conditions.Condition;

public class Game {

	private Player currentPlayer = null, lemuel, isaac, derkhan, yagharek;

	private HashMap<String, Location> locations;
	private LinkedHashMap<String, Player> players;

	/**
	 * Create the Game and initialize its internal map.
	 */
	public Game() {

		initialize();

	}

	/**
	 * Main play routine.
	 */
	public void playOnConsole(String playerName) {
		
		currentPlayer = players.get(playerName);
		
		IORedirect.activateConsole();
		printWelcome();

		String commandLine;
		if (currentPlayer != null) {
			while ((currentPlayer.getQuitStatus() == false) && (currentPlayer.isAlive())) {
				commandLine = IORedirect.getInput();
				// String output = currentPlayer.playTurn(commandLine);
				currentPlayer.playTurn(commandLine);

			}
		}
	}

	public void playWithGUI(String playerName) throws IOException {
		
		currentPlayer = players.get(playerName);
		IORedirect.activateGUI(this);
		printWelcome();
		currentPlayer = players.get(playerName);
		
	}
	public void play(String commandLine)			//for GUI
	{
		if (currentPlayer != null) {
			if ((currentPlayer.getQuitStatus() == false) && (currentPlayer.isAlive())) {
				currentPlayer.playTurn(commandLine);

			}
		}
	}
	
	
	private void initialize() {
		Location theCrow, brockMarsh;

		// create the Locations
		theCrow = new Location("The Crow",null);
		brockMarsh = new Location("Broke Marsh", null);

		// Initialize Location exits
		theCrow.setExit("south", brockMarsh);
		brockMarsh.setExit("north", theCrow);

		// initializing items:
		Item rock = new Item("rock", "Just a rock", 0, 10);
		Item watch = new Item("watch", "Old wrist watch. It shows <currentTime>", 20, 2);
		Item scissors = new Item("scissors", "Just scissors", 2, 5);
		Item paper = new Item("paper", "Just paper", 2, 1);
		Item lockPick = new Item("lock pick", "a lock pick, can be used to open locks on containers", 10, 1);
		Container barrell = new Container("barrell", "Just a barrell", 1, 200, 200);

		barrell.setItem(paper);
		barrell.setItem(paper);
		barrell.setItem(scissors);
		barrell.setItem(scissors);

		Container drawer = new Container("drawer", "Just a drawer", 1, 400, 100);
		// drawer.setLocked(new Condition<Player>(Predicates.requiresItem("rock"),"Rock
		// is needed to open the drawer","drawer opened with a rock","no rocks to open
		// the drawer"));
		drawer.setLocked(new Condition<Player>(Predicates.requiresItem("rock")));
		drawer.setItem(paper);
		drawer.setItem(watch);

		// filling Locations with items:
		theCrow.setItem(rock);
		theCrow.setItem(rock);
		theCrow.setItem(rock);
		theCrow.setItem(scissors);
		theCrow.setItem(paper);
		theCrow.setItem(lockPick);

		theCrow.setContainer(barrell);
		theCrow.setContainer(drawer);

		locations = new HashMap<>();
		locations.put(theCrow.getName(), theCrow);
		locations.put(brockMarsh.getName(), brockMarsh);

		lemuel = new Player("Lemuel", theCrow);
		isaac = new Player("Isaac", theCrow);
		derkhan = new Player("Derkhan", theCrow);
		yagharek = new Player("Yagharek", theCrow);

		NPC npc = new NPC("npc", theCrow); // what if multiple npcs are needed that are the same?
		npc.initiateDefaultStates();
		npc.setLink("Can you tell me the time?", "It's <currentTime>", "You don't have scissors", "ATTENDING",
				"TELLTIME", null, Predicates.requiresItem("scissors"));
		npc.setBye();

		
		//isaac.take(rock);
		
		players = new LinkedHashMap<>();
		players.put("Lemuel", lemuel);
		players.put("Isaac", isaac);
		players.put("Derkhan", derkhan);
		players.put("Yagharek", yagharek);
	}

	/**
	 * Print out the opening message for the Game.
	 */
	private void printWelcome() {
		IORedirect.printOutput("Welcome to the game. I haven't come up with a proper name yet.\n");
		IORedirect.printOutput("The input field is the gray box below, you can type the commands there in the following form: \ncommand word + optional word2 + optional word3. \nFor example: \ngo south or take [itemName].\n");
		IORedirect.printOutput("Typing [help] (without the brackets) will display all the commands supported so far.\n\nStart by typing [inspect] to inspect the area around you and see all the interactible objects. Good luck!");
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public void updateCurrentPlayer(String playerName) 
	{
		currentPlayer = (players.get(playerName) != null)? players.get(playerName):currentPlayer;
	}

}