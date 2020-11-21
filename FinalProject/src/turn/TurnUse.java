package turn;

import entity.Actor;
import item.Item;

public class TurnUse extends Turn {
	private Item toUse;
	
	public TurnUse(Item itemToBeUsed) {
		toUse = itemToBeUsed;
	}
	
	// apply the effects of toUse to the specified actor
	public void execute(Actor a) {
		toUse.use(a);
	}
}
