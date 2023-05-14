package GUI.Controller;

import BE.JobImage;
import GUI.MODEL.AddImageModel;
import GUI.MODEL.TechnicianJobModel;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.sql.SQLException;

public class AddImageController {
    public CheckBox checkBox;
    public TextField txtfImageTitle;
    public String filePath;
    TechnicianJobModel technicianJobModel;
    AddImageModel addImageModel;
    public byte[] data;
    public String privacy;

    public AddImageController() throws SQLException {
        technicianJobModel = TechnicianJobModel.getInstance();
        addImageModel = AddImageModel.getInstance();
    }

    public void handleFinishImage(ActionEvent actionEvent) {
        int id = 1;
        String title = txtfImageTitle.getText();
        int jobId = technicianJobModel.getSelectedJob().getId();
        if(checkBox.isSelected()){
            privacy = "private";
        }else {
            privacy = "pulbic";
        }
        try {
            JobImage jobImage = new JobImage(id, title, data, jobId, privacy);
            addImageModel.addImage(jobImage);
        }catch (SQLException e){
            e.printStackTrace();
            alertUser("Could not insert the image into the database");
        }
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void handleChoosePicture(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Documentation File");
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            String filePath = selectedFile.getAbsolutePath();
            File file = new File(filePath);
            try {
                FileInputStream fileInput = new FileInputStream(file);
                ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = fileInput.read(buffer)) != -1) {
                    byteOutput.write(buffer, 0, bytesRead);
                }
                data = byteOutput.toByteArray();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                alertUser("Could not find file");
            } catch (IOException e) {
                e.printStackTrace();
                alertUser("Something went wrong :(");
            }
        }
    }

    private void alertUser(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(error);
        alert.setHeaderText(error + "");
        alert.showAndWait();
    }
}
