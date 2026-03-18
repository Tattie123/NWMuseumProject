package org.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class MoveArtefactController {
    @FXML
    private Button BackButton;
    @FXML
    private ComboBox<?> ArtefactComboBox;
    @FXML
    private ComboBox<?> RoomComboBox;
    @FXML
    private Button MoveButton;

    @FXML
    public void handleBack() {
        SceneManager.switchScene("Staff Menu.fxml");
    }

    @FXML
    public void handleMoveArtefact() {
        String selectedArtefact = ArtefactComboBox.getValue() != null ? ArtefactComboBox.getValue().toString() : "Unknown";
        String selectedRoom = RoomComboBox.getValue() != null ? RoomComboBox.getValue().toString() : "Unknown";
        // TODO: Implement move artefact to room functionality
        System.out.println("Moving artefact " + selectedArtefact + " to room " + selectedRoom);
    }
}

