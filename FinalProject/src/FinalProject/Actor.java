package FinalProject;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.io.File;

public class Actor extends Entity {
	private Map<String, Integer> stats;
	private ArrayList<Item> inventory;
	private Turn myTurn;
	
	public Actor(ArrayList<Item> inv, Map<String, Integer> stats, int[] pos, String name) {
		super(pos, name);
		inventory = inv;
		this.stats = stats;
	}
	
	public Actor(String entityData) {
		super(entityData);
	}
	
	public ArrayList<Item> getInventory() {
		return inventory;
	}
	public void setInventory(ArrayList<Item> newInven ){
		inventory = newInven;
	}
	
	public Turn getMyTurn() {
		return myTurn;
	}
	public void setMyTurn(Turn newTurn) {
		myTurn = newTurn;
	}
}

//A