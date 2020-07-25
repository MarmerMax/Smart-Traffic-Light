package GUI;

import Objects.Car.Car;
import Objects.Crossroad.Crossroad;
import Objects.CrossroadInfo.CrossroadInfo;
import Objects.Road.RoadCreator;
import Objects.Conditions.Conditions;
import Objects.TrafficLight.TrafficLightState.GreenState;
import Objects.TrafficLight.TrafficLightState.RedState;
import SystemSTL.SystemSTL;
import Tools.Constants;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
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
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.scene.shape.Rectangle;
import SystemSTL.LaneInfo;
import SystemSTL.CarInfo;

import javafx.event.ActionEvent;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProgramGUI {

    private Scene windowHome, windowClientTypes, windowOptions, windowSimulation;
    private Stage window;
    private boolean analyst;
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
        Crossroad crossroad_1 = new Crossroad(RoadCreator.createRoads(54, 1));
        Crossroad crossroad_2 = new Crossroad(RoadCreator.createRoads(433, 1));
        crossroad_info_1 = new CrossroadInfo(crossroad_1);
        crossroad_info_2 = new CrossroadInfo(crossroad_2);

        createHomeWindow();
        createClientTypesWindow();
        createOptionsWindow();
        createSimulationWindow();
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
        buttonStart.setOnAction(e -> window.setScene(windowClientTypes));

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
    private void createOptionsWindow() {
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
        cars_spinners_1.add(new Spinner<>(1, 1000, 10));
        cars_spinners_1.add(new Spinner<>(1, 5, 3));
        cars_spinners_1.add(new Spinner<>(1, 1000, 10));
        cars_spinners_1.add(new Spinner<>(1, 1000, 10));

//        for (Spinner<Integer> spinner : cars_spinners_1) {
//            if (spinner.getValue() == 10) {
//                upgradeSpinner(spinner, 1, 1000, false);
//
//            } else {
//                upgradeSpinner(spinner, 1, 5, false);
//
//            }
//        }

        cars1.getChildren().addAll(carsLabel1, cars_spinners_1.get(0), cars_spinners_1.get(1), cars_spinners_1.get(2), cars_spinners_1.get(3));

        VBox speedLimit1 = new VBox(10);
        speedLimit1.getStyleClass().add("options-column");
        Label speedLimitLabel1 = new Label(Constants.speed_limit_label);
        speedLimitLabel1.getStyleClass().add("label-column");
        ArrayList<Spinner<Integer>> limit_spinners_1 = new ArrayList<>();
        limit_spinners_1.add(new Spinner<>(50, 110, 70));
        limit_spinners_1.add(new Spinner<>(50, 110, 70));
        limit_spinners_1.add(new Spinner<>(50, 110, 70));
        limit_spinners_1.add(new Spinner<>(50, 110, 70));


        speedLimit1.getChildren().addAll(speedLimitLabel1, limit_spinners_1.get(0), limit_spinners_1.get(1), limit_spinners_1.get(2), limit_spinners_1.get(3));

        VBox actualSpeed1 = new VBox(10);
        actualSpeed1.getStyleClass().add("options-column");
        Label actualSpeedLabel1 = new Label(Constants.actual_speed_label);
        actualSpeedLabel1.getStyleClass().add("label-column");
        ArrayList<Spinner<Integer>> actual_spinners_1 = new ArrayList<>();
        actual_spinners_1.add(new Spinner<>(50, 110, 70));
        actual_spinners_1.add(new Spinner<>(50, 110, 70));
        actual_spinners_1.add(new Spinner<>(50, 110, 70));
        actual_spinners_1.add(new Spinner<>(50, 110, 70));
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
        cars_spinners_2.add(new Spinner<>(1, 1000, 25));
        cars_spinners_2.add(new Spinner<>(1, 1000, 25));
        cars_spinners_2.add(new Spinner<>(1, 1000, 25));
        cars_spinners_2.add(new Spinner<>(1, 10, 5));
        cars2.getChildren().addAll(carsLabel2, cars_spinners_2.get(0), cars_spinners_2.get(1), cars_spinners_2.get(2), cars_spinners_2.get(3));


        VBox speedLimit2 = new VBox(10);
        speedLimit2.getStyleClass().add("options-column");
        Label speedLimitLabel2 = new Label(Constants.speed_limit_label);
        speedLimitLabel2.getStyleClass().add("label-column");
        ArrayList<Spinner<Integer>> limit_spinners_2 = new ArrayList<>();
        limit_spinners_2.add(new Spinner<>(50, 110, 70));
        limit_spinners_2.add(new Spinner<>(50, 110, 70));
        limit_spinners_2.add(new Spinner<>(50, 110, 70));
        limit_spinners_2.add(new Spinner<>(50, 110, 70));
        speedLimit2.getChildren().addAll(speedLimitLabel2, limit_spinners_2.get(0), limit_spinners_2.get(1), limit_spinners_2.get(2), limit_spinners_2.get(3));

        VBox actualSpeed2 = new VBox(10);
        actualSpeed2.getStyleClass().add("options-column");
        Label actualSpeedLabel2 = new Label(Constants.actual_speed_label);
        actualSpeedLabel2.getStyleClass().add("label-column");
        ArrayList<Spinner<Integer>> actual_spinners_2 = new ArrayList<>();
        actual_spinners_2.add(new Spinner<>(50, 110, 70));
        actual_spinners_2.add(new Spinner<>(50, 110, 70));
        actual_spinners_2.add(new Spinner<>(50, 110, 70));
        actual_spinners_2.add(new Spinner<>(50, 110, 70));
        actualSpeed2.getChildren().addAll(actualSpeedLabel2, actual_spinners_2.get(0), actual_spinners_2.get(1), actual_spinners_2.get(2), actual_spinners_2.get(3));

        crossroad_fields_2.getChildren().addAll(boxLabel2, route2, cars2, speedLimit2, actualSpeed2);


        //Other options
        HBox otherOptions = new HBox();
        otherOptions.getStyleClass().add("options-container");

        VBox boxLabel3 = new VBox(10);
        boxLabel3.getStyleClass().add("options-column");
        Label nameLabel3 = new Label(Constants.other_features_label);
        nameLabel3.getStyleClass().add("label-column");
        boxLabel3.getChildren().addAll(nameLabel3);

        VBox boxButtonRandom = new VBox(10);
        boxButtonRandom.getStyleClass().add("options-column");
        Button buttonRandom = new Button(Constants.random_button_label);
        boxButtonRandom.getChildren().add(buttonRandom);
        buttonRandom.setOnAction(e -> {
            boolean answer = ConfirmBox.display(Constants.random_window_label, Constants.generate_random_data_label);
            if (answer) {

            }
        });

        //Database
        VBox boxButtonDatabase = new VBox(10);
        boxButtonDatabase.getStyleClass().add("options-column");
        Button buttonDatabase = new Button(Constants.database_button_label);
        boxButtonDatabase.getChildren().add(buttonDatabase);
        buttonDatabase.setOnAction(e -> {
            DatabaseBox.login();
//        	DatabaseBox.display();
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
                //reset all values
            }
        });

        VBox boxButtonInfo = new VBox(10);
        boxButtonInfo.getStyleClass().add("options-column");
        Button buttonInfo = new Button(Constants.info_button_label);
        boxButtonInfo.getChildren().add(buttonInfo);
        buttonInfo.setOnAction(e -> {
            //info
        });

        otherOptions.getChildren().addAll(boxLabel3, boxButtonDatabase, boxButtonRandom, boxButtonReset, boxButtonInfo);

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
            int[] cars_inputs_1 = {cars_spinners_1.get(0).getValue(), cars_spinners_1.get(1).getValue(), cars_spinners_1.get(2).getValue(), cars_spinners_1.get(3).getValue()};
            int[] actual_inputs_1 = {actual_spinners_1.get(0).getValue(), actual_spinners_1.get(1).getValue(), actual_spinners_1.get(2).getValue(), actual_spinners_1.get(3).getValue()};
            int[] limit_inputs_1 = {limit_spinners_1.get(0).getValue(), limit_spinners_1.get(1).getValue(), limit_spinners_1.get(2).getValue(), limit_spinners_1.get(3).getValue()};

            int[] cars_inputs_2 = {cars_spinners_2.get(0).getValue(), cars_spinners_2.get(1).getValue(), cars_spinners_2.get(2).getValue(), cars_spinners_2.get(3).getValue()};
            int[] actual_inputs_2 = {actual_spinners_2.get(0).getValue(), actual_spinners_2.get(1).getValue(), actual_spinners_2.get(2).getValue(), actual_spinners_2.get(3).getValue()};
            int[] limit_inputs_2 = {limit_spinners_2.get(0).getValue(), limit_spinners_2.get(1).getValue(), limit_spinners_2.get(2).getValue(), limit_spinners_2.get(3).getValue()};

            //else alert box fail!

            crossroad_info_1.setCrossroadInfo(cars_inputs_1, actual_inputs_1, limit_inputs_1);
            crossroad_info_2.setCrossroadInfo(cars_inputs_2, actual_inputs_2, limit_inputs_2);

            conditions = new Conditions(crossroad_info_1, crossroad_info_2);
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
        ExecutorService executor = Executors.newFixedThreadPool(2);

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
            //Save to database window
        });

        Button buttonBack = new Button(Constants.back_button_label);
        buttonBack.setOnAction(e -> {
            boolean goBack = ConfirmBox.display(Constants.go_to_previous_page_window_label,
                    Constants.go_to_previous_page_from_simulation_text);

            if (goBack) {
//                executor.shutdownNow();
                window.setScene(windowOptions);
            }
        });

        simulation = new Pane();
        simulation.setPrefSize(990, 530);
        updateSimulation();

        BorderPane borderPane = new BorderPane();

        Button buttonStart = new Button(Constants.start_button_label);
        buttonStart.setOnAction(e -> {
            SystemSTL systemSTL = new SystemSTL(conditions);
            Thread run_system = new Thread(new Runnable() {
                @Override
                public void run() {
                    systemSTL.run();
                }
            });
//            run_system.start();

            Thread update_simulation = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (!systemSTL.isFinished()) {
                        try {
                            Thread.sleep(10);
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
//            update_simulation.start();

            executor.execute(run_system);
            executor.execute(update_simulation);
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

        updateCars();
        updateTrafficLights();
    }

    private synchronized void updateCars() {

        Rectangle outputClip = new Rectangle(990, 530);
        simulation.setClip(outputClip);

        simulation.layoutBoundsProperty().addListener((ov, oldValue, newValue) -> {
            outputClip.setWidth(newValue.getWidth());
            outputClip.setHeight(newValue.getHeight());
        });


//        updateNorthSouthCars();
//        updateWestEastCars();

        Image image = new Image("file:images/cars/car1.png");
        ImageView image_view = createCar(758, 500, 0, image);
        simulation.getChildren().add(image_view);
    }

    private void updateWestEastCars() {
        ArrayList<ImageView> east_crossroad_1 =
                createCars(1, 1, conditions.getCarsInFirstCrossroad().get(1));
        ArrayList<ImageView> east_crossroad_2 =
                createCars(2, 1, conditions.getCarsInSecondCrossroad().get(1));
        ArrayList<ImageView> west_crossroad_1 =
                createCars(1, 3, conditions.getCarsInFirstCrossroad().get(3));
        ArrayList<ImageView> west_crossroad_2 =
                createCars(2, 3, conditions.getCarsInSecondCrossroad().get(3));

        addCars(east_crossroad_1);
        addCars(east_crossroad_2);
        addCars(west_crossroad_1);
        addCars(west_crossroad_2);
    }

    private void updateNorthSouthCars() {
        ArrayList<ImageView> north_crossroad_1 =
                createCars(1, 0, conditions.getCarsInFirstCrossroad().get(0));
        ArrayList<ImageView> north_crossroad_2 =
                createCars(2, 0, conditions.getCarsInSecondCrossroad().get(0));
        ArrayList<ImageView> south_crossroad_1 =
                createCars(1, 2, conditions.getCarsInFirstCrossroad().get(2));
        ArrayList<ImageView> south_crossroad_2 =
                createCars(2, 2, conditions.getCarsInSecondCrossroad().get(2));

        addCars(north_crossroad_1);
        addCars(north_crossroad_2);
        addCars(south_crossroad_1);
        addCars(south_crossroad_2);
    }

    private void addCars(ArrayList<ImageView> cars) {
        for (ImageView image_view : cars) {
            simulation.getChildren().add(image_view);
        }
    }

    private ArrayList<ImageView> createCars(int crossroad, int direction, LaneInfo lane_info) {
        ArrayList<ImageView> cars = new ArrayList<>();

        for (CarInfo car_info : lane_info.getCarsInLane()) {
            if (car_info.getDistanceFromCrossroad() < 300 && car_info.getDistanceFromCrossroad() > -300) {
                createCarOnMapByPlace(1, 0, car_info);
            }
        }

        return cars;
    }

    //TODO: fix initial place for x and y
    private ImageView createCarOnMapByPlace(int crossroad_number, int direction, CarInfo car) {
        int[] place;

        if (crossroad_number == 1) {
            place = generatePlace(300, 300, direction, car.getDistanceFromCrossroad());
        } else {
            place = generatePlace(600, 300, direction, car.getDistanceFromCrossroad());
        }

        return createCar(place[0], place[1], getRotateByDirection(direction), car.getCar().getImage());
    }

    //TODO: calculate right place for cars
    private int[] generatePlace(int x, int y, int direction, double distance_from_crossroad) {
        int[] place = new int[2];

        return place;
    }

    private ImageView createCar(int x, int y, int rotate, Image image) {
        ImageView image_view = new ImageView(image);
        image_view.setX(x);
        image_view.setY(y);
        image_view.setFitHeight(Constants.CAR_HEIGHT);
        image_view.setFitWidth(Constants.CAR_WIDTH);
        image_view.setRotate(rotate);
        return image_view;
    }

    private int getRotateByDirection(int direction) {
        switch (direction) {
            case 0:
                return 0;
            case 1:
                return 90;
            case 2:
                return 180;
            case 3:
                return 270;
            default:
                throw new RuntimeException("Bad direction for car...");
        }
    }

    private synchronized void updateTrafficLights() {
        ImageView[] traffic_lights_crossroad_1 = createTrafficLights(1);
        ImageView[] traffic_lights_crossroad_2 = createTrafficLights(2);

        addTrafficLights(traffic_lights_crossroad_1);
        addTrafficLights(traffic_lights_crossroad_2);
    }

    private void addTrafficLights(ImageView[] images) {
        simulation.getChildren().addAll(images[0], images[1], images[2], images[3]);
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
