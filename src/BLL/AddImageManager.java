package BLL;

import BE.JobImage;
import DAL.DB.AddImageDAO_DB;
import DAL.IAddImageDAO;

import java.sql.SQLException;

public class AddImageManager {
    private IAddImageDAO addImageDAO;

    public AddImageManager() {
        addImageDAO = new AddImageDAO_DB();
    }

    public void addImage(JobImage jobImage) throws SQLException {
        addImageDAO.addImage(jobImage);
    }
}
