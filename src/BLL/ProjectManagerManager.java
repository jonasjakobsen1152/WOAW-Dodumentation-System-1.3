package BLL;

import BE.Job;
import BE.User;
import DAL.DB.ProjectManagerDAO_DB;
import DAL.IProjectManagerDAO;

import java.sql.SQLException;
import java.util.ArrayList;

public class ProjectManagerManager {
    private IProjectManagerDAO projectManagerDAO;

    public ProjectManagerManager(){
        projectManagerDAO = new ProjectManagerDAO_DB();
    }

    public ArrayList<User> getAllUsers(String role){
        return projectManagerDAO.getAllUsers(role);
    }

    public void deleteUser(User selectedUser) {
        projectManagerDAO.deleteUser(selectedUser);
    }

    public ArrayList<Job> getAllDocuments() {
        return projectManagerDAO.getAllDocuments();
    }
}
