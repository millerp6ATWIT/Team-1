package entity;

import java.util.ArrayList;
import java.util.Map;

import game.Game;
import item.Armor;
import item.Equipable;
import item.Item;
import item.Weapon;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import turn.Turn;
import game.Sprite;
import game.Level;
import turn.TurnMove;

import java.util.HashMap;
import java.io.File;

public class Actor extends Entity {
	public static String[] genericStats = {"HP", "DEF", "STR"};
	
	private Map<String, Integer> stats;
	private ArrayList<Item> inventory;
	private Turn myTurn;
	
	public Actor(ArrayList<Item> inv, Map<String, Integer> stats, int[] pos, String name, String sprite) {
		super(pos, name, sprite);
		inventory = inv;
		this.stats = stats;
	}
	
	public Actor(String entityData) {
		super(entityData);
		stats = new HashMap<String, Integer>();
		inventory = new ArrayList<Item>();
		for(String attribute: genericStats) {
			stats.put(attribute, Integer.parseInt(Game.extractAttribute(entityData, attribute)));
		}
	}
	
	public ArrayList<Item> getInventory() {
		return inventory;
	}
	
	public void setInventory(ArrayList<Item> newInven ) {
		inventory = newInven;
	}
	
	public Turn getMyTurn() {
		return myTurn;
	}
	
	public void setMyTurn(Turn newTurn) {
		myTurn = newTurn;
	}
	
	public void doTurn() {
		myTurn.execute();
		myTurn = null;
	}
	
	public Map<String, Integer> getStats() {
		return stats;
	}
	
	public Armor getEquippedArmor() {
		for(Item i: inventory) {
			if(i instanceof Equipable) {
				if(i instanceof Armor) {
					Armor a = (Armor) i;
					if(a.getIsEquipped()) {
						return a;
					}
				}
			}
		}
		return null;
	}
	
	public Weapon getEquippedWeapon() {
		for(Item i: inventory) {
			if(i instanceof Equipable) {
				if(i instanceof Weapon) {
					Weapon w = (Weapon) i;
					if(w.getIsEquipped()) {
						return w;
					}
				}
			}
		}
		return null;
	}
	
	public void pickUp(Level level) {
		ArrayList<Entity> entitiesHere = level.entitiesAt(position);
		
		for(Entity e: entitiesHere) {
			if(e instanceof Container) {
				Container c = (Container) e;
				for(Item i: c.getInventory()) {
					inventory.add(i);
				}
				level.getEntities().remove(e);
			}
		}
	}
	
	public Turn getEnemyTurn(Level level) {
		int[] playerPos = level.getPlayer().getPosition();
		int[][] possibleDestinations = {
			{position[0] + 1, position[1]},
			{position[0] - 1, position[1]},
			{position[0], position[1] + 1},
			{position[0], position[1] - 1},
			{position[0] + 1, position[1] + 1},
			{position[0] - 1, position[1] + 1},
			{position[0] + 1, position[1] - 1},
			{position[0] - 1, position[1] = 1},
		};
		
		double minDistanceToPlayer = Integer.MAX_VALUE;
		double distanceToPlayer;
		int[] nearestPosToPlayer = position;
		for(int[] possiblePosition: possibleDestinations) {
			if(!level.isImpassable(possiblePosition)) {
				distanceToPlayer = Math.sqrt(Math.pow(playerPos[1] - possiblePosition[1], 2) + Math.pow(playerPos[0] - possiblePosition[0], 2));
				if(distanceToPlayer < minDistanceToPlayer) {
					minDistanceToPlayer = distanceToPlayer;
					nearestPosToPlayer = possiblePosition;
				}
			}
		}
		
		return new TurnMove(nearestPosToPlayer, this);
	}
	
}
