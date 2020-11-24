package game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Sprite {
	private int spriteBounds[];
	private int renderPriority;
	
	public Sprite(int[] spriteBounds, int renderPriority) {
		this.spriteBounds = spriteBounds;
	}
	
	public Sprite(String spritedata) {
		spriteBounds = new int[4];
		
		spriteBounds[0] = Integer.parseInt(Game.extractAttribute(spritedata, "x"));
		spriteBounds[1] = Integer.parseInt(Game.extractAttribute(spritedata, "y"));
		spriteBounds[2] = Integer.parseInt(Game.extractAttribute(spritedata, "h"));
		spriteBounds[3] = Integer.parseInt(Game.extractAttribute(spritedata, "w"));
		
		renderPriority = Integer.parseInt(Game.extractAttribute(spritedata, "renderpriority"));
	}
	
	public int[] getBounds() {
		return spriteBounds;
	}
	
	public int getRenderPriority() {
		return renderPriority;
	}
	
	public void render(GraphicsContext cg, int[] screenPos) {
		cg.drawImage(Game.spritesheet, spriteBounds[0], spriteBounds[1], spriteBounds[2], spriteBounds[3], screenPos[0], screenPos[1], spriteBounds[2], spriteBounds[3]);
	}
}