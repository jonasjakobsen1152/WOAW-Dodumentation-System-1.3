package GUI.Controller;

import javafx.event.ActionEvent;
import javafx.stage.FileChooser;

import java.io.File;

public class AddImageController {
    String filePath;
    public void handleFinishDocumentation(ActionEvent actionEvent) {
    }

    public void handleFinishImage(ActionEvent actionEvent) {

    }

    public void handleChoosePicture(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Documentation File");
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            this.filePath = selectedFile.getAbsolutePath();


        }
    }
}
