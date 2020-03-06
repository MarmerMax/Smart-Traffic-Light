package GUI;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ProgramGUI {

    private Scene windowHome, windowOptions;
    private Stage window;

    public ProgramGUI(Stage stage){
        window = stage;
        createUI();
    }

    private void createUI(){
        createHomeWindow();
        createOptionsWindow();
    }

    private void createHomeWindow(){
        Label label1 = new Label("Welcome to the home page!!!");
        Button buttonStart = new Button("Let's start");
        Button buttonAboutUs = new Button("About us");

        buttonStart.setOnAction(e -> window.setScene(windowOptions));
        buttonAboutUs.setOnAction(e -> createAboutUsAlertWindow());

        VBox layout1 = new VBox(20);
        layout1.getChildren().addAll(label1, buttonStart, buttonAboutUs);

        windowHome = new Scene(layout1, 600, 400);
    }

    private void createAboutUsAlertWindow(){
        Stage windowAboutUs = new Stage();

        windowAboutUs.initModality(Modality.APPLICATION_MODAL);
        windowAboutUs.setTitle("About us");

        Label label = new Label();
        label.setText("Smart Traffic Light\n" +
                "Version 1.0\n" +
                "Created by Netanel Davidov and Maxim Marmer");
        Button button = new Button("Close");
        button.setOnAction(e -> windowAboutUs.close());

        VBox layout = new VBox(20);
        layout.getChildren().addAll(label, button);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        windowAboutUs.setScene(scene);
        windowAboutUs.setResizable(false);
        windowAboutUs.showAndWait();
    }

    private void createOptionsWindow(){
        Button buttonBackHome = new Button("Back home");
        buttonBackHome.setOnAction(e -> window.setScene(windowHome));

        String imgPath = "file:images/lights/red-s.png";
        Image img = new Image(imgPath);

        StackPane layout2 = new StackPane();
        layout2.getChildren().add(buttonBackHome);
        layout2.getChildren().add(new ImageView(img));
        windowOptions = new Scene(layout2, 1000, 660);
    }

    public Scene getScene(){
        return windowHome;
    }

}
