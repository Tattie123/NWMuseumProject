package org.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class AddArtefactController {
    @FXML
    private Button BackButton;
    @FXML
    private TextField ArtefactNameField;
    @FXML
    private TextField StyleField;
    @FXML
    private ComboBox<?> OriginCountryCombo1;
    @FXML
    private ComboBox<?> TypeCombo;
    @FXML
    private ComboBox<?> CurrentRoomCombo;
    @FXML
    private ComboBox<?> HistoricEraCombo;
    @FXML
    private ComboBox<?> OriginCountryCombo2;
    @FXML
    private DatePicker CreationDatePicker;
    @FXML
    private TextField WidthField;
    @FXML
    private TextField HeightField;
    @FXML
    private Button SubmitButton;

    @FXML
    public void handleBack() {
        SceneManager.switchScene("Staff Menu.fxml");
    }

    @FXML
    public void handleAddArtefact() {
        String name = ArtefactNameField.getText();
        String style = StyleField.getText();
        // TODO: Implement artefact addition to database
        System.out.println("Adding artefact: " + name + " with style: " + style);
    }
}

