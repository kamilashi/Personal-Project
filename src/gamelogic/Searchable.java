package gamelogic;

public interface Searchable extends Interactible {
	void setItem(Item item);
    Item extractItem(String itemName);
	Item search();
    
}
