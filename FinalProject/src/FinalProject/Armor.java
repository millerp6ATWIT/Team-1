package FinalProject;

public class Armor extends Equipable {
	private int protection;
	
	public Armor(int protection, boolean inInven, Entity owner) {
		super(inInven, owner);
		this.protection = protection;
	}
	
	public int getProtection() {
		return protection;
	}
	public void setProtection(int newProtec) {
		protection = newProtec;
	}
	
	public void use(Entity e) {
		
	}
	
	public void use(Actor a) {
		
	}
	
}
