package GUI.MODEL;

import BE.Documentation;
import BE.JobImage;
import BE.Job;
import BLL.TechnicianJobManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.SQLException;

public class TechnicianJobModel {
    private static TechnicianJobModel instance;
    public ObservableList<Documentation> documentationsToBeViewed;
    public ObservableList<JobImage> imagesToBeViewed;
    public TechnicianJobManager technicianJobManager;
    public LoginModel loginModel;
    public Job selectedJob;
    public TechnicianJobModel() {
        documentationsToBeViewed = FXCollections.observableArrayList();
        imagesToBeViewed = FXCollections.observableArrayList();
        loginModel = LoginModel.getInstance();
        technicianJobManager = new TechnicianJobManager();

        
    }

    public static TechnicianJobModel getInstance() throws SQLException {
        if(instance == null){
            instance = new TechnicianJobModel();
        }
        return instance;
    }

    public void showList()  {
        try {
            documentationsToBeViewed.clear();
            documentationsToBeViewed.addAll(technicianJobManager.getDocumentation(getSelectedJob()));

            imagesToBeViewed.clear();
            imagesToBeViewed.addAll(technicianJobManager.getImages(getSelectedJob()));
        }catch (SQLException e){
            e.printStackTrace();
            alertUser("Could not the get the lists");
        }
    }
    public void setSelectedJob(Job selectedDocument) {
        this.selectedJob = selectedDocument;
    }

    private void alertUser(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(error);
        alert.setHeaderText(error + "");
        alert.showAndWait();
    }

    public Job getSelectedJob(){
        return selectedJob;
    }
    public ObservableList<Documentation> getDocumentationsToBeViewed(){return documentationsToBeViewed;}

    public void deleteDocumentation(Documentation selectedDocumentation) throws SQLException {
        technicianJobManager.deleteDocumentation(selectedDocumentation);
        showList();
    }

    public ObservableList<JobImage> getImagesToBeViewed() {
        return imagesToBeViewed;
    }

    public void deleteImage(JobImage selectedJobImage) throws SQLException {
        technicianJobManager.deleteImage(selectedJobImage);
        showList();
    }
}
