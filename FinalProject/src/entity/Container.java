package entity;

import java.util.ArrayList;
import item.*;
import game.Game;
import java.io.File;

public class Container extends Entity {
	
	final static int NUM_ITEMS_CAN_HOLD = 10;
	private ArrayList<Item> inventory = new ArrayList<Item>();
	
	public Container(ArrayList<Item> inv, int[] pos, String name, String sprite) {
		super(pos, name, sprite);
		inventory = inv;
	}
	
	public Container(String containerData) {
		super(containerData);
		String itemAndNum;
		String itemType;
		String item;
		String itemData;
		for(int i = 0; i < NUM_ITEMS_CAN_HOLD; i++) {
			itemAndNum = "item" + i;
			item = Game.extractAttribute(containerData, itemAndNum);
			if(!item.equals("null")) {
				itemData = Game.processData(Game.fileToString(new File(Game.getDef(item))));
				itemType = Game.extractAttribute(itemData, "type");
				
				if(itemType.equals("weapon")) {
					inventory.add(new Weapon(itemData));
				}
				else if(itemType.equals("armor")) {
					inventory.add(new Armor(itemData));
				}
				else if(itemType.equals("consumable")) {
					inventory.add(new Consumable(itemData));
				}
			}
		}
	}
	
	public ArrayList<Item> getInventory() {
		return inventory;
	}
	
}
