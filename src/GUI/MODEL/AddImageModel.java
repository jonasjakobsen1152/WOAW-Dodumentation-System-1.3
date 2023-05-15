package GUI.MODEL;

import BE.JobImage;
import BLL.AddImageManager;

import java.sql.SQLException;

public class AddImageModel {
    private static AddImageModel instance;
    public AddImageManager addImageManager;
    public TechnicianJobModel technicianJobModel;

    private AddImageModel() throws SQLException {
        addImageManager = new AddImageManager();
        technicianJobModel = TechnicianJobModel.getInstance();
    }

    public static AddImageModel getInstance() throws SQLException {
        if(instance == null){
            instance = new AddImageModel();
        }
        return instance;
    }

    public void addImage(JobImage jobImage) throws SQLException {
        addImageManager.addImage(jobImage);
        //Uses the showlist method from the technicianJobModel to show the new image in the technicianJobWindow
        technicianJobModel.showList();
    }
}
