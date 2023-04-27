package BLL;

import BE.User;
import DAL.DB.AdminDAO_DB;
import DAL.IAdminDAO;

import java.sql.SQLException;
import java.util.ArrayList;

public class AdminManager {
private IAdminDAO adminDAO;
    public AdminManager(){adminDAO = new AdminDAO_DB();}

    public ArrayList<User> getAllUsers(){return adminDAO.getAllUsers();}

    public void deleteUser(User selectedUser) throws SQLException {
        adminDAO.deleteUser(selectedUser);
    }
}
