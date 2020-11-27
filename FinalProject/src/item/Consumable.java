package item;

import java.util.Map;

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
		if(owner instanceof Actor) {
			Actor a = (Actor) owner;
			use(a);
		}
	}
	
	public void use(Actor a) {
		Map<String, Integer> targetStats = a.getStats();
		targetStats.put(targetStat, targetStats.get(targetStat) + value);
	}
	
}
