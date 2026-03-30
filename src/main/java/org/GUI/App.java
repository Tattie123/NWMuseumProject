package org.GUI;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.museum.data.DataBase;
import org.museum.data.Inventory;

public class App extends Application
{
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        // make primary stage available to SceneManager and ErrorController
        SceneManager.setPrimaryStage(primaryStage);

        try
        {
            // attempt to get a connection (will throw on failure)
            DataBase.getConnection(false);
            Inventory.getInstance().UpdateArtefactsFromDB(false);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Start Page.fxml"));
            Parent root = loader.load();

            primaryStage.setTitle("Museum");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
            try (var iconStream = getClass().getResourceAsStream("/MuseumIcon2.png")) {
                if (iconStream != null) {
                    primaryStage.getIcons().add(new Image(iconStream));
                }
            }

            primaryStage.setResizable(false);
        } catch (Exception e)
        {
            ErrorController.showError("Database Connection Failed", "Unable to connect to the database. \n\nDetails: " + e.getMessage());
            // after showing error, exit the application
            Platform.exit();
        }
    }
}
