package gamelogic;


public class Item  implements Inspectable
{
	protected String description;
	protected int price;
    protected int weight;
    protected String name;
   
    /**
     * Constructor for objects of class Item
     */
    public Item(String name, String description, int price, int weight) 
    {
        this.name = name;
        this.description = String.format(description);
        this.price = price;
        this.weight = weight;
    }
    /**
     * methods:
     */
    public String getDescription()
    {
        return description;
    }
    public String getName()
    {
        return name;
    }
    public int getPrice()
    {
        return price;
    }
    public int getWeight()
    {
        return weight;
    }
    public Item getContainedItem()
    {   
        return null;
    }
    public void printDescription()
    {
        IORedirect.printOutput(String.format("%s",description));
    }
    
    public void setItem(Item item)
    {   return;
    }
    
	public Item extractItem(String itemName) {
		return null;
	}
	
	public Item search() {
		return null;
	}
	
}
