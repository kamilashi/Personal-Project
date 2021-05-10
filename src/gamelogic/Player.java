package gamelogic;

import java.util.HashMap;

import gamelogic.PlayerStatus.Status;

public class Player extends Character implements Inspectable {

	private Parser parser;
	private boolean quit = false;
	PlayerStatus status = new PlayerStatus();
	Interactible interactiveObject;
	private boolean interacting = false;
	
	public Player(String name, Location currentLocation) {
		super(name, currentLocation);
		currentLocation.addPlayer(this);
		
		setHpMax(100);
		setHpCurrent(100);
		
		this.description = "default player description";
		this.parser = new Parser();
		
		status.setStatus(Status.IDLE);
	}
	

	public void playTurn(String userInput) {
		
		if(!interacting )
		{
		Command command = parser.getCommand(userInput);
		quit = processCommand(command); // don't need this assignment
		}
		else
		{
			Response response = interactiveObject.process(userInput);
			interacting = response.stillInteracting;
			if(!interacting)
			{
				interactiveObject = null;
				status.setStatus(Status.IDLE);
			}
			if(response.item!=null)
			{
				take(response.item);
			}
		}
	}

	private boolean processCommand(Command command) // change boolean to void
	{
		boolean wantToQuit = false;

		CommandWord commandWord = command.getCommandWord();

		switch (commandWord) {
		
		case UNKNOWN:
			IORedirect.printOutput("?");
			return wantToQuit;

		case HELP:
			printHelp();
			break;

		case GO:
			goTo(command);
			break;

		case TAKE:
			pickUp(command);
			break;

		case DROP:
			drop(command);
			break;

		case INSPECT:
			inspect(command);
			break;
			
		case UNLOCK:
			unlock(command);
			break;

		case TALKTO:
			talkTo(command);
			break;

		case INVENTORY:
			this.printInventory();
			break;

		case QUIT:
			return wantToQuit = quit(command);

		case SEARCH:
			search(command);
			break;

		default:
			break;
		}

		// else command not recognized.
		return wantToQuit;
	}

	public void unlock(Command command) {
		String objectName = command.getSecondWord();
		Unlockable object = currentLocation.getUnlockable(objectName);
		object.unlockBy(this);
		
	}

	public void talkTo(Command command) {
		String npcName = null;
		if (command.hasThirdWord()) {
			npcName = command.getThirdWord();
		} else if ((command.hasSecondWord()) & (!command.hasThirdWord())) {
			npcName = command.getSecondWord();
		}
		NPC npc = currentLocation.getNPC(npcName);
		if (npc != null) {
			IORedirect.printOutput(this.name + " approaches " + npcName);
			
			setInteracting(true,Status.TALKING,npc);
			
			npc.startDialogue(this);
		} else {
			IORedirect.printNotFound();
		}

	}

	/**
	 * Try to go in to one direction. If there is an exit, enter the new Location,
	 * otherwise print an error message.
	 */
	public void goTo(Command command) {
		String direction = command.getSecondWord();
		Location nextLocation = null;

		if (command.hasSecondWord()) {
			if (direction.equals("back")) {
				nextLocation = previousLocation;
			} else { // Try to leave current Location.
				nextLocation = currentLocation.getExit(direction);
			}
			if (nextLocation == null) {
				IORedirect.printOutput("Can't go there!");
			} 
			else if (nextLocation.getName().contains("bedLocation")) {
				nextLocation.printDescription();
			} else {

				previousLocation = currentLocation;
				currentLocation = nextLocation;
				currentLocation.printDescription();
				previousLocation.removePlayer(this);
				currentLocation.addPlayer(this);
			}
		} else {
			// if there is no second word, we don't know where to go...
			IORedirect.printOutput("Go where?");
			return;
		}
	}

