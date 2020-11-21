package game;

import java.util.ArrayList;

import entity.Entity;

import java.io.File;
import javafx.scene.canvas.GraphicsContext;

public class Level {
	private ArrayList<Entity> entities;
	
	public Level(ArrayList<Entity> entities) {
		this.entities = entities;
	}
	
	// given a position, returns an arraylist of entities at that position
	public ArrayList<Entity> entitiesAt(int[] position) {
		ArrayList<Entity> entities = new ArrayList<>();
		
		return entities;
	}
	
	// given a csv file, populate arraylist of entites
	public void loadFile(File f) {
		
	}
	
	public void addEntity(Entity toAdd) {
		entities.add(toAdd);
	}
	
	public ArrayList<Entity> getEntities() {
		return entities;
	}
	
}
