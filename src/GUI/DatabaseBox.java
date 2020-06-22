package GUI;

import Database.Database;
import Utils.Constants;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DatabaseBox {

    private static String query = "";

    public static String display() {
        query = "";

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(Constants.database_window_label);

        window.setOnCloseRequest(e -> {
            e.consume();
            query = "";
            window.close();
        });

        VBox topMenu = new VBox(10);
        Label label = new Label();
        label.setText(Constants.choose_file_from_database_label);
        label.setStyle("-fx-font-size: 14pt");
        topMenu.setAlignment(Pos.CENTER);
        topMenu.getChildren().addAll(label);

        //load all file names from database and add them to list
        //String [] arr = {"Java", "JavaScript", "C#", "Python"};
        String[] arr = Database.getInstance().getConditionsNames().split("\n");

        ObservableList<String> langs = FXCollections.observableArrayList(arr);
        ListView<String> langsListView = new ListView<String>(langs);
        MultipleSelectionModel<String> langsSelectionModel = langsListView.getSelectionModel();
        langsSelectionModel.selectedItemProperty().addListener(new ChangeListener<String>() {

            public void changed(ObservableValue<? extends String> changed, String oldValue, String newValue) {
                query = newValue;
            }

        });

        Button yesButton = new Button(Constants.confirm_button_database);
        yesButton.setOnAction(e -> {
            window.close();
        });

        Button noButton = new Button(Constants.cancel_button);
        noButton.setOnAction(e -> {
            query = "";
            window.close();
        });

        HBox bottomMenu = new HBox(10);
        bottomMenu.getChildren().addAll(yesButton, noButton);
        bottomMenu.setAlignment(Pos.CENTER);

        BorderPane borderPane = new BorderPane();
        borderPane.getStylesheets().add("file:src/GUI/style.css");
        borderPane.setTop(topMenu);
        borderPane.setCenter(langsListView);
        borderPane.setBottom(bottomMenu);

        Scene scene = new Scene(borderPane, 300, 300);
        window.setScene(scene);
        window.setResizable(false);
        window.showAndWait();

        return query;
    }

}
