package GUI;

import CSV.CSVCondition;
import Objects.Crossroad.Crossroad;
import Objects.CrossroadInfo.CrossroadInfo;
import Objects.Road.RoadCreator;
import Objects.Conditions.Conditions;
import SystemSTL.SystemSTL;
import Tools.Constants;
import Tools.Utils;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.scene.shape.Rectangle;
import SystemSTL.TrafficComputation.Lane.LaneInfo;
import SystemSTL.TrafficComputation.Car.CarInfo;

import javafx.event.ActionEvent;

import Database.Database;
import Database.DatabaseConditions;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProgramGUI {

    private Scene windowHome, windowClientTypes, windowOptions, windowSimulation;
    private Stage window;
    private static boolean analyst;
    private Conditions conditions;
    private Pane simulation;

    private CrossroadInfo crossroad_info_1;
    private CrossroadInfo crossroad_info_2;


    public ProgramGUI(Stage stage) {
        window = stage;
        window.setResizable(false);
        createUI();
    }

    /**
     * This function create:
     * 2 crossroads and their information,
     * Home Window,
     * Client Types Window,
     * Options Window,
     * SimulationWindow
     */
    private void createUI() {
//        conditions = Utils.createStartConditions();
//        ResultsBox.display("1", "2");

        createHomeWindow();
    }

    /**
     * This function create Home Window contains:
     * Let's start button - go to window that allow to choose client types.
     * About us button
     */
    private void createHomeWindow() {
        //Title
        Label label = new Label(Constants.home_page_window_label);
        HBox topMenu = new HBox();
        topMenu.setMinHeight(80);
        topMenu.setAlignment(Pos.CENTER);
        topMenu.getChildren().addAll(label);

        //Let's start button
        Button buttonStart = new Button(Constants.lets_start_button_label);
        buttonStart.setOnAction(e -> {
            createClientTypesWindow();
            window.setScene(windowClientTypes);
        });

        //About us button
        Button buttonAboutUs = new Button(Constants.about_us_button_label);
        buttonAboutUs.setOnAction(e -> AlertBox.display(Constants.about_us_window_label, Constants.about_us_text));

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

    /**
     * Random -
     * Database - user need to connect first to his local database, after that the user can choose which condition he want to run.
     */
    @SuppressWarnings("Duplicates")
    private void createOptionsWindow(boolean analyst) {
        VBox centerMenu = new VBox(20);

        //Crossroad 1
        HBox crossroad_fields_1 = new HBox();
        crossroad_fields_1.getStyleClass().add("options-container");

        VBox label1 = new VBox(10);
        label1.getStyleClass().add("options-column");
        Label nameLabel1 = new Label(Constants.crossroad_1_label);
        nameLabel1.getStyleClass().add("label-column");
        label1.getChildren().addAll(nameLabel1);

        VBox route1 = new VBox(10);
        route1.getStyleClass().add("options-column");
        Label routeLabel1 = new Label(Constants.route_label);
        routeLabel1.getStyleClass().add("label-column");
        Label northText1 = new Label(Constants.north_label);
        Label eastText1 = new Label(Constants.east_label);
        Label southText1 = new Label(Constants.south_label);
        Label westText1 = new Label(Constants.west_label);
        northText1.getStyleClass().add("label-direction");
        eastText1.getStyleClass().add("label-direction");
        southText1.getStyleClass().add("label-direction");
        westText1.getStyleClass().add("label-direction");
        route1.getChildren().addAll(routeLabel1, northText1, eastText1, southText1, westText1);

        VBox cars1 = new VBox(10);
        cars1.getStyleClass().add("options-column");
        Label carsLabel1 = new Label(Constants.cars_count_label);
        carsLabel1.getStyleClass().add("label-column");
        ArrayList<Spinner<Integer>> cars_spinners_1 = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            if (i != 1) {
                cars_spinners_1.add(new Spinner<>(Constants.CARS_COUNT_MIN, Constants.CARS_COUNT_LONG_ROAD_MAX, Constants.CARS_COUNT_LONG_ROAD));
            } else {
                cars_spinners_1.add(new Spinner<>(Constants.CARS_COUNT_MIN, Constants.CARS_COUNT_SHORT_ROAD_MAX, Constants.CARS_COUNT_SHORT_ROAD));
            }

            if (!analyst) {
                cars_spinners_1.get(i).setDisable(true);
            } else {
                cars_spinners_1.get(i).setDisable(false);
            }
        }

//        for (Spinner<Integer> spinner : cars_spinners_1) {
//            if (spinner.getValue() == 10) {
//                upgradeSpinner(spinner, 1, 1000, false);
//
//            } else {
//                upgradeSpinner(spinner, 1, 5, false);
//            }
//        }

        cars1.getChildren().addAll(carsLabel1, cars_spinners_1.get(0), cars_spinners_1.get(1), cars_spinners_1.get(2), cars_spinners_1.get(3));

        VBox speedLimit1 = new VBox(10);
        speedLimit1.getStyleClass().add("options-column");
        Label speedLimitLabel1 = new Label(Constants.speed_limit_label);
        speedLimitLabel1.getStyleClass().add("label-column");
        ArrayList<Spinner<Integer>> limit_spinners_1 = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            limit_spinners_1.add(new Spinner<>(Constants.SPEED_LIMIT_MIN, Constants.SPEED_LIMIT_MAX, Constants.SPEED_LIMIT_DEFAULT, 10));

            if (!analyst) {
                limit_spinners_1.get(i).setDisable(true);
            } else {
                limit_spinners_1.get(i).setDisable(false);
            }
        }

        speedLimit1.getChildren().addAll(speedLimitLabel1, limit_spinners_1.get(0), limit_spinners_1.get(1), limit_spinners_1.get(2), limit_spinners_1.get(3));

        VBox actualSpeed1 = new VBox(10);
        actualSpeed1.getStyleClass().add("options-column");
        Label actualSpeedLabel1 = new Label(Constants.actual_speed_label);
        actualSpeedLabel1.getStyleClass().add("label-column");
        ArrayList<Spinner<Integer>> actual_spinners_1 = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            actual_spinners_1.add(new Spinner<>(Constants.ACTUAL_LIMIT_MIN, Constants.ACTUAL_LIMIT_MAX, Constants.ACTUAL_LIMIT_DEFAULT));

            if (!analyst) {
                actual_spinners_1.get(i).setDisable(true);
            } else {
                actual_spinners_1.get(i).setDisable(false);
            }
        }

        actualSpeed1.getChildren().addAll(actualSpeedLabel1, actual_spinners_1.get(0), actual_spinners_1.get(1), actual_spinners_1.get(2), actual_spinners_1.get(3));

        crossroad_fields_1.getChildren().addAll(label1, route1, cars1, speedLimit1, actualSpeed1);


        //Crossroad 2
        HBox crossroad_fields_2 = new HBox();
        crossroad_fields_2.getStyleClass().add("options-container");

        VBox boxLabel2 = new VBox(10);
        boxLabel2.getStyleClass().add("options-column");
        Label nameLabel2 = new Label(Constants.crossroad_2_label);
        nameLabel2.getStyleClass().add("label-column");
        boxLabel2.getChildren().addAll(nameLabel2);

        VBox route2 = new VBox(10);
        route2.getStyleClass().add("options-column");
        Label routeLabel2 = new Label(Constants.route_label);
        routeLabel2.getStyleClass().add("label-column");
        Label northText2 = new Label(Constants.north_label);
        Label eastText2 = new Label(Constants.east_label);
        Label southText2 = new Label(Constants.south_label);
        Label westText2 = new Label(Constants.west_label);
        northText2.getStyleClass().add("label-direction");
        eastText2.getStyleClass().add("label-direction");
        southText2.getStyleClass().add("label-direction");
        westText2.getStyleClass().add("label-direction");
        route2.getChildren().addAll(routeLabel2, northText2, eastText2, southText2, westText2);

        VBox cars2 = new VBox(10);
        cars2.getStyleClass().add("options-column");
        Label carsLabel2 = new Label(Constants.cars_count_label);
        carsLabel2.getStyleClass().add("label-column");
        ArrayList<Spinner<Integer>> cars_spinners_2 = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            if (i != 3) {
                cars_spinners_2.add(new Spinner<>(Constants.CARS_COUNT_MIN, Constants.CARS_COUNT_LONG_ROAD_MAX, Constants.CARS_COUNT_LONG_ROAD));
            } else {
                cars_spinners_2.add(new Spinner<>(Constants.CARS_COUNT_MIN, Constants.CARS_COUNT_SHORT_ROAD_MAX, Constants.CARS_COUNT_SHORT_ROAD));
            }

            if (!analyst) {
                cars_spinners_2.get(i).setDisable(true);
            } else {
                cars_spinners_2.get(i).setDisable(false);
            }
        }

        cars2.getChildren().addAll(carsLabel2, cars_spinners_2.get(0), cars_spinners_2.get(1), cars_spinners_2.get(2), cars_spinners_2.get(3));


        VBox speedLimit2 = new VBox(10);
        speedLimit2.getStyleClass().add("options-column");
        Label speedLimitLabel2 = new Label(Constants.speed_limit_label);
        speedLimitLabel2.getStyleClass().add("label-column");
        ArrayList<Spinner<Integer>> limit_spinners_2 = new ArrayList<>();


        for (int i = 0; i < 4; i++) {
            limit_spinners_2.add(new Spinner<>(Constants.SPEED_LIMIT_MIN, Constants.SPEED_LIMIT_MAX, Constants.SPEED_LIMIT_DEFAULT, 10));

            if (!analyst) {
                limit_spinners_2.get(i).setDisable(true);
            } else {
                limit_spinners_2.get(i).setDisable(false);
            }
        }

        speedLimit2.getChildren().addAll(speedLimitLabel2, limit_spinners_2.get(0), limit_spinners_2.get(1), limit_spinners_2.get(2), limit_spinners_2.get(3));

        VBox actualSpeed2 = new VBox(10);
        actualSpeed2.getStyleClass().add("options-column");
        Label actualSpeedLabel2 = new Label(Constants.actual_speed_label);
        actualSpeedLabel2.getStyleClass().add("label-column");
        ArrayList<Spinner<Integer>> actual_spinners_2 = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            actual_spinners_2.add(new Spinner<>(Constants.ACTUAL_LIMIT_MIN, Constants.ACTUAL_LIMIT_MAX, Constants.ACTUAL_LIMIT_DEFAULT));

            if (!analyst) {
                actual_spinners_2.get(i).setDisable(true);
            } else {
                actual_spinners_2.get(i).setDisable(false);
            }
        }


        //Speed limit listeners - there are directions that go along the same road through two intersections,
        //so the speed limit should be the same on them
        limit_spinners_1.get(1).valueProperty().addListener((obs, oldValue, newValue) ->
                limit_spinners_2.get(1).getValueFactory().setValue(newValue));

        limit_spinners_2.get(1).valueProperty().addListener((obs, oldValue, newValue) ->
                limit_spinners_1.get(1).getValueFactory().setValue(newValue));

        limit_spinners_1.get(3).valueProperty().addListener((obs, oldValue, newValue) ->
                limit_spinners_2.get(3).getValueFactory().setValue(newValue));

        limit_spinners_2.get(3).valueProperty().addListener((obs, oldValue, newValue) ->
                limit_spinners_1.get(3).getValueFactory().setValue(newValue));


        //actual speed listeners - actual speed can't be more than speed limit
        Utils.createActualSpeedListeners(actual_spinners_1, limit_spinners_1);
        Utils.createActualSpeedListeners(actual_spinners_2, limit_spinners_2);

        Utils.creatSpeedLimitListeners(actual_spinners_1, limit_spinners_1);
        Utils.creatSpeedLimitListeners(actual_spinners_2, limit_spinners_2);

        actualSpeed2.getChildren().addAll(actualSpeedLabel2, actual_spinners_2.get(0), actual_spinners_2.get(1), actual_spinners_2.get(2), actual_spinners_2.get(3));
        crossroad_fields_2.getChildren().addAll(boxLabel2, route2, cars2, speedLimit2, actualSpeed2);


        //Other options
        HBox otherOptions = new HBox();
        otherOptions.getStyleClass().add("options-container");

