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
import javafx.stage.Modality;
import javafx.stage.Stage;

public class InformationBox {

    private static ImageView image_view;
    private static Label text;
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

//        Label label = new Label();
//        label.setText(message);
//        label.setStyle("-fx-font-size: 12pt; -fx-font-weight: none");


        image_view = new ImageView(image_info);
        text = new Label(Constants.INFO);

        Button button_info = new Button("Main Info");
        button_info.setPrefSize(100, 50);
        button_info.setOnAction(e -> {
            image_view.setImage(image_info);
            text.setText(Constants.INFO);
        });

        Button button_info_directions = new Button("Directions");
        button_info_directions.setOnAction(e -> {
            image_view.setImage(image_info_directions);
            text.setText(Constants.INFO_DIRECTIONS);
        });

        Button button_info_traffic_lights = new Button("Traffic lights");
        button_info_traffic_lights.setOnAction(e -> {
            image_view.setImage(image_info_traffic_lights);
            text.setText(Constants.INFO_TRAFFIC_LIGHTS);
        });

        Button button_info_cars_count = new Button("Cars count");
        button_info_cars_count.setOnAction(e -> {
            image_view.setImage(image_info);
            text.setText(Constants.INFO_CARS_COUNT);
        });

        Button button_info_speed_limit = new Button("Speed limit");
        button_info_speed_limit.setOnAction(e -> {
            image_view.setImage(image_info);
            text.setText(Constants.INFO_SPEED_LIMIT);
        });

        Button button_info_actual_speed = new Button("Actual speed");
        button_info_actual_speed.setOnAction(e -> {
            image_view.setImage(image_info);
            text.setText(Constants.INFO_ACTUAL_SPEED);
        });


        BorderPane border_pane = new BorderPane();
        border_pane.getStylesheets().add("file:src/GUI/style.css");

        HBox top_menu = new HBox(10);
        top_menu.setAlignment(Pos.CENTER);
        Label label = new Label("Tutorial");
        top_menu.getChildren().add(label);

        VBox content = new VBox(10);
        content.setAlignment(Pos.CENTER);

        HBox image_text = new HBox(10);
        image_text.setAlignment(Pos.CENTER);
        image_text.getChildren().addAll(image_view, text);

        HBox buttons = new HBox(10);
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().add(button_info);
        buttons.getChildren().add(button_info_directions);
        buttons.getChildren().add(button_info_traffic_lights);
        buttons.getChildren().add(button_info_cars_count);
        buttons.getChildren().add(button_info_speed_limit);
        buttons.getChildren().add(button_info_actual_speed);

        content.getChildren().addAll(image_text, buttons);

        border_pane.setTop(top_menu);
        border_pane.setCenter(content);

        Scene scene = new Scene(border_pane);
        window.setScene(scene);
        window.setResizable(false);
        window.showAndWait();
    }
}
