package game;

import java.io.File;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.util.HashMap;

public class Sprite {
	static HashMap<String, Image> loadedImages = new HashMap<>();
	
	private int renderPriority;
	private Image image;
	private double[] dims;
	
	public Sprite(int renderPriority, Image image, double[] dims) {
		this.renderPriority = renderPriority;
		this.image = image;
		this.dims = dims;
	}
	
	public Sprite(String spritedata) {
		spritedata = Game.processData(spritedata);
		this.dims = new double[2];
		
		renderPriority = Integer.parseInt(Game.extractAttribute(spritedata, "renderpriority"));
		dims[0] = Integer.parseInt(Game.extractAttribute(spritedata, "h"));
		dims[1] = Integer.parseInt(Game.extractAttribute(spritedata, "w"));
		
		String imageDir = Game.extractAttribute(spritedata, "imagedir");
		
		if(loadedImages.containsKey(imageDir)) {
			image = loadedImages.get(imageDir);
		} else {
			loadedImages.put(imageDir, new Image(new File(imageDir).toURI().toString()));
			image = loadedImages.get(imageDir);
		}
	}
	
	public double[] getDims() {
		return dims;
	}
	
	public Image getImage() {
		return image;
	}
	
	public int getRenderPriority() {
		return renderPriority;
	}
	
	public void render(GraphicsContext cg, double[] screenPos) {
		cg.drawImage(image, screenPos[0], screenPos[1], dims[0] * Game.TILE_WIDTH, dims[1] * Game.TILE_WIDTH);
	}
}