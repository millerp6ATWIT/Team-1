package turn;

import entity.Actor;

public abstract class Turn {
	
	// executes the turn, sets actor's turn reference to null
	public abstract void execute(Actor a);
}
