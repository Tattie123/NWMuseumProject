package org.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class AddImageMenuController {
    @FXML
    private Button BackButton;
    @FXML
    private ComboBox<?> ArtefactComboBox;
    @FXML
    private Button UploadImageButton;

    @FXML
    public void handleBack() {
        SceneManager.switchScene("Staff Menu.fxml");
    }

    @FXML
    public void handleUploadImage() {
        String selectedArtefact = ArtefactComboBox.getValue() != null ? ArtefactComboBox.getValue().toString() : "Unknown";
        // TODO: Implement image upload functionality
        System.out.println("Uploading image for artefact: " + selectedArtefact);
    }
}

