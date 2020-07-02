package GUI;

import Tools.Constants;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
public class ConfirmBox {

    static boolean answer;

    public static boolean display(String title, String message) {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);

        VBox topMenu = new VBox();
        Label label = new Label();
        label.setText(message);
        label.setStyle("-fx-font-size: 14pt");
        topMenu.setAlignment(Pos.CENTER);
        topMenu.getChildren().addAll(label);

        Button yesButton = new Button(Constants.yes_button_label);
        yesButton.setOnAction(e -> {
            answer = true;
            window.close();
        });
        Button noButton = new Button(Constants.no_button_label);
        noButton.setOnAction(e -> {
            answer = false;
            window.close();
        });

        HBox bottomMenu = new HBox(10);
        bottomMenu.getChildren().addAll(yesButton, noButton);
        bottomMenu.setAlignment(Pos.CENTER);

        BorderPane borderPane = new BorderPane();
        borderPane.getStylesheets().add("file:src/GUI/style.css");
        borderPane.setTop(topMenu);
        borderPane.setCenter(bottomMenu);

        Scene scene = new Scene(borderPane, 300, 100);
        window.setScene(scene);
        window.setResizable(false);
        window.showAndWait();

        return answer;
    }

}
