package Main;

import GUI.ConfirmBox;
import GUI.ProgramGUI;
import Tools.Constants;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
//                window.close();
                System.exit(1);
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
