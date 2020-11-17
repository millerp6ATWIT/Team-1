package FinalProject;

public abstract class Item {
	private boolean inInventory;
	private Entity owner;
	
	public Item(boolean inInven, Entity owner) {
		inInventory = inInven;
		this.owner = owner;
	}
	
	// apply the effects of the item to the entity
	public abstract void use(Entity e);
	
	// apply the effects of the item to the actor
	public abstract void use(Actor a);
	
	public Entity getOwner() {
		return owner;
	}
	
}
