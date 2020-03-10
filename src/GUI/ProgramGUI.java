package GUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class ProgramGUI {

    private Scene windowHome, windowClientTypes, windowOptions, windowSimulation;
    private Stage window;
    private boolean analyst;

    public ProgramGUI(Stage stage) {
        window = stage;
        window.setResizable(false);
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

    @SuppressWarnings("Duplicates")
    private void createOptionsWindow() {
        HBox topMenu = new HBox();
        Label label = new Label("Choose traffic situation");
        topMenu.setMinHeight(80);
        topMenu.setAlignment(Pos.CENTER);
        topMenu.getChildren().addAll(label);

        HBox bottomMenu = new HBox(20);
        bottomMenu.setAlignment(Pos.CENTER);
        Button buttonRun = new Button("Run");
        buttonRun.setOnAction(e -> {
//            window.setScene(windowSimulation)
            //create crossroadInfo with fields data
            //check if data correct
            //continue to next window
            //else alert box fail!
        });
        Button buttonBack = new Button("Back");
        buttonBack.setOnAction(e -> {
            boolean goBack = ConfirmBox.display("Go to previous page",
                    "If you go back all options will reset\nSure you want to go back?");
            if (goBack) window.setScene(windowClientTypes);
        });
        bottomMenu.getChildren().addAll(buttonBack, buttonRun);


        VBox centerMenu = new VBox(20);

        //1
        HBox crossroad1 = new HBox();
        crossroad1.getStyleClass().add("options-container");

        VBox label1 = new VBox(10);
        label1.getStyleClass().add("options-column");
        Label nameLabel1 = new Label("Crossroad 1");
        nameLabel1.getStyleClass().add("label-column");
        label1.getChildren().addAll(nameLabel1);

        VBox route1 = new VBox(10);
        route1.getStyleClass().add("options-column");
        Label routeLabel1 = new Label("Route");
        routeLabel1.getStyleClass().add("label-column");
        Label northText1 = new Label("North");
        Label southText1 = new Label("South");
        Label eastText1 = new Label("East");
        Label westText1 = new Label("West");
        southText1.getStyleClass().add("label-direction");
        northText1.getStyleClass().add("label-direction");
        westText1.getStyleClass().add("label-direction");
        eastText1.getStyleClass().add("label-direction");
        route1.getChildren().addAll(routeLabel1, northText1, southText1, eastText1, westText1);

        VBox cars1 = new VBox(10);
        cars1.getStyleClass().add("options-column");
        Label carsLabel1 = new Label("Cars count");
        carsLabel1.getStyleClass().add("label-column");
        Spinner<Integer> northCars1 = new Spinner<>(1, 1000, 25);
        Spinner<Integer> southCars1 = new Spinner<>(1, 1000, 25);
        Spinner<Integer> eastCars1 = new Spinner<>(1, 1000, 25);
        Spinner<Integer> westCars1 = new Spinner<>(1, 1000, 25);
        cars1.getChildren().addAll(carsLabel1, northCars1, southCars1, eastCars1, westCars1);

        VBox speedLimit1 = new VBox(10);
        speedLimit1.getStyleClass().add("options-column");
        Label speedLimitLabel1 = new Label("Speed limit");
        speedLimitLabel1.getStyleClass().add("label-column");
        Spinner<Integer> northLimit1 = new Spinner<>(1, 110, 70);
        Spinner<Integer> southLimit1 = new Spinner<>(1, 110, 70);
        Spinner<Integer> eastLimit1 = new Spinner<>(1, 110, 70);
        Spinner<Integer> westLimit1 = new Spinner<>(1, 110, 70);
        speedLimit1.getChildren().addAll(speedLimitLabel1, northLimit1, southLimit1, eastLimit1, westLimit1);

        VBox actualSpeed1 = new VBox(10);
        actualSpeed1.getStyleClass().add("options-column");
        Label actualSpeedLabel1 = new Label("Actual speed");
        actualSpeedLabel1.getStyleClass().add("label-column");
        Spinner<Integer> northActual1 = new Spinner<>(1, 110, 70);
        Spinner<Integer> southActual1 = new Spinner<>(1, 110, 70);
        Spinner<Integer> eastActual1 = new Spinner<>(1, 110, 70);
        Spinner<Integer> westActual1 = new Spinner<>(1, 110, 70);
        actualSpeed1.getChildren().addAll(actualSpeedLabel1, northActual1, southActual1, eastActual1, westActual1);

        crossroad1.getChildren().addAll(label1, route1, cars1, speedLimit1, actualSpeed1);

        //2
        HBox crossroad2 = new HBox();
        crossroad2.getStyleClass().add("options-container");

        VBox boxLabel2 = new VBox(10);
        boxLabel2.getStyleClass().add("options-column");
        Label nameLabel2 = new Label("Crossroad 2");
        nameLabel2.getStyleClass().add("label-column");
        boxLabel2.getChildren().addAll(nameLabel2);

        VBox route2 = new VBox(10);
        route2.getStyleClass().add("options-column");
        Label routeLabel2 = new Label("Route");
        routeLabel2.getStyleClass().add("label-column");
        Label northText2 = new Label("North");
        Label southText2 = new Label("South");
        Label eastText2 = new Label("East");
        Label westText2 = new Label("West");
        southText2.getStyleClass().add("label-direction");
        northText2.getStyleClass().add("label-direction");
        westText2.getStyleClass().add("label-direction");
        eastText2.getStyleClass().add("label-direction");
        route2.getChildren().addAll(routeLabel2, northText2, southText2, eastText2, westText2);

        VBox cars2 = new VBox(10);
        cars2.getStyleClass().add("options-column");
        Label carsLabel2 = new Label("Cars count");
        carsLabel2.getStyleClass().add("label-column");
        Spinner<Integer> northCars2 = new Spinner<>(1, 1000, 25);
        Spinner<Integer> southCars2 = new Spinner<>(1, 1000, 25);
        Spinner<Integer> eastCars2 = new Spinner<>(1, 1000, 25);
        Spinner<Integer> westCars2 = new Spinner<>(1, 1000, 25);
        cars2.getChildren().addAll(carsLabel2, northCars2, southCars2, eastCars2, westCars2);

        VBox speedLimit2 = new VBox(10);
        speedLimit2.getStyleClass().add("options-column");
        Label speedLimitLabel2 = new Label("Speed limit");
        speedLimitLabel2.getStyleClass().add("label-column");
        Spinner<Integer> northLimit2 = new Spinner<>(1, 110, 70);
        Spinner<Integer> southLimit2 = new Spinner<>(1, 110, 70);
        Spinner<Integer> eastLimit2 = new Spinner<>(1, 110, 70);
        Spinner<Integer> westLimit2 = new Spinner<>(1, 110, 70);
        speedLimit2.getChildren().addAll(speedLimitLabel2, northLimit2, southLimit2, eastLimit2, westLimit2);

        VBox actualSpeed2 = new VBox(10);
        actualSpeed2.getStyleClass().add("options-column");
        Label actualSpeedLabel2 = new Label("Actual speed");
        actualSpeedLabel2.getStyleClass().add("label-column");
        Spinner<Integer> northActual2 = new Spinner<>(1, 110, 70);
        Spinner<Integer> southActual2 = new Spinner<>(1, 110, 70);
        Spinner<Integer> eastActual2 = new Spinner<>(1, 110, 70);
        Spinner<Integer> westActual2 = new Spinner<>(1, 110, 70);
        actualSpeed2.getChildren().addAll(actualSpeedLabel2, northActual2, southActual2, eastActual2, westActual2);

        crossroad2.getChildren().addAll(boxLabel2, route2, cars2, speedLimit2, actualSpeed2);

        //3
        HBox otherOptions = new HBox();
        otherOptions.getStyleClass().add("options-container");

        VBox boxLabel3 = new VBox(10);
        boxLabel3.getStyleClass().add("options-column");
        Label nameLabel3 = new Label("Other features");
        nameLabel3.getStyleClass().add("label-column");
        boxLabel3.getChildren().addAll(nameLabel3);

        VBox boxButtonRandom = new VBox(10);
        boxButtonRandom.getStyleClass().add("options-column");
        Button buttonRandom = new Button("Random");
        boxButtonRandom.getChildren().add(buttonRandom);

        VBox boxButtonDatabase = new VBox(10);
        boxButtonDatabase.getStyleClass().add("options-column");
        Button buttonDatabase = new Button("Database");
        boxButtonDatabase.getChildren().add(buttonDatabase);

        VBox boxButtonReset = new VBox(10);
        boxButtonReset.getStyleClass().add("options-column");
        Button buttonReset = new Button("Reset");
        boxButtonReset.getChildren().add(buttonReset);

        VBox boxButtonInfo = new VBox(10);
        boxButtonInfo.getStyleClass().add("options-column");
        Button buttonInfo = new Button("Info");
        boxButtonInfo.getChildren().add(buttonInfo);

        buttonRandom.setOnAction(e -> {
            boolean answer = ConfirmBox.display("Random", "Generate random data?");
            if (answer) {

            }
        });
        buttonDatabase.setOnAction(e -> {
            //load from database
        });
        buttonReset.setOnAction(e -> {
            boolean answer = ConfirmBox.display("Reset", "Reset all values?");
            if (answer) {
                //reset all values
            }
        });
        buttonInfo.setOnAction(e -> {
            //info
        });
        otherOptions.getChildren().addAll(boxLabel3, boxButtonDatabase, boxButtonRandom, boxButtonReset, boxButtonInfo);

        centerMenu.getChildren().addAll(crossroad1, crossroad2, otherOptions);

        BorderPane borderPane = new BorderPane();
        borderPane.getStylesheets().add("file:src/GUI/style.css");
        borderPane.setTop(topMenu);
        borderPane.setCenter(centerMenu);
        borderPane.setBottom(bottomMenu);
//        borderPane.setLeft(leftMenu);
//        borderPane.setRight(rightMenu);
//        Button buttonBackHome = new Button("Back home");
//        buttonBackHome.setOnAction(e -> window.setScene(windowHome));
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
