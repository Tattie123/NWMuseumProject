package org.GUI;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.museum.data.DataBase;
import org.museum.other.Room;

import java.util.List;

public class RoomMenuController {
    @FXML
    private Button BackButton;
    @FXML
    private ListView<Room> RoomListView;
    @FXML
    private TextField RoomNameField;
    @FXML
    private TextField RoomNumberField;
    @FXML
    private TextField RoomCapacityField;
    @FXML
    private Button AddRoomButton;

    @FXML
    public void handleBack() {
        SceneManager.switchScene("Manager Menu.fxml");
    }

    @FXML
    public void initialize() {
        loadRooms();
    }

    private void loadRooms() {
        try {
            List<Room> rooms = DataBase.getRooms();
            RoomListView.setItems(FXCollections.observableArrayList(rooms));
            RoomListView.setCellFactory(lv -> new javafx.scene.control.ListCell<Room>() {
                @Override
                protected void updateItem(Room item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) setText(""); else setText(item.roomName() + " (" + item.roomNum() + ")");
                }
            });
        } catch (Exception e) {
            System.err.println("Unable to load rooms: " + e.getMessage());
        }
    }

    @FXML
    public void handleAddRoom() {
        String roomName = RoomNameField.getText();
        String roomNumber = RoomNumberField.getText();
        String capText = (RoomCapacityField == null) ? "" : RoomCapacityField.getText();
        if (roomName == null || roomName.isBlank() || roomNumber == null || roomNumber.isBlank()) {
            ErrorController.showError("Validation Error", "Room name and number must be provided.");
            return;
        }

        int capacity = 100;
        try {
            if (capText != null && !capText.isBlank()) capacity = Integer.parseInt(capText);
        } catch (NumberFormatException nfe) {
            ErrorController.showError("Validation Error", "Capacity must be a whole number. Using default 100.");
        }

        try {
            Room r = new Room(roomNumber, roomName, capacity);
            boolean ok = DataBase.addRoom(r, false);
            if (ok) {
                loadRooms();
                RoomNameField.clear();
                RoomNumberField.clear();
                if (RoomCapacityField != null) RoomCapacityField.clear();
                ErrorController.showInfo("Success", "Room added.");
            } else {
                ErrorController.showError("DB Error", "Unable to add room.");
            }
        } catch (Exception e) {
            ErrorController.showError("Error", "Unable to add room: " + e.getMessage(), e);
        }
    }
}
