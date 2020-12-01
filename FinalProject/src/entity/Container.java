package entity;

import java.util.ArrayList;
import item.*;
import game.Game;

public class Container extends Entity{
	final static int NUM_ITEMS_CAN_HOLD = 10;
	private ArrayList<Item> inventory;
	
	public Container(ArrayList<Item> inv, int[] pos, String name, String sprite) {
		super(pos, name, sprite);
		inventory = inv;
	}
	
	public Container(String containerData) {
		super(containerData);
		String itemAndNum;
		String itemType;
		for(int i = 0; i < NUM_ITEMS_CAN_HOLD; i++) {
			itemAndNum = "item" + i;
			itemType = Game.extractAttribute(Game.getDef(Game.extractAttribute(containerData, itemAndNum)), "type");
			if(itemType.equals("weapon")) {
				inventory.add(new Weapon(Game.getDef(Game.extractAttribute(containerData, itemAndNum))));
			}
			else if(itemType.equals("armor")) {
				inventory.add(new Armor(Game.getDef(Game.extractAttribute(containerData, itemAndNum))));
			}
			else if(itemType.equals("consumable")) {
				inventory.add(new Consumable(Game.getDef(Game.extractAttribute(containerData, itemAndNum))));
			}
		}
	}
	
	public ArrayList<Item> getInventory() {
		return inventory;
	}
	
}
