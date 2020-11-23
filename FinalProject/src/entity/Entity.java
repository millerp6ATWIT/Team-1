package entity;

import java.util.Scanner;

import game.Game;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Entity {
	public static final int TILE_WIDTH = 16;
	public static final int TILE_HEIGHT = 16;
	private int[] position;
	private String name;
	
	public Entity(int[] pos, String name) {
		position = pos;
		this.name = name;
	}

	public Entity(String entityData) {
		name = Game.extractAttribute(entityData, "name");
	}
	
	public void render(GraphicsContext gc) {
		Paint original = gc.getFill();
		gc.setFill(Color.BLACK);
		gc.fillRect(position[0] * TILE_WIDTH, position[1] * TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);
		gc.setFill(original);
	}
	
	public int[] getPosition() {
		return position;
	}
	public void setPosition(int[] pos) {
		position = pos;
	}
	
	public String getName() {
		return name;
	}
}
