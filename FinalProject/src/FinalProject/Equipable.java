package FinalProject;

public abstract class Equipable extends Item {
	protected boolean isEquipped;
	
	public Equipable(boolean inInven, Entity owner) {
		super(inInven, owner);
		isEquipped = false;
	}
	
	public abstract void use(Entity e);
	
	public abstract void use(Actor a);
	
	public boolean getIsEquipped() {
		return isEquipped;
	}
	
}