	/**
	 * pick Up an item
	 */
	public void pickUp(Command command) {
		String itemName = null;
		
		if(command.hasThirdWord())
		{
		itemName = command.getSecondWord() + " " + command.getThirdWord();
		}
		else
		{
		itemName = command.getSecondWord();
		}
		
		if (itemName==null) {
			IORedirect.printOutput("Take what?");
			return;
		}
		
		Item extractedItem = currentLocation.extractItem(itemName);

		if (extractedItem == null) {
			IORedirect.printOutput("Can't see it anywhere");
			return;
		} else {
			take(extractedItem);
		}

	}
	public void take(Item item) {
		if (item.getWeight() > tempStrength) {
			IORedirect.printOutput("Too heavy!");
		} else {
			boolean addedSuccessfully = inventory.add(item);
			if (addedSuccessfully) {
				setChanged();
				this.notifyObservers(item);
				IORedirect.printOutput(this.name + " takes " + item.getName());
				//printInventory();  //only with console! otherwise looks ugly
			} else {
				IORedirect.printOutput(this.name + "'s inventory is full! ");
				IORedirect.printOutput("Dropping the "+item.name+" on the floor.");
				currentLocation.setItem(item);
			}
		}
		
	}

	public void drop(Command command) {

		String itemName = null;
		
		if(command.hasThirdWord())
		{
		itemName = command.getSecondWord() + " " + command.getThirdWord();
		}
		else
		{
		itemName = command.getSecondWord();
		}
		
		if (itemName==null) {
			IORedirect.printOutput("Drop what?");
			return;
		}

		Item extractedItem = inventory.extract(itemName);
		if (extractedItem != null) {
			IORedirect.printOutput(this.name + " drops the " + extractedItem.getName() + " on the floor");
			currentLocation.setItem(extractedItem);
			return;
		}
		IORedirect.printNotFound();
		return;
	}

	public void inspect(Command command) {
		
		if (command.hasSecondWord()) {
			String objectName = command.getSecondWord();
			if(command.hasThirdWord())
			{
				objectName+= " "+command.getThirdWord();
			}

			Inspectable object = currentLocation.getInspectable(objectName);

			try {
				object.printDescription();
			} catch (Exception e) {
				IORedirect.printNotFound();
			}

		} else {
			currentLocation.printDescription();
			IORedirect.printOutput(" ");
			currentLocation.printSurriondings();
		}
	}

	public boolean quit(Command command) {
		if (command.hasSecondWord()) {
			IORedirect.printOutput("Quit what?");
			return false;
		} else {
			IORedirect.printOutput(this.name + " has quit");
			quit = true;
			return true; // signal that we want to exit
		}

	}
	public void search(Command command) {
		Searchable object = null;
		Item extractedItem = null;
		String containerName = command.getSecondWord();
		if (!command.hasSecondWord()) {
			IORedirect.printOutput("Search what?");
			;
		} else {
			object = currentLocation.getSearchable(containerName);
		}
		if(object!=null)
		{
			setInteracting(true, Status.SEARCHING, object);
			extractedItem = object.search();
		}
		
		if(extractedItem!=null)
		{
			take(extractedItem);
		}
	}

	private void printHelp() {
		IORedirect.printOutput(String.format("Available commands: %n"));
		parser.showCommands();
	}
	
	
	 public void printInventory() {
	 super.printInventory();
	 IORedirect.printOutput("Total weight: " + inventory.getWeight() + "/" + inventory.getWeightCapacity());
	 }
	 //public Item[] getInventoryItems() {
		// return (Item[]) inventory.getItems().toArray();
		// }
	 public HashMap<String,Item> getItems() {
			return inventory.getItemsAsHashmap();
		}
	 
	 public HashMap<String,Integer> getInventoryCounts() {
		 return inventory.getItemCounts();
		 }

	public boolean getQuitStatus() {
		return quit;
	}

	public boolean hasItem(String itemName) {
		return (inventory.getItem(itemName) != null);
	}

	public String getStatusName() 
	{
		if(interacting)
		{
			return status.getStatus().toString() + ":";
		}
		else
		{
			return "Current location:";
		}
	}
	public String getStatusDescription() 
	{
		if(interacting)
		{
			return interactiveObject.getName();
		}
		else
		{
			return currentLocation.getName();
		}
	}
	public boolean isInteracting()
	{
		return interacting;
	}
	public void setInteracting(boolean interacting, Status st, Interactible object)
	{
		this.interacting = interacting;
		this.status.setStatus(st);
		this.interactiveObject = object;
	}
	
	public PlayerStatus getStatus() {
		return status;
	}

	public String getMaxWeight() {
		return inventory.getWeightCapacity()+"";
	}
	public String getCurrentWeight() {
		return inventory.getWeight()+"";
	}


	public Interactible getInteractiveObject() {
		// TODO Auto-generated method stub
		return interactiveObject;
	}

}