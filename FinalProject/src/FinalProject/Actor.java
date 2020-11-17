package FinalProject;

import java.util.ArrayList;

public class Actor extends Entity {
	private ArrayList<Item> inventory;
	private Turn myTurn;
	
	public Actor(ArrayList<Item> inv, int[] pos, String name) {
		super(pos, name);
		inventory = inv;
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
