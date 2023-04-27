package DAL.DB;

import BE.User;
import DAL.ICreateUpdateUserDAO;

import java.sql.*;
import java.util.ArrayList;

public class CreateUpdateUserDAO_DB implements ICreateUpdateUserDAO {
    private MyDatabaseConnector databaseConnector;

    public CreateUpdateUserDAO_DB(){
        databaseConnector = new MyDatabaseConnector();
    }

    @Override
    public void createUser(String username, String password, String role) {
        try (Connection conn = databaseConnector.getConnection()){
            String sql = "INSERT INTO Users (Username, Password, Role) VALUES (?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1,username);
            stmt.setString(2,password);
            stmt.setString(3,role);

            stmt.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
