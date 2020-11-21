package entity;

import java.util.ArrayList;
import item.Item;

public class Container extends Entity{
	private ArrayList<Item> inventory;
	
	public Container(ArrayList<Item> inv, int[] pos, String name) {
		super(pos, name);
		inventory = inv;
	}
	
	public ArrayList<Item> getInventory() {
		return inventory;
	}
}
