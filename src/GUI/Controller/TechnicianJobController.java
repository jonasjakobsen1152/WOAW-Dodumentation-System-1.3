package GUI.Controller;

import BE.Documentation;
import BE.JobImage;
import GUI.MODEL.DocumentationModel;
import GUI.MODEL.TechnicianJobModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

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
    @FXML
    private Pane imagePane;

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
        tblImages.setOnMouseClicked(event ->{
            tblNotes.getSelectionModel().clearSelection();
            selectedJobImage = tblImages.getSelectionModel().getSelectedItem();
            byte[] imageData = selectedJobImage.getData();
            ByteArrayInputStream byteArray = new ByteArrayInputStream(imageData);
            Image image = new Image(byteArray);
            imgImage.setImage(image);
        });
    }

    public void handleAddDocumentation(ActionEvent actionEvent) {
        selectedDocumentation = tblNotes.getSelectionModel().getSelectedItem();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/DocumentationWindow.fxml"));
            AnchorPane pane = loader.load();

            DocumentationController documentationController = loader.getController();

            documentationController.setupCreate(selectedDocumentation);

            Stage dialogWindow = new Stage();
            Scene scene = new Scene(pane);
            dialogWindow.setScene(scene);
            dialogWindow.showAndWait();
        }catch (IOException e){
            e.printStackTrace();
            alertUser("Could not open the documentation window");
        }
    }
    public void showDocumentation(){
        clmNotes.setCellValueFactory(new PropertyValueFactory<Documentation,String>("title"));
        tblNotes.setItems(technicianJobModel.getDocumentationsToBeViewed());

        clmImages.setCellValueFactory(new PropertyValueFactory<JobImage, String>("title"));
        tblImages.setItems(technicianJobModel.getImagesToBeViewed());
    }

    public void handleUpdateDocumentation(ActionEvent actionEvent) {
        selectedDocumentation = tblNotes.getSelectionModel().getSelectedItem();
        if(selectedDocumentation == null){
            alertUser("Select a customer to update");
        }else {
            documentationModel.setSelectedDocumentation(selectedDocumentation);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/GUI/View/DocumentationWindow.fxml"));
            try{
                AnchorPane pane = loader.load();

                DocumentationController documentationController = loader.getController();
                documentationModel.getInstance();

                documentationController.setupUpdate(selectedDocumentation);

                Stage dialogWindow = new Stage();
                Scene scene = new Scene(pane);
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

    public void handleDeleteDocumentation(ActionEvent actionEvent) {
        selectedDocumentation = tblNotes.getSelectionModel().getSelectedItem();
        if(selectedDocumentation == null){
            alertUser("Select a customer to delete");
        }else {
            try {
                technicianJobModel.deleteDocumentation(selectedDocumentation);
            } catch (SQLException e) {
                e.printStackTrace();
                alertUser("Could not delete documentation");
            }
        }
        }

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
                }catch (SQLException e){
                    alertUser("Could not delete Image from the database");
                }
            }
        }
    }

    public void HandleAddImage(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/AddImageWindow.fxml"));
            AnchorPane pane = loader.load();


            Stage dialogWindow = new Stage();
            Scene scene = new Scene(pane);
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


}
