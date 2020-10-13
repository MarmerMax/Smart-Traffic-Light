package Main;

import GUI.ProgramGUI;
import Tools.Constants;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

//java --module-path C:\javafx-sdk-13.0.2\lib --add-modules javafx.controls,javafx.fxml,javafx.graphics,javafx.web -jar STL.jar

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        Scene scene = new ProgramGUI(primaryStage).getScene();
        scene.getStylesheets().add("file:src/Main/style.css");
        primaryStage.setTitle(Constants.window_label);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
