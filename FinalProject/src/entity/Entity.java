package entity;

import java.io.File;
import java.util.Scanner;

import game.Game;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import game.Sprite;

public class Entity {
	protected int[] position;
	protected String name;
	protected Sprite sprite;
	
	public Entity(int[] pos, String name, String sprite) {
		position = pos;
		this.name = name;
		sprite = Game.fileToString(new File(Game.getDef(sprite)));
	}

	public Entity(String entityData) {
		System.out.println(entityData);
		name = Game.extractAttribute(entityData, "name");
		sprite = new Sprite(Game.fileToString(new File(Game.getDef(Game.extractAttribute(entityData, "sprite")))));
	}
	
	public void render(GraphicsContext gc, double[] cameraPos) {
		double[] screenPos = getScreenPosition();
		screenPos[0] -= cameraPos[0];
		screenPos[1] -= cameraPos[1];
		sprite.render(gc, screenPos);
	}
	
	public double[] getScreenPosition() {
		return new double[] {position[0] * Game.TILE_WIDTH, position[1] * Game.TILE_HEIGHT};
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
