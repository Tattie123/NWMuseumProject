package org.GUI;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import org.museum.artefacts.Artefact;
import org.museum.data.Inventory;

import java.net.URL;
import java.util.ResourceBundle;

public class StartPageController implements Initializable
{
    @FXML
    private Button ViewAllImages;
    @FXML
    private Button LoginAsOtherUser;
    @FXML
    private ListView<Artefact> ArtefactList;
    @FXML
    private ListView<?> RoomList;
    @FXML
    private Button SearchByName;
    @FXML
    private Button SearchByType;
    @FXML
    private Button ShowAll;

    @FXML
    public void handleViewAllImages() {
        SceneManager.switchScene("View All Images.fxml");
    }

    @FXML
    public void handleLoginAsOtherUser() {
        SceneManager.switchScene("Login Menu.fxml");
    }

    @FXML
    public void handleSearchByName() {
        // TODO: Implement search by name functionality
        System.out.println("Searching by name...");
    }

    @FXML
    public void handleSearchByType() {
        // TODO: Implement search by type functionality
        System.out.println("Searching by type...");
    }

    @FXML
    public void handleShowAll() {
        // TODO: Implement show all functionality
        System.out.println("Showing all artefacts...");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        ArtefactList.setItems(FXCollections.observableArrayList(Inventory.getInstance().getArtifacts()).sorted());

    }
}
