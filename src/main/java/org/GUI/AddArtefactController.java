package org.GUI;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.museum.artefacts.Artefact;
import org.museum.artefacts.Painting;
import org.museum.artefacts.Misc;
import org.museum.data.DataBase;
import org.museum.data.Inventory;
import org.museum.other.Room;

import java.sql.Date;
import java.util.List;

public class AddArtefactController {
    @FXML
    private Button BackButton;
    @FXML
    private TextField ArtefactNameField;
    @FXML
    private TextField StyleField;
    @FXML
    private ComboBox<String> OriginCountryCombo1;
    @FXML
    private ComboBox<String> TypeCombo;
    @FXML
    private ComboBox<Room> CurrentRoomCombo;
    @FXML
    private ComboBox<String> HistoricEraCombo;
    @FXML
    private ComboBox<String> OriginCountryCombo2;
    @FXML
    private DatePicker CreationDatePicker;
    @FXML
    private TextField WidthField;
    @FXML
    private TextField HeightField;
    @FXML
    private Button SubmitButton;
    // Author field exists in FXML but was not wired; add reference
    @FXML
    private TextField AuthorField;

    @FXML
    public void initialize() {
        // populate type choices and rooms
        TypeCombo.setItems(FXCollections.observableArrayList("Painting", "Misc", "Furniture", "Pottery", "Sculpture"));
        try {
            List<Room> rooms = DataBase.getRooms();
            CurrentRoomCombo.setItems(FXCollections.observableArrayList(rooms));
            CurrentRoomCombo.setCellFactory(lv -> new javafx.scene.control.ListCell<Room>() {
                @Override
                protected void updateItem(Room item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) setText(""); else setText(item.roomName() + " (" + item.roomNum() + ")");
                }
            });
            CurrentRoomCombo.setButtonCell(new javafx.scene.control.ListCell<Room>() {
                @Override
                protected void updateItem(Room item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) setText(""); else setText(item.roomName() + " (" + item.roomNum() + ")");
                }
            });
        } catch (Exception e) {
            System.err.println("Unable to load rooms for Add Artefact: " + e.getMessage());
        }
    }

    @FXML
    public void handleBack() {
        SceneManager.switchScene("Staff Menu.fxml");
    }

    @FXML
    public void handleAddArtefact() {
        String name = ArtefactNameField.getText();
        String style = StyleField.getText();
        String originCountry = OriginCountryCombo1 == null ? "" : OriginCountryCombo1.getValue();
        String type = TypeCombo == null ? "Misc" : TypeCombo.getValue();
        Room currentRoom = CurrentRoomCombo == null ? null : CurrentRoomCombo.getValue();
        String historicEra = HistoricEraCombo == null ? "" : HistoricEraCombo.getValue();
        java.sql.Date creationDate = null;
        if (CreationDatePicker != null && CreationDatePicker.getValue() != null) creationDate = Date.valueOf(CreationDatePicker.getValue());
        double width = 0, height = 0;
        try { if (WidthField != null && !WidthField.getText().isBlank()) width = Double.parseDouble(WidthField.getText()); } catch (Exception ignored) {}
        try { if (HeightField != null && !HeightField.getText().isBlank()) height = Double.parseDouble(HeightField.getText()); } catch (Exception ignored) {}
        String author = (AuthorField == null) ? "" : AuthorField.getText();

        if (name == null || name.isBlank()) {
            ErrorController.showError("Validation Error", "Artefact name is required.");
            return;
        }

        try {
            Artefact artefact;
            if (type != null && type.equals("Painting")) {
                artefact = new Painting(historicEra, style, originCountry, (currentRoom==null?"":currentRoom.roomNum()), author, creationDate, width, height, name, false);
            } else {
                artefact = new Misc(historicEra, style, originCountry, (currentRoom==null?"":currentRoom.roomNum()), author, creationDate, width, height, name, false);
            }

            boolean ok = DataBase.addArtefact(artefact, false);
            if (ok) {
                Inventory.getInstance().UpdateArtefactsFromDB(false);
                ErrorController.showInfo("Success", "Artefact added: " + name);
                // clear inputs
                ArtefactNameField.clear(); StyleField.clear(); if (AuthorField!=null) AuthorField.clear();
            } else {
                ErrorController.showError("DB Error", "Unable to add artefact.");
            }
        } catch (Exception e) {
            ErrorController.showError("Error", "Unable to add artefact: " + e.getMessage(), e);
        }
    }
}
