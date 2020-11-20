package turn;

import entity.Actor;

public class TurnMove extends Turn {
	private int[] destination;
	
	public TurnMove(int[] endPos) {
		destination = endPos;
	}
	
	// set position of actor to destination
	public void execute(Actor a) {
		a.setPosition(destination);
	}
}
