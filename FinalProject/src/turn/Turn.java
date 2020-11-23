package turn;

import entity.Actor;

public abstract class Turn {
	protected Actor target;
	
	// executes the turn, sets actor's turn reference to null
	public abstract void execute();
}
