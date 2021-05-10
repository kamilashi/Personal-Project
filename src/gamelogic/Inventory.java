package gamelogic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;
//import java.util.function.Predicate;
import conditions.Condition;

public class Inventory {

	private HashMap<String, Item> items;
	private HashMap<String, Integer> itemCounter;
	//private Predicate<Player> predicate = null;
	private Condition<Player> condition = null;
	private int weightCapacity = 9999;
	private int weight = 0;

	public Inventory(int weightCapacity) {
		this.weightCapacity = weightCapacity;
		items = new HashMap<>();
		itemCounter = new HashMap<>();
	}

	public boolean add(Item item) {
		if (weight + item.getWeight() <= weightCapacity) {
			items.put(item.getName(), item);
			int count = itemCounter.getOrDefault(item.getName(), 0);
			itemCounter.put(item.getName(), count + 1);
			weight += item.getWeight();
			return true;
		}
		return false;
	}

	public Item extract(String itemName) {
		Item item = items.get(itemName);
		if (item != null) {
			int count = itemCounter.getOrDefault(item.getName(), 0);
			itemCounter.put(itemName, --count);
			if (count == 0) {
				items.remove(itemName);
			}
			weight -= item.getWeight();
		}
		return item;
	}

	public void remove(String itemName) {
		Item item = items.get(itemName);
		try {
			int count = itemCounter.getOrDefault(itemName, 0);
			if (--count == 0) {
				itemCounter.remove(itemName);
			} else {
				itemCounter.put(itemName, count);
			}
			items.remove(itemName);
			weight -= item.getWeight();
		} catch (Exception e) {
		}
	}

	public Item getItem(String itemName) {
		return items.get(itemName);
	}

	public ArrayList<Item> getItems() {
		Collection<Item> values = items.values();
		ArrayList<Item> listOfValues = new ArrayList<>(values);
		return listOfValues;
	}
	public HashMap<String,Item> getItemsAsHashmap() {
		return items;
	}
	public HashMap<String,Integer> getItemCounts() {
		return itemCounter;
	}

	public void print() {
		if (!itemCounter.isEmpty()) {
			for (Entry<String, Integer> entry : itemCounter.entrySet()) {
				IORedirect.printOutput(entry.getKey() + " (" + entry.getValue() + ")");
			}
			
		} else {
			IORedirect.printOutput(String.format("%nEmpty"));
		}
	}

	public void printWithPrice() {
		if (!items.isEmpty()) {
			IORedirect.printOutput(String.format("%n    ITEM (#)   | WEIGHT |  VALUE |"));
			for (Entry<String, Item> entry : items.entrySet()) {
				IORedirect.printOutput(String.format("%-15s|  %-5d |  %-5d |",
						entry.getKey() + " (" + itemCounter.get(entry.getKey()) + ") ",
						items.get(entry.getKey()).getWeight(), items.get(entry.getKey()).getPrice()));

			}
			
		} else {
			IORedirect.printOutput(String.format("%nEmpty"));
		}
	}
	
	public void setCondition(Condition<Player> condition) {
		this.condition = condition;
	}
	
	public Condition<Player> getCondition() {
		return condition;
	}

	public int getWeight() {
		return weight;
	}

	public int getWeightCapacity() {
		return weightCapacity;
	}

	public boolean isEmpty() {
		return items.isEmpty();
	}
}
