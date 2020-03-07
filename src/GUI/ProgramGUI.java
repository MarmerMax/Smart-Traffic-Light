package GUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class ProgramGUI {

    private Scene windowHome, windowClientTypes, windowOptions;
    private Stage window;
    private boolean analyst;

    public ProgramGUI(Stage stage) {
        window = stage;
        createUI();
    }

    private void createUI() {
        createHomeWindow();
        createOptionsWindow();
        createClientTypesWindow();
    }

    private void createHomeWindow() {
        Label label = new Label("Welcome to the Smart traffic light!");
        HBox topMenu = new HBox();
        topMenu.setMinHeight(80);
        topMenu.setAlignment(Pos.CENTER);
        topMenu.getChildren().addAll(label);

        Button buttonStart = new Button("Let's start");
        buttonStart.setOnAction(e -> window.setScene(windowClientTypes));

        Button buttonAboutUs = new Button("About us");
        buttonAboutUs.setOnAction(e -> createAboutUsAlertWindow());

        VBox leftMenu = new VBox();
        leftMenu.setPadding(new Insets(50));
        leftMenu.setSpacing(20);
        leftMenu.getChildren().addAll(buttonStart, buttonAboutUs);

        VBox rightMenu = new VBox();
        Image image = new Image("file:images/others/main-image-3.jpg");
        rightMenu.setPrefSize(400, 400);
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
        Label label = new Label("Choose traffic situation");
        HBox topMenu = new HBox();
        topMenu.setMinHeight(80);
        topMenu.setAlignment(Pos.CENTER);
        topMenu.getChildren().addAll(label);

        //
        //

        Button buttonBackHome = new Button("Back home");
        buttonBackHome.setOnAction(e -> window.setScene(windowHome));

        String imgPath = "file:images/lights/red-s.png";
        Image img = new Image(imgPath);

        StackPane layout2 = new StackPane();
        layout2.getChildren().add(buttonBackHome);
        layout2.getChildren().add(new ImageView(img));
        windowOptions = new Scene(layout2, 1000, 660);
    }

    private void createClientTypesWindow() {
        Label label = new Label("Choose your occupation");
        HBox topMenu = new HBox();
        topMenu.setMinHeight(80);
        topMenu.setAlignment(Pos.CENTER);
        topMenu.getChildren().addAll(label);

        VBox leftMenu = new VBox(20);
        Button buttonAnalyst = new Button("Analyst");
        buttonAnalyst.setOnAction(e -> {
            analyst = true;
            window.setScene(windowOptions);
        });
        leftMenu.setAlignment(Pos.CENTER);
        Image imageAnalyst = new Image("file:images/others/analyst.png");
        ImageView imageViewAnalyst = new ImageView(imageAnalyst);
        imageViewAnalyst.setFitHeight(236);
        imageViewAnalyst.setFitWidth(250);
        leftMenu.getChildren().addAll(imageViewAnalyst, buttonAnalyst);

        VBox rightMenu = new VBox(20);
        Button buttonObserver = new Button("Observer");
        buttonObserver.setOnAction(e -> {
            analyst = false;
            window.setScene(windowOptions);
        });
        rightMenu.setAlignment(Pos.CENTER);
        Image imageObserver = new Image("file:images/others/observer.png");
        ImageView imageViewObserver = new ImageView(imageObserver);
        imageViewObserver.setFitHeight(236);
        imageViewObserver.setFitWidth(250);
        rightMenu.getChildren().addAll(imageViewObserver, buttonObserver);

        BorderPane borderPane = new BorderPane();
        borderPane.getStylesheets().add("file:src/GUI/style.css");
        borderPane.setTop(topMenu);
        borderPane.setLeft(leftMenu);
        borderPane.setRight(rightMenu);
        windowClientTypes = new Scene(borderPane, 600, 400);
    }

    public Scene getScene() {
        return windowHome;
    }

}
