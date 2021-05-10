package gamelogic;

public class PlayerStatus {
	
	public enum Status
	{
		IDLE("..."), 
		UNKNOWN("?"),   //no need?
	    TALKING("Talking to"), 
	    SEARCHING("Searching");
	    
	    private String statusString;
	    
	    Status(String statusString)
	    {
	        this.statusString = statusString;
	    }
	    Status()
	    {
	        this.statusString = "";
	    }
	    public String toString()
	    {
	        return statusString;
	    }
	    
	}
	
	private Status currentStatus;
	
	public PlayerStatus() {}

	public void setStatus(Status status)
    {
            currentStatus = status;
            
    }
	public Status getStatus( )
    {
           return currentStatus ;
    }
	
	public String toString()
    {
        return currentStatus.statusString;
    }
	
}
