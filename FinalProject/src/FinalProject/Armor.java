package FinalProject;

public class Armor extends Item{
	private int protection;
	
	public Armor(int mag, boolean inInven, Entity owner) {
		super(inInven, owner);
		protection = mag;
	}
	
	public void use(Entity e) {
		
	}
	
	public void use(Actor a) {
		
	}
	
}
