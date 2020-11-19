package FinalProject;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.io.File;

public class Actor extends Entity {
	public static String[] genericStats = {"HP", "DEF"};
	
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
	
	public Map<String, Integer> getStats() {
		return stats;
	}
	
	public Armor getEquippedArmor() {
		for(Item i: inventory) {
			if(i instanceof Equipable) {
				if(i instanceof Armor) {
					Armor a = (Armor) i;
					if(a.getIsEquipped()) {
						return a;
					}
				}
			}
		}
		return null;
	}
	
	public Weapon getEquippedWeapon() {
		for(Item i: inventory) {
			if(i instanceof Equipable) {
				if(i instanceof Weapon) {
					Weapon w = (Weapon) i;
					if(w.getIsEquipped()) {
						return w;
					}
				}
			}
		}
		return null;
	}
	
}

//A