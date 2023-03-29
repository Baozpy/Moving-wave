// 
//      
///  Description: Practice javaFX animation and multi-threading programming.

import java.lang.*;
import javafx.util.*;
import java.util.*;
import javafx.scene.layout.*;
import javafx.animation.*;
import javafx.scene.shape.Shape;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.geometry.Pos;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.*;
import javafx.scene.shape.*;

public class WaveDisplayPane extends Pane {

	// Task #1: immplement instance variables, constructor, and methods
	// as outlned in the UML diagram and assignment description
	private Timeline timeline;
	private int time;
	private int waveLength;
	private int waveAmplitude;
	private int paneWidth;
	private Color color;

	// Constructor
	public WaveDisplayPane(int paneWidth, Color color) {
		this.waveAmplitude = 100;
		this.waveLength = 50;
		this.paneWidth = paneWidth;
		this.color = color;
		this.time = 0;
		this.setStyle("-fx-background-color:white;" + "-fx-border-color:black;");
		KeyFrame kf = new KeyFrame(Duration.millis(500), new WaveHandler());
		timeline = new Timeline(kf);
		// KeyValue kv = new KeyValue(dot.xProperty(), 20);
		timeline.setCycleCount(Timeline.INDEFINITE);
		// timeline.getKeyFrames().add(kf);
		timeline.play();
	}

	// reset the speed
	public void setRate(int num) {
		timeline.setRate(num);
	}

	// plays an animation
	public void resume() {
		timeline.play();
	}

	//// pauses an animation and resets the time to 0 (zero)
	public void suspend() {
		timeline.pause();
		time = 0;
	}

	// sets a new color for the wave
	public void changeColor(Color c) {
		color = c;
	}

	// removes all waves from the pane and suspends the animation
	public void clearPane() {
		this.getChildren().clear();
	}

	// set height
	public void setWaveLength(int num) {
		waveLength = num;
	}

	// reset Amplitude
	public void setWaveAmplitude(int num) {
		waveAmplitude = num;
	}

	// defines an event listener to draw a new point
	private class WaveHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			time++;
			int x = (waveLength * time) / 100;
			int y = (int) (waveAmplitude * Math.sin((0.0174533) * time) + 115);

			if (x < paneWidth) {
				Circle dot = new Circle(x, y, 2);
				dot.setFill(color);
				getChildren().add(dot);
			} else
				suspend();
		}
	}
}
