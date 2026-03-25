package org.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.museum.artefacts.Artefact;
import org.museum.data.DataBase;
import org.museum.data.Inventory;
import org.museum.other.Room;

import java.net.URL;
import java.util.ResourceBundle;

public class StartPageController implements Initializable
{
    @FXML
    private TextField ArtefactSearchBox;
    @FXML
    private Button ViewAllImages;
    @FXML
    private Button LoginAsOtherUser;
    @FXML
    private ListView<Artefact> ArtefactList;
    @FXML
    private ListView<Room> RoomList;
    // changed from Button to RadioButton to allow selecting search mode
    @FXML
    private RadioButton SearchByName;
    @FXML
    private RadioButton SearchByType;
    @FXML
    private Button ShowAll;
    // label to display the currently active search mode for better UX
    @FXML
    private Label SearchModeLabel;

    private ObservableList<Artefact> artefactObservable;

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
        // select the radio button and focus the search box
        if (SearchByName != null) SearchByName.setSelected(true);
        ArtefactSearchBox.requestFocus();
    }

    @FXML
    public void handleSearchByType() {
        // select the radio button and focus the search box
        if (SearchByType != null) SearchByType.setSelected(true);
        ArtefactSearchBox.requestFocus();
    }

    @FXML
    public void handleShowAll() {
        ArtefactSearchBox.clear();
        // clear room selection too to show everything
        if (RoomList != null) RoomList.getSelectionModel().clearSelection();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        try
        {
            // refresh artefacts from DB
            Inventory.getInstance().UpdateArtefactsFromDB(false);

            var artifacts = Inventory.getInstance().getArtifacts();
            if (artifacts != null) {
                artefactObservable = FXCollections.observableArrayList(artifacts);
            } else {
                artefactObservable = FXCollections.observableArrayList();
            }

            // create a filtered view bound to the search box text
            FilteredList<Artefact> filtered = new FilteredList<>(artefactObservable, p -> true);
            ArtefactList.setItems(filtered);

            // populate rooms list from DB
            if (RoomList != null) {
                try {
                    var rooms = DataBase.getRooms();
                    ObservableList<Room> roomObs = FXCollections.observableArrayList(rooms);
                    RoomList.setItems(roomObs);

                    // show human readable name in the list
                    RoomList.setCellFactory(lv -> new ListCell<Room>() {
                        @Override
                        protected void updateItem(Room item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty || item == null) {
                                setText("");
                            } else {
                                setText(item.roomName() + " (" + item.roomNum() + ")");
                            }
                        }
                    });

                } catch (Exception e) {
                    // if rooms cannot be loaded, keep the list empty but log
                    System.err.println("Unable to load rooms: " + e.getMessage());
                }
            }

            // tooltip to clarify behaviour
            if (ArtefactSearchBox != null) {
                ArtefactSearchBox.setPromptText("Search For Artefact");
                ArtefactSearchBox.setTooltip(new Tooltip("Live search: type to filter artefacts. Use the radio buttons to switch search mode (Name / Type)."));
            }

            // setup a simple toggle group in code so FXML stays minimal
            ToggleGroup searchToggle = new ToggleGroup();
            if (SearchByName != null) SearchByName.setToggleGroup(searchToggle);
            if (SearchByType != null) SearchByType.setToggleGroup(searchToggle);
            if (SearchByName != null) SearchByName.setSelected(true); // default to name

            // set initial search mode label
            if (SearchModeLabel != null) {
                SearchModeLabel.setText("Searching by: Name");
            }

            // predicate update helper - now also respects selected room
            java.util.function.BiConsumer<FilteredList<Artefact>, String> updatePredicate = (filt, q) -> {
                String query = q == null ? "" : q.trim().toLowerCase();
                boolean useType = (SearchByType != null && SearchByType.isSelected());
                Room selectedRoom = (RoomList == null) ? null : RoomList.getSelectionModel().getSelectedItem();

                filt.setPredicate(a -> {
                    if (a == null) return false;

                    // room filter first (if a room is selected)
                    if (selectedRoom != null) {
                        if (a.getCurrentRoom() == null || !a.getCurrentRoom().equals(selectedRoom.roomNum())) {
                            return false;
                        }
                    }

                    // then apply search query
                    if (query.isEmpty()) return true;
                    if (useType) {
                        return a.getType() != null && a.getType().toLowerCase().contains(query);
                    } else {
                        return a.getName() != null && a.getName().toLowerCase().contains(query);
                    }
                });
            };

            ArtefactSearchBox.textProperty().addListener((obs, oldV, newV) -> {
                updatePredicate.accept(filtered, newV);
            });

            // when the toggle changes reapply the predicate using current search text and update label
            searchToggle.selectedToggleProperty().addListener((obs, oldT, newT) -> {
                updatePredicate.accept(filtered, ArtefactSearchBox.getText());
                if (SearchModeLabel != null) {
                    if (SearchByType != null && SearchByType.isSelected()) {
                        SearchModeLabel.setText("Searching by: Type");
                    } else {
                        SearchModeLabel.setText("Searching by: Name");
                    }
                }
            });

            // when a room is selected, reapply predicate to filter artefacts
            if (RoomList != null) {
                RoomList.getSelectionModel().selectedItemProperty().addListener((obs, oldRoom, newRoom) -> {
                    updatePredicate.accept(filtered, ArtefactSearchBox.getText());
                });
            }

            // display artefact name and type in the list and add a right-click context menu
            ArtefactList.setCellFactory(lv -> new ListCell<Artefact>() {
                @Override
                protected void updateItem(Artefact item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText("");
                        setContextMenu(null);
                        setOnMouseClicked(null);
                    } else {
                        // show name and type together
                        String type = item.getType() == null ? "" : item.getType();
                        setText(item.getName() + (type.isEmpty() ? "" : " (" + type + ")"));

                        // create context menu for this artefact
                        MenuItem viewImages = new MenuItem("View Images");
                        viewImages.setOnAction(evt -> {
                            try {
                                var images = DataBase.getImageFromArtefact(item.getName(), false);
                                if (images.isEmpty()) {
                                    ErrorController.showError("No Images", "No images found for artefact: " + item.getName());
                                } else {
                                    Inventory.getInstance().ViewImagesOfArtefact(images);
                                }
                            } catch (Exception e) {
                                ErrorController.showError("Image Error", "Unable to load images: " + e.getMessage(), e);
                            }
                        });

                        MenuItem viewDetails = new MenuItem("View Details");
                        viewDetails.setOnAction(evt -> {
                            try {
                                // open the new Artefact Details dialog
                                ArtefactDetailsController.showFor(item);
                            } catch (Exception e) {
                                ErrorController.showError("Details Error", "Unable to show details: " + e.getMessage(), e);
                            }
                        });

                        MenuItem requestLoan = new MenuItem("Request Loan");
                        requestLoan.setOnAction(evt -> {
                            try {
                                // preselect the artefact in the Request Loan scene then switch
                                RequestLoanController.setPreselectedArtefact(item.getName());
                                SceneManager.switchScene("Request Loan.fxml");
                            } catch (Exception e) {
                                ErrorController.showError("Request Loan Error", "Unable to open Request Loan: " + e.getMessage(), e);
                            }
                        });

                        ContextMenu menu = new ContextMenu(viewImages, viewDetails, requestLoan);

                        // ensure the right-click selects this item before showing menu
                        menu.setOnShowing(evt -> {
                            ListView<Artefact> parent = getListView();
                            if (parent != null) {
                                parent.getSelectionModel().select(getIndex());
                            }
                        });

                        setContextMenu(menu);

                        // double click to view details
                        setOnMouseClicked((MouseEvent me) -> {
                            if (me.getButton() == MouseButton.PRIMARY && me.getClickCount() == 2) {
                                try {
                                    ArtefactDetailsController.showFor(item);
                                } catch (Exception e) {
                                    ErrorController.showError("Details Error", "Unable to show details: " + e.getMessage(), e);
                                }
                            }
                        });
                    }
                }
            });

            System.out.println("StartPageController initialized with " + (Inventory.getInstance().getArtifacts() == null ? 0 : Inventory.getInstance().getArtifacts().size()) + " artefacts.");

        } catch (Exception e)
        {
            // show a user friendly error popup instead of throwing
            ErrorController.showError("Database Error", "Unable to load artefacts: " + e.getMessage(), e);
        }

     }
}
