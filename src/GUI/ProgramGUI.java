package GUI;

import Objects.Crossroad.Crossroad;
import Objects.CrossroadInfo.CrossroadInfo;
import Objects.CrossroadInfo.DirectionInfo.DirectionInfo;
import Objects.SystemConditions.Conditions;
import com.sun.javafx.scene.traversal.Direction;
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

import java.util.ArrayList;

public class ProgramGUI {

    private Scene windowHome, windowClientTypes, windowOptions, windowSimulation;
    private Stage window;
    private boolean analyst;
    private Conditions conditions;

    public ProgramGUI(Stage stage) {
        window = stage;
        window.setResizable(false);
        createUI();
    }

    private void createUI() {
        createHomeWindow();
        createClientTypesWindow();
        createOptionsWindow();
        createSimulationWindow();
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
        VBox centerMenu = new VBox(20);

        //1
        HBox crossroad_fields_1 = new HBox();
        crossroad_fields_1.getStyleClass().add("options-container");

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
        Label eastText1 = new Label("East");
        Label southText1 = new Label("South");
        Label westText1 = new Label("West");
        northText1.getStyleClass().add("label-direction");
        eastText1.getStyleClass().add("label-direction");
        southText1.getStyleClass().add("label-direction");
        westText1.getStyleClass().add("label-direction");
        route1.getChildren().addAll(routeLabel1, northText1, eastText1, southText1, westText1);

        VBox cars1 = new VBox(10);
        cars1.getStyleClass().add("options-column");
        Label carsLabel1 = new Label("Cars count");
        carsLabel1.getStyleClass().add("label-column");
        ArrayList<Spinner<Integer>> cars_spinners_1 = new ArrayList<>();
        cars_spinners_1.add(new Spinner<>(1, 1000, 25));
        cars_spinners_1.add(new Spinner<>(1, 1000, 25));
        cars_spinners_1.add(new Spinner<>(1, 1000, 25));
        cars_spinners_1.add(new Spinner<>(1, 1000, 25));
        cars1.getChildren().addAll(carsLabel1, cars_spinners_1.get(0), cars_spinners_1.get(1), cars_spinners_1.get(2), cars_spinners_1.get(3));

        VBox speedLimit1 = new VBox(10);
        speedLimit1.getStyleClass().add("options-column");
        Label speedLimitLabel1 = new Label("Speed limit");
        speedLimitLabel1.getStyleClass().add("label-column");
        ArrayList<Spinner<Integer>> limit_spinners_1 = new ArrayList<>();
        limit_spinners_1.add(new Spinner<>(50, 110, 70));
        limit_spinners_1.add(new Spinner<>(50, 110, 70));
        limit_spinners_1.add(new Spinner<>(50, 110, 70));
        limit_spinners_1.add(new Spinner<>(50, 110, 70));
        speedLimit1.getChildren().addAll(speedLimitLabel1, limit_spinners_1.get(0), limit_spinners_1.get(1), limit_spinners_1.get(2), limit_spinners_1.get(3));

        VBox actualSpeed1 = new VBox(10);
        actualSpeed1.getStyleClass().add("options-column");
        Label actualSpeedLabel1 = new Label("Actual speed");
        actualSpeedLabel1.getStyleClass().add("label-column");
        ArrayList<Spinner<Integer>> actual_spinners_1 = new ArrayList<>();
        actual_spinners_1.add(new Spinner<>(50, 110, 70));
        actual_spinners_1.add(new Spinner<>(50, 110, 70));
        actual_spinners_1.add(new Spinner<>(50, 110, 70));
        actual_spinners_1.add(new Spinner<>(50, 110, 70));
        actualSpeed1.getChildren().addAll(actualSpeedLabel1, actual_spinners_1.get(0), actual_spinners_1.get(1), actual_spinners_1.get(2), actual_spinners_1.get(3));

        crossroad_fields_1.getChildren().addAll(label1, route1, cars1, speedLimit1, actualSpeed1);

        //2
        HBox crossroad_fields_2 = new HBox();
        crossroad_fields_2.getStyleClass().add("options-container");

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
        Label eastText2 = new Label("East");
        Label southText2 = new Label("South");
        Label westText2 = new Label("West");
        northText2.getStyleClass().add("label-direction");
        eastText2.getStyleClass().add("label-direction");
        southText2.getStyleClass().add("label-direction");
        westText2.getStyleClass().add("label-direction");
        route2.getChildren().addAll(routeLabel2, northText2, eastText2, southText2, westText2);

        VBox cars2 = new VBox(10);
        cars2.getStyleClass().add("options-column");
        Label carsLabel2 = new Label("Cars count");
        carsLabel2.getStyleClass().add("label-column");
        ArrayList<Spinner<Integer>> cars_spinners_2 = new ArrayList<>();
        cars_spinners_2.add(new Spinner<>(1, 1000, 25));
        cars_spinners_2.add(new Spinner<>(1, 1000, 25));
        cars_spinners_2.add(new Spinner<>(1, 1000, 25));
        cars_spinners_2.add(new Spinner<>(1, 1000, 25));
        cars2.getChildren().addAll(carsLabel2, cars_spinners_2.get(0), cars_spinners_2.get(1), cars_spinners_2.get(2), cars_spinners_2.get(3));


        VBox speedLimit2 = new VBox(10);
        speedLimit2.getStyleClass().add("options-column");
        Label speedLimitLabel2 = new Label("Speed limit");
        speedLimitLabel2.getStyleClass().add("label-column");
        ArrayList<Spinner<Integer>> limit_spinners_2 = new ArrayList<>();
        limit_spinners_2.add(new Spinner<>(50, 110, 70));
        limit_spinners_2.add(new Spinner<>(50, 110, 70));
        limit_spinners_2.add(new Spinner<>(50, 110, 70));
        limit_spinners_2.add(new Spinner<>(50, 110, 70));
        speedLimit2.getChildren().addAll(speedLimitLabel2, limit_spinners_2.get(0), limit_spinners_2.get(1), limit_spinners_2.get(2), limit_spinners_2.get(3));

        VBox actualSpeed2 = new VBox(10);
        actualSpeed2.getStyleClass().add("options-column");
        Label actualSpeedLabel2 = new Label("Actual speed");
        actualSpeedLabel2.getStyleClass().add("label-column");
        ArrayList<Spinner<Integer>> actual_spinners_2 = new ArrayList<>();
        actual_spinners_2.add(new Spinner<>(50, 110, 70));
        actual_spinners_2.add(new Spinner<>(50, 110, 70));
        actual_spinners_2.add(new Spinner<>(50, 110, 70));
        actual_spinners_2.add(new Spinner<>(50, 110, 70));
        actualSpeed2.getChildren().addAll(actualSpeedLabel2, actual_spinners_2.get(0), actual_spinners_2.get(1), actual_spinners_2.get(2), actual_spinners_2.get(3));

        crossroad_fields_2.getChildren().addAll(boxLabel2, route2, cars2, speedLimit2, actualSpeed2);

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

        centerMenu.getChildren().addAll(crossroad_fields_1, crossroad_fields_2, otherOptions);

        HBox topMenu = new HBox();
        Label label = new Label("Choose traffic conditions");
        topMenu.setMinHeight(80);
        topMenu.setAlignment(Pos.CENTER);
        topMenu.getChildren().addAll(label);

        HBox bottomMenu = new HBox(20);
        bottomMenu.setAlignment(Pos.CENTER);
        Button buttonRun = new Button("Run");
        buttonRun.setOnAction(e -> {
            //create crossroadInfo with fields data
            //check if data correct
            //continue to next window
            int [] cars_inputs_1 = {cars_spinners_1.get(0).getValue(), cars_spinners_1.get(1).getValue(), cars_spinners_1.get(2).getValue(), cars_spinners_1.get(3).getValue()};
            int [] actual_inputs_1 = {actual_spinners_1.get(0).getValue(), actual_spinners_1.get(1).getValue(), actual_spinners_1.get(2).getValue(), actual_spinners_1.get(3).getValue()};
            int [] limit_inputs_1 = {limit_spinners_1.get(0).getValue(), limit_spinners_1.get(1).getValue(), limit_spinners_1.get(2).getValue(), limit_spinners_1.get(3).getValue()};

            int [] cars_inputs_2 = {cars_spinners_2.get(0).getValue(), cars_spinners_2.get(1).getValue(), cars_spinners_2.get(2).getValue(), cars_spinners_2.get(3).getValue()};
            int [] actual_inputs_2 = {actual_spinners_2.get(0).getValue(), actual_spinners_2.get(1).getValue(), actual_spinners_2.get(2).getValue(), actual_spinners_2.get(3).getValue()};
            int [] limit_inputs_2 = {limit_spinners_2.get(0).getValue(), limit_spinners_2.get(1).getValue(), limit_spinners_2.get(2).getValue(), limit_spinners_2.get(3).getValue()};

            //else alert box fail!

            Crossroad crossroad_1 = new Crossroad();
            Crossroad crossroad_2 = new Crossroad();

            CrossroadInfo crossroad_info_1 = new CrossroadInfo(crossroad_1);
            CrossroadInfo crossroad_info_2 = new CrossroadInfo(crossroad_2);

            crossroad_info_1.setCrossroadInfo(cars_inputs_1, actual_inputs_1, limit_inputs_1);
            crossroad_info_2.setCrossroadInfo(cars_inputs_2, actual_inputs_2, limit_inputs_2);

            conditions = new Conditions(crossroad_info_1, crossroad_info_2);
            window.setScene(windowSimulation);
        });
        Button buttonBack = new Button("Back");
        buttonBack.setOnAction(e -> {
            boolean goBack = ConfirmBox.display("Go to previous window",
                    "If you go back all options will reset\nSure you want to go back?");
            if (goBack) window.setScene(windowClientTypes);
        });
        bottomMenu.getChildren().addAll(buttonBack, buttonRun);

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

    @SuppressWarnings("Duplicates")
    private void createSimulationWindow() {
        HBox topMenu = new HBox();
        Label label = new Label("Simulation");
        topMenu.setMinHeight(60);
        topMenu.setAlignment(Pos.CENTER);
        topMenu.getChildren().addAll(label);

        HBox bottomMenu = new HBox(20);
        bottomMenu.setMinHeight(60);
        bottomMenu.setAlignment(Pos.CENTER);
        Button buttonSave = new Button("Save");
        buttonSave.setOnAction(e -> {
            //Save to database window
        });
        Button buttonBack = new Button("Back");
        buttonBack.setOnAction(e -> {
            boolean goBack = ConfirmBox.display("Go to previous window",
                    "Current simulation will be deleted\nSure you want to go back?");
            if (goBack) window.setScene(windowOptions);
        });
        Button buttonStart = new Button("Start");
        buttonStart.setOnAction(e -> {
            //Save to database window
        });
        bottomMenu.getChildren().addAll(buttonBack, buttonSave, buttonStart);

        VBox centerMenu = new VBox();
        centerMenu.getStyleClass().add("simulation-container");
//        Image imageObserver = new Image("file:images/others/crossroad.jpg");
//        ImageView imageViewObserver = new ImageView(imageObserver);
//        imageViewObserver.setFitHeight(500);
//        imageViewObserver.setFitWidth(900);
//        imageViewObserver.fitWidthProperty().bind(centerMenu.widthProperty());
//        imageViewObserver.fitHeightProperty().bind(centerMenu.heightProperty());
        Label tempLabel = new Label("Coming soon...");
        centerMenu.getChildren().addAll(tempLabel);

        BorderPane borderPane = new BorderPane();
        borderPane.getStylesheets().add("file:src/GUI/style.css");
        borderPane.setTop(topMenu);
        borderPane.setCenter(centerMenu);
        borderPane.setBottom(bottomMenu);
        windowSimulation = new Scene(borderPane, 1000, 660);
    }

    public Scene getScene() {
        return windowHome;
    }

}
