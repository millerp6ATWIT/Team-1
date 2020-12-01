package game;

import java.io.File;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Sprite {
	private int renderPriority;
	private Image image;
	private double[] dims;
	
	public Sprite(int renderPriority, Image image, double[] dims) {
		this.renderPriority = renderPriority;
		this.image = image;
		this.dims = dims;
	}
	
	public Sprite(String spritedata) {
		this.dims = new double[2];
		
		renderPriority = Integer.parseInt(Game.extractAttribute(spritedata, "renderpriority"));
		image = new Image(new File(Game.extractAttribute(spritedata, "imagedir")).toURI().toString());
		dims[0] = Integer.parseInt(Game.extractAttribute(spritedata, "h"));
		dims[1] = Integer.parseInt(Game.extractAttribute(spritedata, "w"));
	}
	
	public double[] getDims() {
		return dims;
	}
	
	public int getRenderPriority() {
		return renderPriority;
	}
	
	public void render(GraphicsContext cg, double[] screenPos) {
		cg.drawImage(image, screenPos[0], screenPos[1], dims[0] * Game.TILE_WIDTH, dims[1] * Game.TILE_WIDTH);
	}
}