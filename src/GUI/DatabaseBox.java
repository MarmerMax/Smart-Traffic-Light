package GUI;

import java.sql.PreparedStatement;

import Database.Database;
import Database.DatabaseConditions;
import Objects.Conditions.Conditions;
import Tools.ConsoleColors;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class DatabaseBox {

    public enum login_states {no_state, login_failed, login_succeeded}

    ;

    public static login_states login_state = login_states.no_state;
    private static String query = "";

    /**
     * The function allows the user to connect to his database.
     * <p>
     * Login details:
     * url - jdbc:mysql://hostname/databaseName.
     * user - Username to connect to the MySql system.
     * password -
     * <p>
     * Buttons:
     * Connect - This button performs the connection to the database according to the data entered.
     * If the connection fails the user is not moved to the next window until he enters correct data.
     * If the connection success the user moved to conditions list.
     * Create database -
     */
    public static void login() {
        Stage window = new Stage();
        window.setTitle(Constants.database_connect_window_label);

        HBox centerMenu = new HBox(10);
        centerMenu.getStyleClass().add("options-container");

        //login labels
        VBox boxLabel1 = new VBox(10);
        boxLabel1.getStyleClass().add("options-column");
        Label urlLabel = new Label(Constants.url_label);
        urlLabel.getStyleClass().add("label-direction");
        Label userLabel = new Label(Constants.user_label);
        userLabel.getStyleClass().add("label-direction");
        Label passwordLabel = new Label(Constants.password_label);
        passwordLabel.getStyleClass().add("label-direction");

        //login fields
        VBox boxLabel2 = new VBox(10);
        boxLabel2.getStyleClass().add("options-column");
        TextField urlField = new TextField();
        urlField.setText("jdbc:mysql://localhost:3306/stl");
        urlField.getStyleClass().add("label-direction");
        TextField userField = new TextField();
        userField.setText("root");
        userField.getStyleClass().add("label-direction");
        PasswordField passwordField = new PasswordField();
        passwordField.getStyleClass().add("label-direction");

        //Error log
        HBox top = new HBox();
        top.setAlignment(Pos.CENTER);
        top.getStyleClass().add("label-database");

        Label logLabel = new Label("");
        top.getChildren().add(logLabel);

        //Connect button
        Button buttonConnect = new Button(Constants.connect_button);
        buttonConnect.setMaxWidth(500);
        buttonConnect.getStyleClass().add("label-direction");
        buttonConnect.setAlignment(Pos.BOTTOM_LEFT);
        buttonConnect.setOnAction(e -> {
            Database database = Database.getInstance();
            if (database.connect(urlField.getText(), userField.getText(), passwordField.getText())) {
                login_state = login_states.login_succeeded;
                window.close();
            } else {
                logLabel.setText(Constants.connection_fail);
                login_state = login_states.login_failed;
            }
        });

        //Create database button
        Button buttonCreate = new Button(Constants.create_database_button);
        buttonCreate.setMaxWidth(500);
        buttonCreate.getStyleClass().add("label-direction");
        buttonCreate.setOnAction(e -> {
            try {
                Database database = Database.getInstance();
                if (database.createLocalDatabase(urlField.getText(), userField.getText(), passwordField.getText())) {
                    login_state = login_states.login_succeeded;
                    window.close();
                } else {
                    logLabel.setText(Constants.create_database_fail);
                    login_state = login_states.login_failed;
                }
            } catch (Exception ex) {
                System.err.println("ERROR: Failed to create database...");
            }
        });

        HBox bottomMenu = new HBox(10);
        bottomMenu.setPadding(new Insets(10));
        bottomMenu.setAlignment(Pos.CENTER);
        bottomMenu.getChildren().addAll(buttonCreate, buttonConnect);

        boxLabel1.getChildren().addAll(urlLabel, userLabel, passwordLabel);
        boxLabel2.getChildren().addAll(urlField, userField, passwordField);
        centerMenu.getChildren().addAll(boxLabel1, boxLabel2);

        BorderPane borderPane = new BorderPane();
        borderPane.getStylesheets().add("file:src/GUI/style.css");
        borderPane.setTop(top);
        borderPane.setCenter(centerMenu);
        borderPane.setBottom(bottomMenu);

        Scene scene = new Scene(borderPane, 550, 300);
        window.setScene(scene);
        window.setResizable(false);
        window.showAndWait();
    }


    //    public static DatabaseConditions display() {
    public static String display() {
        query = "";

        login();

        Stage window = new Stage();
        if (login_state == login_states.login_succeeded) {
            login_state = login_states.no_state;

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
        return null;
    }

}
