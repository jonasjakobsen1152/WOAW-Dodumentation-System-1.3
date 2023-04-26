package DAL;

import BE.User;

import java.util.ArrayList;

public interface IProjectManagerDAO {
    public ArrayList<User> getAllUsers();
    public void deleteUser(User user);
}
