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
		entities = new ArrayList<Entity>();
		
		int posX, posY;
		posX = posY = 0;
			
		String data, type, symbolName;
		for(char c: leveldata.toCharArray()) {
			if(c == ' ') {
				posX++;
			} else if(c == '\r') {
				
			} else if(c == '\n') {
				posX = 0;
				posY++;
			} else if(c == '\t') {
				posX += 3;
			} else {
				symbolName = Game.extractAttribute(levelHeader, Character.toString(c));
				data = Game.fileToString(new File(Game.getDef(symbolName)));
				type = Game.extractAttribute(data, "type");
				
				if(type.equals("actor")) {
					Actor a = new Actor(data);
					a.setPosition(new int[] {posX, posY});
					entities.add(a);
				} else if(type.equals("tile")) {
					Tile t = new Tile(data);
					t.setPosition(new int[] {posX, posY});
					entities.add(t);
				}
				
				posX++;
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
	
}
