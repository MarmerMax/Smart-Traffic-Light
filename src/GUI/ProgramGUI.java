package GUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ProgramGUI {

    private Scene windowHome, windowClientTypes, windowOptions, windowSimulation;
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
        HBox topMenu = new HBox();
        Label label = new Label("Choose traffic situation");
        topMenu.setMinHeight(80);
        topMenu.setAlignment(Pos.CENTER);
        topMenu.getChildren().addAll(label);

        HBox bottomMenu = new HBox(20);
        bottomMenu.setAlignment(Pos.CENTER);
        Button buttonRun = new Button("Run");
//        buttonRun.setOnAction(e -> window.setScene(windowSimulation));
        Button buttonBack = new Button("Back");
        buttonBack.setOnAction(e -> {
            boolean goBack = ConfirmBox.display("Go to previous page", "If you go back all options will reset\nSure you want to go back?");
            if (goBack) window.setScene(windowClientTypes);
        });
        bottomMenu.getChildren().addAll(buttonRun, buttonBack);

        VBox centerMenu = new VBox(20);
        centerMenu.setAlignment(Pos.CENTER);
        Button buttonDatabase = new Button("Database");
        Button buttonRandom = new Button("Random");
        buttonRandom.setOnAction(e -> {
            //random situation
        });
        buttonDatabase.setOnAction(e -> {
            //load from database
        });
        centerMenu.getChildren().addAll(buttonDatabase, buttonRandom);


        VBox leftMenu = new VBox(20);
        leftMenu.setStyle("-fx-border-width: 2px; -fx-border-color: black;");
        leftMenu.setAlignment(Pos.CENTER);
        leftMenu.setPrefWidth(300);
        Label crossroad1 = new Label("First Crossroad");

        VBox carsNumberLeft = new VBox(5);
        carsNumberLeft.setAlignment(Pos.CENTER);
        Text carsNumberTextLeft = new Text("Number of cars that travel");

        HBox carsNumberTravelLeft = new HBox();
        carsNumberTravelLeft.setAlignment(Pos.CENTER);
        VBox travelTextLeft = new VBox(5);
        Label northTextLeft = new Label("North");
        Label southTextLeft = new Label("South");
        Label eastTextLeft = new Label("East");
        Label westTextLeft = new Label("West");
        northTextLeft.getStyleClass().add("label-cars");
        southTextLeft.getStyleClass().add("label-cars");
        eastTextLeft.getStyleClass().add("label-cars");
        westTextLeft.getStyleClass().add("label-cars");
        travelTextLeft.getChildren().addAll(northTextLeft, southTextLeft, eastTextLeft, westTextLeft);

        VBox travelInputLeft = new VBox(5);
        Spinner<Integer> northInputLeft = new Spinner<>(1, 1000, 25);
        Spinner<Integer> southInputLeft = new Spinner<>(1, 1000, 25);
        Spinner<Integer> eastInputLeft = new Spinner<>(1, 1000, 25);
        Spinner<Integer> westInputLeft = new Spinner<>(1, 1000, 25);
//        westInput.setEditable(true);
        travelInputLeft.getChildren().addAll(northInputLeft, southInputLeft, eastInputLeft, westInputLeft);
        carsNumberTravelLeft.getChildren().addAll(travelTextLeft, travelInputLeft);
        carsNumberLeft.getChildren().addAll(carsNumberTextLeft, carsNumberTravelLeft);
        leftMenu.getChildren().addAll(crossroad1, carsNumberLeft);

        //------------------------------------------------------------------------

        VBox rightMenu = new VBox(20);
        rightMenu.setStyle("-fx-border-width: 2px; -fx-border-color: black;");
        rightMenu.setAlignment(Pos.CENTER);
        rightMenu.setPrefWidth(300);
        Label crossroad2 = new Label("Second Crossroad");

        VBox carsNumberRight = new VBox(5);
        carsNumberRight.setAlignment(Pos.CENTER);
        Text carsNumberTextRight = new Text("Number of cars that travel");

        HBox carsNumberTravelRight = new HBox();
        carsNumberTravelRight.setAlignment(Pos.CENTER);
        VBox travelTextRight = new VBox(5);
        Label northTextRight = new Label("North");
        Label southTextRight = new Label("South");
        Label eastTextRight = new Label("East");
        Label westTextRight = new Label("West");
        southTextRight.getStyleClass().add("label-cars");
        northTextRight.getStyleClass().add("label-cars");
        westTextRight.getStyleClass().add("label-cars");
        eastTextRight.getStyleClass().add("label-cars");
        travelTextRight.getChildren().addAll(northTextRight, southTextRight, eastTextRight, westTextRight);

        VBox travelInputRight = new VBox(5);
        Spinner<Integer> northInputRight = new Spinner<>(1, 1000, 25);
        Spinner<Integer> southInputRight = new Spinner<>(1, 1000, 25);
        Spinner<Integer> eastInputRight = new Spinner<>(1, 1000, 25);
        Spinner<Integer> westInputRight = new Spinner<>(1, 1000, 25);
//        westInput.setEditable(true);
        travelInputRight.getChildren().addAll(northInputRight, southInputRight, eastInputRight, westInputRight);
        carsNumberTravelRight.getChildren().addAll(travelTextRight, travelInputRight);
        carsNumberRight.getChildren().addAll(carsNumberTextRight, carsNumberTravelRight);
        rightMenu.getChildren().addAll(crossroad2, carsNumberRight);

        BorderPane borderPane = new BorderPane();
        borderPane.getStylesheets().add("file:src/GUI/style.css");
        borderPane.setTop(topMenu);
        borderPane.setCenter(centerMenu);
        borderPane.setBottom(bottomMenu);
        borderPane.setLeft(leftMenu);
        borderPane.setRight(rightMenu);

//        Button buttonBackHome = new Button("Back home");
//        buttonBackHome.setOnAction(e -> window.setScene(windowHome));
//
//        String imgPath = "file:images/lights/red-s.png";
//        Image img = new Image(imgPath);

//        StackPane layout2 = new StackPane();
//        layout2.getChildren().add(buttonBackHome);
//        layout2.getChildren().add(new ImageView(img));
        windowOptions = new Scene(borderPane, 1000, 660);
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
        borderPane.setRight(rightMenu);
        borderPane.setLeft(leftMenu);
        windowClientTypes = new Scene(borderPane, 600, 400);
    }

    public Scene getScene() {
        return windowHome;
    }

}
