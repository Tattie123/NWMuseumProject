package org.GUI;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
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

    // Drag & drop UI elements
    @FXML
    private StackPane DropPane;
    @FXML
    private ImageView ImagePreview;
    @FXML
    private Label DropLabel;

    // File selected either by drag-drop or file chooser
    private File droppedFile = null;

    @FXML
    public void initialize() {
        loadArtefacts();
        // ensure label visibility based on preview (null-safe)
        if (ImagePreview != null && DropLabel != null) {
            DropLabel.setVisible(ImagePreview.getImage() == null);
        }
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

    // Drag-and-drop handlers
    @FXML
    public void handleDragOver(DragEvent event) {
        Dragboard db = event.getDragboard();
        if (db.hasFiles() && db.getFiles().stream().anyMatch(this::isImageFile)) {
            event.acceptTransferModes(TransferMode.COPY);
        }
        event.consume();
    }

    @FXML
    public void handleDragDropped(DragEvent event) {
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasFiles()) {
            for (File file : db.getFiles()) {
                if (isImageFile(file)) {
                    droppedFile = file;
                    try {
                        Image fxImage = new Image(file.toURI().toString(), 160, 120, true, true);
                        ImagePreview.setImage(fxImage);
                        DropLabel.setVisible(false);
                    } catch (Exception e) {
                        System.err.println("Failed to load dragged image: " + e.getMessage());
                    }
                    success = true;
                    break;
                }
            }
        }
        event.setDropCompleted(success);
        event.consume();
    }

    @FXML
    public void handleDragEnter(DragEvent event) {
        DropPane.setStyle("-fx-border-color: #00aaff; -fx-border-width: 2;");
        event.consume();
    }

    @FXML
    public void handleDragExit(DragEvent event) {
        DropPane.setStyle("");
        event.consume();
    }

    private boolean isImageFile(File f) {
        String name = f.getName().toLowerCase();
        return name.endsWith(".png") || name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".gif");
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

        File f = (droppedFile != null) ? droppedFile : chooser.showOpenDialog(null);
        if (f == null) return;

        try {
            BufferedImage bi = ImageIO.read(f);
            String name = f.getName();
            String ext = "png";
            int i = name.lastIndexOf('.');
            if (i > 0) ext = name.substring(i + 1);
            DataBase.addImageToArtefact(selectedArtefact, bi, false, ext, f.getAbsolutePath());
            ErrorController.showInfo("Success", "Image uploaded for artefact: " + selectedArtefact);
            // clear dropped file & preview
            droppedFile = null;
            ImagePreview.setImage(null);
            DropLabel.setVisible(true);
        } catch (Exception e) {
            ErrorController.showError("Upload Error", "Unable to upload image: " + e.getMessage(), e);
        }
    }
}
