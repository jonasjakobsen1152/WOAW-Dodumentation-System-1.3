package GUI.Controller;

import BE.Documentation;
import BE.JobImage;
import GUI.MODEL.DocumentationModel;
import GUI.MODEL.TechnicianJobModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class is used to control the technician job window
 */
public class TechnicianJobController implements Initializable {
    public TableColumn clmImages;
    public TableView<JobImage> tblImages;
    public TableColumn clmNotes;
    public TableView<Documentation> tblNotes;
    public TechnicianJobModel technicianJobModel;
    public Documentation selectedDocumentation;
    public JobImage selectedJobImage;
    public DocumentationModel documentationModel;
    public ImageView imgImage;


    public TechnicianJobController() throws SQLException {
        technicianJobModel = TechnicianJobModel.getInstance();
        documentationModel = DocumentationModel.getInstance();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showDocumentation();
        tblNotes.setOnMouseClicked(event -> {
            selectedDocumentation = tblNotes.getSelectionModel().getSelectedItem();
            tblImages.getSelectionModel().clearSelection();
        });
        //Used to show the image that is clicked in the image table
        tblImages.setOnMouseClicked(event ->{
            tblNotes.getSelectionModel().clearSelection();
            selectedJobImage = tblImages.getSelectionModel().getSelectedItem();
            if(selectedJobImage == null){
                return;
            }
            //Gets the byte data from the selected image and sets it to the imageData variable
            byte[] imageData = selectedJobImage.getData();
            //Converts the byte data into a byte array
            ByteArrayInputStream byteArray = new ByteArrayInputStream(imageData);
            //Creates a new image based on the byte array
            Image image = new Image(byteArray);
            //Shows the created image in the image view
            imgImage.setImage(image);
        });
    }

    /**
     * Used to open the documentation window where the user can add notes for the job
     * @param actionEvent
     */
    public void handleAddDocumentation(ActionEvent actionEvent) {
        selectedDocumentation = tblNotes.getSelectionModel().getSelectedItem();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/DocumentationWindow.fxml"));
            AnchorPane pane = loader.load();

            DocumentationController documentationController = loader.getController();

            //Used to remove the update button in the documentation window
            documentationController.setupCreate(selectedDocumentation);

            Stage dialogWindow = new Stage();
            Scene scene = new Scene(pane);
            dialogWindow.initModality(Modality.WINDOW_MODAL);
            dialogWindow.initOwner((((Node)actionEvent.getSource()).getScene().getWindow()));
            dialogWindow.setScene(scene);
            dialogWindow.showAndWait();
        }catch (IOException e){
            e.printStackTrace();
            alertUser("Could not open the documentation window");
        }
    }

    /**
     * Used to set the items in the table views for notes and images.
     */
    public void showDocumentation(){
        //For notes
        clmNotes.setCellValueFactory(new PropertyValueFactory<Documentation,String>("title"));
        tblNotes.setItems(technicianJobModel.getDocumentationsToBeViewed());
        //For images
        clmImages.setCellValueFactory(new PropertyValueFactory<JobImage, String>("title"));
        tblImages.setItems(technicianJobModel.getImagesToBeViewed());
    }

    /**
     * Used to open the documentation window where the user can edit notes for the job
     * @param actionEvent
     */
    public void handleUpdateDocumentation(ActionEvent actionEvent) {
        selectedDocumentation = tblNotes.getSelectionModel().getSelectedItem();
        if(selectedDocumentation == null){
            alertUser("Select a documentation to update");
        }else {
            documentationModel.setSelectedDocumentation(selectedDocumentation);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/GUI/View/DocumentationWindow.fxml"));
            try{
                AnchorPane pane = loader.load();

                DocumentationController documentationController = loader.getController();
                documentationModel.getInstance();

                //Used to remove the create button in the documentation window
                documentationController.setupUpdate(selectedDocumentation);

                Stage dialogWindow = new Stage();
                Scene scene = new Scene(pane);
                dialogWindow.initModality(Modality.WINDOW_MODAL);
                dialogWindow.initOwner((((Node)actionEvent.getSource()).getScene().getWindow()));
                dialogWindow.setScene(scene);
                dialogWindow.show();

            }catch (SQLException e){
                e.printStackTrace();
                alertUser("Could not open update documentation window");
            }catch (IOException e){
                e.printStackTrace();
                alertUser("Could not open update documentation window");
            }
        }

    }

    /**
     * Deletes the chosen documentation
     * @param actionEvent
     */
    public void handleDeleteDocumentation(ActionEvent actionEvent) {
        selectedDocumentation = tblNotes.getSelectionModel().getSelectedItem();
        if(selectedDocumentation == null){
            alertUser("Select a documentation to delete");
        }else {
            try {
                technicianJobModel.deleteDocumentation(selectedDocumentation);
            } catch (SQLException e) {
                e.printStackTrace();
                alertUser("Could not delete documentation");
            }
        }
        }

    /**
     * Deletes the chosen image
     * @param actionEvent
     */
    public void handleDeleteImage(ActionEvent actionEvent) {
        selectedJobImage = tblImages.getSelectionModel().getSelectedItem();
        if(selectedJobImage == null){
            alertUser("Please choose an image to delete");
        }else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning");
            alert.setHeaderText("Are you sure you want to delete: " + selectedJobImage.getTitle().concat("?"));
            Optional<ButtonType> action = alert.showAndWait();
            if (action.get() == ButtonType.OK) {
                try {
                    technicianJobModel.deleteImage(selectedJobImage);
                    imgImage.setImage(null);
                }catch (SQLException e){
                    alertUser("Could not delete Image from the database");
                }
            }
        }
    }

    /**
     * Used to open the add image window where the user can add new images to the application.
     * @param actionEvent
     */
    public void HandleAddImage(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/AddImageWindow.fxml"));
            AnchorPane pane = loader.load();


            Stage dialogWindow = new Stage();
            Scene scene = new Scene(pane);
            dialogWindow.initModality(Modality.WINDOW_MODAL);
            dialogWindow.initOwner((((Node)actionEvent.getSource()).getScene().getWindow()));
            dialogWindow.setScene(scene);
            dialogWindow.showAndWait();
        }catch (IOException e){
            e.printStackTrace();
            alertUser("Could not open the image window");
        }
    }

    private void alertUser(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(error);
        alert.setHeaderText(error + "");
        alert.showAndWait();
    }

    /**
     * Used to open the drawing window where the user can draw images and save them.
     * @param actionEvent
     */
    public void handleOpenDrawing(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/DrawingWindow.fxml"));
            AnchorPane pane = loader.load();


            Stage dialogWindow = new Stage();
            Scene scene = new Scene(pane);
            dialogWindow.initModality(Modality.WINDOW_MODAL);
            dialogWindow.initOwner((((Node)actionEvent.getSource()).getScene().getWindow()));
            dialogWindow.setScene(scene);
            dialogWindow.showAndWait();
        }catch (IOException e){
            e.printStackTrace();
            alertUser("Could not open the drawing window");
        }
    }
}
