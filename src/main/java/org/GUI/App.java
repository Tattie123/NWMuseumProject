package org.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application
{
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        SceneManager.setPrimaryStage(primaryStage);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Start Page.fxml"));
        Parent root = loader.load();

        primaryStage.setTitle("Museum");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
