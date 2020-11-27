package item;

import entity.Actor;
import entity.Entity;
import game.Game;

public abstract class Item {
	protected boolean inInventory;
	protected String name;
	protected Entity owner;
	
	public Item(boolean inInven, Entity owner) {
		inInventory = inInven;
		this.owner = owner;
	}
	
	public Item(String itemData) {
		name = Game.extractAttribute(itemData, "name");
	}
	
	public boolean getInInventory() {
		return inInventory;
	}
	public void setInInventory(boolean inInven) {
		inInventory = inInven;
	}
	
	public Entity getOwner() {
		return owner;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	// apply the effects of the item to the entity
	public abstract void use(Entity e);
	
	// apply the effects of the item to the actor
	public abstract void use(Actor a);
	
}
