package GUI;

import Tools.Constants;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class InformationBox {

    private static ImageView image_view;
    private static Text text;
    private static Label label;
    private static Image image_info = new Image("file:images/info/info.png");
    private static Image image_info_directions = new Image("file:images/info/info_directions.png");
    private static Image image_info_traffic_lights = new Image("file:images/info/info_traffic_lights.png");
//    private static Image image_info_cars_count = new Image();
//    private static Image image_info_speed_limit = new Image();
//    private static Image image_info_actual_speed = new Image();

    public static void display(String title) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);

        image_view = new ImageView(image_info);
        label = new Label(Constants.INFO_LABEL);
        text = new Text(Constants.INFO_TEXT);
        text.setWrappingWidth(400);

        Button button_info = new Button("Main Info");
        button_info.getStyleClass().add("button-info");
        button_info.setOnAction(e -> {
            image_view.setImage(image_info);
            label.setText(Constants.INFO_LABEL);
            text.setText(Constants.INFO_TEXT);
        });

        Button button_info_directions = new Button("Directions");
        button_info_directions.getStyleClass().add("button-info");
        button_info_directions.setOnAction(e -> {
            image_view.setImage(image_info_directions);
            label.setText(Constants.INFO_DIRECTIONS_LABEL);
            text.setText(Constants.INFO_DIRECTIONS_TEXT);
        });

        Button button_info_traffic_lights = new Button("Traffic lights");
        button_info_traffic_lights.getStyleClass().add("button-info");
        button_info_traffic_lights.setOnAction(e -> {
            image_view.setImage(image_info_traffic_lights);
            label.setText(Constants.INFO_TRAFFIC_LIGHTS_LABEL);
            text.setText(Constants.INFO_TRAFFIC_LIGHTS_TEXT);
        });

        Button button_info_cars_count = new Button("Cars count");
        button_info_cars_count.getStyleClass().add("button-info");
        button_info_cars_count.setOnAction(e -> {
            image_view.setImage(image_info);
            label.setText(Constants.INFO_CARS_COUNT_LABEL);
            text.setText(Constants.INFO_CARS_COUNT_TEXT);
        });

        Button button_info_speed_limit = new Button("Speed limit");
        button_info_speed_limit.getStyleClass().add("button-info");
        button_info_speed_limit.setOnAction(e -> {
            image_view.setImage(image_info);
            label.setText(Constants.INFO_SPEED_LIMIT_LABEL);
            text.setText(Constants.INFO_SPEED_LIMIT_TEXT);
        });

        Button button_info_actual_speed = new Button("Actual speed");
        button_info_actual_speed.getStyleClass().add("button-info");
        button_info_actual_speed.setOnAction(e -> {
            image_view.setImage(image_info);
            label.setText(Constants.INFO_ACTUAL_SPEED_LABEL);
            text.setText(Constants.INFO_ACTUAL_SPEED_TEXT);
        });


        BorderPane border_pane = new BorderPane();
        border_pane.getStylesheets().add("file:src/GUI/style.css");
        border_pane.getStyleClass().add("main-color");

        HBox top_menu = new HBox();
        top_menu.setPadding(new Insets(10));
        top_menu.setAlignment(Pos.CENTER);
        Label main_label = new Label("Tutorial");
        top_menu.getChildren().add(main_label);

        VBox content = new VBox(10);
        content.setAlignment(Pos.BOTTOM_LEFT);

        HBox image_pane = new HBox();
        image_pane.setPadding(new Insets(10));
        image_pane.getChildren().add(image_view);

        VBox text_pane = new VBox();
        text_pane.setPadding(new Insets(10));
        text_pane.getChildren().addAll(label, text);

        HBox image_text = new HBox();
        image_text.getChildren().addAll(image_pane, text_pane);

        HBox buttons = new HBox(10);
        buttons.setAlignment(Pos.CENTER);
        buttons.setPadding(new Insets(10));

        buttons.getChildren().add(button_info);
        buttons.getChildren().add(button_info_directions);
        buttons.getChildren().add(button_info_traffic_lights);
        buttons.getChildren().add(button_info_cars_count);
        buttons.getChildren().add(button_info_speed_limit);
        buttons.getChildren().add(button_info_actual_speed);

        content.getChildren().addAll(image_text);

        border_pane.setTop(top_menu);
        border_pane.setCenter(content);
        border_pane.setBottom(buttons);

        Scene scene = new Scene(border_pane);
        window.setScene(scene);
        window.setResizable(false);
        window.showAndWait();
    }
}
