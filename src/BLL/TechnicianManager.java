package BLL;

import BE.Job;
import BE.User;
import DAL.DB.TechnicianDAO_DB;
import DAL.ITechnicianDAO;

import java.util.List;

public class TechnicianManager {
    private ITechnicianDAO technicianDAO;
    public TechnicianManager(){
        technicianDAO = new TechnicianDAO_DB();
    }
    public List<Job> getWork(User selectedUser) {
        return technicianDAO.getWork(selectedUser);
    }
}
