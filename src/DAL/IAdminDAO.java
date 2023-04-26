package DAL;

import BE.User;

import java.util.ArrayList;

public interface IAdminDAO {
    public void deleteUser(User user);
    public ArrayList<User> getAllUsers();


}
