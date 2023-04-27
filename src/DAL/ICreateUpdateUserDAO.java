package DAL;

public interface ICreateUpdateUserDAO {
    void createUser(String username, String password, String role);
}
