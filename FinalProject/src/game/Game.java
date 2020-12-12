package game;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import java.nio.file.Path;
import java.io.File;
import java.nio.file.Files;
import entity.Actor;
import item.Consumable;
import turn.TurnUse;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import entity.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.input.KeyCode;
import turn.*;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Comparator;
import item.*;

public class Game extends Application {
	public static final int TILE_WIDTH = 16;
	public static final int TILE_HEIGHT = 16;
	
	final static String SPRITESHEET_DIR = "data\\spritesheet.png";
	
	final static String WEAPON_HEADER_DIR = "data\\itemdata\\weapons\\weaponheader.txt";
	final static String ARMOR_HEADER_DIR = "data\\itemdata\\armor\\armorheader.txt";
	final static String ACTOR_HEADER_DIR = "data\\entitydata\\actors\\actorheader.txt";
	final static String CONSUMABLE_HEADER_DIR = "data\\itemdata\\consumables\\consumableheader.txt";
	final static String TILE_HEADER_DIR = "data\\entitydata\\tiledata\\tileheader.txt";
	final static String CONTAINER_HEADER_DIR = "data\\entitydata\\containerdata\\containerheader.txt";
	final static String SPRITE_HEADER_DIR = "data\\spritedata\\spriteheader.txt";
	final static String LEVEL_HEADER_DIR = "data\\leveldata\\levelheader.txt";
	
	final static String WEAPON_DEFS = processData(fileToString(new File(WEAPON_HEADER_DIR)));
	final static String ARMOR_DEFS = processData(fileToString(new File(ARMOR_HEADER_DIR)));
	final static String ACTOR_DEFS = processData(fileToString(new File(ACTOR_HEADER_DIR)));
	final static String CONSUMABLE_DEFS = processData(fileToString(new File(CONSUMABLE_HEADER_DIR)));
	final static String TILE_DEFS = processData(fileToString(new File(TILE_HEADER_DIR)));
	final static String CONTAINER_DEFS = processData(fileToString(new File(CONTAINER_HEADER_DIR)));
	final static String SPRITE_DEFS = processData(fileToString(new File(SPRITE_HEADER_DIR)));
	final static String ALL_DEFS = WEAPON_DEFS + ARMOR_DEFS + ACTOR_DEFS + TILE_DEFS + SPRITE_DEFS + CONTAINER_DEFS + CONSUMABLE_DEFS;
	
	final static Image spritesheet = new Image(new File(SPRITESHEET_DIR).toURI().toString());
	
	Level level;
	Actor player;
	UI ui;
	int turn = 0;
	double[] cameraPos;
	
	public static void main(String[] args) {
		
	}
	
	public void init() {
		level = new Level(fileToString(new File(LEVEL_HEADER_DIR)), fileToString(new File("data\\leveldata\\level1.csv")));
		player = level.getPlayer();
		cameraPos = new double[2];
		
		Container c = new Container(Game.fileToString(new File(Game.getDef("container_strpotion"))));
	}
	
	public void stop() {
		
	}
	
	public static String fileToString(File f) {
		try {
			return Files.readString(Path.of((f.getPath())));
		} catch (Exception e) {
			return "";
		}
	}
	
	public static String processData(String toProcess) {
		if(toProcess.length() > 0) {
			toProcess = toProcess.replaceAll(";", ",");
			if(toProcess.charAt(0) != System.lineSeparator().charAt(0))
				toProcess = System.lineSeparator() + toProcess;
			for(int i = 0; i < toProcess.length(); i++) {
				if(toProcess.charAt(i) == ',') {
					if(i < toProcess.length() - 1) {
						if(toProcess.charAt(i + 1) != System.lineSeparator().charAt(0)) {
							toProcess = toProcess.substring(0, i + 1) + System.lineSeparator() + toProcess.substring(i + System.lineSeparator().length() + 1);
						}
					} else {
						toProcess = toProcess + System.lineSeparator();
					}
				}
			}
		}
		return toProcess;
	}
	
	public static String getDef(String toGet) {
		return extractAttribute(ALL_DEFS, toGet);
	}
	
	public void renderScreen(GraphicsContext gc, double[] cameraPos) {
		
		ui.updatePlayerDEF(player.getStats().get("DEF"));
		ui.updatePlayerHP(player.getStats().get("HP"));
		ui.updatePlayerATK(player.getStats().get("STR") + player.getEquippedWeapon().getDamage());
		
		level.getEntities().sort(new Comparator<Entity>() {
			public int compare(Entity e1, Entity e2) {
				return(e1.getSprite().getRenderPriority() - e2.getSprite().getRenderPriority());
			}
		});
		
		gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
		
		for(Entity e: level.getEntities()) {
			e.render(gc, cameraPos);
		}
		
	}
	
	public static String extractAttribute(String data, String attribute) {
		String startToken = (System.getProperty("line.separator") + attribute + ":");
		String endToken = ",";
		if (data.contains(startToken)) {
			int indexStart = data.indexOf(startToken) + startToken.length();
			int indexEnd = data.indexOf(endToken, indexStart);
			//System.out.println("\"" + attribute + "\"" + " expanded to " + "\"" + data.substring(indexStart, indexEnd) + "\"");
			return(data.substring(indexStart, indexEnd));
		}
		return null;
	}
	
