package FinalProject;

public class Weapon extends Item{
	private int damage;
	
	public Weapon(int damage, boolean inInven, Entity owner) {
		super(inInven, owner);
		this.damage = damage;
	}
	
	public void use(Entity e) {
		
	}
	
	public void use(Actor a) {
		
	}
	
}
