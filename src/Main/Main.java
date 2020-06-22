package Main;

import Database.Database;
import GUI.ConfirmBox;
import GUI.ProgramGUI;
import Objects.Car.Car;
import Utils.Constants;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import Objects.TrafficLight.TrafficLight;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

public class Main extends Application {

    Stage window;

    @Override
    public void start(Stage primaryStage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
//        StackPane layout = new StackPane();
//        Scene scene = new Scene(layout, 700, 300);



        window = primaryStage;
        primaryStage.setOnCloseRequest(e -> {
            e.consume();
            boolean answer = ConfirmBox.display(Constants.exit_window_label, Constants.exit_text_label);
            if (answer) {
                window.close();
            }
        });

        Scene scene = new ProgramGUI(window).getScene();
        scene.getStylesheets().add("file:src/Main/style.css");
        primaryStage.setTitle(Constants.window_label);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
