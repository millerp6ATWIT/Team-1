package item;

import java.util.Map;

import entity.Actor;
import entity.Entity;
import game.Game;

public class Armor extends Equipable {
	private int protection;
	
	public Armor(int protection, boolean inInven, Entity owner) {
		super(inInven, owner);
		this.protection = protection;
	}
	
	public Armor(String itemData) {
		super(itemData);
		protection = Integer.parseInt(Game.extractAttribute(itemData, "protection"));
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
		Map<String, Integer> targetStats = a.getStats();
		targetStats.put("DEF", targetStats.get("DEF") - protection);
	}
	
}
