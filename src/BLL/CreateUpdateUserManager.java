package BLL;

import BE.User;
import DAL.DB.CreateUpdateUserDAO_DB;
import DAL.ICreateUpdateUserDAO;

public class CreateUpdateUserManager {
    private ICreateUpdateUserDAO createUpdateUserDAO;
    public CreateUpdateUserManager(){
        createUpdateUserDAO = new CreateUpdateUserDAO_DB();
    }
    public void createUser(String username, String password, String role) {
         createUpdateUserDAO.createUser(username,password,role);
    }

    public void updateUser(User user) {
        createUpdateUserDAO.updateUser(user);
    }
}
