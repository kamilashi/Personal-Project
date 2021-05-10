package gamelogic;

import java.util.function.Predicate;

public abstract class Predicates {

	public static Predicate<Player> requiresItem(String item) {
		return player -> player.hasItem(item);
		
	}

}
