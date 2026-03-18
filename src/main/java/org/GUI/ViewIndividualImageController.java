package org.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class ViewIndividualImageController {
    @FXML
    private Button BackButton;
    @FXML
    private ImageView ImageView;
    @FXML
    private Button FitButton;
    @FXML
    private Button ResetZoomButton;

    @FXML
    public void handleBack() {
        SceneManager.switchScene("View All Images.fxml");
    }

    @FXML
    public void handleFit() {
        // TODO: Implement fit to window functionality
        System.out.println("Fitting image to window...");
    }

    @FXML
    public void handleResetZoom() {
        // TODO: Implement reset zoom functionality
        System.out.println("Resetting zoom...");
    }
}

