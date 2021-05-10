package gamelogic;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Class Location - a Location in an adventure game.
 *
 * A "Location" represents one location in the scenery of the game. It is
 * connected to other Locations via exits. For each existing exit, the Location
 * stores a reference to the neighboring Location.
 * 
 */

public class Location implements Inspectable {
	private String description;
	private String name;
	private HashMap<String, Location> exits; // stores exits of this Location.
	private HashMap<String, Player> players;
	private HashMap<String, NPC> NPCs;
	private HashMap<String, Container> containers;
	// private ArrayList<Item> items;
	private Inventory items;

	/**
	 * Create a Location described "description".
	 * 
	 * @param description The Location's description.
	 */
	public Location(String name, String description) {
		this.name = name;
		if(description!=null)
		{
			this.description = String.format(description);
		}
		else
		{
			this.description = "Location: " + name;
		}
		items = new Inventory(10000);
		exits = new HashMap<>();
		players = new HashMap<>();
		containers = new HashMap<>();
		NPCs = new HashMap<>();
	}

	/**
	 * Define an exit from this Location.
	 * 
	 * @param direction The direction of the exit.
	 * @param neighbor  The Location to which the exit leads.
	 */
	public void setExit(String direction, Location neighbor) {
		exits.put(direction, neighbor);
	}

	public String getName() {
		return name;
	}

	public void setItem(Item item) {
		items.add(item);
	}

	public void setNPC(NPC npc) {
		NPCs.put(npc.getName(), npc);
	}

	/**
	 * Print the of description of the Location
	 */
	public void printDescription() {
		IORedirect.printOutput(description);
	}

	public void printInspectables() {
		ArrayList<Inspectable> objects = this.getInspectables();
		if (objects != null) {
			IORedirect.printOutput(String.format("On this location: %n"));
			for (Inspectable inspectable : objects) {
				IORedirect.printOutput(inspectable.getName());
			}
		}
	}

	public void printSurriondings() {
		IORedirect.printOutput(String.format("On this location"));
		IORedirect.printOutput(String.format("%nPlayers:"));
		for (Player player : players.values()) {
			IORedirect.printOutput(player.getName());
		}
		if (!NPCs.isEmpty()) {
			IORedirect.printOutput(String.format("%nNPCs: "));
			for (NPC npc : NPCs.values()) {
				IORedirect.printOutput(npc.getName());
			}
		}
		if (!containers.isEmpty()) {
			IORedirect.printOutput(String.format("%nContainers: "));
			for (Container container : containers.values()) {
				IORedirect.printOutput(container.getName());
			}
		}
		
		if (!items.isEmpty()) {
			IORedirect.printOutput(String.format("%nItems: "));
			items.print();
		}
		
		

	}

	public Location getExit(String direction) {
		return exits.get(direction);
	}

	public ArrayList<Item> getItems() {
		return items.getItems();
	}

	public ArrayList<Player> getPlayers() {
		Collection<Player> values = players.values();
		ArrayList<Player> listOfValues = new ArrayList<>(values);
		return listOfValues;
	}

	public Item getItem(String itemName) {
		return items.getItem(itemName);
	}

	public Inspectable getInspectable(String objectName) {

		Inspectable object = items.getItem(objectName);
		if (object == null) {
			object = players.get(objectName);
		}
		if (object == null) {
			object = NPCs.get(objectName);
		}

		return object;
	}
	
	public Searchable getSearchable(String objectName) {

		Searchable object = containers.get(objectName);
		
		if (object == null) {
			object = NPCs.get(objectName);
		}

		return object;
	}

	public ArrayList<Inspectable> getInspectables() {

		ArrayList<Inspectable> objects = new ArrayList<Inspectable>(items.getItems());
		objects.addAll(this.getPlayers());

		return objects;
	}

	public void removeItem(String itemName) {
		items.remove(itemName);
	}

	public Item extractItem(String itemName) {
		return items.extract(itemName);
	}

	public void addPlayer(Player player) {
		players.put(player.getName(), player);
	}
	public void setContainer(Container container) {
		containers.put(container.getName(), container);
	}

	public void removePlayer(Player player) {
		players.remove(player.getName());
	}

	public NPC getNPC(String npcName) {
		return NPCs.get(npcName);
	}

	public void addNpc(NPC npc) {
		NPCs.put(npc.getName(), npc);

	}

	public Unlockable getUnlockable(String objectName) {
		return containers.get(objectName);
	}
}