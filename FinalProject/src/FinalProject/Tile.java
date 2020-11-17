package FinalProject;

public class Tile extends Entity{
	private boolean impassable;
	
	public Tile(boolean collision, int[] pos, String name) {
		super(pos, name);
		impassable = collision;
	}
	
}
