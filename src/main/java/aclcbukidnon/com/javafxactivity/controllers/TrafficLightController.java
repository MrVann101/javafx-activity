package aclcbukidnon.com.javafxactivity.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class TrafficLightController {

    // Enum for traffic light colors
    private enum TrafficLightColor {
        STOP,
        HOLD,
        GO
    }

    private TrafficLightColor currentColor = TrafficLightColor.STOP;
    private Timeline timeline;

    // FXML elements (make sure these fx:id values match your FXML file)
    @FXML
    private Circle redLight;

    @FXML
    private Circle orangeLight;

    @FXML
    private Circle greenLight;

    // Called automatically after the FXML is loaded
    @FXML
    public void initialize() {
        // Set initial light state
        updateLights();

        // Set up a timer that changes light every 3 seconds
        timeline = new Timeline(
                new KeyFrame(Duration.seconds(3), e -> onTimerChange())
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play(); // Start cycling automatically
    }

    // Called every 3 seconds to switch light
    private void onTimerChange() {
        switch (currentColor) {
            case STOP:
                currentColor = TrafficLightColor.HOLD;
                break;
            case HOLD:
                currentColor = TrafficLightColor.GO;
                break;
            case GO:
                currentColor = TrafficLightColor.STOP;
                break;
        }
        updateLights();
    }

    // Update the fill color of each light
    private void updateLights() {
        redLight.setFill(currentColor == TrafficLightColor.STOP ? Color.RED : Color.web("#575757"));
        orangeLight.setFill(currentColor == TrafficLightColor.HOLD ? Color.ORANGE : Color.web("#575757"));
        greenLight.setFill(currentColor == TrafficLightColor.GO ? Color.LIMEGREEN : Color.web("#575757"));
    }
}
