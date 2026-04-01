package org.GUI;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class SceneManager {
    private static Stage primaryStage;

    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

    public static void switchScene(String fxmlFile) {
        if (primaryStage == null) {
            ErrorController.showError("Scene Error", "Primary stage is not set. Cannot switch scenes.");
            return;
        }

        String resourcePath = "/GUI/" + fxmlFile;
        URL res = SceneManager.class.getResource(resourcePath);
        //System.out.println("SceneManager: switching to resourcePath='" + resourcePath + "', resolved=" + res);
        if (res == null) {
            ErrorController.showError("Scene Error", "FXML resource not found: " + resourcePath);
            return;
        }

        // Ensure scene construction happens on JavaFX Application Thread
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(res);
                Parent root = loader.load();
                Scene scene = new Scene(root);
                primaryStage.setScene(scene);
                primaryStage.show();
            } catch (Exception e) {
                ErrorController.showError("Scene Load Error", "Unable to load scene: " + fxmlFile, e);
                e.printStackTrace();
            }
        });
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }
}
