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

/**
 * This controller is used to control the AddImageWindow which is used to create a new image
 */
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

    /**
     * This method is called when the finish button is clicked.
     * The method uses the information that has been given to create a new image.
     */
    public void handleFinishImage(ActionEvent actionEvent) {
        int id = 1;
        String title = txtfImageTitle.getText();
        if(title.isEmpty() || data == null){
            alertUser("Please select a Image and write a title");
            return;
        }
        int jobId = technicianJobModel.getSelectedJob().getId();
        //If the checkbox is checked then set the image to private,if not set it to public
        if(checkBox.isSelected()){
            privacy = "private";
        }else {
            privacy = "public";
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

    /**
     * This method is called when the choose an image button is clicked.
     * The method uses the windows filechooser to select an image.
     * The image is then converted into a byte array.
     */
    public void handleChoosePicture(ActionEvent actionEvent) {
        //Creates a new instance of the Filechooser class, this is used to show the file chooser dialog window.
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Documentation File");
        //Adds a file extension filter which is used to show only specific file types
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images",
                "*.png", "*.jpg", "*.gif", "*.tif", "*.bmp"));
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            //Gets the absolute path of the file that was chosen and assigns it to the filepath string variable
            String filePath = selectedFile.getAbsolutePath();
            //Creates a new file using the filepath variable
            File file = new File(filePath);
            try {
                //Used to read the contents of the file
                FileInputStream fileInput = new FileInputStream(file);
                //Creates a byte array output stream object
                ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
                //Creates a byte array with the size of 1024 bytes
                byte[] buffer = new byte[1024];
                int bytesRead;
                //A while loop that reads data from the file into the byte array until the end of the file is reached
                while ((bytesRead = fileInput.read(buffer)) != -1) {
                    byteOutput.write(buffer, 0, bytesRead);
                }
                //Uses the content of the byte output and converts it into af byte array
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

    /**
     * Called when an error occurs and is used to show a alert window telling the user what is wrong
     */
    private void alertUser(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(error);
        alert.setHeaderText(error + "");
        alert.showAndWait();
    }
}
