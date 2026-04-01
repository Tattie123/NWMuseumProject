package org.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ViewInfoController {
    @FXML
    private Button BackButton;

    @FXML
    public void handleBack() {
        SceneManager.switchScene("Start Page.fxml");
    }
}

