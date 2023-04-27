package BLL;

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
}
