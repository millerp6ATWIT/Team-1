package item;

import java.util.Map;
import game.Game;
import entity.Actor;
import entity.Entity;

public class Weapon extends Equipable {
	private int damage;
	
	public Weapon(int damage, boolean inInven, Entity owner) {
		super(inInven, owner);
		this.damage = damage;
	}
	
	public Weapon(String itemData) {
		super(itemData);
		damage = Integer.parseInt(Game.extractAttribute(itemData, "damage"));
	}
	
	public int getDamage() {
		return damage;
	}
	
	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	public void use(Entity e) {
		
	}
	
	public void use(Actor a) {
		if(a == owner) {
			Weapon equippedWeapon = a.getEquippedWeapon();
			if (equippedWeapon != null)
				equippedWeapon.isEquipped = false;
			isEquipped = true;
		} else {
			Map<String, Integer> targetStats = a.getStats();
			targetStats.put("HP", targetStats.get("HP") - damage);
		}
	}
	
}