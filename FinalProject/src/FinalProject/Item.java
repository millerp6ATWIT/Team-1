package FinalProject;

public abstract class Item {
	protected boolean inInventory;
	protected Entity owner;
	
	public Item(boolean inInven, Entity owner) {
		inInventory = inInven;
		this.owner = owner;
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
	
	// apply the effects of the item to the entity
	public abstract void use(Entity e);
	
	// apply the effects of the item to the actor
	public abstract void use(Actor a);
	
}
