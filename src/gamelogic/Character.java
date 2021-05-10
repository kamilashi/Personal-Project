package gamelogic;

import java.util.Observable;

@SuppressWarnings("deprecation")
public abstract class Character extends Observable implements Inspectable
	{
	protected String name;
	protected String description;
    protected Inventory inventory;

    protected boolean isAlive = true;
    
	protected Location currentLocation;
    protected Location previousLocation; 
    
    private int hpMax = 100, hpCurrent;    //temporarily
    

    protected int tempStrength = 100;

	public Character(String name,Location currentLocation)
    {
        this.currentLocation = currentLocation;
        this.name = name;
        this.previousLocation = currentLocation;
        this.description = "default description";
        inventory = new Inventory(tempStrength*2);
    }
	
	public void setDescription(String description)
	{
		 this.description = description;
	}
	
	public String getDescription( )
	{
		 return description;
	}
	
	public String getName()
	{
		return name;
	}

	public boolean isAlive() {
		return isAlive;
	}
	
	public void printDescription()
    {
		IORedirect.printOutput(description);
    }
	
	public void printInventory()
    {
	   IORedirect.printOutput(this.name +"'s inventory:");
       inventory.printWithPrice();        
    }

	public Location getCurrentLocation() {
		return currentLocation;
	}
	
	public int getHpMax() {
		return hpMax;
	}

	public void setHpMax(int hpMax) {
		this.hpMax = hpMax;
	}

	public int getHpCurrent() {
		return hpCurrent;
	}

	public void setHpCurrent(int hpCurrent) {
		this.hpCurrent = hpCurrent;
	}
    
}
