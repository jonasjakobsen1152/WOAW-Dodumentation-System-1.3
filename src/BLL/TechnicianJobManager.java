package BLL;

import BE.Documentation;
import BE.JobImage;
import BE.Job;
import DAL.DB.TechnicianJobDAO_DB;
import DAL.ITechnicianJobDAO;

import java.sql.SQLException;
import java.util.List;

public class TechnicianJobManager {
    public ITechnicianJobDAO technicianJobDAO;
    public TechnicianJobManager(){
        technicianJobDAO = new TechnicianJobDAO_DB();
    }
    public List<Documentation> getDocumentation(Job selectedJob) throws SQLException {
        return technicianJobDAO.getDocumentation(selectedJob);
    }

    public void deleteDocumentation(Documentation selectedDocumentation) throws SQLException {
        technicianJobDAO.deleteDocumentation(selectedDocumentation);
    }

    public List<JobImage> getImages(Job selectedJob) throws SQLException {
        return technicianJobDAO.getImages(selectedJob);
    }
}
