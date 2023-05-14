package GUI.MODEL;

import BE.JobImage;
import BLL.AddImageManager;

import java.sql.SQLException;

public class AddImageModel {
    private static AddImageModel instance;
    public AddImageManager addImageManager;

    private AddImageModel() {
        addImageManager = new AddImageManager();
    }

    public static AddImageModel getInstance() throws SQLException {
        if(instance == null){
            instance = new AddImageModel();
        }
        return instance;
    }

    public void addImage(JobImage jobImage) throws SQLException {
        addImageManager.addImage(jobImage);
    }
}
