package conditions;

import java.util.function.Predicate;

//import gamelogic.Player;

public class Condition<T>{
	private Predicate<T> predicate = null;
	private String positiveResponse = null;
	private String negativeResponse = null;
	private String description = null;
	
	public Condition(Predicate<T> p,String desc, String posResp, String negResp)
	{
		this.predicate = p;
		this.positiveResponse = posResp;
		this.negativeResponse = negResp;
		this.description = desc;
	}
	public Condition(Predicate<T> p)
	{
		this.predicate = p;
	}
	
	public Predicate<T> getPredicate() {
		return predicate;
	}
	public String getPositiveResponse() {
		return positiveResponse;
	}
	public String getNegativeResponse() {
		return negativeResponse;
	}
	public String toString() {
		return description;
	}

}