//        VBox boxLabel3 = new VBox(10);
//        boxLabel3.getStyleClass().add("options-column");
//        Label nameLabel3 = new Label(Constants.other_features_label);
//        nameLabel3.getStyleClass().add("label-column");
//        boxLabel3.getChildren().addAll(nameLabel3);

        VBox boxButtonOpenCSV = new VBox(10);
        boxButtonOpenCSV.getStyleClass().add("options-column");
        Button buttonOpenCSV = new Button(Constants.open_csv_button_label);
        boxButtonOpenCSV.getChildren().add(buttonOpenCSV);
        buttonOpenCSV.setOnAction(e -> {
            String path = OpenBox.display();

            if (path != null) {

                CSVCondition csv_condition = Utils.createConditionsFromCSV(path);

                if (csv_condition != null) {
                    //1
                    Utils.setCSVConditionsInSpinner(cars_spinners_1, limit_spinners_1, actual_spinners_1, csv_condition.getFirstCrossroad());
                    //2
                    Utils.setCSVConditionsInSpinner(cars_spinners_2, limit_spinners_2, actual_spinners_2, csv_condition.getSecondCrossroad());
                } else {
                    AlertBox.display(Constants.fail_window_label, Constants.csv_fail_text_label);
                }
            }
        });

        VBox boxButtonRandom = new VBox(10);
        boxButtonRandom.getStyleClass().add("options-column");
        Button buttonRandom = new Button(Constants.random_button_label);
        boxButtonRandom.getChildren().add(buttonRandom);
        buttonRandom.setOnAction(e -> {
            if (!analyst) {
                AlertBox.display(Constants.fail_window_label, Constants.fail_text_label);
            } else {
                boolean answer = ConfirmBox.display(Constants.random_window_label, Constants.generate_random_data_label);
                if (answer) {
                    //1
                    Utils.createRandomConditions(cars_spinners_1, limit_spinners_1, actual_spinners_1, 1);

                    //2
                    Utils.createRandomConditions(cars_spinners_2, limit_spinners_2, actual_spinners_2, 3);

                    //synchronize between same directions spinners
                    limit_spinners_1.get(1).getValueFactory().setValue(limit_spinners_2.get(1).getValue());
                    limit_spinners_1.get(3).getValueFactory().setValue(limit_spinners_2.get(3).getValue());

                    actual_spinners_1.get(1).getValueFactory().setValue(actual_spinners_2.get(1).getValue());
                    actual_spinners_1.get(3).getValueFactory().setValue(actual_spinners_2.get(3).getValue());
                }
            }
        });

        //Database
        VBox boxButtonDatabase = new VBox(10);
        boxButtonDatabase.getStyleClass().add("options-column");
        Button buttonDatabase = new Button(Constants.database_button_label);
        boxButtonDatabase.getChildren().add(buttonDatabase);
        buttonDatabase.setOnAction(e -> {
            DatabaseBox.display();

//            DatabaseConditions database = DatabaseBox.display();
//            Utils.setConditionsInSpinner(cars_spinners_1, limit_spinners_1, actual_spinners_1, database.getFirstCrossroad());
//            Utils.setConditionsInSpinner(cars_spinners_2, limit_spinners_2, actual_spinners_2, database.getSecondCrossroad());
//
//            ResultsBox.display(); // previous result

            //load from database
            //String query_name = DatabaseBox.display();
            //System.out.println(query_name);
        });

        VBox boxButtonReset = new VBox(10);
        boxButtonReset.getStyleClass().add("options-column");
        Button buttonReset = new Button(Constants.reset_button_label);
        boxButtonReset.getChildren().add(buttonReset);
        buttonReset.setOnAction(e -> {
            boolean answer = ConfirmBox.display(Constants.reset_button_label, Constants.reset_conditions_label);
            if (answer) {
                //1
                Utils.resetConditions(cars_spinners_1, limit_spinners_1, actual_spinners_1, 1);

                //2
                Utils.resetConditions(cars_spinners_2, limit_spinners_2, actual_spinners_2, 3);
            }
        });

        VBox boxButtonInfo = new VBox(10);
        boxButtonInfo.getStyleClass().add("options-column");
        Button buttonInfo = new Button(Constants.info_button_label);
        boxButtonInfo.getChildren().add(buttonInfo);
        buttonInfo.setOnAction(e -> {
            InformationBox.display("Information");
        });


        otherOptions.getChildren().addAll(boxButtonOpenCSV, boxButtonDatabase, boxButtonRandom, boxButtonReset, boxButtonInfo);

        centerMenu.getChildren().addAll(crossroad_fields_1, crossroad_fields_2, otherOptions);

        //Title
        HBox topMenu = new HBox();
        Label label = new Label(Constants.traffic_conditions_window_label);
        topMenu.setMinHeight(80);
        topMenu.setAlignment(Pos.CENTER);
        topMenu.getChildren().addAll(label);


        //Run
        HBox bottomMenu = new HBox(20);
        bottomMenu.setAlignment(Pos.CENTER);
        Button buttonRun = new Button(Constants.run_button_label);
        buttonRun.setOnAction(e -> {
            //create crossroadInfo with fields data
            //check if data correct
            //continue to next window
            Crossroad crossroad_1 = new Crossroad(RoadCreator.createRoads(54, 1));
            Crossroad crossroad_2 = new Crossroad(RoadCreator.createRoads(433, 1));
            crossroad_info_1 = new CrossroadInfo(crossroad_1);
            crossroad_info_2 = new CrossroadInfo(crossroad_2);

            int[] cars_inputs_1 = {cars_spinners_1.get(0).getValue(), cars_spinners_1.get(1).getValue(), cars_spinners_1.get(2).getValue(), cars_spinners_1.get(3).getValue()};
            int[] actual_inputs_1 = {actual_spinners_1.get(0).getValue(), actual_spinners_1.get(1).getValue(), actual_spinners_1.get(2).getValue(), actual_spinners_1.get(3).getValue()};
            int[] limit_inputs_1 = {limit_spinners_1.get(0).getValue(), limit_spinners_1.get(1).getValue(), limit_spinners_1.get(2).getValue(), limit_spinners_1.get(3).getValue()};

            int[] cars_inputs_2 = {cars_spinners_2.get(0).getValue(), cars_spinners_2.get(1).getValue(), cars_spinners_2.get(2).getValue(), cars_spinners_2.get(3).getValue()};
            int[] actual_inputs_2 = {actual_spinners_2.get(0).getValue(), actual_spinners_2.get(1).getValue(), actual_spinners_2.get(2).getValue(), actual_spinners_2.get(3).getValue()};
            int[] limit_inputs_2 = {limit_spinners_2.get(0).getValue(), limit_spinners_2.get(1).getValue(), limit_spinners_2.get(2).getValue(), limit_spinners_2.get(3).getValue()};

            crossroad_info_1.setCrossroadInfo(cars_inputs_1, limit_inputs_1, actual_inputs_1);
            crossroad_info_2.setCrossroadInfo(cars_inputs_2, limit_inputs_2, actual_inputs_2);

            conditions = new Conditions(crossroad_info_1, crossroad_info_2);
            createSimulationWindow();
            window.setScene(windowSimulation);
        });


        //Back
        Button buttonBack = new Button(Constants.back_button_label);
        buttonBack.setOnAction(e -> {
            boolean goBack = ConfirmBox.display(Constants.go_to_previous_page_window_label,
                    Constants.go_to_previous_page_from_conditions_text);
            if (goBack) window.setScene(windowClientTypes);
        });
        bottomMenu.getChildren().addAll(buttonBack, buttonRun);

        BorderPane borderPane = new BorderPane();
        borderPane.getStylesheets().add("file:src/GUI/style.css");
        borderPane.setTop(topMenu);
        borderPane.setCenter(centerMenu);
        borderPane.setBottom(bottomMenu);
