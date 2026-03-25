package org.GUI;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.FileChooser;
import org.museum.data.DataBase;
import org.museum.data.Inventory;
import org.museum.artefacts.Artefact;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

public class AddImageMenuController {
    @FXML
    private Button BackButton;
    @FXML
    private ComboBox<String> ArtefactComboBox;
    @FXML
    private Button UploadImageButton;

    @FXML
    public void initialize() {
        loadArtefacts();
    }

    private void loadArtefacts() {
        try {
            Inventory.getInstance().UpdateArtefactsFromDB(false);
            List<Artefact> arts = Inventory.getInstance().getArtifacts();
            if (arts != null) {
                ArtefactComboBox.setItems(FXCollections.observableArrayList(arts.stream().map(Artefact::getName).toList()));
            }
        } catch (Exception e) {
            System.err.println("Unable to load artefacts for image upload: " + e.getMessage());
        }
    }

    @FXML
    public void handleBack() {
        SceneManager.switchScene("Staff Menu.fxml");
    }

    @FXML
    public void handleUploadImage() {
        String selectedArtefact = ArtefactComboBox.getValue();
        if (selectedArtefact == null || selectedArtefact.isBlank()) {
            ErrorController.showError("Validation Error", "Please select an artefact to attach the image to.");
            return;
        }

        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select Image");
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        File f = chooser.showOpenDialog(null);
        if (f == null) return;

        try {
            BufferedImage bi = ImageIO.read(f);
            String name = f.getName();
            String ext = "png";
            int i = name.lastIndexOf('.');
            if (i > 0) ext = name.substring(i + 1);
            DataBase.addImageToArtefact(selectedArtefact, bi, false, ext, f.getAbsolutePath());
            ErrorController.showInfo("Success", "Image uploaded for artefact: " + selectedArtefact);
        } catch (Exception e) {
            ErrorController.showError("Upload Error", "Unable to upload image: " + e.getMessage(), e);
        }
    }
}
