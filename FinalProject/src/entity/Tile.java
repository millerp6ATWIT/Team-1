package entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import game.Game;

public class Tile extends Entity{
	private boolean impassable;
	
	public Tile(boolean collision, int[] pos, String name) {
		super(pos, name);
		impassable = collision;
	}
	
	public Tile(String tiledata) {
		super(tiledata);
		if(Game.extractAttribute(tiledata, "impassable").equals("true")) {
			impassable = true;
		} else if(Game.extractAttribute(tiledata, "impassable").equals("false")) {
			impassable = false;
		}
	}
	
	public boolean getImpassable() {
		return impassable;
	}
	
	public void render(GraphicsContext gc) {
		Paint original = gc.getFill();
		gc.setFill(Color.BLACK);
		gc.fillRect(position[0] * TILE_WIDTH, position[1] * TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);
		gc.setFill(original);
	}
	
}

//A