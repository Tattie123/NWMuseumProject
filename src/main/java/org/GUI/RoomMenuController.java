package org.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class RoomMenuController {
    @FXML
    private Button BackButton;
    @FXML
    private ListView<?> RoomListView;
    @FXML
    private TextField RoomNameField;
    @FXML
    private TextField RoomNumberField;
    @FXML
    private Button AddRoomButton;

    @FXML
    public void handleBack() {
        SceneManager.switchScene("Manager Menu.fxml");
    }

    @FXML
    public void handleAddRoom() {
        String roomName = RoomNameField.getText();
        String roomNumber = RoomNumberField.getText();
        // TODO: Implement add room to database functionality
        System.out.println("Adding room: " + roomName + " with number: " + roomNumber);
        // Clear fields after adding
        RoomNameField.clear();
        RoomNumberField.clear();
    }
}

