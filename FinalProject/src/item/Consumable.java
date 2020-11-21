package item;

import entity.Actor;
import entity.Entity;

public class Consumable extends Item {
	private int value;
	private String targetStat;
	
	public Consumable(int mag, String stat, boolean inInven, Entity owner) {
		super(inInven, owner);
		value = mag;
		targetStat = stat;
	}
	
	public int getValue() {
		return value;
	}
	
	public String getTargetStat() {
		return targetStat;
	}
	
	public void use(Entity e) {
		
	}
	
	public void use(Actor a) {
		
	}
	
}
