package gamelogic;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.function.Predicate;

public class NPC extends Character implements Inspectable,Searchable {
	// private HashMap<String, State> states;
	private HashMap<String, State> states;
	private State currentState;
	private State idle, attending; // default states
	private boolean talking;
	private Player interactingPlayer = null;

	private class Link {
		private String key;
		private String playerLine;
		private String positiveResponse;
		private String negativeResponse;
		private Predicate<Player> predicate;
		private boolean isTerminator = false;

		private Link(String key, String playerLine, String positiveResponse) {
			this.key = key;
			this.playerLine = playerLine;
			this.positiveResponse = positiveResponse;
			this.negativeResponse = "";
			this.predicate = null;
		}

		private Link(String key, String playerLine, String positiveResponse, String negativeResponse, Predicate<Player> p) {
			this.key = key;
			this.playerLine = playerLine;
			this.positiveResponse = positiveResponse;
			this.negativeResponse = negativeResponse; // only positive response in this one
			this.predicate = p;
		}

		public void setTerminator() {
			this.isTerminator = true;
		}

		public String getKey() {
			return key;
		}

		public String getPlayerLine() {
			return playerLine;
			}
		}

		private class State {
		private String stateName;
		private String stateResponse = "";
		private int numberOfLinks = 0;
		private LinkedHashMap<Link, State> links;

		public State(String stateName) {
			this.stateName = stateName;
			links = new LinkedHashMap<>();
		}

		public State(String stateName, String stateResponse) {
			this.stateName = stateName;
			this.stateResponse = stateResponse;
			links = new LinkedHashMap<>();
		}

		/**
		 * connect two existing states by an existing link
		 */
		public void setLink(Link link, State desctination) {
			links.put(link, desctination);
			numberOfLinks++;
		}

		/**
		 * print all the links
		 */
		public void printLinks() {
			if (!links.isEmpty()) {
				for (Entry<Link, State> entry : links.entrySet()) {
					IORedirect.printOutput(entry.getKey().getKey() + ". " + entry.getKey().getPlayerLine());
				}
			}
			IORedirect.printOutput(" ");
		}

		public int getNumberOfLinks() {
			return numberOfLinks;
		}

		public String getName() {
			return stateName;
		}
	}

	public NPC(String name, Location currentLocation) {
		super(name, currentLocation);
		currentLocation.addNpc(this);
		setHpMax(500);
		setHpCurrent(500);
		states = new HashMap<>();
		this.description = "just an npc";
		currentState = new State("DEFAULT", "..."); // just so that it's not null
	}

	/**
	 * creates the default states: idle and attending
	 */
	public void initiateDefaultStates() {
		idle = new State("IDLE");
		attending = new State("ATTENDING", null );
		Link link = new Link((1 + idle.getNumberOfLinks() + ""), "Hi!", "What do you want?");
		idle.setLink(link, attending);
		currentState = idle;
		states.put(idle.getName(), idle);
		states.put(attending.getName(), attending);
	}

	/**
	 * connects all the states by a "bye" link to idle. Only call at the very end of
	 * the dialogue construction!
	 */
	public void setBye() {

		for (State state : states.values()) {
			Link bye = new Link((1 + state.getNumberOfLinks() + ""), "Bye!", "Bye.");
			bye.setTerminator();
			state.setLink(bye, idle);
		}
	}

	public void setIdleStateResponse(String response) {
		idle.stateResponse = response;
	}

	public void setAttendingStateResponse(String response) {
		attending.stateResponse = response;
	}

	/**
	 * connect two existing states by creating a link
	 */
	public void setLink(String playerLine, String positiveResponse, String source, String desctination) {

		Link link = new Link((1 + states.get(source).getNumberOfLinks() + ""), playerLine, positiveResponse);
		State sourceState = states.get(source);
		State desctinationState = states.get(desctination);
		try {
			sourceState.setLink(link, desctinationState);
		} catch (Exception e) {
			IORedirect.printError("Couldn't establish a state link");
		}
	}

	/**
	 * connect two existing states by creating a link with a condition
	 */
	public void setLink(String playerLine, String positiveResponse, String negativeResponse, String source,
			String desctination, Predicate<Player> p) {

		Link link = new Link((1 + states.get(source).getNumberOfLinks() + ""), playerLine, positiveResponse,
				negativeResponse, p);
		State sourceState = states.get(source);
		State desctinationState = states.get(desctination);
		try {
			sourceState.setLink(link, desctinationState);
		} catch (Exception e) {
			IORedirect.printError("Couldn't establish a state link");
		}
	}

	/**
	 * create a link AND the destination state
	 */
	public void setLink(String playerLine, String positiveResponse, String source, String desctination,
			String stateResponse) {

		Link link = new Link((1 + states.get(source).getNumberOfLinks() + ""), playerLine, positiveResponse);
		State sourceState = states.get(source);
		State desctinationState = new State(desctination, stateResponse);
		try {
			sourceState.setLink(link, desctinationState);
			states.put(desctinationState.getName(), desctinationState);
		} catch (Exception e) {
			IORedirect.printError("Couldn't establish a state link");
		}
	}

	/**
	 * create a link with predicate(condition) and the destination state
	 */
	public void setLink(String playerLine, String positiveResponse, String negativeResponse, String source,
			String desctination, String stateResponse, Predicate<Player> p) {
		Link link = new Link((1 + states.get(source).getNumberOfLinks() + ""), playerLine, positiveResponse,
				negativeResponse, p);
		State sourceState = states.get(source);
		State desctinationState = new State(desctination, stateResponse);
		try {
			sourceState.setLink(link, desctinationState);
			states.put(desctinationState.getName(), desctinationState);
		} catch (Exception e) {
			IORedirect.printError("Couldn't establish a state link");
		}
	}

	public void startDialogue(Player player) {

		talking = true;
		interactingPlayer = player;
		if(currentState.stateResponse!=null)
		{
		IORedirect.printOutput(currentState.stateResponse);
		}
		currentState.printLinks();
		
	}

	public Response process(String key) {

		//boolean keepTalking = true;

		State nextState = null;
		for (Link link : currentState.links.keySet()) {
			if (key.equalsIgnoreCase(link.getKey())) {
				IORedirect.printOutput(interactingPlayer.getName() + ": " + link.getPlayerLine());  //players name + line
				if (link.isTerminator) // if a terminating link was found
				{
					IORedirect.printOutput(this.name + ": " + link.positiveResponse);
					currentState = idle;
					talking = false;
					interactingPlayer = null;
					return new Response(false,null);
				}

				if ((link.predicate == null) || (link.predicate.test(interactingPlayer ))) {
					// either if there's no condition or condition met
					nextState = currentState.links.get(link);
					IORedirect.printOutput(this.name + ": " + link.positiveResponse);
					break;
				} else {
					// condition not met
					nextState = currentState;
					IORedirect.printOutput(this.name + ": " + link.negativeResponse);
					break;
				}
			}
		}

		if (nextState != null) {
			currentState = nextState;
			if(currentState.stateResponse!=null)
			{
			IORedirect.printOutput(currentState.stateResponse);
			}
			IORedirect.printOutput("");  //put space between the npc response and dialogue options
			currentState.printLinks();
		}
		
		return new Response(talking,null);

	}

	@Override
	public void setItem(Item item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Item extractItem(String itemName) {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public Item search() {
	super.printInventory();
	if(!inventory.isEmpty())
	{
	String itemName = IORedirect.getInput();
	Item extractedItem = extractItem(itemName);
	return extractedItem;
	}
	else
	{
		return null;
	}
	}
}
