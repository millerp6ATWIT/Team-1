package game;

import java.util.ArrayList;
import entity.Entity;
import java.io.File;
import javafx.scene.canvas.GraphicsContext;
import java.util.Map;
import java.util.HashMap;
import entity.*;
import item.*;
import java.util.Arrays;

public class Level {
	private ArrayList<Entity> entities;
	
	public Level(ArrayList<Entity> entities) {
		this.entities = entities;
	}
	
	public Level(String levelHeader, String leveldata) {
		levelHeader = Game.processData(levelHeader);
		entities = new ArrayList<Entity>();
		
		String entityData, type;
		int posX, posY;
		posX = posY = 0;
		for(char c: leveldata.toCharArray()) {
			if(c == ',') {
				posX++;
			} else if(c == '\n') {
				posX = 0;
				posY++;
			} else if(c == '\r') {
				
			} else {
				entityData = Game.processData(Game.fileToString(new File(Game.getDef(Game.extractAttribute(levelHeader, Character.toString(c))))));
				type = Game.extractAttribute(entityData, "type");
				
				if(type.equals("actor")) {
					Actor a = new Actor(entityData);
					a.setPosition(new int[] {posX, posY});
					entities.add(a);
				} else if(type.equals("tile")) {
					Tile t = new Tile(entityData);
					t.setPosition(new int[] {posX, posY});
					entities.add(t);
				} else if(type.equals("container")) {
					Container con = new Container(entityData);
					con.setPosition(new int[] {posX, posY});
					entities.add(con);
				}
			}
		}
	}
	
	// given a position, returns an arraylist of entities at that position
	public ArrayList<Entity> entitiesAt(int[] position) {
		ArrayList<Entity> entitiesAt = new ArrayList<>();
		
		for(Entity e: entities) {
			if(Arrays.equals(e.getPosition(), position))
				entitiesAt.add(e);
		}
		
		return entitiesAt;
	}
	
	public void addEntity(Entity toAdd) {
		entities.add(toAdd);
	}
	
	public void removeEntity(Entity toRemove) {
		entities.remove(toRemove);
	}
	
	public Actor getPlayer() {
		for(Entity e: entities) {
			if (e.getName().equals("Player")) {
				return (Actor) e;
			}
		}
		return null;
	}
	
	public ArrayList<Entity> getEntities() {
		return entities;
	}
	
	public boolean isImpassable(int[] position) {
		for(Entity e: entitiesAt(position)) {
			if(e instanceof Tile) {
				Tile t = (Tile) e;
				if(t.getImpassable()) {
					return true;
				}
			}
		}
		
		return false;
	}
	
}
