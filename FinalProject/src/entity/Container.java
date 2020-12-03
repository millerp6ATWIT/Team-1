package entity;

import java.util.ArrayList;
import item.*;
import game.Game;
import java.io.File;

public class Container extends Entity {
	
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
		String itemData;
		for(int i = 0; i < NUM_ITEMS_CAN_HOLD; i++) {
			itemAndNum = "item" + i;
			itemData = Game.fileToString(new File(Game.getDef(Game.extractAttribute(containerData, itemAndNum))));
			itemType = Game.extractAttribute(itemData, "type");
			
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
