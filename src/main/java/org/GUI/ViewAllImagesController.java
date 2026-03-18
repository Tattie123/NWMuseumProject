package org.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class ViewAllImagesController {
    @FXML
    private Button BackButton;
    @FXML
    private ImageView ImageView;
    @FXML
    private Button FitButton;
    @FXML
    private Button NextButton;
    @FXML
    private Button PreviousButton;
    @FXML
    private Button ResetZoomButton;

    @FXML
    public void handleBack() {
        SceneManager.switchScene("Start Page.fxml");
    }

    @FXML
    public void handleFit() {
        // TODO: Implement fit to window functionality
        System.out.println("Fitting image to window...");
    }

    @FXML
    public void handleNext() {
        // TODO: Implement next image functionality
        System.out.println("Showing next image...");
    }

    @FXML
    public void handlePrevious() {
        // TODO: Implement previous image functionality
        System.out.println("Showing previous image...");
    }

    @FXML
    public void handleResetZoom() {
        // TODO: Implement reset zoom functionality
        System.out.println("Resetting zoom...");
    }
}

