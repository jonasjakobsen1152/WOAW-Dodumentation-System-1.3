import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/GUI/VIEW/Login.fxml"));
            primaryStage.setScene(new Scene(root));
            primaryStage.getIcons().add(new Image("GUI/VIEW/Images/Screenshot_1.png"));
            primaryStage.show();
        }catch (IOException e){
            e.printStackTrace();
            System.out.println("Could not start the application");
        }
    }
}
