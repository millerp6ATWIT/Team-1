package game;

import java.util.ArrayList;


import entity.Actor;
import item.Item;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class UI {
	private SimpleStringProperty playerHP;
	private SimpleStringProperty playerDEF;
	private SimpleStringProperty playerATK;
	private StackPane display;
	private BorderPane overlay;
	private FlowPane statDisplay;
	private Canvas canvas;
	private Background background;
	private ScrollPane inventory;
	
	
	public UI(Rectangle2D sceneBounds, Actor player) {
		playerHP = new SimpleStringProperty("HP: ");
		playerDEF = new SimpleStringProperty("DEF: ");
		playerATK = new SimpleStringProperty("ATK: ");
		
		Text hp = new Text();
		hp.textProperty().bind(playerHP);
		hp.setScaleX(1.5);
		hp.setScaleY(1.5);
		hp.setFill(Color.RED);
		Text def = new Text();
		def.textProperty().bind(playerDEF);
		def.setScaleX(1.5);
		def.setScaleY(1.5);
		def.setFill(Color.BLUE);
		Text str = new Text();
		str.textProperty().bind(playerATK);
		str.setScaleX(1.5);
		str.setScaleY(1.5);
		str.setFill(Color.ORANGE);
		
		display = new StackPane();
		overlay = new BorderPane();
		statDisplay = new FlowPane();
		canvas = new Canvas(sceneBounds.getWidth(), sceneBounds.getHeight());
		inventory = new ScrollPane(new HBox());
		background = new Background(new BackgroundFill(Color.GREY, new CornerRadii(0), new Insets(0, sceneBounds.getWidth() / 4, 0, sceneBounds.getWidth() / 4)));
		
		overlay.setPrefSize(sceneBounds.getWidth(), sceneBounds.getHeight());
		statDisplay.setPrefHeight(25);
		statDisplay.setAlignment(Pos.CENTER);
		statDisplay.setHgap(75);
		statDisplay.setBackground(background);
		inventory.setPrefWidth(sceneBounds.getWidth() / 6);
		
		statDisplay.getChildren().add(hp);
		statDisplay.getChildren().add(def);
		statDisplay.getChildren().add(str);
		
		
		overlay.setBottom(statDisplay);
		overlay.setRight(inventory);
		overlay.setPadding(new Insets(sceneBounds.getWidth() / 50, sceneBounds.getWidth() / 50, sceneBounds.getWidth() / 50, sceneBounds.getWidth() / 50));
		display.getChildren().add(canvas);
		display.getChildren().add(overlay);
	}
	
	public Pane getOverlay() {
		return display;
	}
	
	public Canvas getCanvas() {
		return canvas;
	}
	
	public void updatePlayerHP(int playerHP) {
		this.playerHP.set("HP: " + Integer.toString(playerHP));
	}
	
	public void updatePlayerDEF(int playerDEF) {
		this.playerDEF.set("DEF: " + Integer.toString(playerDEF));
	}
	
	public void updatePlayerATK(int playerATK) {
		this.playerATK.set("ATK: " + Integer.toString(playerATK));
	}
	
	public void updatePlayerInventory(ArrayList<Item> inv) {
		((HBox) inventory.getContent()).getChildren().clear();
		
		for(Item i: inv) {
			Button b = new Button(i.getName());
			b.setPrefSize(inventory.getWidth() - 2.5, inventory.getHeight() / 10);
			b.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					i.use(i.getOwner());
					((HBox) inventory.getContent()).getChildren().remove(b);
				}
			});
			
			((HBox) inventory.getContent()).getChildren().add(b);
		}
	}
}
