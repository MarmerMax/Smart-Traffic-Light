package GUI;

import CSV.CSVCondition;
import Database.Database;
import Database.DatabaseConditions;
import Tools.Constants;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
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

import java.io.File;
import java.util.ArrayList;

public class OpenBox {


    private static String file;

    public static String display() {

        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Choose CSV file");

        window.setOnCloseRequest(e -> {
            e.consume();
            window.close();
        });

        VBox topMenu = new VBox(20);
        Label label = new Label();
        label.setText(Constants.choose_file_from_database_label);
        label.setStyle("-fx-font-size: 14pt");
        topMenu.setAlignment(Pos.CENTER);
        topMenu.setPadding(new Insets(10));
        topMenu.getChildren().addAll(label);

        //load all file names from database and add them to list
        ArrayList<String> files = new ArrayList<>();
        getFilesName(files, "");


        HBox centerMenu = new HBox(20);
        centerMenu.setAlignment(Pos.CENTER);
        centerMenu.setPadding(new Insets(10));
        ObservableList<String> langs = FXCollections.observableArrayList(files);
        ListView<String> langsListView = new ListView<String>(langs);
        MultipleSelectionModel<String> langsSelectionModel = langsListView.getSelectionModel();
        langsSelectionModel.selectedItemProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> changed, String oldValue, String newValue) {
                file = newValue;
            }
        });
        centerMenu.getChildren().addAll(langsListView);


        Button openButton = new Button(Constants.confirm_button_database);
        openButton.setOnAction(e -> {
            window.close();
        });

        Button noButton = new Button(Constants.cancel_button);
        noButton.setOnAction(e -> {
            file = null;
            window.close();
        });

        HBox bottomMenu = new HBox(20);
        bottomMenu.getChildren().addAll(openButton, noButton);
        bottomMenu.setAlignment(Pos.CENTER);
        bottomMenu.setPadding(new Insets(10));

        BorderPane borderPane = new BorderPane();
        borderPane.getStylesheets().add("file:src/GUI/style.css");
        borderPane.setTop(topMenu);
        borderPane.setCenter(centerMenu);
        borderPane.setBottom(bottomMenu);

        Scene scene = new Scene(borderPane, 350, 400);
        window.setScene(scene);
        window.setResizable(false);
        window.showAndWait();

        return file;
    }

    private static void getFilesName(ArrayList<String> files, String path) {
        File folder = new File("CSVs" + path);
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                String name = listOfFiles[i].getName();
                String extension = getExtension(name);
                if (extension != null && extension.equals("csv")) {
                    files.add(path + "/" + listOfFiles[i].getName());
                }
            } else if (listOfFiles[i].isDirectory()) {
                String folder_name = listOfFiles[i].getName();
                getFilesName(files, path + "/" + folder_name);
            }
        }
    }

    private static String getExtension(String file_name) {
        try {
            String[] name_words = file_name.split("\\.");
            String extension = name_words[name_words.length - 1];
            return extension;
        } catch (Exception e) {

        }
        return null;
    }
}
