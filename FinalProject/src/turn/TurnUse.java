package turn;

import entity.Actor;
import item.Item;
import java.util.ArrayList;

public class TurnUse extends Turn {
	private Item toUse;
	
	public TurnUse(Item itemToBeUsed, Actor target) {
		toUse = itemToBeUsed;
		this.target = target;
	}
	
	// apply the effects of toUse to the specified actor
	public void execute() {
		toUse.use(target);
	}
	
	public boolean isLegal() {
		ArrayList<Item> inventory = ((Actor) toUse.getOwner()).getInventory();
		for(int i = 0; i < inventory.size(); i++) {
			if(inventory.get(i).equals(toUse)) {
				return true;
			}
		}
		return false;
	}
}
