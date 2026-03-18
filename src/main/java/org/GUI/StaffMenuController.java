package org.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class StaffMenuController {
    @FXML
    private Button BackButton;
    @FXML
    private Button MoveArtefact;
    @FXML
    private Button AddArtefact;
    @FXML
    private Button AddImage;

    @FXML
    public void handleBack() {
        SceneManager.switchScene("Start Page.fxml");
    }

    @FXML
    public void handleMoveArtefact() {
        SceneManager.switchScene("Move Artefact Menu.fxml");
    }

    @FXML
    public void handleAddArtefact() {
        SceneManager.switchScene("Add Artefact.fxml");
    }

    @FXML
    public void handleAddImage() {
        SceneManager.switchScene("Add Image Menu.fxml");
    }
}

