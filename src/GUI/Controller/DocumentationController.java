package GUI.Controller;

import javafx.event.ActionEvent;
import javafx.stage.FileChooser;

import java.io.File;

public class DocumentationController {
    public void handleFinishDocumentation(ActionEvent actionEvent) {
    }

    public void handleFinishImage(ActionEvent actionEvent) {
    }

    public void handleChoosePicture(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Documentation File");
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            String filePath = selectedFile.getAbsolutePath();
            // Do something with the file path, e.g. save it or display it to the user
            System.out.println("Selected file path: " + filePath);
        }
    }
}