	public void processTurns() {
		if(player.getMyTurn() != null) {
			player.doTurn();
			
			ArrayList<Entity> toRemove = new ArrayList<>();
			for(Entity e: level.getEntities()) {
				if(e instanceof Actor) {
					Actor a = (Actor) e;
					if(e != player) {
						if(a.getDistanceFrom(player.getPosition()) < 10) {
							a.setMyTurn(a.getEnemyTurn(level));
						} else {
							a.setMyTurn(new TurnPass());
						}
						
						a.doTurn();
					}
					a.pickUp(level);
					if(a.getStats().get("HP") <= 0) {
						a.getStats().put("HP", 0);
						toRemove.add(a);
					}
				}
				if(e instanceof Container) {
					Container c = (Container) e;
					if(c.getInventory().size() == 0) {
						toRemove.add(c);
					}
				}
			}
			level.getEntities().removeAll(toRemove);
			
			if(level.getPlayer() == null) {
				init();
			}
			
			ui.updatePlayerInventory(player.getInventory());
			turn++;
		}
	}
	
	public void handleInput(KeyCode k) {
		if(k == KeyCode.W) {
			int[] destination = new int[2];
			destination[0] = player.getPosition()[0];
			destination[1] = player.getPosition()[1] - 1;
			player.setMyTurn(new TurnMove(destination, player));
		} else if(k == KeyCode.A) {
			int[] destination = new int[2];
			destination[0] = player.getPosition()[0] - 1;
			destination[1] = player.getPosition()[1];
			player.setMyTurn(new TurnMove(destination, player));
		} else if(k == KeyCode.X) {
			int[] destination = new int[2];
			destination[0] = player.getPosition()[0];
			destination[1] = player.getPosition()[1] + 1;
			player.setMyTurn(new TurnMove(destination, player));
		} else if(k == KeyCode.D) {
			int[] destination = new int[2];
			destination[0] = player.getPosition()[0] + 1;
			destination[1] = player.getPosition()[1];
			player.setMyTurn(new TurnMove(destination, player));
		} else if(k == KeyCode.Q) {
			int[] destination = new int[2];
			destination[0] = player.getPosition()[0] - 1;
			destination[1] = player.getPosition()[1] + 1;
			player.setMyTurn(new TurnMove(destination, player));
		} else if(k == KeyCode.E) {
			int[] destination = new int[2];
			destination[0] = player.getPosition()[0] + 1;
			destination[1] = player.getPosition()[1] + 1;
			player.setMyTurn(new TurnMove(destination, player));
		} else if(k == KeyCode.Z) {
			int[] destination = new int[2];
			destination[0] = player.getPosition()[0] - 1;
			destination[1] = player.getPosition()[1] - 1;
			player.setMyTurn(new TurnMove(destination, player));
		} else if(k == KeyCode.C) {
			int[] destination = new int[2];
			destination[0] = player.getPosition()[0] + 1;
			destination[1] = player.getPosition()[1] - 1;
			player.setMyTurn(new TurnMove(destination, player));
		} else if(k == KeyCode.S) {
			player.setMyTurn(new TurnPass());
		}
		
		Turn playerTurn = player.getMyTurn();
		if(playerTurn != null) {
			boolean isLegal;
			boolean isEnemy = false;
			if (playerTurn instanceof TurnMove) {
				isLegal = ((TurnMove) playerTurn).isLegal(level);
				isEnemy = ((TurnMove) playerTurn).isEnemy(level);
			} else if(playerTurn instanceof TurnUse) {
				isLegal = ((TurnUse) playerTurn).isLegal();
			} else {
				isLegal = true;
			}
			
			if(isEnemy) {
				player.setMyTurn(new TurnUse(player.getEquippedWeapon(), ((TurnMove) playerTurn).getEnemyAt(level)));
			}else if(!isLegal) {
				player.setMyTurn(null);
			}
		}
		
	}
	
	public void start(Stage stage) {
		Rectangle2D screen = Screen.getPrimary().getBounds();
		Rectangle2D sceneBounds = new Rectangle2D(0, 0, screen.getWidth() / 2, screen.getHeight() / 2);
		
		Group group = new Group();
		Scene scene = new Scene(group, sceneBounds.getWidth(), sceneBounds.getHeight());
		ui = new UI(sceneBounds, player);
		GraphicsContext gc = ui.getCanvas().getGraphicsContext2D();
		
		group.getChildren().add(ui.getOverlay());
		
		stage.setResizable(false);
		stage.setScene(scene);
		stage.show();
		gc.setFill(Color.BLACK);
		
		ui.updatePlayerInventory(player.getInventory());
		
		scene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			public void handle(KeyEvent event) {
				handleInput(event.getCode());
			}
		});
		
		new AnimationTimer() {
			public void handle(long now) {
				processTurns();
				cameraPos[0] = (player.getPosition()[0] * TILE_WIDTH) - ui.getCanvas().getWidth() / 2;
				cameraPos[1] = (player.getPosition()[1] * TILE_HEIGHT) - ui.getCanvas().getHeight() / 2;
				renderScreen(gc, cameraPos);
			}
		}.start();
	}
}
