package Main;

import GUI.ProgramGUI;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import Objects.TrafficLight.TrafficLight;

import java.nio.file.Paths;
import java.util.Random;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
//        StackPane layout = new StackPane();
//        Scene scene = new Scene(layout, 700, 300);
        Scene scene = new ProgramGUI(primaryStage).getScene();

        primaryStage.setTitle("Smart Traffic Light");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
