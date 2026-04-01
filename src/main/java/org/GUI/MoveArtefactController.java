package org.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import org.museum.artefacts.Artefact;
import org.museum.data.Inventory;
import org.museum.other.Room;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MoveArtefactController implements Initializable {
    @FXML
    private Button BackButton;
    @FXML
    private ComboBox<String> ArtefactComboBox;
    @FXML
    private ComboBox<Room> RoomComboBox;
    @FXML
    private Button MoveButton;
    @FXML
    private Label CurrentRoomLabel;

    @FXML
    public void handleBack() {
        SceneManager.switchScene("Staff Menu.fxml");
    }

    @FXML
    public void handleMoveArtefact() {
        String selectedArtefact = ArtefactComboBox.getValue();
        Room selectedRoom = RoomComboBox.getValue();
        if (selectedArtefact == null || selectedRoom == null) {
            ErrorController.showError("Selection Error", "Please select both an artefact and a target room.");
            return;
        }

        try {
            boolean ok = Inventory.moveArtefactToRoom(selectedArtefact, selectedRoom.roomNum(), false);
            if (ok) {
                // refresh local lists
                loadArtefacts();
                updateCurrentRoomLabelFor(selectedArtefact);
                ErrorController.showInfo("Success", "Artefact moved successfully.");
            } else {
                ErrorController.showError("Move Failed", "Unable to move artefact. Please try again.");
            }
        } catch (Exception e) {
            ErrorController.showError("Move Error", "Error while moving artefact: " + e.getMessage(), e);
        }
    }

    private void loadArtefacts() {
        try {
            Inventory.getInstance().UpdateArtefactsFromDB(false);
            List<Artefact> arts = Inventory.getInstance().getArtifacts();
            ObservableList<String> artefactNames = FXCollections.observableArrayList();
            if (arts != null) {
                for (Artefact a : arts) {
                    if (a != null && a.getName() != null) artefactNames.add(a.getName());
                }
            }
            ArtefactComboBox.setItems(artefactNames);

            // update current room display when selection changes
            // update when selection changes and also when the user activates a selection
            ArtefactComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> updateCurrentRoomLabelFor(newV));
            ArtefactComboBox.setOnAction(evt -> updateCurrentRoomLabelFor(ArtefactComboBox.getValue()));

            // if there's a preselected value, show it immediately
            if (ArtefactComboBox.getValue() != null) {
                updateCurrentRoomLabelFor(ArtefactComboBox.getValue());
            }
        } catch (Exception e) {
            System.err.println("Unable to load artefacts for move menu: " + e.getMessage());
        }
    }

    private void updateCurrentRoomLabelFor(String artefactName) {
        if (artefactName == null) {
            if (CurrentRoomLabel != null) CurrentRoomLabel.setText("-");
            return;
        }
        try {
            Artefact art = Inventory.getInstance().getArtefactByName(artefactName, false);
            if (art == null) {
                if (CurrentRoomLabel != null) CurrentRoomLabel.setText("Unknown");
                return;
            }
            String roomId = art.getCurrentRoom();
            if (roomId == null || roomId.isEmpty()) {
                if (CurrentRoomLabel != null) CurrentRoomLabel.setText("Unassigned");
                return;
            }
            try {
                Room r = org.museum.data.DataBase.getRoomFromName(roomId, false);
                if (r != null) {
                    if (CurrentRoomLabel != null) CurrentRoomLabel.setText(r.roomName() + " (" + r.roomNum() + ")");
                    return;
                }
            } catch (Exception ignored) {
                // fall back to showing raw room id
            }
            if (CurrentRoomLabel != null) CurrentRoomLabel.setText(roomId);
        } catch (Exception e) {
            if (CurrentRoomLabel != null) CurrentRoomLabel.setText("Error");
        }
    }

    private void loadRooms() {
        try {
            List<Room> rooms = org.museum.data.DataBase.getRooms();
            ObservableList<Room> roomObs = FXCollections.observableArrayList(rooms);
            RoomComboBox.setItems(roomObs);
            // show readable room name in the ComboBox
            RoomComboBox.setCellFactory(lv -> new javafx.scene.control.ListCell<Room>() {
                @Override
                protected void updateItem(Room item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) setText(""); else setText(item.roomName() + " (" + item.roomNum() + ")");
                }
            });
            RoomComboBox.setButtonCell(new javafx.scene.control.ListCell<Room>() {
                @Override
                protected void updateItem(Room item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) setText(""); else setText(item.roomName() + " (" + item.roomNum() + ")");
                }
            });
        } catch (Exception e) {
            System.err.println("Unable to load rooms for move menu: " + e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // ensure the current room label is visible and initialized
        if (CurrentRoomLabel != null) {
            CurrentRoomLabel.setVisible(true);
            CurrentRoomLabel.setText("-");
        }

        loadArtefacts();
        loadRooms();
    }
}
