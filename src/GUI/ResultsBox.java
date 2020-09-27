package GUI;

import Database.DatabaseConditions;
import Tools.Constants;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ResultsBox {

    @SuppressWarnings("Duplicates")
    public static void display(DatabaseConditions database_conditions) {
        Stage stage = new Stage();
        stage.setTitle("Bar Chart");

        Button button = new Button(Constants.close_button_label);
        button.setOnAction(e -> stage.close());

        final String TIME_LABEL = "Time";
        final String AWT_LABEL = "AWT";
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String, Number> bc = new BarChart<String, Number>(xAxis, yAxis);

        bc.setCategoryGap(100);
//        bc.setTitle("Chart");
        xAxis.setLabel("Duration type");
        yAxis.setLabel("Seconds");

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Initial");
        series1.getData().add(new XYChart.Data(TIME_LABEL, database_conditions.getInitialTime()));
        series1.getData().add(new XYChart.Data(AWT_LABEL, database_conditions.getInitialAWS()));

        XYChart.Series series2 = new XYChart.Series();
        series2.setName("Algorithm");
        series2.getData().add(new XYChart.Data(TIME_LABEL, database_conditions.getBetterTime()));
        series2.getData().add(new XYChart.Data(AWT_LABEL, database_conditions.getBetterAWS()));

//        XYChart.Series series3 = new XYChart.Series();
//        series3.setName("Simulation");
//        series3.getData().add(new XYChart.Data(TIME_LABEL, database_conditions.getSimulationTime()));


        BorderPane border_pane = new BorderPane();

        HBox top_menu = new HBox();
        top_menu.setPadding(new Insets(10));
        top_menu.setAlignment(Pos.CENTER);
        Label main_label = new Label("Results");
        main_label.setStyle("-fx-font-size: 16pt; -fx-font-weight: 400;");
        top_menu.getChildren().add(main_label);

        VBox content = new VBox(10);
        content.setAlignment(Pos.BOTTOM_LEFT);


        HBox chart = new HBox(10);
        chart.setPadding(new Insets(10));
        chart.getChildren().add(bc);


        VBox description = new VBox(10);

        VBox first_crossroad_box = new VBox(10);
        first_crossroad_box.setPadding(new Insets(10));
        first_crossroad_box.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        VBox second_crossroad_box = new VBox(10);
        second_crossroad_box.setPadding(new Insets(10));
        second_crossroad_box.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        VBox results_box = new VBox(10);
        results_box.setPadding(new Insets(10));
        results_box.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        Label first_crossroad_label = new Label("First crossroad");
        Label second_crossroad_label = new Label("Second crossroad");
        Label results_label = new Label("Results");

        first_crossroad_box.setAlignment(Pos.CENTER);
        second_crossroad_box.setAlignment(Pos.CENTER);
        results_box.setAlignment(Pos.CENTER);

        first_crossroad_label.setStyle("-fx-font-size: 12pt; -fx-font-weight: 300;");
        second_crossroad_label.setStyle("-fx-font-size: 12pt; -fx-font-weight: 300;");
        results_label.setStyle("-fx-font-size: 12pt; -fx-font-weight: 300;");

        HBox first_crossroad_conditions = new HBox(10);
        HBox second_crossroad_conditions = new HBox(10);
        HBox results_conditions = new HBox(10);
        results_conditions.setSpacing(50);

        first_crossroad_conditions.setAlignment(Pos.CENTER);
        second_crossroad_conditions.setAlignment(Pos.CENTER);
        results_conditions.setAlignment(Pos.BASELINE_LEFT);


        VBox first_directions = new VBox();
        first_directions.setAlignment(Pos.CENTER);
        Label first_empty = new Label("");
        Label first_north = new Label("north");
        Label first_east = new Label("east");
        Label first_south = new Label("south");
        Label first_west = new Label("west");
        first_directions.getChildren().addAll(first_empty, first_north, first_east, first_south, first_west);

        VBox first_cars_count = new VBox();
        first_cars_count.setAlignment(Pos.CENTER);
        Label first_cars_label = new Label("cars count");
        Label first_cars_north = new Label("" + database_conditions.getCarsFirstCrossroad()[Constants.NORTH_DIRECTION]);
        Label first_cars_east = new Label("" + database_conditions.getCarsFirstCrossroad()[Constants.EAST_DIRECTION]);
        Label first_cars_south = new Label("" + +database_conditions.getCarsFirstCrossroad()[Constants.SOUTH_DIRECTION]);
        Label first_cars_west = new Label("" + +database_conditions.getCarsFirstCrossroad()[Constants.WEST_DIRECTION]);
        first_cars_count.getChildren().addAll(first_cars_label, first_cars_north, first_cars_east, first_cars_south, first_cars_west);

        VBox first_speed_limit = new VBox();
        first_speed_limit.setAlignment(Pos.CENTER);
        Label first_speed_label = new Label("speed limit");
        Label first_speed_north = new Label("" + database_conditions.getSpeedLimitFirstCrossroad()[Constants.NORTH_DIRECTION]);
        Label first_speed_east = new Label("" + database_conditions.getSpeedLimitFirstCrossroad()[Constants.EAST_DIRECTION]);
        Label first_speed_south = new Label("" + database_conditions.getSpeedLimitFirstCrossroad()[Constants.SOUTH_DIRECTION]);
        Label first_speed_west = new Label("" + database_conditions.getSpeedLimitFirstCrossroad()[Constants.WEST_DIRECTION]);
        first_speed_limit.getChildren().addAll(first_speed_label, first_speed_north, first_speed_east, first_speed_south, first_speed_west);

        VBox first_actual_speed = new VBox();
        first_actual_speed.setAlignment(Pos.CENTER);
        Label first_actual_label = new Label("actual speed");
        Label first_actual_north = new Label("" + database_conditions.getActualSpeedFirstCrossroad()[Constants.NORTH_DIRECTION]);
        Label first_actual_east = new Label("" + database_conditions.getActualSpeedFirstCrossroad()[Constants.EAST_DIRECTION]);
        Label first_actual_south = new Label("" + database_conditions.getActualSpeedFirstCrossroad()[Constants.SOUTH_DIRECTION]);
        Label first_actual_west = new Label("" + database_conditions.getActualSpeedFirstCrossroad()[Constants.WEST_DIRECTION]);
        first_actual_speed.getChildren().addAll(first_actual_label, first_actual_north, first_actual_east, first_actual_south, first_actual_west);

        VBox second_directions = new VBox();
        second_directions.setAlignment(Pos.CENTER);
        Label second_empty = new Label("");
        Label second_north = new Label("north");
        Label second_east = new Label("east");
        Label second_south = new Label("south");
        Label second_west = new Label("west");
        second_directions.getChildren().addAll(second_empty, second_north, second_east, second_south, second_west);

        VBox second_cars_count = new VBox();
        second_cars_count.setAlignment(Pos.CENTER);
        Label second_cars_label = new Label("cars count");
        Label second_cars_north = new Label("" + database_conditions.getCarsSecondCrossroad()[Constants.NORTH_DIRECTION]);
        Label second_cars_east = new Label("" + database_conditions.getCarsSecondCrossroad()[Constants.EAST_DIRECTION]);
        Label second_cars_south = new Label("" + database_conditions.getCarsSecondCrossroad()[Constants.SOUTH_DIRECTION]);
        Label second_cars_west = new Label("" + database_conditions.getCarsSecondCrossroad()[Constants.WEST_DIRECTION]);
        second_cars_count.getChildren().addAll(second_cars_label, second_cars_north, second_cars_east, second_cars_south, second_cars_west);

        VBox second_speed_limit = new VBox();
        second_speed_limit.setAlignment(Pos.CENTER);
        Label second_speed_label = new Label("speed limit");
        Label second_speed_north = new Label("" + database_conditions.getSpeedLimitSecondCrossroad()[Constants.NORTH_DIRECTION]);
        Label second_speed_east = new Label("" + database_conditions.getSpeedLimitSecondCrossroad()[Constants.EAST_DIRECTION]);
        Label second_speed_south = new Label("" + database_conditions.getSpeedLimitSecondCrossroad()[Constants.SOUTH_DIRECTION]);
        Label second_speed_west = new Label("" + database_conditions.getSpeedLimitSecondCrossroad()[Constants.WEST_DIRECTION]);
        second_speed_limit.getChildren().addAll(second_speed_label, second_speed_north, second_speed_east, second_speed_south, second_speed_west);

        VBox second_actual_speed = new VBox();
        second_actual_speed.setAlignment(Pos.CENTER);
        Label second_actual_label = new Label("actual speed");
        Label second_actual_north = new Label("" + database_conditions.getActualSpeedSecondCrossroad()[Constants.NORTH_DIRECTION]);
        Label second_actual_east = new Label("" + database_conditions.getActualSpeedSecondCrossroad()[Constants.EAST_DIRECTION]);
        Label second_actual_south = new Label("" + database_conditions.getActualSpeedSecondCrossroad()[Constants.SOUTH_DIRECTION]);
        Label second_actual_west = new Label("" + database_conditions.getActualSpeedSecondCrossroad()[Constants.WEST_DIRECTION]);
        second_actual_speed.getChildren().addAll(second_actual_label, second_actual_north, second_actual_east, second_actual_south, second_actual_west);


        VBox results_types = new VBox();
        results_types.setAlignment(Pos.CENTER);
        Label results_empty = new Label("");
        Label results_initial = new Label("Initial");
        Label results_algorithm = new Label("Algorithm");
        results_types.getChildren().addAll(results_empty, results_initial, results_algorithm);

        VBox results_times = new VBox();
        results_times.setAlignment(Pos.CENTER);
        Label results_times_label = new Label("Time");
        Label results_times_initial = new Label("" + database_conditions.getInitialTime());
        Label results_times_algorithm = new Label("" + database_conditions.getBetterTime());
        results_times.getChildren().addAll(results_times_label, results_times_initial, results_times_algorithm);

        VBox results_awt = new VBox();
        results_awt.setAlignment(Pos.CENTER);
        Label results_awt_label = new Label("AWT");
        Label results_awt_initial = new Label("" + database_conditions.getInitialAWS());
        Label results_awt_algorithm = new Label("" + database_conditions.getBetterAWS());
        results_awt.getChildren().addAll(results_awt_label, results_awt_initial, results_awt_algorithm);


        first_crossroad_conditions.getChildren().addAll(
                first_directions,
                first_cars_count,
                first_speed_limit,
                first_actual_speed);


        second_crossroad_conditions.getChildren().addAll(
                second_directions,
                second_cars_count,
                second_speed_limit,
                second_actual_speed);

        results_conditions.getChildren().addAll(
                results_types,
                results_times,
                results_awt);

        first_crossroad_box.getChildren().addAll(first_crossroad_label, first_crossroad_conditions);
        second_crossroad_box.getChildren().addAll(second_crossroad_label, second_crossroad_conditions);
        results_box.getChildren().addAll(results_label, results_conditions);

        description.getChildren().addAll(first_crossroad_box, second_crossroad_box, results_box);


        HBox chart_text = new HBox(10);
        chart_text.getChildren().addAll(chart, description);
        content.getChildren().addAll(chart_text);

        border_pane.setTop(top_menu);
        border_pane.setCenter(content);

        Scene scene = new Scene(border_pane, 800, 500);
//        bc.getData().addAll(series1, series2, series3);
        bc.getData().addAll(series1, series2);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.showAndWait();
    }
}
