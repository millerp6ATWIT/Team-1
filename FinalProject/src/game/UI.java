package game;

import entity.Actor;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class UI {
	private SimpleStringProperty playerHP;
	private SimpleStringProperty playerDEF;
	private SimpleStringProperty playerSTR;
	private StackPane display;
	private BorderPane overlay;
	private FlowPane statDisplay;
	private Canvas canvas;
	
	public UI(Rectangle2D screenBounds, Actor player) {
		playerHP = new SimpleStringProperty("HP: ");
		playerDEF = new SimpleStringProperty("DEF: ");
		playerSTR = new SimpleStringProperty("STR: ");
		
		Text hp = new Text();
		Text def = new Text();
		Text str = new Text();
		hp.textProperty().bind(playerHP);
		def.textProperty().bind(playerDEF);
		str.textProperty().bind(playerSTR);
		
		display = new StackPane();
		overlay = new BorderPane();
		statDisplay = new FlowPane();
		canvas = new Canvas();
		
		overlay.setPrefSize(screenBounds.getWidth(), screenBounds.getHeight());
		canvas.setWidth(screenBounds.getWidth());
		canvas.setHeight(screenBounds.getHeight());
		statDisplay.setPrefHeight(50);
		statDisplay.setAlignment(Pos.CENTER);
		statDisplay.setHgap(10);
		
		statDisplay.getChildren().add(hp);
		statDisplay.getChildren().add(def);
		statDisplay.getChildren().add(str);
		
		
		overlay.setBottom(statDisplay);
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
	
	public void updatePlayerSTR(int playerSTR) {
		this.playerSTR.set("STR: " + Integer.toString(playerSTR));
	}
}
