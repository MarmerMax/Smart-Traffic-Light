package GUI;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ProgramGUI {

    private Scene scene1, scene2;
    private Stage window;

    public ProgramGUI(Stage stage){
        window = stage;
        createUI();
    }

    private void createUI(){
        createOptionsWindow();
        createSystemWindow();
    }

    private void createOptionsWindow(){
        Label label1 = new Label("Welcome to home page!!!");
        Button button1 = new Button("Start system!");
        button1.setOnAction(e -> window.setScene(scene2));

        VBox layout1 = new VBox(20);
        layout1.getChildren().addAll(label1, button1);
        scene1 = new Scene(layout1, 200, 200);
    }

    private void createSystemWindow(){
        Button button2 = new Button("Go home");
        button2.setOnAction(e -> window.setScene(scene1));

        String imgPath = "file:images/lights/red-s.png";
        Image img = new Image(imgPath);


        StackPane layout2 = new StackPane();
        layout2.getChildren().add(button2);
        layout2.getChildren().add(new ImageView(img));
        scene2 = new Scene(layout2, 1300, 660);
    }

    public Scene getScene(){
        return scene1;
    }

}
