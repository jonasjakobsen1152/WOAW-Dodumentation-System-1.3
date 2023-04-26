package BLL;

import BE.User;
import DAL.DB.ProjectManagerDAO_DB;
import DAL.IProjectManagerDAO;

import java.util.ArrayList;

public class ProjectManagerManager {
    private IProjectManagerDAO projectManagerDAO;

    public ProjectManagerManager(){
        projectManagerDAO = new ProjectManagerDAO_DB();
    }

    public ArrayList<User> getAllUsers(){
        return projectManagerDAO.getAllUsers();
    }
}
