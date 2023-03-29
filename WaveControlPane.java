// 
//      
///  Description: Practice javaFX animation and multi-threading programming.

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

import java.lang.*;
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
import javafx.scene.control.ColorPicker;

import javafx.scene.paint.Color;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Slider; //***
import javafx.beans.value.ChangeListener; //***
import javafx.beans.value.ObservableValue; //***

public class WaveControlPane extends Pane {

	private WaveDisplayPane wavePane;
	private int width, height;
	private Color color;
	private ColorPicker picker;

	// ******************************************************************
	// add instance variables for sliders, buttons, and labels
	// ******************************************************************
	private Button btnStart, btnStop, btnClear, btnSurprise;
	private Slider sldSpeed, sldWidth, sldHeight;
	private Label lbSpeed, lbWidth, lbHeight;
	// Text text1,text2,text3;

	// constructor to create all components, set their handler/listener,
	// and arrange them using layout panes.
	public WaveControlPane(int h, int w, Color initialColor) {
		this.color = initialColor;
		this.width = (int) (h * 0.68);
		this.height = w - 10;

		// creates a pane to display waves with the specified color
		wavePane = new WaveDisplayPane(width, color);
		wavePane.setMinSize(width, height);
		wavePane.setMaxSize(width, height);

		// create a color picker with the specified initial color
		picker = new ColorPicker(color);
		picker.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

		// ************************************************************************
		// create 4 buttons and resize them to the width of the VBox pane,
		// then add them to the VBox buttonPane instantiated below.
		// *************************************************************************
		btnStart = new Button("Start");
		btnStop = new Button("Stop");
		btnClear = new Button("Clear");
		btnSurprise = new Button("Surprise!");
		btnStart.setMaxWidth(Double.MAX_VALUE);
		btnStop.setMaxWidth(Double.MAX_VALUE);
		btnClear.setMaxWidth(Double.MAX_VALUE);
		btnSurprise.setMaxWidth(Double.MAX_VALUE);

		VBox buttonPane = new VBox(btnStart, btnStop, btnClear, btnSurprise, picker);

		buttonPane.setPrefSize(100, 100);
		buttonPane.setAlignment(Pos.CENTER);

		// ************************************************************************
		// create 3 sliders and 3 labels and add them to the VBox panes
		// instantiated below.
		// *************************************************************************
		lbSpeed = new Label("Speed");
		sldSpeed = new Slider(5, 100, 20);
		sldSpeed.setMajorTickUnit(10.0);
		sldSpeed.setMinorTickCount(5);
		// sldSpeed.setValue(20);
		sldSpeed.setShowTickLabels(true);
		sldSpeed.setShowTickMarks(true);

		lbWidth = new Label("Width");
		sldWidth = new Slider(5, 100, 100);
		sldWidth.setMajorTickUnit(10.0);
		sldWidth.setMinorTickCount(5);
		// sldWidth.setValue(100);
		sldWidth.setShowTickLabels(true);
		sldWidth.setShowTickMarks(true);

		lbHeight = new Label("Height");
		sldHeight = new Slider(5, 100, 100);
		sldHeight.setMajorTickUnit(10.0);
		sldHeight.setMinorTickCount(5);
		// sldHeight.setValue(100);
		sldHeight.setShowTickLabels(true);
		sldHeight.setShowTickMarks(true);

		VBox speedSliderPane = new VBox(lbSpeed, sldSpeed);
		VBox waveLengthSliderPane = new VBox(lbWidth, sldWidth);
		VBox waveAmplitudeSliderPane = new VBox(lbHeight, sldHeight);

		TilePane sliderPane = new TilePane();
		sliderPane.setPrefColumns(3);
		sliderPane.setPadding(new Insets(5, 5, 5, 5));
		sliderPane.setAlignment(Pos.CENTER);
		sliderPane.getChildren().addAll(speedSliderPane, waveLengthSliderPane, waveAmplitudeSliderPane);

		HBox controls = new HBox(buttonPane, sliderPane);
		controls.setAlignment(Pos.CENTER);

		BorderPane controlsAndWaves = new BorderPane();
		controlsAndWaves.setLeft(controls);
		controlsAndWaves.setCenter(wavePane);

		this.getChildren().add(controlsAndWaves);

		// ************************************************************************
		// Register the buttons, sliders, and color picker with the
		// appropriate handler object
		// *************************************************************************
		btnStart.setOnAction(new ButtonHandler());
		btnStop.setOnAction(new ButtonHandler());
		btnClear.setOnAction(new ButtonHandler());
		btnSurprise.setOnAction(new ButtonHandler());
		sldSpeed.valueProperty().addListener(new SpeedHandler());
		sldWidth.valueProperty().addListener(new WaveLengthHandler());
		sldHeight.valueProperty().addListener(new WaveAmplitudeHandler());
		sldSpeed.setOrientation(Orientation.VERTICAL);
		sldWidth.setOrientation(Orientation.VERTICAL);
		sldHeight.setOrientation(Orientation.VERTICAL);
		picker.setOnAction(new ColorHandler());

	}

	// ************************************************************************
	// Implement event handlers for the four buttons (task #3a),
	// the color picker (task #3b), the speed slider (task #3c), and the
	// sliders for wave amplitude and legth (tasks #3d, #3e)
	// *************************************************************************
	private class ButtonHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			Object source = e.getSource();
			if (source == btnStart) {
				wavePane.resume();
			}
			if (source == btnStop) {
				wavePane.suspend();
			}
			if (source == btnClear) {
				wavePane.clearPane();
			}
			if (source == btnSurprise) {
				wavePane.suspend();
				int length = (int) (Math.random() * (100 - 5)) + 5;
				wavePane.setWaveLength(length);
				sldWidth.setValue(length);
				int Height = (int) (Math.random() * (100 - 5)) + 5;
				wavePane.setWaveAmplitude(Height);
				sldHeight.setValue(Height);
				int r = (int) (Math.random() * 255);
				int g = (int) (Math.random() * 255);
				int b = (int) (Math.random() * 255);
				Color c1 = Color.rgb(r, g, b);
				wavePane.changeColor(c1);
				picker.setValue(c1);
				int rate = (int) (Math.random() * (100 - 5)) + 5;
				wavePane.setRate(rate);
				sldSpeed.setValue(rate);
				wavePane.resume();
			}
		}
	}

	private class ColorHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			Color c = picker.getValue();
			wavePane.changeColor(c);
		}
	}

	private class SpeedHandler implements ChangeListener<Number> {
		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
			// get vertical slider's current value.
			// double yCoordinate = (sldSpeed.getMax() - sldSpeed.getValue()) /
			// sldSpeed.getMax() * wavePane.getHeight();
			wavePane.setRate((int) sldSpeed.getValue());

		}
	}

	private class WaveLengthHandler implements ChangeListener<Number> {
		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
			// double yCoordinate = (sldWidth.getMax() - sldWidth.getValue()) /
			// sldWidth.getMax() * wavePane.getHeight();
			wavePane.setWaveLength((int) sldWidth.getValue());
		}
	}

	private class WaveAmplitudeHandler implements ChangeListener<Number> {
		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
			// double yCoordinate = (sldHeight.getMax() - sldHeight.getValue()) /
			// sldHeight.getMax()
			// * wavePane.getHeight();
			wavePane.setWaveAmplitude((int) sldHeight.getValue());
		}
	}

}