//        Button buttonBackHome = new Button("Back home");
//        buttonBackHome.setOnAction(e -> window.setScene(windowHome));
//        String imgPath = "file:images/lights/red-s.png";
//        Image img = new Image(imgPath);
//        StackPane layout2 = new StackPane();
//        layout2.getChildren().add(buttonBackHome);
//        layout2.getChildren().add(new ImageView(img));
        windowOptions = new Scene(borderPane, 1000, 660);
    }

    /**
     * This function allow to user choose his client type - Analyst/Observer.
     * Analyst - go to window with all options.
     * Observer - go to window with limited options.
     */
    private void createClientTypesWindow() {
        //Title
        Label label = new Label(Constants.client_type_window_label);
        HBox topMenu = new HBox();
        topMenu.setMinHeight(80);
        topMenu.setAlignment(Pos.CENTER);
        topMenu.getChildren().addAll(label);


        //Analyst button
        VBox leftMenu = new VBox(20);
        Button buttonAnalyst = new Button(Constants.analyst_button_label);
        buttonAnalyst.setOnAction(e -> {
            analyst = true;
            createOptionsWindow(analyst);
            window.setScene(windowOptions);
        });
        leftMenu.setAlignment(Pos.CENTER);
        Image imageAnalyst = new Image("file:images/others/analyst.png");
        ImageView imageViewAnalyst = new ImageView(imageAnalyst);
        imageViewAnalyst.setFitHeight(236);
        imageViewAnalyst.setFitWidth(250);
        leftMenu.getChildren().addAll(imageViewAnalyst, buttonAnalyst);


        //Observer button
        VBox rightMenu = new VBox(20);
        Button buttonObserver = new Button(Constants.observer_button_label);
        buttonObserver.setOnAction(e -> {
            analyst = false;
            createOptionsWindow(analyst);
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
        Utils.printSimulationCreated();

        ExecutorService executor = Executors.newFixedThreadPool(2);
        SystemSTL systemSTL = new SystemSTL(conditions);

        HBox topMenu = new HBox();
        Label label = new Label(Constants.simulation_window_label);
        topMenu.setMinHeight(60);
        topMenu.setAlignment(Pos.CENTER);
        topMenu.getChildren().addAll(label);

        HBox bottomMenu = new HBox(20);
        bottomMenu.setMinHeight(60);
        bottomMenu.setAlignment(Pos.CENTER);

        Button buttonSave = new Button(Constants.save_button_label);
        buttonSave.setOnAction(e -> {
            //Login before saving
            if (Database.getInstance().getConnection() == null) {
                DatabaseBox.login();
                //Save to database window
                Database.getInstance().save(conditions);
            } else {
                //Save to database window
                Database.getInstance().save(conditions);
            }
        });

        Button buttonBack = new Button(Constants.back_button_label);
        buttonBack.setOnAction(e -> {
            boolean goBack = ConfirmBox.display(Constants.go_to_previous_page_window_label,
                    Constants.go_to_previous_page_from_simulation_text);

            if (goBack) {
                systemSTL.stop();
                window.setScene(windowOptions);
            }
        });

        simulation = new Pane();
        simulation.setPrefSize(990, 530);
        updateSimulation();

        BorderPane borderPane = new BorderPane();

        Button buttonStart = new Button(Constants.start_button_label);
        buttonStart.setOnAction(e -> {
            Thread run_system = new Thread(new Runnable() {
                @Override
                public void run() {

                    systemSTL.run();

                    while (!systemSTL.getIsFinished() && !systemSTL.getIsStopped()) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            if (systemSTL.getIsFinished()) {
                                DatabaseConditions database_conditions = Utils.createDatabaseConditions(conditions);

                                ResultsBox.display(database_conditions);
                            }
                        }
                    });
                }
            });

            Thread update_simulation = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (!systemSTL.getIsFinished() && !systemSTL.getIsStopped()) {
                        try {
                            Thread.sleep(30);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                updateSimulation();
                            }
                        });
                    }
                }
            });

            executor.execute(run_system);
            executor.execute(update_simulation);
            executor.shutdown();
        });
        bottomMenu.getChildren().addAll(buttonBack, buttonSave, buttonStart);


        borderPane.getStylesheets().add("file:src/GUI/style.css");
        borderPane.setTop(topMenu);
        borderPane.setCenter(simulation);
        borderPane.setBottom(bottomMenu);

        windowSimulation = new Scene(borderPane, 1000, 660);
    }

    private synchronized void updateSimulation() {
        simulation.getChildren().clear();
        simulation.getStyleClass().add("simulation-container");

        Image image_road = new Image("file:images/others/road.png");

        ImageView imageViewRoad = new ImageView(image_road);
        imageViewRoad.setFitHeight(530);
        imageViewRoad.setFitWidth(990);
        simulation.getChildren().addAll(imageViewRoad);

        updateTrafficLights();
        updateCars();
    }

    private synchronized void updateCars() {

        Rectangle outputClip = new Rectangle(990, 530);
        simulation.setClip(outputClip);

        simulation.layoutBoundsProperty().addListener((ov, oldValue, newValue) -> {
            outputClip.setWidth(newValue.getWidth());
            outputClip.setHeight(newValue.getHeight());
        });

        updateNorthSouthCars();
        updateWestEastCars();
    }

    private void updateWestEastCars() {
        ArrayList<ImageView> east_crossroad_1 =
                createCars(1, Constants.EAST_DIRECTION, conditions.getLanesInfoFirstCrossroad().get(1));
        ArrayList<ImageView> east_crossroad_2 =
                createCars(2, Constants.EAST_DIRECTION, conditions.getLanesInfoSecondCrossroad().get(1));
        ArrayList<ImageView> west_crossroad_1 =
                createCars(1, Constants.WEST_DIRECTION, conditions.getLanesInfoFirstCrossroad().get(3));
        ArrayList<ImageView> west_crossroad_2 =
                createCars(2, Constants.WEST_DIRECTION, conditions.getLanesInfoSecondCrossroad().get(3));

        addCars(east_crossroad_1);
        addCars(east_crossroad_2);
        addCars(west_crossroad_1);
        addCars(west_crossroad_2);
    }

    private synchronized void updateNorthSouthCars() {
        ArrayList<ImageView> north_crossroad_1 =
                createCars(1, Constants.NORTH_DIRECTION, conditions.getLanesInfoFirstCrossroad().get(0));
        ArrayList<ImageView> north_crossroad_2 =
                createCars(2, Constants.NORTH_DIRECTION, conditions.getLanesInfoSecondCrossroad().get(0));
        ArrayList<ImageView> south_crossroad_1 =
                createCars(1, Constants.SOUTH_DIRECTION, conditions.getLanesInfoFirstCrossroad().get(2));
        ArrayList<ImageView> south_crossroad_2 =
                createCars(2, Constants.SOUTH_DIRECTION, conditions.getLanesInfoSecondCrossroad().get(2));

        addCars(north_crossroad_1);
        addCars(north_crossroad_2);
        addCars(south_crossroad_1);
        addCars(south_crossroad_2);
    }

    private synchronized ArrayList<ImageView> createCars(int crossroad, int direction, LaneInfo lane_info) {
        ArrayList<ImageView> cars = new ArrayList<>();

        try {

            for (CarInfo car_info : lane_info.getCarsInLane()) {
                if (lane_info.getCarsInLane().size() == 0) {
                    break;
                }
                if (-40 < car_info.getDistanceFromCrossroad() && car_info.getDistanceFromCrossroad() < 40) {
                    cars.add(createCarOnMapByPlace(crossroad, direction, car_info));
                }
            }

        } catch (ConcurrentModificationException e) {

        }

        return cars;
    }

    private ImageView createCarOnMapByPlace(int crossroad_number, int direction, CarInfo car) {
        int[] place = generatePlace(crossroad_number, direction, car.getDistanceFromCrossroad());
        return createCar(place[0], place[1], getRotateByDirection(direction), car);
    }

    private int[] generatePlace(int crossroad_number, int direction, double distance_from_crossroad) {
        int x = 0;
        int y = 0;

        distance_from_crossroad *= Constants.METER_TO_PIXEL;

        if (direction == Constants.NORTH_DIRECTION) {
            x += 187;
            y += 130;

            y -= distance_from_crossroad;
        } else if (direction == Constants.EAST_DIRECTION) {
            x += 305;
            y += 204;

            x += distance_from_crossroad;
        } else if (direction == Constants.SOUTH_DIRECTION) {
            x += 237;
            y += 315;

            y += distance_from_crossroad;

        } else if (direction == Constants.WEST_DIRECTION) {
            x += 118;
            y += 250;

            x -= distance_from_crossroad;
        }

        if (crossroad_number == 2) {
            x += 528;
        }

        int[] place = {x, y};

        return place;
    }

    private int getRotateByDirection(int direction) {
        switch (direction) {
            case 0:
                return 180;
            case 1:
                return 270;
            case 2:
                return 0;
            case 3:
                return 90;
            default:
                throw new RuntimeException("Bad direction for car...");
        }
    }

    private ImageView createCar(int x, int y, int rotate, CarInfo car) {
        ImageView image_view = new ImageView(car.getCar().getImage());
        image_view.setX(x);
        image_view.setY(y);
        image_view.setFitHeight(car.getCar().getLength() * Constants.METER_TO_PIXEL);
        image_view.setFitWidth(car.getCar().getWidth() * Constants.METER_TO_PIXEL);
        image_view.setRotate(rotate);
        return image_view;
    }

    private void addCars(ArrayList<ImageView> cars) {
        simulation.getChildren().addAll(cars);
    }

    private synchronized void updateTrafficLights() {
        ImageView[] traffic_lights_crossroad_1 = createTrafficLights(1);
        ImageView[] traffic_lights_crossroad_2 = createTrafficLights(2);

        addTrafficLights(traffic_lights_crossroad_1);
        addTrafficLights(traffic_lights_crossroad_2);
    }

    private ImageView[] createTrafficLights(int crossroad_number) {
        ImageView[] views = new ImageView[4];
        CrossroadInfo crossroad_info = null;
        int x = 0, y = 170;

        if (crossroad_number == 1) {
            x = 150;
            crossroad_info = crossroad_info_1;
        } else if (crossroad_number == 2) {
            x = 675;
            crossroad_info = crossroad_info_2;
        }

        views[0] = createTrafficLight(x, y, 180,
                crossroad_info.getCrossroad().getNorthTrafficLight().getTrafficLightImage());

        views[1] = createTrafficLight(x + 150, y + 10, 270,
                crossroad_info.getCrossroad().getEastTrafficLight().getTrafficLightImage());

        views[2] = createTrafficLight(x + 140, y + 150, 0,
                crossroad_info.getCrossroad().getSouthTrafficLight().getTrafficLightImage());

        views[3] = createTrafficLight(x - 10, y + 150, 90,
                crossroad_info.getCrossroad().getWestTrafficLight().getTrafficLightImage());

        return views;
    }

    private ImageView createTrafficLight(int x, int y, int rotate, Image image) {
        ImageView image_view = new ImageView(image);
        image_view.setX(x);
        image_view.setY(y);
        image_view.setFitHeight(Constants.TRAFFIC_LIGHT_HEIGHT);
        image_view.setFitWidth(Constants.TRAFFIC_LIGHT_WIDTH);
        image_view.setRotate(rotate);
        return image_view;
    }

    private void addTrafficLights(ImageView[] images) {
//        simulation.getChildren().addAll(images[0], images[1], images[2], images[3]);
        simulation.getChildren().addAll(images);
    }

    private void upgradeSpinner(Spinner<Integer> spinner, int min, int max, boolean limit) {
        spinner.setEditable(true);

        ArrayList<Integer> arr = new ArrayList<>();

        int speed_limit_step = 1;

        if (limit) {
            speed_limit_step = 5;
        }

        for (int i = min; i < max; i += speed_limit_step) {
            arr.add(i);
        }

        // Item List.
        ObservableList<Integer> items = FXCollections.observableArrayList(arr);

        // Value Factory:
        SpinnerValueFactory<Integer> valueFactory = //
                new SpinnerValueFactory.ListSpinnerValueFactory<>(items);

        // The converter to convert between text and item object.
        InputConverter converter = new InputConverter();
        valueFactory.setConverter(converter);

        spinner.setValueFactory(valueFactory);

        spinner.getEditor().setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                String text = spinner.getEditor().getText();
                SpinnerValueFactory.ListSpinnerValueFactory<Integer>//
                        valueFactory = (SpinnerValueFactory.ListSpinnerValueFactory<Integer>) spinner.getValueFactory();

                StringConverter<Integer> converter = valueFactory.getConverter();
                Integer enterValue;
                try {
                    enterValue = converter.fromString(text);
                } catch (RuntimeException e) {
                    enterValue = 1;
                }


                // If the list does not contains 'enterValue'.
                if (!valueFactory.getItems().contains(enterValue)) {
                    // Add new item to list
                    valueFactory.getItems().add(enterValue);
                    // Set to current
                    valueFactory.setValue(enterValue);

                    if (enterValue > max) {
                        enterValue = max;
                    } else {
                        enterValue = min;
                    }

                    spinner.getValueFactory().setValue(enterValue);
                    System.out.println(spinner.getValueFactory().getValue());

                }
            }
        });
    }

    public Scene getScene() {
        return windowHome;
    }

}

class InputConverter extends StringConverter<Integer> {

    @Override
    public String toString(Integer object) {
        return object + "";
    }

    @Override
    public Integer fromString(String string) {
        return Integer.parseInt(string);
    }

}

