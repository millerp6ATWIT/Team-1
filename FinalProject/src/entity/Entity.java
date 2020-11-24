package entity;

import java.io.File;
import java.util.Scanner;

import game.Game;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import game.Sprite;

public class Entity {
	public static final int TILE_WIDTH = 16;
	public static final int TILE_HEIGHT = 16;
	protected int[] position;
	protected String name;
	protected Sprite sprite;
	
	public Entity(int[] pos, String name, String sprite) {
		position = pos;
		this.name = name;
		sprite = Game.fileToString(new File(Game.getDef(sprite)));
	}

	public Entity(String entityData) {
		name = Game.extractAttribute(entityData, "name");
		sprite = new Sprite(Game.fileToString(new File(Game.getDef(Game.extractAttribute(entityData, "sprite")))));
	}
	
	public void render(GraphicsContext gc) {
		int[] screenPos = {position[0] * TILE_WIDTH, position[1] * TILE_HEIGHT};
		sprite.render(gc, screenPos);
	}
	
	public int[] getPosition() {
		return position;
	}
	public void setPosition(int[] pos) {
		position = pos;
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	public String getName() {
		return name;
	}
}
