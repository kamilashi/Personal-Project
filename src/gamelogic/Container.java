package gamelogic;

import conditions.Condition;

/**
 * Сlass Container is inherited from its main Item class.
 */
public class Container extends Item implements Inspectable, Searchable, Unlockable, Interactible {
	private Inventory items;

	private boolean locked = false;

	public Container(String name, String description, int price, int weight, int weightCapacity) {
		super(name, description, price, weight);
		items = new Inventory(weightCapacity);
	}

	public void setItem(Item item) {
		items.add(item);
	}

	public void setLocked(Condition<Player> c) {
		this.locked = true;
		items.setCondition(c);
	}

	public void setUnlocked() {
		this.locked = false;
		items.setCondition(null);
	}
	public boolean isLocked() {
		return locked;
	}
	
	public boolean unlockBy(Player player) {
		Condition<Player> condition = items.getCondition();
		if(condition!=null)
		{	String warning = condition.toString();
			if(warning!=null)
			{
			IORedirect.printOutput(warning);
			}
				if(condition.getPredicate().test(player))
				{	
					
					if(condition.getPositiveResponse()!=null)
					{
					IORedirect.printOutput(condition.getPositiveResponse());
					}
					else
					{
					IORedirect.printOutput("Unlocked the " + this.getName());	
					}
					setUnlocked();
				}
				else 
				{	
					if(condition.getNegativeResponse()!=null)
					{
					IORedirect.printOutput(condition.getNegativeResponse());
					}
					else
					{
					IORedirect.printOutput("Couldn't unlock the " + this.getName());	
					}
					return locked;
				}
		}
		
		return locked;
	}

	public Item extractItem(String itemName) {
		return items.extract(itemName);
	}

	public void printContainedItems() {
			IORedirect.printOutput("Inside the " + this.name + ": ");
			items.print();
	}

	@Override
	public Item search() {
		
		//either no predicate or it's fulfilled
		if(!this.isLocked())
		{
			this.printContainedItems();
			if (!items.isEmpty()) {
				IORedirect.printOutput("Choose an item to take: ");
				//String itemName = IORedirect.getInput();
				//Item extractedItem = extractItem(itemName);
				//return extractedItem;
			}
		}else
		{
			IORedirect.printOutput("It's locked");
		}
		return null;
	}

	@Override
	public Response process(String itemName) {
		Item extractedItem = extractItem(itemName);
		//boolean keepInteracting = extractedItem!=null;
		return new Response(false,extractedItem);
	}
}
