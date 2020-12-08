package turn;

import java.util.ArrayList;

import entity.*;
import game.Level;

public class TurnMove extends Turn {
	private int[] destination;
	
	public TurnMove(int[] endPos, Actor target) {
		destination = endPos;
		this.target = target;
	}
	
	// set position of actor to destination
	public void execute() {
		target.setPosition(destination);
	}
	
	// returns a boolean on if a move is legal
	public boolean isLegal(Level l) {
		ArrayList<Entity> e = l.entitiesAt(destination);
		for(int i = 0; i < e.size(); i++) {
			if(e.get(i) instanceof Tile) {
				Tile t = (Tile) e.get(i);
				if(t.getImpassable() == true) {
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean isEnemy(Level l) {
		ArrayList<Entity> e = l.entitiesAt(destination);
		for(int i = 0; i < e.size(); i++) {
			if(e.get(i) instanceof Actor) {
				return true;
			}
		}
		return false;
	}
	
	public Actor getEnemyAt(Level l) {
		ArrayList<Entity> e = l.entitiesAt(destination);
		for(int i = 0; i < e.size(); i++) {
			if(e.get(i) instanceof Actor) {
				return (Actor)e.get(i);
			}
		}
		return null;
	}
	
	public int[] getDestination() {
		return destination;
	}
}
