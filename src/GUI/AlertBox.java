package GUI;

import Utils.Constants;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {

    public static void display(String title, String message) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);

        Label label = new Label();
        label.setText(message);
        label.setStyle("-fx-font-size: 12pt; -fx-font-weight: none");

        Button button = new Button(Constants.close_button_label);
        button.setOnAction(e -> window.close());

        VBox layout = new VBox(20);
        layout.getStylesheets().add("file:src/GUI/style.css");
        layout.getChildren().addAll(label, button);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.setResizable(false);
        window.showAndWait();
    }

}
