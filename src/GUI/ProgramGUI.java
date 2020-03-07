package GUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ProgramGUI {

    private Scene windowHome, windowOptions;
    private Stage window;

    public ProgramGUI(Stage stage) {
        window = stage;
        createUI();
    }

    private void createUI() {
        createHomeWindow();
        createOptionsWindow();
    }

    private void createHomeWindow() {

        Label label = new Label("Welcome to the Smart traffic light!");
        HBox topMenu = new HBox();
        topMenu.setMinHeight(80);
        topMenu.setAlignment(Pos.CENTER);
        topMenu.getChildren().addAll(label);

        Button buttonStart = new Button("Let's start");
        buttonStart.setOnAction(e -> window.setScene(windowOptions));

        Button buttonAboutUs = new Button("About us");
        buttonAboutUs.setOnAction(e -> createAboutUsAlertWindow());

        VBox leftMenu = new VBox();
        leftMenu.setPadding(new Insets(50));
        leftMenu.setSpacing(20);
        leftMenu.getChildren().addAll(buttonStart, buttonAboutUs);


        VBox rightMenu = new VBox();
        Image image = new Image("file:images/others/main-image.jpg");
        rightMenu.setPrefSize(400, 260);
        BackgroundImage backgroundimage = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        Background background = new Background(backgroundimage);
        rightMenu.setBackground(background);

        BorderPane borderPane = new BorderPane();
        borderPane.getStylesheets().add("file:src/GUI/style.css");
        borderPane.setTop(topMenu);
        borderPane.setLeft(leftMenu);
        borderPane.setRight(rightMenu);

        windowHome = new Scene(borderPane, 600, 400);
    }

    private void createAboutUsAlertWindow() {
        String title = "About us";
        String aboutUs = "Smart Traffic Light\nVersion 1.0\n" +
                "Created by Netanel Davidov and Maxim Marmer";
        AlertBox.display(title, aboutUs);
    }

    private void createOptionsWindow() {
        Button buttonBackHome = new Button("Back home");
        buttonBackHome.setOnAction(e -> window.setScene(windowHome));

        String imgPath = "file:images/lights/red-s.png";
        Image img = new Image(imgPath);

        StackPane layout2 = new StackPane();
        layout2.getChildren().add(buttonBackHome);
        layout2.getChildren().add(new ImageView(img));
        windowOptions = new Scene(layout2, 1000, 660);
    }

    public Scene getScene() {
        return windowHome;
    }

}
